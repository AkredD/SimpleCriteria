package com.criteria.simple.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationSortInput {
    private String field;

    private SortDirection direction;
}
