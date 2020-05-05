package com.criteria.simple.model.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NotFilterNode<T> extends AbstractFilterNode<T> {
    private final AbstractFilterNode<T> childNode;

    public NotFilterNode(AbstractFilterNode<T> childNode) {
        super(null);
        this.childNode = childNode;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.not(
                childNode.toPredicate(root, criteriaQuery, criteriaBuilder)
        );
    }
}
