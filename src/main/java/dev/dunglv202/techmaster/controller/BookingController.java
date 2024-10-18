package dev.dunglv202.techmaster.controller;

import dev.dunglv202.techmaster.dto.req.BookingRequest;
import dev.dunglv202.techmaster.dto.resp.BookingDTO;
import dev.dunglv202.techmaster.model.Pagination;
import dev.dunglv202.techmaster.model.ResultPage;
import dev.dunglv202.techmaster.service.BookingService;
import dev.dunglv202.techmaster.util.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public void bookTickets(@Valid @RequestBody BookingRequest booking) {
        bookingService.bookTickets(booking);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResultPage<BookingDTO> getAllBookings(Pagination pagination) {
        return bookingService.getAllBookings(pagination);
    }

    @GetMapping("/{id}/pay")
    @PreAuthorize("hasRole('USER')")
    public void payTickets(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String paymentLink = bookingService.buildPaymentUrl(id, HttpUtils.extractIpAddress(request));
        response.sendRedirect(paymentLink);
    }
}
