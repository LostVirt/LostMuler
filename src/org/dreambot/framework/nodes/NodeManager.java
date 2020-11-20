package org.dreambot.framework.nodes;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.framework.nodes.impl.RootNode;

public class NodeManager<T extends AbstractScript> {

    private RootNode<T> rootNode;

    public NodeManager() {
        rootNode = new RootNode<>();
    }

    public Node<T> addNodes(Node<T>... nodes) {
        rootNode.addChildren(nodes);
        return rootNode;
    }

    public int onLoop(T context) {
        return rootNode.onLoop(context);
    }
}
