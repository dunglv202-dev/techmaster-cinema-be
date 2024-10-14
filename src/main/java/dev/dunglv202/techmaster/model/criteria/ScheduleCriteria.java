package dev.dunglv202.techmaster.model.criteria;

import dev.dunglv202.techmaster.entity.*;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@Setter
@Accessors(chain = true, fluent = true)
public class ScheduleCriteria {
    private Movie movie;
    private LocalDate date;
    private Location location;

    public Specification<Schedule> build() {
        return ofMovie(movie).and(onDate(date)).and(atLocation(location));
    }

    private Specification<Schedule> ofMovie(Movie movie) {
        return (root, query, cb) -> cb.equal(root.get(Schedule_.movie), movie);
    }

    private Specification<Schedule> onDate(LocalDate date) {
        return (root, query, cb) -> cb.equal(root.get(Schedule_.start).as(LocalDate.class), date);
    }

    private Specification<Schedule> atLocation(Location location) {
        return (root, query, cb) -> cb.equal(root.get(Schedule_.cinema).get(Cinema_.location), location);
    }
}
