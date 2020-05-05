package com.criteria.simple.model;

import com.criteria.simple.dto.PaginationFilterExpressionInput;
import com.criteria.simple.dto.PaginationInput;
import com.criteria.simple.model.filter.*;
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
public class Pagination<T> {

    /**
     * Filter description object
     */
    private final transient List<Specification<?>> filterRequestList = new ArrayList<>();
    /**
     * Sort fields
     */
    private List<Sorting> sort = new ArrayList<>();
    /**
     * Number of requested page
     */
    private Integer page;
    /**
     * Requested page size
     */
    private Integer size;

    public Pagination(PaginationInput input, Class<T> parametrizedClass) {
        this.page = input.getPage();
        this.size = input.getSize();
        if (input.getSort() != null)
            this.sort = input.getSort().stream()
                    .map(inputSort -> new Sorting(inputSort.getField(), inputSort.getDirection()))
                    .collect(Collectors.toList());
        if (input.getFilter() != null) {
            this.filterRequestList.add(
                    prepareFilterTree(input.getFilter())
            );
        }
    }


    private AbstractFilterNode<T> prepareFilterTree(PaginationFilterExpressionInput filter) {
        AbstractFilterNode<T> result = null;

        // And filter node
        if (filter.getAnd() != null && filter.getNot() == null
                && filter.getOr() == null && filter.getValue() == null) {
            result = new AndFilterNode<>(
                    filter.getAnd().stream()
                            .map(this::prepareFilterTree)
                            .collect(Collectors.toList())
            );
        }

        // Or filter node
        if (filter.getAnd() == null && filter.getNot() == null
                && filter.getOr() != null && filter.getValue() == null) {
            result = new OrFilterNode<>(
                    filter.getAnd().stream()
                            .map(this::prepareFilterTree)
                            .collect(Collectors.toList())
            );
        }

        // Value filter node
        if (filter.getAnd() == null && filter.getNot() == null
                && filter.getOr() == null && filter.getValue() != null) {
            result = new ValueFilterNode<>(
                    new Filtering(
                            filter.getValue().getField(),
                            filter.getValue().getValue(),
                            FilterType.valueOf(filter.getValue().getFilterType())
                    )
            );
        }

        // Not filter node
        if (filter.getAnd() == null && filter.getNot() != null
                && filter.getOr() == null && filter.getValue() == null) {
            result = new NotFilterNode<>(
                    prepareFilterTree(filter.getNot())
            );
        }

        if (result == null)
            throw new IllegalArgumentException("Only one field of filter Expression should by not null");

        return result;
    }

    public Pagination<T> addSort(Sorting sorting) {
        if (!sort.contains(sorting))
            sort.add(sorting);
        return this;
    }

    public Pagination<T> addAdditionalSpecification(Specification<?> specification) {
        this.filterRequestList.add(specification);
        return this;
    }

    public Pagination<T> addAdditionalFilterInput(PaginationFilterExpressionInput input) {
        this.filterRequestList.add(
                prepareFilterTree(input)
        );
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

    public Specification<T> getResultSpecification(Class<T> dtoClass) {
        return filterRequestList.stream()
                .map(specification -> (Specification<T>) specification)
                .reduce(Specification::and)
                .orElseThrow(
                        NoSuchElementException::new
                );
    }
}
