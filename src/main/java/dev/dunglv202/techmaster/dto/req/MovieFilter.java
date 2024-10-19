package dev.dunglv202.techmaster.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieFilter {
    /**
     * Category's code
     */
    private String category;

    private Boolean nowShowing;

    private Boolean comingSoon;
}
