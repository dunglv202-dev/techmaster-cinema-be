package dev.dunglv202.techmaster.controller;

import dev.dunglv202.techmaster.dto.req.ScheduleFilter;
import dev.dunglv202.techmaster.dto.resp.CinemaSchedule;
import dev.dunglv202.techmaster.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies/{movieId}/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private ScheduleService scheduleService;

    @GetMapping
    public List<CinemaSchedule> getAllSchedules(@PathVariable long movieId, ScheduleFilter filter) {
        return scheduleService.getAllSchedules(movieId, filter);
    }
}
