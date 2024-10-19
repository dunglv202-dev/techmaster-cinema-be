package dev.dunglv202.techmaster.service;

import dev.dunglv202.techmaster.dto.req.BookingRequest;
import dev.dunglv202.techmaster.dto.resp.BookingDTO;
import dev.dunglv202.techmaster.model.Pagination;
import dev.dunglv202.techmaster.model.ResultPage;

public interface BookingService {
    void bookTickets(BookingRequest booking);

    ResultPage<BookingDTO> getAllBookings(Pagination pagination);

    String buildPaymentUrl(long bookingId, String ipAddress);

    void cancelBooking(long bookingId);
}
