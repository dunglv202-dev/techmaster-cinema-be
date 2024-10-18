package dev.dunglv202.techmaster.repository;

import dev.dunglv202.techmaster.entity.Booking;
import dev.dunglv202.techmaster.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule> {
    /**
     * Find all pending booking (which is in PENDING status and not expired) at {@code time}
     */
    @Query("""
        FROM Booking b
        WHERE b.schedule = :schedule
        AND b.status = dev.dunglv202.techmaster.constant.BookingStatus.PENDING_PAYMENT
        AND b.paymentDeadline >= :time
    """)
    List<Booking> findAllPendingAtTimeBySchedule(@Param("schedule") Schedule schedule, @Param("time") LocalDateTime time);
}
