package com.criteria.simple.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    /**
     * Sort fields
     */
    private final List<Sorting> sort = new ArrayList<>();
    /**
     * Filter description object
     */
    private final transient List<Specification<?>> filterRequestList = new ArrayList<>();
    /**
     * Number of requested page
     */
    private Integer page;
    /**
     * Requested page size
     */
    private Integer size;

    public Pagination addSort(Sorting sorting) {
        if (!sort.contains(sorting))
            sort.add(sorting);
        return this;
    }

    public Pagination addAdditionalSpecification(Specification<?> specification) {
        this.filterRequestList.add(specification);
        return this;
    }

    public PageRequest processAndGetPageRequest() {
        final List<Sort.Order> orders = sort != null
                ? sort.stream()
                .map(s -> new Sort.Order(s.getDirection(), s.getField()))
                .collect(Collectors.toList())
                : new ArrayList<>();

        return (page != null && size != null)
                ? PageRequest.of(page - 1, size, Sort.by(orders))
                : PageRequest.of(1, 1);
    }

    public <T> Specification<T> getResultSpecification(Class<T> dtoClass) {
        return filterRequestList.stream()
                .map(specification -> (Specification<T>) specification)
                .reduce(Specification::and)
                .orElseThrow(
                        NoSuchElementException::new
                );
    }
}
