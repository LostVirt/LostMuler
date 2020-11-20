package org.dreambot.framework.gui.components;

import org.dreambot.framework.gui.UIColours;

import javax.swing.*;

public class DreamLabel extends JLabel {

    public DreamLabel() {
        this("");
    }

    public DreamLabel(String text) {
        super(text);
        setOpaque(true);
        setForeground(UIColours.TEXT_COLOR);
        setBackground(UIColours.BODY_COLOUR);
    }
}
