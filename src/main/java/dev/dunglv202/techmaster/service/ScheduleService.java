package dev.dunglv202.techmaster.service;

import dev.dunglv202.techmaster.dto.req.ScheduleFilter;
import dev.dunglv202.techmaster.dto.resp.CinemaSchedule;

import java.util.List;

public interface ScheduleService {
    List<CinemaSchedule> getAllSchedules(long movieId, ScheduleFilter filter);
}
