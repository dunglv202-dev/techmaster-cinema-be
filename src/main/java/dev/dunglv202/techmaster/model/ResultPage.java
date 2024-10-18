package dev.dunglv202.techmaster.model;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ResultPage<T> {
    private final int totalPages;
    private final long totalElements;
    private final List<T> content;

    public ResultPage(Page<T> pages) {
        this.totalPages = pages.getTotalPages();
        this.totalElements = pages.getTotalElements();
        this.content = pages.getContent();
    }
}
