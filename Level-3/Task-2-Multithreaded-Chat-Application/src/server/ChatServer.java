package server;

import util.ConsoleHelper;
import util.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ChatServer {

    private static final int SHUTDOWN_GRACE_SECONDS = 5;

    private final int port;
    private final ServerManager serverManager;
    private final ExecutorService clientThreadPool;

    private volatile boolean running;
    private ServerSocket serverSocket;


    public ChatServer(int port, ServerManager serverManager) {
        this.port = port;
        this.serverManager = serverManager;
        this.clientThreadPool = Executors.newCachedThreadPool();
        this.running = false;
    }


    public static void main(String[] args) {
        ServerManager serverManager = new ServerManager();
        ChatServer chatServer = new ChatServer(Constants.DEFAULT_PORT, serverManager);

        Runtime.getRuntime().addShutdownHook(
                new Thread(chatServer::stop, "chat-server-shutdown-hook"));

        chatServer.start();
    }

    public void start() {
        ConsoleHelper.printBanner("MULTITHREADED CHAT SERVER");

        try (ServerSocket socket = new ServerSocket(port)) {
            this.serverSocket = socket;
            this.running = true;

            ConsoleHelper.printInfo("Server started on port " + port + ".");
            ConsoleHelper.printInfo("Waiting for clients... (Ctrl+C to stop)");

            acceptClientsLoop(socket);
        } catch (IOException exception) {
            if (running) {
                ConsoleHelper.printError(
                        "Could not start server on port " + port + ": " + exception.getMessage());
                ConsoleHelper.printError(
                        "The port may already be in use by another application.");
            }
        } finally {
            stop();
        }
    }


    private void acceptClientsLoop(ServerSocket socket) {
        while (running && !socket.isClosed()) {
            try {
                Socket clientSocket = socket.accept();
                ConsoleHelper.printInfo(
                        "Incoming connection from " + clientSocket.getRemoteSocketAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, serverManager);
                clientThreadPool.submit(clientHandler);
            } catch (SocketException exception) {
                // Listening socket closed during shutdown — expected, exit quietly.
                if (running) {
                    ConsoleHelper.printError("Listening socket error: " + exception.getMessage());
                }
                return;
            } catch (IOException exception) {
                // A single failed accept must not bring the server down.
                ConsoleHelper.printError(
                        "Failed to accept a client connection: " + exception.getMessage());
            }
        }
    }


    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;

        ConsoleHelper.printInfo("Shutting down server...");
        serverManager.notifyServerShutdown();

        closeListeningSocket();
        shutdownThreadPool();

        ConsoleHelper.printInfo("Server stopped. Goodbye.");
    }


    private void closeListeningSocket() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException exception) {
                ConsoleHelper.printError(
                        "Error closing listening socket: " + exception.getMessage());
            }
        }
    }


    private void shutdownThreadPool() {
        clientThreadPool.shutdown();
        try {
            if (!clientThreadPool.awaitTermination(SHUTDOWN_GRACE_SECONDS, TimeUnit.SECONDS)) {
                clientThreadPool.shutdownNow();
            }
        } catch (InterruptedException exception) {
            clientThreadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
