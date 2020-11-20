package org.dreambot.framework.gui;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.framework.Handlers.SocketHandler;
import org.dreambot.framework.Main;
import org.dreambot.framework.gui.components.*;
import org.dreambot.framework.Api.Api;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import static java.lang.Integer.parseInt;

public class ScriptFrame extends DreamFrame {

    private DreamPanel body, content;
    private DreamPanel body2, content2;

    public DreamButton start, login;
    public boolean startScript = false;

    public DreamTextField mulePort = new DreamHintTextField("Port Default: 1337");

    public ScriptFrame() throws IOException {
        super("LostMuler", ImageIO.read(new URL("https://www.osrsbox.com/osrsbox-db/items-icons/1003.png")));
        DreamTabbedPane tPane = new DreamTabbedPane();
        tPane.addTab("Start", body = new DreamPanel());
        tPane.addTab("Utilities", body2 = new DreamPanel());
        setLocationRelativeTo(null);
        add(tPane, BorderLayout.CENTER);
        setSize(250, 125);

        /**
         * Start & Settings Tab
         */
        body.setBorder(new EmptyBorder(7, 8, 7, 8));
        body.add(content = new DreamPanel(), BorderLayout.CENTER);
        content.setLayout(new GridLayout(0, 1, 0, 2));

        content.add(mulePort);
        content.add(start = new DreamButton("Start", true));

        //-----------------------------------//

        body2.setBorder(new EmptyBorder(7,8,7,8));
        body2.add(content2 = new DreamPanel(), BorderLayout.CENTER);
        content2.setLayout(new GridLayout(0, 1, 0, 2));

        content2.add(new DreamLabel("More Features Soon(TM)"));
        content2.add(login = new DreamButton("Login", true));

        tPane.addChangeListener(l -> {
            if (tPane.getSelectedIndex() == 0) {
                setSize(250, 125);
            } else if (tPane.getSelectedIndex() == 1) {
                setSize(250, 125);
            }
        });

        start.addActionListener(l -> {
            if (!startScript) {
                Api.mulePort = mulePort.getText().trim().isEmpty() ? 1337 : parseInt(mulePort.getText().trim());
                MethodProvider.log("Started Script! - Muling on Port: " + Api.mulePort);
                Api.server = new SocketHandler("Server", Api.mulePort);
                Api.server.start();
                startScript = true;
            } else {
                MethodProvider.log("Already Started Script..");
                MethodProvider.log(Api.server.isStarted());
            }
        });

        login.addActionListener(l -> {
            Main.loginButton = true;
        });

    }


    public static void main(String... args) throws IOException {
        JFrame frame = new ScriptFrame();
        frame.setVisible(true);
    }



}