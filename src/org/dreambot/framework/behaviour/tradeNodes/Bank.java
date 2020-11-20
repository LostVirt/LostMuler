package org.dreambot.framework.behaviour.tradeNodes;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class Bank extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Inventory.isFull();
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Bank";

        return Api.sleep();
    }

}
