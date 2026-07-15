package server;

import model.Message;
import model.User;
import util.ConsoleHelper;
import util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Serves a single connected client on its own thread.
 * <p>
 * Performs the username handshake (rejecting duplicates), relays incoming
 * chat lines to the {@link ServerManager} for broadcasting, answers the
 * {@code /list} command, and guarantees cleanup on any disconnect — expected
 * or not. An exception in one handler never affects other clients.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ServerManager serverManager;

    private BufferedReader reader;
    private PrintWriter writer;
    private User user;

    /**
     * Creates a handler for one client connection.
     *
     * @param clientSocket  the accepted client socket
     * @param serverManager the shared registry and broadcaster
     */
    public ClientHandler(Socket clientSocket, ServerManager serverManager) {
        this.clientSocket = clientSocket;
        this.serverManager = serverManager;
    }

    /**
     * Runs the client session: handshake, message loop, and cleanup.
     */
    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            if (!performUsernameHandshake()) {
                return;
            }

            serverManager.broadcastNotification(user.getUsername() + Constants.NOTIFICATION_JOINED);
            messageLoop();
        } catch (SocketException exception) {
            // Abrupt client disconnect — handled in cleanup; not a server error.
        } catch (IOException exception) {
            ConsoleHelper.printError("Connection error with "
                    + describeClient() + ": " + exception.getMessage());
        } finally {
            cleanup();
        }
    }

    /**
     * Sends one line of text to this client.
     *
     * @param line the line to send
     */
    public void sendLine(String line) {
        if (writer != null) {
            writer.println(line);
        }
    }

    /**
     * Returns this client's username, or a placeholder before handshake.
     *
     * @return username string
     */
    public String getUsername() {
        return user != null ? user.getUsername() : "(connecting)";
    }

    /**
     * Closes the underlying socket, releasing the handler thread.
     */
    public void closeConnection() {
        try {
            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException exception) {
            // Best-effort close during shutdown.
        }
    }

    /**
     * Negotiates a unique username with the client.
     * <p>
     * Repeats the {@link Constants#PROTOCOL_SUBMIT_USERNAME} prompt until the
     * client supplies a non-blank name that is not already registered.
     * </p>
     *
     * @return {@code true} when a username was accepted; {@code false} if the
     *         client disconnected during the handshake
     * @throws IOException if reading from the client fails
     */
    private boolean performUsernameHandshake() throws IOException {
        while (true) {
            writer.println(Constants.PROTOCOL_SUBMIT_USERNAME);
            String requestedName = reader.readLine();

            if (requestedName == null) {
                return false;
            }

            requestedName = requestedName.trim();
            if (requestedName.isBlank()) {
                continue;
            }

            if (serverManager.registerClient(requestedName, this)) {
                this.user = new User(requestedName);
                writer.println(Constants.PROTOCOL_USERNAME_ACCEPTED);
                return true;
            }

            writer.println(Constants.PROTOCOL_USERNAME_TAKEN);
        }
    }

    /**
     * Reads chat lines from the client until it exits or disconnects.
     *
     * @throws IOException if the connection fails mid-session
     */
    private void messageLoop() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.isBlank()) {
                continue;
            }
            if (line.equalsIgnoreCase(Constants.COMMAND_EXIT)) {
                return;
            }
            if (line.equalsIgnoreCase(Constants.COMMAND_LIST)) {
                serverManager.sendUserList(this);
                continue;
            }

            serverManager.broadcastMessage(new Message(user.getUsername(), line));
        }
    }

    /**
     * Unregisters the client, announces the departure, and closes the socket.
     * Safe against partial initialization (e.g. handshake never finished).
     */
    private void cleanup() {
        if (user != null) {
            serverManager.unregisterClient(user.getUsername());
            serverManager.broadcastNotification(user.getUsername() + Constants.NOTIFICATION_LEFT);
        }
        closeConnection();
    }

    /**
     * Describes the client for server-side log messages.
     *
     * @return username or remote address
     */
    private String describeClient() {
        return user != null
                ? user.getUsername()
                : String.valueOf(clientSocket.getRemoteSocketAddress());
    }
}
