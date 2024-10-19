package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.dto.req.MovieFilter;
import dev.dunglv202.techmaster.dto.resp.DetailMovieDTO;
import dev.dunglv202.techmaster.dto.resp.MovieDTO;
import dev.dunglv202.techmaster.entity.Category;
import dev.dunglv202.techmaster.entity.Movie;
import dev.dunglv202.techmaster.exception.ClientVisibleException;
import dev.dunglv202.techmaster.mapper.CategoryRepository;
import dev.dunglv202.techmaster.mapper.MovieMapper;
import dev.dunglv202.techmaster.model.Pagination;
import dev.dunglv202.techmaster.model.ResultPage;
import dev.dunglv202.techmaster.model.criteria.MovieCriteria;
import dev.dunglv202.techmaster.repository.MovieRepository;
import dev.dunglv202.techmaster.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResultPage<MovieDTO> getAllMovies(MovieFilter filter, Pagination pagination) {
        Category category = categoryRepository.findByCode(filter.getCategory()).orElse(null);
        Specification<Movie> criteria = new MovieCriteria()
            .nowShowing(filter.getNowShowing())
            .comingSoon(filter.getComingSoon())
            .category(category)
            .build();
        Page<MovieDTO> page = movieRepository.findAll(criteria, pagination.toPageable())
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
