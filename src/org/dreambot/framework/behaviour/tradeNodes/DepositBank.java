package org.dreambot.framework.behaviour.tradeNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Api.Items;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class DepositBank extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Inventory.isFull();
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Bank";

        if (Bank.isOpen()) {
            if (Bank.contains(i -> i != null && i.getID() == Items.COINS)
                && Bank.withdrawAll(Items.COINS)) {
                MethodProvider.sleepUntil(() -> !Bank.contains(i -> i != null && i.getID() == Items.COINS), 2000 + Api.sleep());
                return Api.sleep();
            }

            Bank.depositAllExcept(i -> i != null && i.getID() == Items.COINS);
            return Api.sleep();
        }

        if (Walking.shouldWalk(0)) {
            Bank.openClosest();
        }
        return Api.sleep();
    }

}
