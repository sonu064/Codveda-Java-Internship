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


public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ServerManager serverManager;

    private BufferedReader reader;
    private PrintWriter writer;
    private User user;


    public ClientHandler(Socket clientSocket, ServerManager serverManager) {
        this.clientSocket = clientSocket;
        this.serverManager = serverManager;
    }

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
        } catch (IOException exception) {
            ConsoleHelper.printError("Connection error with "
                    + describeClient() + ": " + exception.getMessage());
        } finally {
            cleanup();
        }
    }

    public void sendLine(String line) {
        if (writer != null) {
            writer.println(line);
        }
    }

    public String getUsername() {
        return user != null ? user.getUsername() : "(connecting)";
    }

    public void closeConnection() {
        try {
            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException exception) {

        }
    }


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

    private void cleanup() {
        if (user != null) {
            serverManager.unregisterClient(user.getUsername());
            serverManager.broadcastNotification(user.getUsername() + Constants.NOTIFICATION_LEFT);
        }
        closeConnection();
    }

    private String describeClient() {
        return user != null
                ? user.getUsername()
                : String.valueOf(clientSocket.getRemoteSocketAddress());
    }
}
