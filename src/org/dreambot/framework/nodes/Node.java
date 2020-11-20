package org.dreambot.framework.nodes;

import org.dreambot.api.script.AbstractScript;

public abstract class Node<T extends AbstractScript> {

    public abstract boolean isValid(T context);

    public abstract int onLoop(T context);
}

