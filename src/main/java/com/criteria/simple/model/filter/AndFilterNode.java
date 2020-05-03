package com.criteria.simple.model.filter;

import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class AndFilterNode<T> extends AbstractFilterNode<T> {

    protected AndFilterNode(@NonNull List<AbstractFilterNode<T>> childNode) {
        super(childNode);
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                (Predicate[]) childNode.stream()
                        .map(node -> node.toPredicate(root, criteriaQuery, criteriaBuilder))
                        .toArray()
        );
    }
}
