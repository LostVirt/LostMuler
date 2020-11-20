package org.dreambot.framework.behaviour;

import org.dreambot.api.Client;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class LogOut extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Api.hasMuled;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "LogOut";

        if (Client.isLoggedIn()) {
            if (Api.standArea.contains(Players.localPlayer())) {
                Tabs.logout();
                return Api.sleep();
            }

            if (Walking.shouldWalk(0)) {
                Walking.walk(Api.standArea.getRandomTile());
            }
            return Api.sleep();
        }



        return Api.sleep();
    }

}
