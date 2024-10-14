package dev.dunglv202.techmaster.dto.req;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleFilter {
    /**
     * Location code
     */
    @NotBlank
    private String location;

    @NotNull
    @FutureOrPresent
    private LocalDate date;
}
