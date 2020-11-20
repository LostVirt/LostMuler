package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class EnableShiftDrop extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return ((PlayerSettings.getConfig(1055) >> 17) & 0x1) != 1;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Turning on Shift Dropping..";
        if (!Tabs.isOpen(Tab.OPTIONS)) {
            if (Tabs.open(Tab.OPTIONS)) {
                MethodProvider.sleepUntil(() -> Tabs.isOpen(Tab.OPTIONS), 3000);
            }
        } else if (Widgets.getWidgetChild(261, 79) == null || !Widgets.getWidgetChild(261, 79).isVisible()) {
            if (Widgets.getWidgetChild(261, 1, 7) != null) {
                if (Widgets.getWidgetChild(261, 1, 7).interact("Controls")) {
                    MethodProvider.sleep(1000, 1500);
                }
            }
        } else if (Widgets.getWidgetChild(261, 79).interact()) {
            MethodProvider.sleepUntil(() -> ((PlayerSettings.getConfig(1055) >> 17) & 0x1) == 1, 3000);
        }
        return 250 + Api.sleep();
    }
}
