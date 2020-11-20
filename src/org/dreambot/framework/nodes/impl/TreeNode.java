package org.dreambot.framework.nodes.impl;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.framework.nodes.Node;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class TreeNode<T extends AbstractScript> extends Node<T> {

    private final List<Node<T>> children;

    public TreeNode() {
        this.children = new LinkedList<>();
    }


    public TreeNode<T> addChildren(Node<T>... children) {
        Collections.addAll(this.children,children);
        return this;
    }


    @Override
    public int onLoop(T context) {
        return  children.stream()
                .filter(c -> Objects.nonNull(c) && c.isValid(context))
                .findAny()
                .map(tNode -> tNode.onLoop(context)).orElse(600);

    }
}
