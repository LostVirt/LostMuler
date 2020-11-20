package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class TurnOffAutoRetaliate extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return (Combat.isAutoRetaliateOn() && PlayerSettings.getConfig(226) != 3) || (PlayerSettings.getConfig(226) == 3 && !Combat.isAutoRetaliateOn());
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Turning Auto Retaliate off!";
        if (Tabs.isOpen(Tab.COMBAT)) {
            if (PlayerSettings.getConfig(226) == 3) {
                if (Combat.toggleAutoRetaliate(true)) {
                    MethodProvider.sleepUntil(Combat::isAutoRetaliateOn, 500 + Api.sleep());
                }
            } else if (Combat.toggleAutoRetaliate(false)) {
                MethodProvider.sleepUntil(() -> !Combat.isAutoRetaliateOn(), 500 + Api.sleep());
            }
        } else Tabs.open(Tab.COMBAT);
        return Api.sleep();
    }

}
