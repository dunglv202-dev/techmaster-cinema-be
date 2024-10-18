package dev.dunglv202.techmaster.controller;

import dev.dunglv202.techmaster.dto.req.ScheduleFilter;
import dev.dunglv202.techmaster.dto.resp.CinemaSchedule;
import dev.dunglv202.techmaster.dto.resp.DetailMovieDTO;
import dev.dunglv202.techmaster.dto.resp.MovieDTO;
import dev.dunglv202.techmaster.service.MovieService;
import dev.dunglv202.techmaster.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final ScheduleService scheduleService;

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public DetailMovieDTO getDetailMovie(@PathVariable long id) {
        return movieService.getDetailMovie(id);
    }

    @GetMapping("/{movieId}/schedules")
    public List<CinemaSchedule> getAllSchedules(@PathVariable long movieId, ScheduleFilter filter) {
        return scheduleService.getAllSchedules(movieId, filter);
    }
}
