package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Handlers.CameraHandler;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class ChangeZoom extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return !(Camera.getZoom() > (Api.desiredZoom - 100) && Camera.getZoom() < (Api.desiredZoom + 100)) && PlayerSettings.getConfig(1021) != 16576 && PlayerSettings.getConfig(1021) != 17088;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Changing Zoom";
        CameraHandler.changeZoom(Api.desiredZoom);
        return 250 + Api.sleep();
    }
}
