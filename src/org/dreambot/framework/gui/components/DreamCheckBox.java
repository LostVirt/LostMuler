package org.dreambot.framework.gui.components;

import org.dreambot.framework.gui.UIColours;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serializable;

public class DreamCheckBox extends JCheckBox {

    public DreamCheckBox() {
       this("", false);
    }

    public DreamCheckBox(String text, boolean direction) {
        setText(text);
        setForeground(UIColours.TEXT_COLOR);
        setOpaque(true);
        setIcon(new CheckLAF());
        setHorizontalTextPosition(direction ? SwingConstants.LEFT : SwingConstants.RIGHT);
        setBackground(UIColours.BODY_COLOUR);
        setFocusPainted(false);
    }

    private class CheckLAF implements Icon, UIResource, Serializable {

        protected int getControlSize() {
            return 13;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            JCheckBox cb = (JCheckBox) c;
            ButtonModel model = cb.getModel();
            int controlSize = getControlSize();
            if (model.isEnabled()) {
                if (model.isPressed() && model.isArmed()) {
                    g.setColor(UIColours.BUTTON_COLOUR.brighter());
                    g.fillRect(x, y, controlSize - 1, controlSize - 1);
                } else {
                    g.setColor(UIColours.BUTTON_COLOUR.brighter());
                    g.fillRect(x, y, controlSize, controlSize);
                    g.setColor(UIColours.BUTTON_COLOUR);
                    g.fillRect(x,y,controlSize - 1,controlSize - 1);
                }
                g.setColor(UIColours.TEXT_COLOR);
            } else {
                g.setColor(UIColours.BUTTON_COLOUR);
                g.drawRect(x, y, controlSize - 1, controlSize - 1);
            }
            if (model.isSelected()) {
                drawCheck(c, g, x, y);
            }
        }

        protected void drawCheck(Component c, Graphics g, int x, int y) {
            int controlSize = getControlSize();
            g.fillRect(x + 3, y + 5, 2, controlSize - 8);
            g.drawLine(x + (controlSize - 4), y + 3, x + 5, y + (controlSize - 6));
            g.drawLine(x + (controlSize - 4), y + 4, x + 5, y + (controlSize - 5));
        }

        public int getIconWidth() {
            return getControlSize();
        }

        public int getIconHeight() {
            return getControlSize();
        }

    }

}
