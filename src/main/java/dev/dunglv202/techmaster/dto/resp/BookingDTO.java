package dev.dunglv202.techmaster.dto.resp;

import dev.dunglv202.techmaster.constant.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BookingDTO {
    private long id;
    private String cinema;
    private String room;
    private MovieDTO movie;
    private LocalDateTime start;
    private List<String> seats;
    private BookingStatus status;
    private double grandTotal;
    private LocalDateTime paymentDeadline;

    /**
     * Because cron/scheduler might have not update booking status after its expiration => intercept to get actual status
     */
    public BookingStatus getStatus() {
        return status == BookingStatus.PENDING_PAYMENT && paymentDeadline.isBefore(LocalDateTime.now())
            ? BookingStatus.CANCELED
            : status;
    }
}
