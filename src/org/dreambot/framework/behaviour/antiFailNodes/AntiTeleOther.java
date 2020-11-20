package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class AntiTeleOther extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Widgets.getWidget(326) != null;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Closing Tele-Other interface..";
        boolean escClose = PlayerSettings.getConfig(Api.escCloseInterface) < 0;
        if (escClose && Keyboard.closeInterfaceWithESC()) {
            MethodProvider.sleepUntil(() -> Widgets.getWidget(326) == null, Api.sleep());
        } else if (Widgets.getWidgetChild(326, 96) != null && Widgets.getWidgetChild(326, 96).interact()) {
            MethodProvider.sleepUntil(() -> Widgets.getWidget(326) == null, Api.sleep());
        }
        return 250 + Api.sleep();
    }
}
