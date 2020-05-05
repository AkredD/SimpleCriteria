package com.criteria.simple.dto;

import com.criteria.simple.model.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationInput {
    private Integer page;

    private Integer size;

    private List<PaginationSortInput> sort;

    private PaginationFilterExpressionInput filter;

    public <T> Pagination<T> transformToPagination(Class<T> dtoClass) {
        return new Pagination<>(this, dtoClass);
    }
}
