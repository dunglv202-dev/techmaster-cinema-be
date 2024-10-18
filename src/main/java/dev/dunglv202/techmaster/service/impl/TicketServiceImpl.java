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
import dev.dunglv202.techmaster.model.ResultPage;
import dev.dunglv202.techmaster.model.Seat;
import dev.dunglv202.techmaster.repository.BookingRepository;
import dev.dunglv202.techmaster.repository.ScheduleRepository;
import dev.dunglv202.techmaster.service.TicketService;
import dev.dunglv202.techmaster.util.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final BookingRepository bookingRepository;
    private final ScheduleRepository scheduleRepository;
    private final AuthHelper authHelper;

    @Override
    @Transactional
    public void bookTickets(BookingRequest booking) {
        Schedule schedule = scheduleRepository.findById(booking.getScheduleId())
            .orElseThrow(() -> new ClientVisibleException("{schedule.invalid}"));

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
}
