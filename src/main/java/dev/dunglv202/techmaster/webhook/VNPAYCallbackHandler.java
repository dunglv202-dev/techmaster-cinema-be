package dev.dunglv202.techmaster.webhook;

import dev.dunglv202.techmaster.service.impl.VNPAYPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/vn-pay/callback")
@RequiredArgsConstructor
public class VNPAYCallbackHandler {
    private final VNPAYPaymentService paymentService;

    @GetMapping
    public void handleCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        paymentService.handleCallback(request, response);
    }
}
