package dev.dunglv202.techmaster.dto.resp;

import dev.dunglv202.techmaster.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailMovieDTO {
    private String name;
    private String thumbnail;
    private String trailer;
    private int durationInMinutes;
    private String description;
    private List<Category> categories;
    private String director;
    private List<String> actors;
    private boolean liked;
}
