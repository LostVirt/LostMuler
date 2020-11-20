package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class EnableEscCloseInterface extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return PlayerSettings.getConfig(Api.escCloseInterface) > 0;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Turning on Esc Interfaces..";
        if (!Tabs.isOpen(Tab.OPTIONS)) {
            Tabs.open(Tab.OPTIONS);
            MethodProvider.sleepUntil(() -> PlayerSettings.getConfig(Api.acceptAid) == 1, Api.sleep());
        }
        if (Widgets.getWidgetChild(121, 103) != null && Widgets.getWidgetChild(121, 103).interact()) {
            Keyboard.closeInterfaceWithESC();
            MethodProvider.sleep(Calculations.random(500, 750));
        } else if (Widgets.getWidgetChild(261, 77) != null && Widgets.getWidgetChild(261, 77).isVisible() && Widgets.getWidgetChild(261, 77).interact()) {
            MethodProvider.sleep(Calculations.random(500, 750));
        } else if (Tabs.isOpen(Tab.OPTIONS) && Widgets.getWidgetChild(261, 1, 6) != null && Widgets.getWidgetChild(261, 1, 6).interact()) {
            MethodProvider.sleep(Calculations.random(500, 750));
        }


        return 250 + Api.sleep();
    }
}
