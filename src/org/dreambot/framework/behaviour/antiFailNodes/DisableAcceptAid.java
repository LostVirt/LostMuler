package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class DisableAcceptAid extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return (PlayerSettings.getConfig(Api.acceptAid) & 1) == 1;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Turning off Accept Aid..";
        if (Tabs.isOpen(Tab.OPTIONS)) {
            WidgetChild aidToggle = Widgets.getWidgetChild(261, 94);
            if (aidToggle != null && aidToggle.isVisible() && aidToggle.interact("Toggle Accept Aid")) {
                MethodProvider.sleepUntil(() -> PlayerSettings.getConfig(Api.acceptAid) == 0, Api.sleep());
            }
        } else if (Tabs.open(Tab.OPTIONS)) {
            MethodProvider.sleepUntil(() -> Tabs.isOpen(Tab.OPTIONS), Api.sleep());
        }
        return 250 + Api.sleep();
    }
}

/*
@Override
    public boolean isValid(Main context) {
        return (context.getPlayerSettings().getConfig(API.ACCEPT_AID) & 1) == 1;
    }

    @Override
    public int onLoop(Main context) {
        API.STATE = "Turning off Accept Aid..";
        if (context.getTabs().isOpen(Tab.OPTIONS)) {
            WidgetChild aidToggle = context.getWidgets().getWidgetChild(261, 94);
            if (aidToggle != null && aidToggle.isVisible() && aidToggle.interact("Toggle Accept Aid")) {
                MethodProvider.sleepUntil(() -> context.getPlayerSettings().getConfig(API.ACCEPT_AID) == 0, API.sleep());
            }
        } else if (context.getTabs().open(Tab.OPTIONS)) {
            MethodProvider.sleepUntil(() -> context.getTabs().isOpen(Tab.OPTIONS), API.sleep());
        }
        return 250 + API.sleep();
    }
 */