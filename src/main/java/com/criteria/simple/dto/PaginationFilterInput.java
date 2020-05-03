package com.criteria.simple.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationFilterInput {
    private String field;

    private String value;

    private String filterType;
}
