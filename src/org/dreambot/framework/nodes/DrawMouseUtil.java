package org.dreambot.framework.nodes;

/**
 * DrawMouseUtil by holic
 **/

import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import static org.dreambot.api.methods.MethodProvider.log;

public class DrawMouseUtil {
    LinkedList<MousePathPoint> mousePath = new LinkedList<MousePathPoint>();
    private boolean RAINBOW = false;
    private final int STROKE = 2;
    private int mX, mY;
    private long angle;
    private BasicStroke cursorStroke = new BasicStroke(STROKE);
    private final int randomMouse = Calculations.random(5);
    private final int randomMouseTrail = Calculations.random(7);
    private Color cursorColor = Color.WHITE;
    private Color trailColor = cursorColor;
    private final Color[] cursorColors = {new Color(78, 216, 255), new Color(90, 222, 98), new Color(215, 182, 77), new Color(232, 134, 124), new Color(215, 120, 124), new Color(183, 138, 215), Color.WHITE};
    private AffineTransform oldTransform;
    private int r = 0;
    private int g = 0;
    private int b = 0;
    private final int duration = 650;

    public DrawMouseUtil() {
        Client.getInstance().setDrawMouse(false);
    }

    public void setRainbow(boolean RAINBOW) {
        if (RAINBOW) {
            g = 255;
        } else {
            g = 0;
        }
        this.RAINBOW = RAINBOW;
    }

    public void setRandomColor() {
        if (Calculations.random(2) != 1) {
            log("Rainbow mouse!");
            setRainbow(true);
        } else {
            setRainbow(false);
            cursorColor = getRandomColour();
            trailColor = cursorColor;
        }
    }

    private Color getRandomColour() {
        return cursorColors[Calculations.random(cursorColors.length - 1)];
    }

    public void setCursorStroke(BasicStroke cursorStroke) {
        this.cursorStroke = cursorStroke;
    }

    public void setCursorColor(Color cursorColor) {
        this.cursorColor = cursorColor;
    }

    public void setTrailColor(Color trailColor) {
        this.trailColor = trailColor;
    }

    public void drawRandomMouse(Graphics g) {
        switch (randomMouse) {
            case 0:
                drawPlusMouse(g);
                break;
            case 1:
                drawCrossMouse(g);
                break;
            case 2:
                drawCircleMouse(g);
                break;
            case 3:
                drawDotMouse(g);
                break;
            case 4:
                drawRotatingCrossMouse(g);
                break;
            case 5:
                drawRotatingCircleMouse(g);
                break;
        }
    }

    public void drawRandomMouseTrail(Graphics g) {
        switch (randomMouseTrail) {
            case 0:
                drawTrail(g);
                break;
            case 1:
                drawZoomTrail(g);
                break;
            case 2:
                drawPlusTrail(g);
                break;
            case 3:
                drawCircleTrail(g);
                break;
            case 4:
                drawDotTrail(g);
                break;
            case 5:
                drawRotatingSlashTrail(g);
                break;
            case 6:
                drawRotatingCrossTrail(g);
                break;
            case 7:
                drawTextTrail(g, "Virtest");
                break;
        }
    }

    /**
     * * ** ** ** **
     * Mouse cursor
     * * ** ** ** **
     **/

    public void drawPlusMouse(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int s = 4;
        Point cP = Client.getMousePosition();
        int cX = (int) cP.getX();
        int cY = (int) cP.getY();
        g2.setColor(Color.BLACK);
        g2.setStroke(cursorStroke);

        /* + Cursor */
        g2.drawLine(cX - s + 1, cY + 1, cX + s + 1, cY + 1);
        g2.drawLine(cX + 1, cY - s + 1, cX + 1, cY + s + 1);

        g2.setColor(cursorColor);

        g2.drawLine(cX - s, cY, cX + s, cY);
        g2.drawLine(cX, cY - s, cX, cY + s);
        g2.setStroke(new BasicStroke(1));
    }

    public void drawCrossMouse(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int s = 3;
        Point cP = Client.getMousePosition();
        int cX = (int) cP.getX();
        int cY = (int) cP.getY();
        g2.setStroke(cursorStroke);
        g2.setColor(Color.BLACK);

        /* X Cursor */
        g2.drawLine(cX - s + 1, cY - s + 1, cX + s + 1, cY + s + 1);
        g2.drawLine(cX - s + 1, cY + s + 1, cX + s + 1, cY - s + 1);

        g2.setColor(cursorColor);

        g2.drawLine(cX - s, cY - s, cX + s, cY + s);
        g2.drawLine(cX - s, cY + s, cX + s, cY - s);
        g2.setStroke(new BasicStroke(1));
    }

    public void drawCircleMouse(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        int mX = Client.getMousePosition().x;
        mY = Client.getMousePosition().y;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        if (mX != -1) {
            g2.setStroke(cursorStroke);
            g2.setColor(Color.BLACK);
            g2.drawOval(mX - 1, mY - 1, 4, 4);
            g2.setColor(cursorColor);
            g2.drawOval(mX - 2, mY - 2, 4, 4);
            g2.setStroke(new BasicStroke(1));
        }
    }

    public void drawDotMouse(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        int mX = Client.getMousePosition().x;
        mY = Client.getMousePosition().y;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        if (mX != -1) {
            g2.setStroke(cursorStroke);
            g2.setColor(Color.BLACK);
            g2.drawOval(mX - 1, mY - 1, 4, 4);
            g2.setColor(cursorColor);
            g2.drawOval(mX - 2, mY - 2, 4, 4);
            g2.setStroke(new BasicStroke(1));
        }
    }

    public void drawRotatingCircleMouse(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        int mX = Client.getMousePosition().x;
        mY = Client.getMousePosition().y;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        if (mX != -1) {
            g2.setStroke(cursorStroke);
            g2.drawOval(mX - 2, mY - 2, 4, 4);
            g2.setColor(cursorColor);
            g2.rotate(Math.toRadians(angle += 6), mX, mY);
            g2.draw(new Arc2D.Double(mX - 6, mY - 6, 12, 12, 330, 60, Arc2D.OPEN));
            g2.draw(new Arc2D.Double(mX - 6, mY - 6, 12, 12, 151, 60, Arc2D.OPEN));
            g2.setTransform(oldTransform);
            g2.setStroke(new BasicStroke(1));
        }
    }

    public void drawRotatingCrossMouse(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        Point cP = Client.getMousePosition();
        int cX = (int) cP.getX();
        int cY = (int) cP.getY();
        int s = 4;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        if (mX != -1) {
            g2.setStroke(cursorStroke);
            g2.setColor(Color.BLACK);
            //g.rotate(Math.toRadians(angle+=1), mX, mY);
            Line2D lineShadow = new Line2D.Double(cX - s + 1, cY + 1, cX + s + 1, cY + 1);
            Line2D lineShadow2 = new Line2D.Double(cX + 1, cY - s + 1, cX + 1, cY + s + 1);
            AffineTransform atS =
                    AffineTransform.getRotateInstance(
                            Math.toRadians(angle += 4), cX + 1, cY + 1);
            AffineTransform atS2 =
                    AffineTransform.getRotateInstance(
                            Math.toRadians(angle), cX + 1, cY + 1);
            g2.draw(atS.createTransformedShape(lineShadow));
            g2.draw(atS2.createTransformedShape(lineShadow2));

            g2.setColor(nextCursorColor());
            Line2D line = new Line2D.Double(cX - s, cY, cX + s, cY);
            Line2D line2 = new Line2D.Double(cX, cY - s, cX, cY + s);
            AffineTransform at =
                    AffineTransform.getRotateInstance(
                            Math.toRadians(angle += 4), cX, cY);
            AffineTransform at2 =
                    AffineTransform.getRotateInstance(
                            Math.toRadians(angle), cX, cY);
            // Draw the rotated line
            g2.draw(at.createTransformedShape(line));
            g2.draw(at2.createTransformedShape(line2));

            g2.setStroke(new BasicStroke(1));
        }
    }

    /**
     * * ** ** ** **
     * Mouse trails
     * * ** ** ** **
     **/

    public void drawTrail(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        int mX = Client.getMousePosition().x;
        mY = Client.getMousePosition().y;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();

        Point clientCursor = Client.getMousePosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, duration);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                Color c = nextTrailColor();
                int tmpcursorStroke = STROKE;
                if (STROKE > 1)
                    tmpcursorStroke = (a.getAlpha() > 175 ? STROKE : STROKE - 1);
                g2.setStroke(new BasicStroke(tmpcursorStroke));
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), a.getAlpha()));    //trail color
                g2.drawLine(a.x, a.y, lastPoint.x, lastPoint.y);
                g2.setStroke(new BasicStroke(1));
            }
            lastPoint = a;
        }
    }

    public void drawZoomTrail(Graphics g) {
        String zoom = "zoom zoom  ";
        int zoomIndex = 0, zoomIndexStart = -1;
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        g2.setFont(new Font("default", Font.BOLD, 12));
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();

        Point clientCursor = Client.getMousePosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, duration * 2);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (zoomIndex >= zoom.length())
                zoomIndex = 0;

            String toDraw = String.valueOf(zoom.toCharArray()[zoomIndex]);
            if (lastPoint != null) {
                Color c = nextTrailColor();
                toDraw = a.getAlpha() > 175 ? toDraw.toUpperCase() : toDraw;
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), a.getAlpha()));    //trail color
                g2.drawString(toDraw, a.x, a.y + 5);
            }
            lastPoint = a;
            zoomIndex++;
        }
        g2.setFont(new Font("default", Font.PLAIN, 12));
    }

    public void drawTextTrail(Graphics g, String trail) {
        int zoomIndex = 0, zoomIndexStart = -1;
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        g2.setFont(new Font("default", Font.BOLD, 12));
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();

        Point clientCursor = Client.getMousePosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, duration * 2);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                Color c = nextTrailColor();
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), a.getAlpha()));    //trail color
                g2.drawString(trail, a.x, a.y);
            }
            lastPoint = a;
            zoomIndex++;
        }
        g2.setFont(new Font("default", Font.PLAIN, 12));
    }


    public void drawDotTrail(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();

        Point clientCursor = Client.getMousePosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, duration * 2);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                Color c = nextTrailColor();
                int size = a.getAlpha() > 200 ? 6 : a.getAlpha() > 150 ? 5 : a.getAlpha() > 100 ? 4 : a.getAlpha() > 50 ? 3 : 2;

                g2.setStroke(cursorStroke);
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), a.getAlpha()));    //trail color
                g2.fillOval(a.x, a.y, size, size);
                g2.setStroke(new BasicStroke(1));
            }
            lastPoint = a;
        }
    }

    public void drawCircleTrail(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();

        Point clientCursor = Client.getMousePosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, duration * 2);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                Color c = nextTrailColor();
                int size = a.getAlpha() > 200 ? 6 : a.getAlpha() > 150 ? 5 : a.getAlpha() > 100 ? 4 : a.getAlpha() > 50 ? 3 : 2;

                g2.setStroke(cursorStroke);
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), a.getAlpha()));    //trail color
                g2.drawOval(a.x, a.y, size, size);
                g2.setStroke(new BasicStroke(1));
            }
            lastPoint = a;
        }
    }

    public void drawPlusTrail(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();

        Point clientCursor = Client.getMousePosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, duration * 2);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                Color c = nextTrailColor();
                int size = a.getAlpha() > 200 ? 5 : a.getAlpha() > 150 ? 4 : a.getAlpha() > 100 ? 3 : a.getAlpha() > 50 ? 2 : 1;

                g2.setStroke(cursorStroke);
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), a.getAlpha()));    //trail color

                g2.drawLine(a.x - size + 1, a.y + 1, a.x + size + 1, a.y + 1);
                g2.drawLine(a.x + 1, a.y - size + 1, a.x + 1, a.y + size + 1);
                g2.setStroke(new BasicStroke(1));
            }
            lastPoint = a;
        }
    }

    public void drawRotatingSlashTrail(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();

        Point clientCursor = Client.getMousePosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, duration * 2);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                Color c = nextTrailColor();
                int size = a.getAlpha() > 200 ? 5 : a.getAlpha() > 150 ? 4 : a.getAlpha() > 100 ? 3 : a.getAlpha() > 50 ? 2 : 1;

                g2.setStroke(cursorStroke);
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), a.getAlpha()));    //trail color
                Line2D line = new Line2D.Double(a.x - size, a.y, a.x + size, a.y);
                Line2D line2 = new Line2D.Double(a.x, a.y - size, a.x, a.y + size);

                AffineTransform at =
                        AffineTransform.getRotateInstance(
                                Math.toRadians(angle += 4), a.x, a.y);
                g2.draw(at.createTransformedShape(line));
                g2.setStroke(new BasicStroke(1));
            }
            lastPoint = a;
        }
    }

    public void drawRotatingCrossTrail(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        oldTransform = g2.getTransform();
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();

        Point clientCursor = Client.getMousePosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, duration * 2);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                Color c = nextTrailColor();
                int size = a.getAlpha() > 200 ? 5 : a.getAlpha() > 150 ? 4 : a.getAlpha() > 100 ? 3 : a.getAlpha() > 50 ? 2 : 1;

                g2.setStroke(cursorStroke);
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), a.getAlpha()));    //trail color
                Line2D line = new Line2D.Double(a.x - size, a.y, a.x + size, a.y);
                Line2D line2 = new Line2D.Double(a.x, a.y - size, a.x, a.y + size);

                AffineTransform at =
                        AffineTransform.getRotateInstance(
                                Math.toRadians(angle += 4), a.x, a.y);
                g2.draw(at.createTransformedShape(line));
                g2.draw(at.createTransformedShape(line2));
                g2.setStroke(new BasicStroke(1));
            }
            lastPoint = a;
        }
    }


    public void nextRGB() {
        if (r == 255 && g < 255 & b == 0) {
            g++;
        }
        if (g == 255 && r > 0 && b == 0) {
            r--;
        }
        if (g == 255 && b < 255 && r == 0) {
            b++;
        }
        if (b == 255 && g > 0 && r == 0) {
            g--;
        }
        if (b == 255 && r < 255 && g == 0) {
            r++;
        }
        if (r == 255 && b > 0 && g == 0) {
            b--;
        }
    }

    public Color currentCursorColor() {
        if (!RAINBOW) {
            return cursorColor;
        } else {
            return new Color(r, g, b);
        }
    }

    public Color currentTrailColor() {
        if (!RAINBOW) {
            return trailColor;
        } else {
            return new Color(r, g, b);
        }
    }

    public Color nextCursorColor() {
        nextRGB();
        return currentCursorColor();
    }

    public Color nextTrailColor() {
        if (!RAINBOW) //Don't call this if it is set to rainbow so we're not double calling nextRGB()
            nextRGB();
        return currentTrailColor();
    }

    public class MousePathPoint extends Point {

        private final long finishTime;
        private final double lastingTime;
        private final int alpha = 255;

        public MousePathPoint(int x, int y, int lastingTime) {
            super(x, y);
            this.lastingTime = lastingTime;
            finishTime = System.currentTimeMillis() + lastingTime;
        }

        public int getAlpha() {
            int newAlpha = ((int) ((finishTime - System.currentTimeMillis()) / (lastingTime / alpha)));
            if (newAlpha > 255)
                newAlpha = 255;
            if (newAlpha < 0)
                newAlpha = 0;
            return newAlpha;
        }

        public boolean isUp() {
            return System.currentTimeMillis() >= finishTime;
        }
    }
}
