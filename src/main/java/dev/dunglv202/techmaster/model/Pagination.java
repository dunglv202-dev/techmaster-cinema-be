package dev.dunglv202.techmaster.model;

import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Setter
public class Pagination {
    private int page = 1;
    private int limit = 10;

    public Pageable toPageable() {
        return Pageable.ofSize(limit).withPage(page - 1);
    }
}
