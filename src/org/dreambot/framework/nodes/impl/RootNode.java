package org.dreambot.framework.nodes.impl;

import org.dreambot.api.script.AbstractScript;

public class RootNode<T extends AbstractScript> extends TreeNode<T> {

    @Override
    public boolean isValid(T context) {
        return true;
    }
}
