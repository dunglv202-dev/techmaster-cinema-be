package dev.dunglv202.techmaster.entity;

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
public class Movie extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String thumbnail;

    private String trailer;

    private int durationInMinutes;

    private String description;

    private String director;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> actors;

    private LocalDateTime premiereDate;

    private LocalDateTime finalScreening;

    @ManyToMany
    private List<Category> categories;
}
