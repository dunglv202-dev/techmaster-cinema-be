package dev.dunglv202.techmaster.service;

import dev.dunglv202.techmaster.dto.resp.DetailMovieDTO;
import dev.dunglv202.techmaster.dto.resp.MovieDTO;

import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMovies();

    DetailMovieDTO getDetailMovie(long id);
}
