package dev.dunglv202.techmaster.service;

import dev.dunglv202.techmaster.dto.req.BookingDTO;

public interface TicketService {
    void placeTickets(BookingDTO bookingDTO);
}
