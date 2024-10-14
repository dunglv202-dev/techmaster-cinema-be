package dev.dunglv202.techmaster.repository;

import dev.dunglv202.techmaster.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
