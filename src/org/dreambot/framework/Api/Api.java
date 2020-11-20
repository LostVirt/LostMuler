package org.dreambot.framework.Api;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.framework.Handlers.SocketHandler;


/*
API to have global variables in one place
 */
public class Api {

    public static boolean hasMuled = false;

    public static int totalOutgoing = 0;
    public static int outgoingValue = 0;

    public static int totalIncoming = 0;
    public static int incomingValue = 0;

    public static int totalCoins = 0;

    public static int mulePort = 1337;

    public static SocketHandler server;

    public static int desiredZoom = 250;

    public static String gameMessage = "";

    public static Area standArea;

    //player settings
    public static int acceptAid = 427; // 1 = on 0 = off
    public static int escCloseInterface = 1224; // below 0 = on, over 0 = off

    public static int sleep() {
        return (int) Calculations.nextGaussianRandom(300, 50);
    }

    private static String lastLog = "";
    public static String state = "";

    public static void updateState(String message) {
        if (!lastLog.equals(message)) {
            MethodProvider.log(message);
        }
        state = message;
        lastLog = message;
    }
}
