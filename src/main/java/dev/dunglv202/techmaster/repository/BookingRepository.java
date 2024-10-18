package dev.dunglv202.techmaster.repository;

import dev.dunglv202.techmaster.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
