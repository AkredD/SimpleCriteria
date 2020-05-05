package com.criteria.simple.model.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class OrFilterNode<T> extends AbstractFilterNode<T> {
    public OrFilterNode(List<AbstractFilterNode<T>> childNode) {
        super(childNode);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(
                (Predicate[]) childNode.stream()
                        .map(node -> node.toPredicate(root, criteriaQuery, criteriaBuilder))
                        .toArray()
        );
    }
}
