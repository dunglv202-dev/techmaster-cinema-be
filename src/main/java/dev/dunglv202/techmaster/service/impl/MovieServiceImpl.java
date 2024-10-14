package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.dto.resp.MovieDTO;
import dev.dunglv202.techmaster.mapper.MovieMapper;
import dev.dunglv202.techmaster.repository.MovieRepository;
import dev.dunglv202.techmaster.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream().map(MovieMapper.INSTANCE::toMovieDTO).toList();
    }
}
