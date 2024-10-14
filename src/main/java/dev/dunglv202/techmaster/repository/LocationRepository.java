package dev.dunglv202.techmaster.repository;

import dev.dunglv202.techmaster.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location findByCode(String location);
}
