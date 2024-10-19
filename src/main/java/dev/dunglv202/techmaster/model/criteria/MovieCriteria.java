package dev.dunglv202.techmaster.model.criteria;

import dev.dunglv202.techmaster.entity.Category;
import dev.dunglv202.techmaster.entity.Movie;
import dev.dunglv202.techmaster.entity.Movie_;
import jakarta.annotation.Nullable;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@Setter
@Accessors(chain = true, fluent = true)
public class MovieCriteria {
    private Boolean nowShowing;
    private Boolean comingSoon;
    private Category category;

    public Specification<Movie> build() {
        return isNowShowing(nowShowing).and(isComingSoon(comingSoon)).and(inCategory(category));
    }

    private Specification<Movie> isNowShowing(@Nullable Boolean showing) {
        if (showing == null) return Specification.where(null);
        return (root, query, cb) -> cb.and(
            cb.greaterThanOrEqualTo(root.get(Movie_.finalScreening), LocalDateTime.now()),
            cb.lessThanOrEqualTo(root.get(Movie_.premiereDate), LocalDateTime.now())
        );
    }

    private Specification<Movie> isComingSoon(@Nullable Boolean comingSoon) {
        if (comingSoon == null) return Specification.where(null);
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Movie_.premiereDate), LocalDateTime.now());
    }

    private Specification<Movie> inCategory(@Nullable Category category) {
        if (category == null) return Specification.where(null);
        return (root, query, cb) -> cb.isMember(category, root.get(Movie_.categories));
    }
}
