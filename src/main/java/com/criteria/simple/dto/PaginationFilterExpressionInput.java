package com.criteria.simple.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PaginationFilterExpressionInput {
    private List<PaginationFilterExpressionInput> and;

    private List<PaginationFilterExpressionInput> or;

    private PaginationFilterExpressionInput not;

    private PaginationFilterInput value;
}
