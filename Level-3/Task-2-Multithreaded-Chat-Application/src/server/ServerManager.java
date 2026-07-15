package server;

import model.Message;
import util.ConsoleHelper;
import util.Constants;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe registry and broadcaster for connected chat clients.
 * <p>
 * Owns the mapping of usernames to their {@link ClientHandler} instances and
 * provides all fan-out operations: chat broadcasts, join/leave notifications,
 * user listing, and shutdown notices. Backed by {@link ConcurrentHashMap} so
 * handlers on different threads can register and broadcast without external
 * locking.
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class ServerManager {

    /** Connected clients keyed by lowercase username for case-insensitive uniqueness. */
    private final Map<String, ClientHandler> connectedClients;

    /**
     * Creates an empty server manager.
     */
    public ServerManager() {
        this.connectedClients = new ConcurrentHashMap<>();
    }

    /**
     * Atomically registers a client under the given username.
     *
     * @param username the requested display name
     * @param handler  the handler serving this client
     * @return {@code true} if registered; {@code false} if the name is already taken
     */
    public boolean registerClient(String username, ClientHandler handler) {
        return connectedClients.putIfAbsent(normalize(username), handler) == null;
    }

    /**
     * Removes a client from the registry.
     *
     * @param username the username to remove
     */
    public void unregisterClient(String username) {
        if (username != null) {
            connectedClients.remove(normalize(username));
        }
    }

    /**
     * Broadcasts a chat message to every connected client.
     *
     * @param message the message to broadcast
     */
    public void broadcastMessage(Message message) {
        String formatted = message.format();
        for (ClientHandler handler : connectedClients.values()) {
            handler.sendLine(formatted);
        }
    }

    /**
     * Broadcasts a server notification (join/leave/shutdown) to every client.
     *
     * @param notificationText the notification body
     */
    public void broadcastNotification(String notificationText) {
        broadcastMessage(new Message(Constants.SERVER_NAME, notificationText));
        ConsoleHelper.printInfo(notificationText);
    }

    /**
     * Sends the sorted list of online users to one requesting client.
     *
     * @param requester the handler that asked for the list
     */
    public void sendUserList(ClientHandler requester) {
        TreeSet<String> usernames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (ClientHandler handler : connectedClients.values()) {
            usernames.add(handler.getUsername());
        }

        StringBuilder listing = new StringBuilder("Online users (" + usernames.size() + "):");
        for (String name : usernames) {
            listing.append(System.lineSeparator()).append("  - ").append(name);
        }
        requester.sendLine(new Message(Constants.SERVER_NAME, listing.toString()).format());
    }

    /**
     * Notifies all clients that the server is shutting down and closes their
     * connections.
     */
    public void notifyServerShutdown() {
        broadcastMessage(new Message(Constants.SERVER_NAME,
                "Server is shutting down. You have been disconnected."));
        for (ClientHandler handler : connectedClients.values()) {
            handler.closeConnection();
        }
        connectedClients.clear();
    }

    /**
     * Returns the number of currently connected clients.
     *
     * @return connected client count
     */
    public int getOnlineCount() {
        return connectedClients.size();
    }

    /**
     * Normalizes a username for case-insensitive registry keys.
     *
     * @param username raw username
     * @return normalized key
     */
    private String normalize(String username) {
        return username.trim().toLowerCase();
    }
}
