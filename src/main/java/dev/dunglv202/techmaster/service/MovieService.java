package dev.dunglv202.techmaster.service;

import dev.dunglv202.techmaster.dto.req.MovieFilter;
import dev.dunglv202.techmaster.dto.resp.DetailMovieDTO;
import dev.dunglv202.techmaster.dto.resp.MovieDTO;
import dev.dunglv202.techmaster.model.Pagination;
import dev.dunglv202.techmaster.model.ResultPage;

public interface MovieService {
    ResultPage<MovieDTO> getAllMovies(MovieFilter filter, Pagination pagination);

    DetailMovieDTO getDetailMovie(long id);
}
