package com.criteria.simple.model.filter;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public abstract class AbstractFilterNode<T> implements Specification<T> {
    protected List<AbstractFilterNode<T>> childNode;

    protected AbstractFilterNode(List<AbstractFilterNode<T>> childNode) {
        this.childNode = childNode;
    }

    public List<AbstractFilterNode<T>> getChildNode() {
        return childNode;
    }

    public void addNode(AbstractFilterNode<T> node) {
        childNode.add(node);
    }
}
