package dev.dunglv202.techmaster.entity;

import dev.dunglv202.techmaster.constant.BookingStatus;
import dev.dunglv202.techmaster.constant.GeneralConfig;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Booking extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Schedule schedule;

    @ElementCollection
    private List<String> seats;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private double grandTotal;

    private String paymentRef;

    private LocalDateTime paymentDeadline;

    @PrePersist
    public void prePersist() {
        if (timestamp == null) timestamp = LocalDateTime.now();
        grandTotal = seats.stream()
            .map(schedule::getSeat).map(schedule::getPrice)
            .reduce(Double::sum).orElse(0.0);
        paymentDeadline = timestamp.plus(GeneralConfig.PAYMENT_DEADLINE);
    }

    public boolean isPayable() {
        return status == BookingStatus.PENDING_PAYMENT && LocalDateTime.now().isBefore(paymentDeadline);
    }
}
