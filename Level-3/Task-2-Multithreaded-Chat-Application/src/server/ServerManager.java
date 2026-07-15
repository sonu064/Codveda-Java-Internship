package server;

import model.Message;
import util.ConsoleHelper;
import util.Constants;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManager {

    private final Map<String, ClientHandler> connectedClients;
    public ServerManager() {
        this.connectedClients = new ConcurrentHashMap<>();
    }

    public boolean registerClient(String username, ClientHandler handler) {
        return connectedClients.putIfAbsent(normalize(username), handler) == null;
    }


    public void unregisterClient(String username) {
        if (username != null) {
            connectedClients.remove(normalize(username));
        }
    }

    public void broadcastMessage(Message message) {
        String formatted = message.format();
        for (ClientHandler handler : connectedClients.values()) {
            handler.sendLine(formatted);
        }
    }

    public void broadcastNotification(String notificationText) {
        broadcastMessage(new Message(Constants.SERVER_NAME, notificationText));
        ConsoleHelper.printInfo(notificationText);
    }

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


    public void notifyServerShutdown() {
        broadcastMessage(new Message(Constants.SERVER_NAME,
                "Server is shutting down. You have been disconnected."));
        for (ClientHandler handler : connectedClients.values()) {
            handler.closeConnection();
        }
        connectedClients.clear();
    }


    public int getOnlineCount() {
        return connectedClients.size();
    }

    private String normalize(String username) {
        return username.trim().toLowerCase();
    }
}
