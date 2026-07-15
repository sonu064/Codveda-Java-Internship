package client;

import util.ConsoleHelper;
import util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient {

    private final String host;
    private final int port;
    private final Scanner scanner;

    private Socket socket;
    private BufferedReader serverReader;
    private PrintWriter serverWriter;
    private MessageReceiver messageReceiver;

    private volatile boolean running;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.scanner = new Scanner(System.in);
        this.running = false;
    }


    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : Constants.DEFAULT_HOST;
        int port = args.length > 1 ? parsePort(args[1]) : Constants.DEFAULT_PORT;

        new ChatClient(host, port).start();
    }


    public void start() {
        ConsoleHelper.printBanner("MULTITHREADED CHAT CLIENT");

        if (!connectToServer()) {
            return;
        }

        try {
            if (!performUsernameHandshake()) {
                ConsoleHelper.printError("Connection closed before a username was accepted.");
                return;
            }

            running = true;
            ConsoleHelper.printInfo("Connected. Type " + Constants.COMMAND_HELP
                    + " for commands, " + Constants.COMMAND_EXIT + " to leave.");
            System.out.println();

            startMessageReceiver();
            inputLoop();
        } catch (IOException exception) {
            ConsoleHelper.printError("Lost connection to server: " + exception.getMessage());
        } finally {
            disconnect();
        }
    }


    private boolean connectToServer() {
        ConsoleHelper.printInfo("Connecting to " + host + ":" + port + " ...");
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), Constants.CONNECT_TIMEOUT_MILLIS);
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverWriter = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException exception) {
            ConsoleHelper.printError("Could not connect to " + host + ":" + port
                    + " - " + exception.getMessage());
            ConsoleHelper.printError("Make sure the server is running (java -cp out server.ChatServer).");
            return false;
        }
    }


    private boolean performUsernameHandshake() throws IOException {
        String serverLine;
        while ((serverLine = serverReader.readLine()) != null) {
            switch (serverLine) {
                case Constants.PROTOCOL_SUBMIT_USERNAME -> {
                    ConsoleHelper.printPrompt("Enter username: ");
                    if (!scanner.hasNextLine()) {
                        return false;
                    }
                    serverWriter.println(scanner.nextLine().trim());
                }
                case Constants.PROTOCOL_USERNAME_TAKEN ->
                        ConsoleHelper.printError("That username is already taken. Try another.");
                case Constants.PROTOCOL_USERNAME_ACCEPTED -> {
                    return true;
                }
                default -> ConsoleHelper.printChat(serverLine);
            }
        }
        return false;
    }


    private void startMessageReceiver() {
        messageReceiver = new MessageReceiver(serverReader, this::handleServerDisconnect);
        Thread receiverThread = new Thread(messageReceiver, "chat-message-receiver");
        receiverThread.setDaemon(true);
        receiverThread.start();
    }


    private void inputLoop() {
        while (running && scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                continue;
            }
            if (input.equalsIgnoreCase(Constants.COMMAND_EXIT)) {
                serverWriter.println(Constants.COMMAND_EXIT);
                return;
            }
            if (input.equalsIgnoreCase(Constants.COMMAND_HELP)) {
                printHelp();
                continue;
            }
            if (input.equalsIgnoreCase(Constants.COMMAND_CLEAR)) {
                ConsoleHelper.clearScreen();
                continue;
            }

            serverWriter.println(input);
        }
    }


    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  " + Constants.COMMAND_HELP + "   Show this help");
        System.out.println("  " + Constants.COMMAND_LIST + "   List online users");
        System.out.println("  " + Constants.COMMAND_CLEAR + "  Clear the screen");
        System.out.println("  " + Constants.COMMAND_EXIT + "   Leave the chat");
    }


    private void handleServerDisconnect() {
        running = false;
        ConsoleHelper.printInfo("Press Enter to close the client.");
    }


    private void disconnect() {
        running = false;
        if (messageReceiver != null) {
            messageReceiver.stop();
        }
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException exception) {
        }
        ConsoleHelper.printInfo("You have left the chat. Goodbye!");
    }


    private static int parsePort(String portArgument) {
        try {
            return Integer.parseInt(portArgument);
        } catch (NumberFormatException exception) {
            ConsoleHelper.printError("Invalid port '" + portArgument
                    + "'. Using default port " + Constants.DEFAULT_PORT + ".");
            return Constants.DEFAULT_PORT;
        }
    }
}
