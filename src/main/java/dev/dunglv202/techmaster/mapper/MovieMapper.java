package dev.dunglv202.techmaster.mapper;

import dev.dunglv202.techmaster.dto.resp.MovieDTO;
import dev.dunglv202.techmaster.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CategoryMapper.class)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDTO toMovieDTO(Movie movie);
}
