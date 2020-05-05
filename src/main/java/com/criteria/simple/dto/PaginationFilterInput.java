package com.criteria.simple.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaginationFilterInput {
    private String field;

    private Object value;

    private String filterType;
}
