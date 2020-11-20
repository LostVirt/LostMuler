package org.dreambot.framework.behaviour;

import org.dreambot.api.Client;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.impl.TreeNode;

public class AntiFailTree extends TreeNode<Main> {

    private final Area fishingRealm = new Area(2738, 4739, 2812, 4665);

    @Override
    public boolean isValid(Main context) {
        return Client.isLoggedIn()
                && (((Combat.isAutoRetaliateOn() && PlayerSettings.getConfig(226) != 3) || (PlayerSettings.getConfig(226) == 3 && !Combat.isAutoRetaliateOn())) // auto retaliate
                || Widgets.getWidget(326) != null // anti teleport
                || Widgets.getWidgetChild(233, 3) != null
                || PlayerSettings.getConfig(Api.escCloseInterface) > 0 // Close interface
                || (PlayerSettings.getConfig(Api.acceptAid) & 1) == 1 // accept aid
                || (PlayerSettings.getConfig(168) != 4 || PlayerSettings.getConfig(169) != 4 || PlayerSettings.getConfig(872) != 4) // disable sound
                || PlayerSettings.getConfig(1306) != 1// npc attack options
                || Widgets.getWidgetChild(310, 2, 11) != null // poll booth
                || ((PlayerSettings.getConfig(1055) >> 17) & 0x1) != 1 // Shift drop
                || Widgets.getWidgetChild(664, 28, 0) != null // Anti Bank tutorial
                || Widgets.getWidgetChild(277, 15) != null // Close quest interface
                || (!(Camera.getZoom() > (Api.desiredZoom - 100) && Camera.getZoom() < (Api.desiredZoom + 100)) && PlayerSettings.getConfig(1021) != 16576 && PlayerSettings.getConfig(1021) != 17088) // Change Zoom
                || (Widgets.getWidgetChild(193, 0, 2) != null)); // login chat interface
    }
}
