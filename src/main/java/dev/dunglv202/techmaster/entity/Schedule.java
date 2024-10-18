package dev.dunglv202.techmaster.entity;

import dev.dunglv202.techmaster.model.Seat;
import dev.dunglv202.techmaster.model.SeatPosition;
import dev.dunglv202.techmaster.util.SeatConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Schedule extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Cinema cinema;

    @ManyToOne
    private Room room;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<List<Seat>> seats;

    @Embedded
    private Prices prices;

    public Seat getSeat(String seat) {
        SeatPosition position = SeatConverter.parse(seat);
        return seats.get(position.getRow()).get(position.getColumn());
    }

    public double getPrice(Seat seat) {
        return switch (seat.getType()) {
            case STANDARD -> prices.getNormalPrice();
            case VIP -> prices.getVipPrice();
        };
    }

    public void takeSeats(List<String> seats) {
        seats.stream().map(this::getSeat).forEach(seat -> seat.setStatus(Seat.Status.OCCUPIED));
    }
}
