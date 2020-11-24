package org.dreambot.framework.behaviour.tradeNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class TradeBot extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return !Inventory.isFull();
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "TradeBot";
        if ((Bank.isOpen() && Bank.close()) || (GrandExchange.isOpen() && GrandExchange.close())) {
            MethodProvider.sleepUntil(() -> !Bank.isOpen() && !GrandExchange.isOpen(), 1000 + Api.sleep());
            return Api.sleep();
        }

        Player bot = Players.closest(p -> p.getName().equalsIgnoreCase(Api.server.getBotName()));
        if (bot != null) {
            if (bot.distance() <= 2 && !Api.gameMessage.equals("Sending trade offer...") && bot.interact("Trade with")) {
                MethodProvider.sleepUntil(() -> Api.gameMessage.equals("Sending trade offer...") || Trade.isOpen(), 1500 + Api.sleep());
            }
        }

        return Api.sleep();
    }

}
