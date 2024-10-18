package dev.dunglv202.techmaster.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class PaymentInfo {
    private LocalDateTime timestamp;
    private String reference;
    private BigDecimal amount;
    private String description;
    private LocalDateTime expiration;
    private String ipAddress;
}
