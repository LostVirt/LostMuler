package org.dreambot.framework.behaviour;

import org.dreambot.api.Client;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class LogOut extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Api.hasMuled && Client.isLoggedIn();
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "LogOut";

        if ((Bank.isOpen() && Bank.close()) || (GrandExchange.isOpen() && GrandExchange.close())) {
            MethodProvider.sleepUntil(() -> !Bank.isOpen() && !GrandExchange.isOpen(), 1000 + Api.sleep());
            return Api.sleep();
        }

        if (Api.standArea.contains(Players.localPlayer())) {
            Tabs.logout();
            return Api.sleep();
        }

        if (Walking.shouldWalk(0)) {
            Walking.walk(Api.standArea.getRandomTile());
        }
        return Api.sleep();
    }

}
