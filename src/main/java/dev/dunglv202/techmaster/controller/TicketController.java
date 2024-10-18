package dev.dunglv202.techmaster.controller;

import dev.dunglv202.techmaster.dto.req.BookingRequest;
import dev.dunglv202.techmaster.dto.resp.BookingDTO;
import dev.dunglv202.techmaster.model.Pagination;
import dev.dunglv202.techmaster.model.ResultPage;
import dev.dunglv202.techmaster.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/book")
    @PreAuthorize("hasRole('USER')")
    public void bookTickets(@Valid @RequestBody BookingRequest booking) {
        ticketService.bookTickets(booking);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResultPage<BookingDTO> getAllBookings(Pagination pagination) {
        return ticketService.getAllBookings(pagination);
    }
}
