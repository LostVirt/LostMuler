package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class CloseInterfaces extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Bank.isOpen() || GrandExchange.isOpen() || Trade.isOpen();
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Closing interface..";
        if (Bank.isOpen()) {
            if (Bank.close()) {
                MethodProvider.sleepUntil(() -> !Bank.isOpen(), 500 + Api.sleep());
            }
        } else if (GrandExchange.isOpen()) {
            if (GrandExchange.close()) {
                MethodProvider.sleepUntil(() -> !GrandExchange.isOpen(), 500 + Api.sleep());
            }
        } else if (Trade.isOpen()) {
            if (Trade.close()) {
                MethodProvider.sleepUntil(() -> !Trade.isOpen(), 500 + Api.sleep());
            }
        }
        return 250 + Api.sleep();
    }
}
