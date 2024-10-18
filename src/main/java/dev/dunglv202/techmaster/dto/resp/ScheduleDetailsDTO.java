package dev.dunglv202.techmaster.dto.resp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ScheduleDetailsDTO {
    private String cinema;
    private String room;
    private MovieDTO movie;
    private List<List<SeatDTO>> seats;
    private LocalDateTime start;
    private LocalDateTime end;
}
