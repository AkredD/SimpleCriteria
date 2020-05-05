package com.criteria.simple.model.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Filtering {
    private String field;

    private Object value;

    private FilterType filterType;
}
