package org.dreambot.framework.gui.components;

import org.dreambot.framework.gui.UIColours;

import javax.swing.*;

public class DreamLabelTitle extends JLabel {

    public DreamLabelTitle() {
        this("");
    }

    public DreamLabelTitle(String text) {
        super(text);
        setOpaque(true);
        setForeground(UIColours.TITLE_COLOR);
        setBackground(UIColours.BODY_COLOUR);
    }
}
