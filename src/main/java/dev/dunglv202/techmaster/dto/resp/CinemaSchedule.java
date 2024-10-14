package dev.dunglv202.techmaster.dto.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CinemaSchedule {
    /**
     * Name of cinema
     */
    private String name;

    private List<ScheduleDTO> schedules;
}
