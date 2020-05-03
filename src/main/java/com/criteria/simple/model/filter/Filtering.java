package com.criteria.simple.model.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Filtering {
    private String field;

    private Object value;

    private FilterType filterType;
}
