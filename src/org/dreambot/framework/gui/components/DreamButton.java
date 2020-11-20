package org.dreambot.framework.gui.components;

import org.dreambot.framework.gui.UIColours;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DreamButton extends JButton {

    public DreamButton(String text, boolean bordered) {
        super(text);
        setFocusPainted(false);
        setFocusable(false);
        setBackground(UIColours.BUTTON_COLOUR);
        setForeground(UIColours.TEXT_COLOR);
        setOpaque(true);
        if (bordered) {
            setBorder(new CompoundBorder(new LineBorder(Color.darkGray),
                    new EmptyBorder(2, 2, 2, 2)));
        } else {
            setBorder(new CompoundBorder());
        }
        setContentAreaFilled(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(UIColours.MENU_COLOR.darker());
        } else if (getModel().isRollover()) {
            g.setColor(UIColours.MENU_COLOR.brighter());
        } else {
            g.setColor(UIColours.MENU_COLOR);
        }
        g.fillRect(0,0,getWidth(),getHeight());
        super.paintComponent(g);
    }
}
