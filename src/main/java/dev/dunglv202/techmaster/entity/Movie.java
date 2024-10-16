package dev.dunglv202.techmaster.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @ElementCollection
    private List<String> actors;

    @ManyToMany
    private List<Category> categories;
}
