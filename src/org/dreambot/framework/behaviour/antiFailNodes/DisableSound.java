package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class DisableSound extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return PlayerSettings.getConfig(168) != 4 || PlayerSettings.getConfig(169) != 4 || PlayerSettings.getConfig(872) != 4;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Disable Sounds";
        if (Tabs.isOpen(Tab.OPTIONS)) {
            if (Widgets.getWidgetChild(261, 1, 2) != null) {
                if (Widgets.getWidgetChild(261, 1, 2).getTextureId() == 762) {
                    if (PlayerSettings.getConfig(168) != 4 && Widgets.getWidgetChild(261, 38) != null && Widgets.getWidgetChild(261, 38).interact()) {
                        MethodProvider.sleepUntil(() -> PlayerSettings.getConfig(168) == 4, 500 + Api.sleep());
                    } else if (PlayerSettings.getConfig(169) != 4 && Widgets.getWidgetChild(261, 44) != null && Widgets.getWidgetChild(261, 44).interact()) {
                        MethodProvider.sleepUntil(() -> PlayerSettings.getConfig(169) == 4, 500 + Api.sleep());
                    } else if (PlayerSettings.getConfig(872) != 4 && Widgets.getWidgetChild(261, 50) != null && Widgets.getWidgetChild(261, 50).interact()) {
                        MethodProvider.sleepUntil(() -> PlayerSettings.getConfig(872) == 4, 500 + Api.sleep());
                    }
                } else if (Widgets.getWidgetChild(261, 1, 2).interact("Audio")) {
                    MethodProvider.sleepUntil(() -> Widgets.getWidgetChild(261, 1, 2).getTextureId() == 762, 500 + Api.sleep());
                }
            }
        } else if (Tabs.open(Tab.OPTIONS)) {
            MethodProvider.sleepUntil(() -> Tabs.isOpen(Tab.OPTIONS), Api.sleep());
        }
        return 250 + Api.sleep();
    }
}
