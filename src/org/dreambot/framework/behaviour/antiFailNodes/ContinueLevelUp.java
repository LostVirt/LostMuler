package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class ContinueLevelUp extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Widgets.getWidgetChild(233, 3) != null;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Solving Level Up Interface";
        if (Dialogues.canContinue() && Dialogues.continueDialogue()) {
            MethodProvider.sleep(Calculations.random(500, 750));
        }
        return Api.sleep();
    }

}
