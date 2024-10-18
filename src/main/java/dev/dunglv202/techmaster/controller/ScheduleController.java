package dev.dunglv202.techmaster.controller;

import dev.dunglv202.techmaster.dto.resp.ScheduleDetailsDTO;
import dev.dunglv202.techmaster.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/{id}")
    public ScheduleDetailsDTO getScheduleDetails(@PathVariable long id) {
        return scheduleService.getScheduleDetails(id);
    }
}
