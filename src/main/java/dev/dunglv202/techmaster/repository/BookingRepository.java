package dev.dunglv202.techmaster.repository;

import dev.dunglv202.techmaster.entity.Booking;
import dev.dunglv202.techmaster.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByUser(User user, Pageable pageable);
}
