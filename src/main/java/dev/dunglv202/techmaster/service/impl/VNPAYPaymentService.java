package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.constant.BookingStatus;
import dev.dunglv202.techmaster.entity.Booking;
import dev.dunglv202.techmaster.exception.ClientVisibleException;
import dev.dunglv202.techmaster.model.PaymentInfo;
import dev.dunglv202.techmaster.model.prop.VNPAYProperties;
import dev.dunglv202.techmaster.repository.BookingRepository;
import dev.dunglv202.techmaster.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VNPAYPaymentService implements PaymentService {
    private static final String STATUS_SUCCESS = "00";
    private static final String COMMAND_PAY = "pay";
    private static final String ORDER_TYPE = "other";
    private static final String DEFAULT_LOCALE = "vn";
    private static final String DEFAULT_CURR_CODE = "VND";
    private static final DateTimeFormatter DATE_TIME_FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final VNPAYProperties vnpayProperties;
    private final BookingRepository bookingRepository;

    @Override
    public String buildPaymentUrl(PaymentInfo paymentInfo) {
        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", vnpayProperties.getVersion());
        params.put("vnp_Command", COMMAND_PAY);
        params.put("vnp_TmnCode", vnpayProperties.getCode());
        params.put("vnp_Locale", DEFAULT_LOCALE);
        params.put("vnp_CurrCode", DEFAULT_CURR_CODE);
        params.put("vnp_TxnRef", paymentInfo.getReference());
        params.put("vnp_OrderInfo", paymentInfo.getDescription());
        params.put("vnp_OrderType", ORDER_TYPE);
        params.put("vnp_Amount", paymentInfo.getAmount().multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.DOWN).toString());
        params.put("vnp_ReturnUrl", vnpayProperties.getCallback());
        params.put("vnp_IpAddr", paymentInfo.getIpAddress());
        params.put("vnp_CreateDate", DATE_TIME_FMT.format(paymentInfo.getTimestamp()));
        params.put("vnp_ExpireDate", DATE_TIME_FMT.format(paymentInfo.getExpiration()));
        params.put("vnp_SecureHash", makeSecureHash(params));
        return vnpayProperties.getUrl() + "?" + buildQueryString(params);
    }

    @Override
    @Transactional
    public void handleCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Check if successful
        if (!STATUS_SUCCESS.equals(request.getParameter("vnp_TransactionStatus"))) {
            throw new ClientVisibleException("{payment.failed}");
        }

        // Verify payment with checksum
        Map<String, String> params = new TreeMap<>();
        request.getParameterNames().asIterator().forEachRemaining(key -> {
            if (key.equals("vnp_SecureHash")) return;
            params.put(key, request.getParameter(key));
        });
        String checksum = makeSecureHash(params);
        if (!checksum.equals(request.getParameter("vnp_SecureHash"))) {
            throw new ClientVisibleException("{payment.invalid}");
        }

        // Check if payment is enough for booking
        Booking booking = bookingRepository.findById(Long.parseLong(request.getParameter("vnp_TxnRef").split("-")[0]))
            .orElseThrow(() -> new ClientVisibleException("{payment.invalid}"));
        double amount = Double.parseDouble(request.getParameter("vnp_Amount")) / 100;
        if (amount < booking.getGrandTotal()) {
            throw new ClientAbortException("{payment.insufficient}");
        }

        // Mark as paid
        booking.setStatus(BookingStatus.PAID);
        bookingRepository.save(booking);
        response.sendRedirect("/me/tickets");
    }

    /**
     * @param params Map of params SORTED in ascending order of key
     */
    private String makeSecureHash(Map<String, String> params) {
        String rawQuery = params.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII))
            .collect(Collectors.joining("&"));
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, vnpayProperties.getSecret()).hmacHex(rawQuery);
    }

    private String buildQueryString(Map<String, String> params) {
        Charset charset = StandardCharsets.US_ASCII;
        return params.entrySet().stream()
            .map(entry -> URLEncoder.encode(entry.getKey(), charset) + "=" + URLEncoder.encode(entry.getValue(), charset))
            .collect(Collectors.joining("&"));
    }
}
