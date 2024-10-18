package dev.dunglv202.techmaster.dto.req;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookingDTO {
    private long scheduleId;

    @Size(min = 1)
    private Set<String> seats;
}
