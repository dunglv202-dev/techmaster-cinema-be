package dev.dunglv202.techmaster.entity;

import dev.dunglv202.techmaster.model.Seat;
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

    @JdbcTypeCode(SqlTypes.JSON)
    private List<List<Seat>> seats;
}
