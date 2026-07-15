package client;

import util.ConsoleHelper;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Background listener that prints every line arriving from the server.
 * <p>
 * Runs on its own thread so the client can type and receive messages
 * simultaneously. When the server stream ends, it invokes the injected
 * disconnect callback so the client can shut down cleanly.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class MessageReceiver implements Runnable {

    private final BufferedReader serverReader;
    private final Runnable onDisconnect;

    private volatile boolean listening;

    /**
     * Creates a receiver bound to the server input stream.
     *
     * @param serverReader reader over the server socket's input stream
     * @param onDisconnect callback invoked when the server connection ends
     */
    public MessageReceiver(BufferedReader serverReader, Runnable onDisconnect) {
        this.serverReader = serverReader;
        this.onDisconnect = onDisconnect;
        this.listening = true;
    }

    /**
     * Reads and prints server lines until the stream closes.
     */
    @Override
    public void run() {
        try {
            String line;
            while (listening && (line = serverReader.readLine()) != null) {
                ConsoleHelper.printChat(line);
            }
        } catch (IOException exception) {
            // Stream closed — either by user exit or server shutdown.
        }

        if (listening) {
            ConsoleHelper.printInfo("Disconnected from server.");
            onDisconnect.run();
        }
    }

    /**
     * Stops the receiver silently (used when the user exits on purpose).
     */
    public void stop() {
        listening = false;
    }
}
