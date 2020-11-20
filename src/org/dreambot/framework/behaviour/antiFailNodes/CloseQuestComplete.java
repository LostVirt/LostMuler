package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class CloseQuestComplete extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Widgets.getWidgetChild(277, 15) != null;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "CloseQuestComplete";
        WidgetChild widget = Widgets.getWidgetChild(277, 15);
        if (widget != null) {
            if (widget.interact()) {
                MethodProvider.sleep(750 + Api.sleep());
            }
        }
        return Api.sleep();
    }

}
