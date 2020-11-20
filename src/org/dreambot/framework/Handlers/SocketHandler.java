package org.dreambot.framework.Handlers;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.framework.Api.Api;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class SocketHandler implements Runnable {

    private Thread thread;
    private String threadName;
    private int port;

    public SocketHandler(String threadName, int port) {
        this.threadName = threadName;
        this.port = port;
    }

    private String serverState;
    private String botRequestData;
    private String botIdentifier;
    private String botName;
    private int botWorld;
    private int botRequestAmount;

    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    private boolean running = true;

    @Override
    public void run() {
        this.serverState = "Loading Server";
        try {
            this.server = new ServerSocket(this.port);
            this.serverState = "Waiting for a Client";

            this.socket = server.accept();
            this.serverState = "Client Found!";

            this.in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));

            while (true) {
                try {
                    this.botRequestData = this.in.readUTF();
                    String[] splitBotData = this.botRequestData.split(",");
                    this.botIdentifier = splitBotData[0];
                    this.botName = splitBotData[1];
                    this.botWorld = parseInt(splitBotData[2]);
                    this.botRequestAmount = parseInt(splitBotData[3]);
                    MethodProvider.log(Arrays.toString(splitBotData));
                    break;
                } catch (IOException i) {
                    MethodProvider.log(i.getMessage());
                }
            }
            this.serverState = "Closing connection";
        } catch (IOException e) {
            MethodProvider.log(e.getMessage());
        } finally {
            try {
                this.serverState = "Closed Connection";
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MethodProvider.log("Stopped Thread");
    }

    public void start() {
        if (this.thread == null) {
            MethodProvider.log("Starting Thread..");
            this.thread = new Thread(this, threadName);
            this.thread.start();
        }
    }

    public void stop() {
        MethodProvider.log("Stopping Thread..");
        this.running = false;
    }


    public void close() throws IOException {
        if (this.socket != null) {
            this.socket.close();
        }

        if (this.server != null) {
            this.server.close();
        }

        if (this.in != null) {
            this.in.close();
        }
    }

    public boolean isStarted() {
        return this.thread.isAlive();
    }

    public String getServerState() {
        return this.serverState;
    }

    public String getBotIdentifier() {
        return this.botIdentifier;
    }

    public String getBotName() {
        return this.botName;
    }

    public int getBotWorld() {
        return this.botWorld;
    }

    public int getBotRequestAmount() {
        return this.botRequestAmount;
    }

}