package dev.dunglv202.techmaster.entity;

import dev.dunglv202.techmaster.constant.BookingStatus;
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
}
