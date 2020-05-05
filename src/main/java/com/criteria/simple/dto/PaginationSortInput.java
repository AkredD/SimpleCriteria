package com.criteria.simple.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PaginationSortInput {
    private String field;

    private Sort.Direction direction;
}
