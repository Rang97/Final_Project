package com.example.demo.global.common;

import lombok.Getter;

import java.util.List;

//임시로 설정, 필요하면 수정 및 추가 가능

@Getter
public class PageResponse<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final int totalCount;
    private final int totalPages;

    private PageResponse(List<T> content, int page, int size, int totalCount) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalCount = totalCount;
        this.totalPages = (int) Math.ceil((double) totalCount / size);
    }

    public static <T> PageResponse<T> of(List<T> content, int page, int size, int totalCount) {
        return new PageResponse<>(content, page, size, totalCount);
    }
}