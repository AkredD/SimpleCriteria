package com.criteria.simple.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
// Only one field should by not null
public class PaginationFilterExpressionInput {
    private List<PaginationFilterExpressionInput> and;

    private List<PaginationFilterExpressionInput> or;

    private PaginationFilterExpressionInput not;

    private PaginationFilterInput value;
}
