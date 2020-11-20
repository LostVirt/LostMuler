package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class AntiBankTutorial extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Widgets.getWidgetChild(664, 28, 0) != null;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Closing Bank Tutorial..";
        if (Widgets.getWidgetChild(664, 28, 0) != null && Widgets.getWidgetChild(664, 28, 0).interact()) {
            MethodProvider.sleepUntil(() -> Widgets.getWidgetChild(664, 28, 0) == null, 500 + Api.sleep());
        }
        return Api.sleep();
    }

}
