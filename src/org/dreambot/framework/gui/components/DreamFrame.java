package org.dreambot.framework.gui.components;


import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.framework.gui.UIColours;
import org.dreambot.framework.gui.util.VisualTools;
import org.dreambot.framework.Api.Api;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class DreamFrame extends JFrame {

    public DreamFrame(String title, BufferedImage logo) {
        setLayout(new BorderLayout());
        setTitle(title);
        setBackground(UIColours.MENU_COLOR);
        getContentPane().setBackground(UIColours.MENU_COLOR);
        setIconImage(logo);
        setAlwaysOnTop(true);
        setMinimumSize(this.getSize());

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                dispose();
                ScriptManager.getScriptManager().stop();
            }

        });
    }

    @Override
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }

}

