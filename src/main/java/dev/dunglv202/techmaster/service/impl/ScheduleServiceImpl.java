package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.dto.req.ScheduleFilter;
import dev.dunglv202.techmaster.dto.resp.CinemaSchedule;
import dev.dunglv202.techmaster.dto.resp.ScheduleDetailsDTO;
import dev.dunglv202.techmaster.entity.Cinema;
import dev.dunglv202.techmaster.entity.Location;
import dev.dunglv202.techmaster.entity.Movie;
import dev.dunglv202.techmaster.entity.Schedule;
import dev.dunglv202.techmaster.exception.ClientVisibleException;
import dev.dunglv202.techmaster.mapper.ScheduleMapper;
import dev.dunglv202.techmaster.model.criteria.ScheduleCriteria;
import dev.dunglv202.techmaster.repository.LocationRepository;
import dev.dunglv202.techmaster.repository.MovieRepository;
import dev.dunglv202.techmaster.repository.ScheduleRepository;
import dev.dunglv202.techmaster.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final MovieRepository movieRepository;
    private final LocationRepository locationRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public List<CinemaSchedule> getAllSchedules(long movieId, ScheduleFilter filter) {
        Movie movie = movieRepository.getReferenceById(movieId);
        Location location = locationRepository.findByCode(filter.getLocation());
        ScheduleCriteria criteria = new ScheduleCriteria()
            .movie(movie)
            .date(filter.getDate())
            .location(location);
        List<Schedule> schedules = scheduleRepository.findAll(criteria.build());

        // Group by cinema
        Map<Cinema, List<Schedule>> cinemaSchedules = new HashMap<>();
        schedules.forEach(schedule -> {
            cinemaSchedules.putIfAbsent(schedule.getCinema(), new ArrayList<>());
            cinemaSchedules.get(schedule.getCinema()).add(schedule);
        });

        return cinemaSchedules.entrySet().stream()
            .map(ScheduleMapper.INSTANCE::toCinemaSchedule)
            .toList();
    }

    @Override
    public ScheduleDetailsDTO getScheduleDetails(long id) {
        return scheduleRepository.findById(id).map(ScheduleMapper.INSTANCE::toScheduleDetailDTO)
            .orElseThrow(() -> new ClientVisibleException("{schedule.invalid}"));
    }
}
