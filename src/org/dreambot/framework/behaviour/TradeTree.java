package org.dreambot.framework.behaviour;

import org.dreambot.api.Client;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.Main;
import org.dreambot.framework.nodes.Node;
import org.dreambot.framework.nodes.impl.TreeNode;

public class TradeTree extends TreeNode<Main> {

    @Override
    public boolean isValid(Main context) {
        return Client.isLoggedIn() && !Api.hasMuled;
    }


}
