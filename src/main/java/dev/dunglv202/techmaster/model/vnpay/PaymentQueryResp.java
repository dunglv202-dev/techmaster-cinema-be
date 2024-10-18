package dev.dunglv202.techmaster.model.vnpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentQueryResp {
    @JsonProperty("vnp_TransactionStatus")
    private String status;

    @JsonProperty("vnp_Amount")
    private BigDecimal amount;
}
