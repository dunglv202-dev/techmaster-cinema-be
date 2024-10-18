package dev.dunglv202.techmaster.service;

import dev.dunglv202.techmaster.model.PaymentInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface PaymentService {
    String buildPaymentUrl(PaymentInfo paymentInfo);
    void handleCallback(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
