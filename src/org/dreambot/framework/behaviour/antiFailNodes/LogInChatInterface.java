package org.dreambot.framework.behaviour.antiFailNodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;

public class LogInChatInterface extends Node<Main> {

    @Override
    public boolean isValid(Main context) {
        return Widgets.getWidgetChild(193, 0, 2) != null;
    }

    @Override
    public int onLoop(Main context) {
        Api.state = "Solving Login Chat Interface..";
        if (Dialogues.canContinue() && Dialogues.continueDialogue()) {
            MethodProvider.sleepUntil(() -> !Dialogues.inDialogue(), 500 + Api.sleep());
        }
        return 250 + Api.sleep();
    }
}
