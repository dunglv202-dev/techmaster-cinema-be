package dev.dunglv202.techmaster.controller;

import dev.dunglv202.techmaster.dto.req.BookingDTO;
import dev.dunglv202.techmaster.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/book")
    public void placeTickets(@Valid @RequestBody BookingDTO bookingDTO) {
        ticketService.placeTickets(bookingDTO);
    }
}
