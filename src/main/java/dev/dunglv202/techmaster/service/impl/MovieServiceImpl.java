package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.dto.resp.DetailMovieDTO;
import dev.dunglv202.techmaster.dto.resp.MovieDTO;
import dev.dunglv202.techmaster.entity.Movie;
import dev.dunglv202.techmaster.exception.ClientVisibleException;
import dev.dunglv202.techmaster.mapper.MovieMapper;
import dev.dunglv202.techmaster.model.Pagination;
import dev.dunglv202.techmaster.model.ResultPage;
import dev.dunglv202.techmaster.repository.MovieRepository;
import dev.dunglv202.techmaster.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public ResultPage<MovieDTO> getAllMovies(Pagination pagination) {
        Page<MovieDTO> page = movieRepository.findAll(pagination.toPageable())
            .map(MovieMapper.INSTANCE::toMovieDTO);
        return new ResultPage<>(page);
    }

    @Override
    public DetailMovieDTO getDetailMovie(long id) {
        return movieRepository.findById(id)
            .map(MovieMapper.INSTANCE::toDetailMovieDTO)
            .orElseThrow(() -> new ClientVisibleException("{movie.invalid}"));
    }
}
