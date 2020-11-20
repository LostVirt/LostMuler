package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class PollBooth extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Widgets.getWidgetChild(310, 2, 11) != null;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Closing Poll Booth..";
        if (Widgets.getWidgetChild(310, 2, 11).interact("Close")) {
            MethodProvider.sleep(Calculations.random(500, 750));
        }
        return 250 + Api.sleep();
    }
}
