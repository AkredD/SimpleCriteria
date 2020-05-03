package com.criteria.simple.dto;

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
}
