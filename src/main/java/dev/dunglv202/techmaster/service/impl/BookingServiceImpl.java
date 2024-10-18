package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.constant.BookingStatus;
import dev.dunglv202.techmaster.dto.req.BookingRequest;
import dev.dunglv202.techmaster.dto.resp.BookingDTO;
import dev.dunglv202.techmaster.entity.Booking;
import dev.dunglv202.techmaster.entity.Booking_;
import dev.dunglv202.techmaster.entity.Schedule;
import dev.dunglv202.techmaster.entity.User;
import dev.dunglv202.techmaster.exception.ClientVisibleException;
import dev.dunglv202.techmaster.mapper.BookingMapper;
import dev.dunglv202.techmaster.model.Pagination;
import dev.dunglv202.techmaster.model.PaymentInfo;
import dev.dunglv202.techmaster.model.ResultPage;
import dev.dunglv202.techmaster.model.Seat;
import dev.dunglv202.techmaster.repository.BookingRepository;
import dev.dunglv202.techmaster.repository.ScheduleRepository;
import dev.dunglv202.techmaster.service.BookingService;
import dev.dunglv202.techmaster.service.PaymentService;
import dev.dunglv202.techmaster.util.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ScheduleRepository scheduleRepository;
    private final AuthHelper authHelper;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public void bookTickets(BookingRequest booking) {
        Schedule schedule = scheduleRepository.findById(booking.getScheduleId())
            .orElseThrow(() -> new ClientVisibleException("{schedule.invalid}"));

        // Check if able to book ticket
        if (!schedule.getStart().isAfter(LocalDateTime.now())) {
            throw new ClientVisibleException("{schedule.invalid}");
        }

        // Check if any sheet unavailable
        List<String> unavailableSeats = new ArrayList<>();
        booking.getSeats().forEach(pos -> {
            Seat seat = schedule.getSeat(pos);
            if (seat.isUnavailable()) unavailableSeats.add(pos);
        });
        if (!unavailableSeats.isEmpty()) {
            throw new ClientVisibleException("{seat.unavailable}: " + String.join(", ", unavailableSeats));
        }

        // Do booking
        Booking newBooking = BookingMapper.INSTANCE.toBooking(booking);
        newBooking.setSchedule(schedule);
        newBooking.setUser(authHelper.getSignedUser());
        newBooking.setStatus(BookingStatus.PENDING_PAYMENT);
        newBooking = bookingRepository.save(newBooking);

        // Update payment
        newBooking.setPaymentRef(generatePaymentRef(newBooking));
        bookingRepository.save(newBooking);
    }

    @Override
    public ResultPage<BookingDTO> getAllBookings(Pagination pagination) {
        User user = authHelper.getSignedUser();
        Sort latestFirst = Sort.by(Sort.Direction.DESC, Booking_.TIMESTAMP);
        var page = bookingRepository.findAllByUser(user, pagination.toPageable().withSort(latestFirst))
            .map(BookingMapper.INSTANCE::toBookingDTO);
        return new ResultPage<>(page);
    }

    @Override
    public String buildPaymentUrl(long bookingId, String ipAddress) {
        User signedUser = authHelper.getSignedUser();
        Booking booking = bookingRepository.findById(bookingId)
            .filter(b -> b.getUser().equals(signedUser))
            .orElseThrow(() -> new ClientVisibleException("{booking.invalid}"));

        if (!booking.isPayable()) {
            throw new ClientVisibleException("{booking.not_payable}");
        }

        PaymentInfo paymentInfo = new PaymentInfo()
            .setReference(booking.getPaymentRef())
            .setAmount(BigDecimal.valueOf(booking.getGrandTotal()))
            .setDescription("Thanh toan ve xem phim")
            .setTimestamp(booking.getTimestamp())
            .setExpiration(booking.getPaymentDeadline())
            .setIpAddress(ipAddress);

        return paymentService.buildPaymentUrl(paymentInfo);
    }

    private String generatePaymentRef(Booking booking) {
        Random random = new Random();
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characterSet.length());
            code.append(characterSet.charAt(index));
        }
        return booking.getId().toString() + "-" + code;
    }
}
