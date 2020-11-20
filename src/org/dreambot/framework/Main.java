package org.dreambot.framework;

import org.dreambot.api.Client;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.framework.Handlers.SocketHandler;
import org.dreambot.framework.behaviour.*;
import org.dreambot.framework.behaviour.antiFailNodes.*;
import org.dreambot.framework.gui.ScriptFrame;
import org.dreambot.framework.Api.Api;
import org.dreambot.framework.nodes.DrawMouseUtil;
import org.dreambot.framework.nodes.NodeManager;
import org.dreambot.framework.nodes.WindMouse;
import org.dreambot.framework.nodes.impl.TreeNode;
import org.dreambot.framework.nodes.paint.CustomPaint;
import org.dreambot.framework.nodes.paint.PaintInfo;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


@ScriptManifest(author = "LostVirt", category = Category.MISC, description = "Muling", name = "LostMuler", version = 1.1)
public class Main extends AbstractScript implements ChatListener, PaintInfo {

    private NodeManager<Main> nodeManager;
    private TreeNode<Main> antiTree;

    private Timer runTimer;
    private final DrawMouseUtil drawMouseUtil = new DrawMouseUtil();
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public static ScriptFrame gui;

    @Override
    public void onStart() {
        disableSolver(RandomEvent.LOGIN);

        SwingUtilities.invokeLater(() -> {
            try {
                gui = new ScriptFrame();
                gui.setUndecorated(true);
                gui.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        Client.getInstance().setMouseMovementAlgorithm(new WindMouse());
        Client.getInstance().setDrawMouse(false);
        Keyboard.setWordsPerMinute(150);
        Walking.setRunThreshold(20);

        drawMouseUtil.setCursorColor(new Color(11, 162, 0));
        drawMouseUtil.setTrailColor(new Color(0, 0, 0, 75));
        drawMouseUtil.setCursorStroke(new BasicStroke(2));

        runTimer = new Timer();
        nodeManager = new NodeManager<>();

        antiTree = new AntiFailTree();

        nodeManager.addNodes(
                antiTree.addChildren(
                        new CloseQuestComplete(),
                        new TurnOffAutoRetaliate(),
                        new AntiBankTutorial(),
                        new PollBooth(),
                        new AntiTeleOther(),
                        new CloseInterfaces(),
                        new LogInChatInterface(),
                        new ContinueLevelUp(),
                        new ChangeZoom(),
                        new EnableEscCloseInterface(),
                        new EnableShiftDrop(),
                        new DisableAcceptAid(),
                        new DisableSound(),
                        new NpcAttackOptions())
        );

    }

    public static boolean loginButton = false;
    private boolean hasLoggedInYet = false;
    @Override
    public int onLoop() {
        if (loginButton) {
            if (hasLoggedInYet) {
                if (!Client.isLoggedIn()) {
                    log("Resume the script");
                    hasLoggedInYet = false;
                    loginButton = false;
                }
                return 500;
            }

            if (Client.isLoggedIn()) {
                hasLoggedInYet = true;
                disableSolver(RandomEvent.LOGIN);
            } else {
                enableSolver(RandomEvent.LOGIN);
            }
            return 500;
        }

        if (Client.isLoggedIn()) {
            if (Api.standArea == null) {
                Api.standArea = Players.localPlayer().getTile().getArea(1);
            }
            disableSolver(RandomEvent.LOGIN);
        }

        if (gui.startScript) {

        }
        return gui.startScript ? this.nodeManager.onLoop(this) : 500;
    }

    @Override
    public void onExit() {
        gui.dispose();
        try {
            Api.server.close();
            log("Closed Server..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getPaintInfo() {
        return new String[]{
                "LostMuler",
                "Runtime: " + runTimer.formatTime(),
                "Server State: " + Api.server.getServerState(),
                "Bot Identifier: " + Api.server.getBotIdentifier() + " Bot Name: " + Api.server.getBotName() + " Bot World: " + Api.server.getBotWorld(),
                "Bot Request: " + (Api.server.getBotRequestAmount() < 1 ? "No Request.." : (Api.server.getBotRequestAmount() + " Gp")),
                "Incoming: " + (Api.totalIncoming > 1000 ? (Api.totalIncoming /1000) + "K " : Api.totalIncoming) + "(" + (Api.totalIncoming > 1000 ? runTimer.getHourlyRate(Api.totalIncoming /1000) + "K" : runTimer.getHourlyRate(Api.totalIncoming)) + "/hr)",
                "Outgoing: " + (Api.totalOutgoing > 1000 ? (Api.totalOutgoing /1000) + "K " : Api.totalOutgoing) + "(" + (Api.totalOutgoing > 1000 ? runTimer.getHourlyRate(Api.totalOutgoing /1000) + "K" : runTimer.getHourlyRate(Api.totalOutgoing)) + "/hr)",
                "Amount of Gold: " + Api.totalCoins,
                "State: " + Api.state};
    }

    private final CustomPaint CUSTOM_PAINT = new CustomPaint(this,
            CustomPaint.PaintLocations.TOP_LEFT_PLAY_SCREEN, new Color[]{new Color(255, 251, 255)},
            "Trebuchet MS",
            new Color[]{new Color(50, 50, 50, 175)},
            new Color[]{new Color(28, 28, 29)},
            1, false, 5, 3, 0);

    /*
    Basic Paint
     */

    @Override
    public void onPaint(Graphics g) {
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHints(aa);

        CUSTOM_PAINT.paint(gg);

        drawMouseUtil.drawRotatingCircleMouse(g);
        drawMouseUtil.drawTrail(g);
    }


    @Override
    public void onPause() {
        log("Paused");
    }

    @Override
    public void onResume() {
        log("Resumed");
    }


    @Override
    public void onGameMessage(Message message) {
        Api.gameMessage = message.getMessage();
        if (message.getMessage().toLowerCase().contains("accepted trade")) {
            Api.server = new SocketHandler("Mule", Api.mulePort);
            Api.hasMuled = true;
            Api.totalIncoming += Api.incomingValue;
            Api.totalOutgoing += Api.outgoingValue;
            Api.totalCoins = Inventory.count(item -> item != null && item.getID() == 995);
            Api.state = "Successfully Traded!";
        }
    }

    public void disableSolver(RandomEvent event) {
        getRandomManager().disableSolver(event);
    }

    public void enableSolver(RandomEvent event) {
        getRandomManager().enableSolver(event);
    }


}
