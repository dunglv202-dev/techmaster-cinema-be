package dev.dunglv202.techmaster.dto.resp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleDTO {
    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
}
