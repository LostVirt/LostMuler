package org.dreambot.framework.behaviour.tradeNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Api.Items;
import org.dreambot.framework.Handlers.TradeHandler;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class InTrade extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Trade.isOpen();
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "InTrade";

        if (!Trade.getTradingWith().equalsIgnoreCase(Api.server.getBotName())) {
            if (Trade.close()) {
                MethodProvider.sleepUntil(() -> !Trade.isOpen(), 500 + Api.sleep());
            }
            return Api.sleep();
        }

        if (Trade.isOpen(1)) {
            if (TradeHandler.amountInTrade(true, Items.COINS) >= Api.server.getBotRequestAmount()) {
                if (Trade.acceptTrade(1)) {
                    MethodProvider.sleepUntil(() -> Trade.isOpen(2), 1000 + Api.sleep());
                }
                return Api.sleep();
            }

            if (Trade.addItem(Items.COINS, Api.server.getBotRequestAmount())) {
                MethodProvider.sleepUntil(() -> TradeHandler.amountInTrade(true, Items.COINS) >= Api.server.getBotRequestAmount(), 3000 + Api.sleep());
            }
            return Api.sleep();
        }

        if (Trade.isOpen(2)) {
            Api.incomingValue = TradeHandler.getTradeAmount(false);
            Api.outgoingValue = TradeHandler.getTradeAmount(true);
            MethodProvider.log("Incoming: " + Api.incomingValue + " Outgoing: " +  Api.outgoingValue);
            if (Trade.acceptTrade(2)) {
                MethodProvider.sleepUntil(() -> !Trade.isOpen(), 5000 + Api.sleep());
            }
        }

        return Api.sleep();
    }

}
