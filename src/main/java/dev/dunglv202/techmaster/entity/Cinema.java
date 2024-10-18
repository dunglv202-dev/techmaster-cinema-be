package dev.dunglv202.techmaster.entity;

import dev.dunglv202.techmaster.model.Seat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Cinema extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Location location;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<List<Seat>> seats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(id, cinema.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
