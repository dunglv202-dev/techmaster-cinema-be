package dev.dunglv202.techmaster.dto.resp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MovieDTO {
    private long id;
    private String name;
    private String thumbnail;
    private List<String> categories;
    private int durationInMinutes;
    private LocalDate premiereDate;
}
