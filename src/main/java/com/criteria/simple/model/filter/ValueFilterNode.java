package com.criteria.simple.model.filter;

import com.criteria.simple.util.TypeSupporting;

import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ValueFilterNode<T> extends AbstractFilterNode<T> {
    private final Filtering filtering;

    protected ValueFilterNode(Filtering filtering) {
        super(null);
        this.filtering = filtering;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return buildPredicateFromFiltering(root, criteriaBuilder);
    }

    private <U> Predicate buildPredicateFromFiltering(Root<T> root, CriteriaBuilder criteriaBuilder) {
        Path<U> targetObject = getFilteringField(root, filtering.getField());
        U value = TypeSupporting.evaluateTargetValue(targetObject, (String) filtering.getValue());
        switch (filtering.getFilterType()) {
            case EQ:
                return criteriaBuilder.equal(targetObject, filtering.getValue());
            case NOT_EQ:
                return criteriaBuilder.notEqual(targetObject, filtering.getValue());
            case MORE:
                if (!(filtering.getValue() instanceof Comparable))
                    throw new UnsupportedOperationException("Cannot compare expression: " + filtering.toString());
                return criteriaBuilder.greaterThan(
                        (Path<Comparable>) targetObject,
                        (Comparable) filtering.getValue());
            case LESS:
                if (!(filtering.getValue() instanceof Comparable))
                    throw new UnsupportedOperationException("Cannot compare expression: " + filtering.toString());
                return criteriaBuilder.lessThan(
                        (Path<Comparable>) targetObject,
                        (Comparable) filtering.getValue());
            case IN:
                var listIn = (filtering.getValue() instanceof Iterable)
                        ? formArray((Iterable) filtering.getValue())
                        : Collections.singletonList(filtering.getValue());
                return criteriaBuilder.in(targetObject)
                        .in(listIn);
            case NOT_INT:
                var listNotIn = (filtering.getValue() instanceof Iterable)
                        ? formArray((Iterable) filtering.getValue())
                        : Collections.singletonList(filtering.getValue());
                return criteriaBuilder.in(targetObject)
                        .in(listNotIn).not();
            case LIKE:
                String likeExpression = filtering.getValue().toString();
                return criteriaBuilder.like((Path<String>) targetObject, likeExpression);
            case NOT_LIKE:
                String notLikeExpression = filtering.getValue().toString();
                return criteriaBuilder.notLike((Path<String>) targetObject, notLikeExpression);
            default:
                throw new UnsupportedOperationException("Unsupported filter " + filtering.toString());
        }
    }

    private <V> List<V> formArray(Iterable<V> list) {
        return StreamSupport.stream(list.spliterator(), false)
                .collect(Collectors.toList());
    }


    private <V, U> Path<U> getFilteringField(Root<V> root, String fieldName) {
        String[] fields = fieldName.split("\\.");
        Path<?> path = root;
        for (String field : fields) {
            path = path.get(field);
        }
        return (Path<U>) path;
    }

    @Override
    public List<AbstractFilterNode<T>> getChildNode() {
        throw new NoSuchElementException("This node has no possibility to have child.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueFilterNode<?> that = (ValueFilterNode<?>) o;
        return filtering.equals(that.filtering);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filtering);
    }
}
