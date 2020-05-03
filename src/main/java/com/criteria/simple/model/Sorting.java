package com.criteria.simple.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Sorting {
    /**
     * Field names for sorting
     */
    private String field;
    /**
     * Sort direction (ascending/descending)
     */
    private Sort.Direction direction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sorting sorting = (Sorting) o;
        return field.equals(sorting.field) &&
                direction == sorting.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, direction);
    }
}
