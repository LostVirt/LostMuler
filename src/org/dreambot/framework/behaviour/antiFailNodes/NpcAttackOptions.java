package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class NpcAttackOptions extends Node<Main> {

    private final Area fishingRealm = new Area(2738, 4739, 2812, 4665);

    @Override
    public boolean isValid(Main context) {
        return fishingRealm.contains(Players.localPlayer()) ? PlayerSettings.getConfig(1306) != 2 : PlayerSettings.getConfig(1306) != 1;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Attack Options..";
        if (Tabs.isOpen(Tab.OPTIONS)) {
            WidgetChild controlsTab = Widgets.getWidgetChild(261, 1, 6);
            if (controlsTab != null && controlsTab.getTextureId() == 762) {
                WidgetChild npcDropDown = Widgets.getWidgetChild(261, 92, 3);
                if (npcDropDown != null && npcDropDown.getTextureId() == 773) {
                    WidgetChild npcAlwaysRightClick = Widgets.getWidgetChild(261, 109, fishingRealm.contains(Players.localPlayer()) ? 3 : 2); // change widget if other is needed.
                    if (npcAlwaysRightClick != null && npcAlwaysRightClick.isVisible() && npcAlwaysRightClick.interact("Select")) {
                        MethodProvider.sleepUntil(() -> fishingRealm.contains(Players.localPlayer()) ? PlayerSettings.getConfig(1306) != 2 : PlayerSettings.getConfig(1306) != 1, Api.sleep());
                    }
                } else if (npcDropDown != null && npcDropDown.getTextureId() == 788 && npcDropDown.interact()) {
                    MethodProvider.sleepUntil(() -> npcDropDown.getTextureId() == 773, 500 + Api.sleep());
                }
            } else if (controlsTab != null && controlsTab.interact("Controls")) {
                MethodProvider.sleepUntil(() -> controlsTab.getTextureId() == 762, 500 + Api.sleep());
            }
        } else if (Tabs.open(Tab.OPTIONS)) {
            MethodProvider.sleepUntil(() -> Tabs.isOpen(Tab.OPTIONS), Api.sleep());
        }
        return 250 + Api.sleep();
    }
}
