package org.dreambot.framework.Handlers;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.framework.Api.Api;

import java.awt.*;

public class CameraHandler implements Runnable {

    public static void changeZoom(int level) {
        WidgetChild canvas = Widgets.getWidgetChild(122, 0);
        if (canvas != null) {
            if (canvas.getRectangle().contains(Mouse.getPosition())) {
                if (Camera.getZoom() <= level) {
                    Mouse.scrollUpWhile(2000, () -> Camera.getZoom() >= level);
                } else {
                    Mouse.scrollDownWhile(2000, () -> Camera.getZoom() <= level);
                }
            } else if (Mouse.move(getRandomPointOnRectangle(canvas.getRectangle()))) {
                MethodProvider.sleepUntil(() -> canvas.getRectangle().contains(Mouse.getPosition()), 1000 + Api.sleep());
            }
        }
    }

    private static Point getRandomPointOnRectangle(Rectangle rectangle) {
        int rMaxX = (int) rectangle.getMaxX();
        int rMinX = (int) rectangle.getMinX();
        int rMaxY = (int) rectangle.getMaxY();
        int rMinY = (int) rectangle.getMinY();
        return new Point(Calculations.random(rMinX, rMaxX), Calculations.random(rMinY, rMaxY));
    }

    public static void moveCamera(int hP, int lP, int hY, int lY) {
        if (!cameraWithin(hP, lP, hY, lY)) {
            CameraHandler cameraThread = new CameraHandler(hP, lP, hY, lY);
            cameraThread.start();
        }
    }

    private Thread thread;
    private String threadName = "Camera";
    private static int highYaw = 0;
    private static int lowYaw = 0;
    private static int highPitch = 0;
    private static int lowPitch = 0;

    private CameraHandler(int hP, int lP, int hY, int lY) {
        highYaw = hY;
        lowYaw = lY;
        highPitch = hP;
        lowPitch = lP;
    }

    private void start() {
        Api.updateState("Starting Camera Thread");
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    @Override
    public void run() {
        if (yawWithin(highYaw, lowYaw) && !pitchWithin(highPitch, lowPitch)) {
            Camera.keyboardRotateToPitch(Calculations.random(lowPitch, highPitch));
        } else if (!yawWithin(highYaw, lowYaw) && pitchWithin(highPitch, lowPitch)) {
            Camera.keyboardRotateToYaw(Calculations.random(lowYaw, highYaw));
        } else {
            Camera.keyboardRotateTo(yawWithin(highYaw, lowYaw) ? Camera.getYaw() : Calculations.random(lowYaw, highYaw), pitchWithin(highPitch, lowPitch) ? Camera.getPitch() : Calculations.random(lowPitch, highPitch));
        }
    }

    private static boolean cameraWithin(int hP, int lP, int hY, int lY) {
        return pitchWithin(hP, lP) && yawWithin(hY, lY);
    }

    private static boolean yawWithin(int hY, int lY) {
        return Camera.getYaw() <= highYaw && Camera.getYaw() >= lowYaw;
    }

    private static boolean pitchWithin(int hP, int lP) {
        return Camera.getPitch() <= highPitch && Camera.getPitch() >= lowPitch;
    }

}
