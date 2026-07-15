package client;

import util.ConsoleHelper;

import java.io.BufferedReader;
import java.io.IOException;


public class MessageReceiver implements Runnable {

    private final BufferedReader serverReader;
    private final Runnable onDisconnect;

    private volatile boolean listening;


    public MessageReceiver(BufferedReader serverReader, Runnable onDisconnect) {
        this.serverReader = serverReader;
        this.onDisconnect = onDisconnect;
        this.listening = true;
    }
/
    @Override
    public void run() {
        try {
            String line;
            while (listening && (line = serverReader.readLine()) != null) {
                ConsoleHelper.printChat(line);
            }
        } catch (IOException exception) {
        }

        if (listening) {
            ConsoleHelper.printInfo("Disconnected from server.");
            onDisconnect.run();
        }
    }


    public void stop() {
        listening = false;
    }
}
