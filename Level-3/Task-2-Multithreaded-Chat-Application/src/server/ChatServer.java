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

/**
 * Entry point for the chat server.
 * <p>
 * Binds a {@link ServerSocket} to {@link Constants#DEFAULT_PORT}, accepts an
 * unlimited number of client connections, and hands each connection to a
 * {@link ClientHandler} running on a shared {@link ExecutorService}.
 * </p>
 * <p>
 * Client registration and message broadcasting are delegated to a
 * {@link ServerManager} instance supplied via constructor injection, keeping
 * this class focused solely on connection acceptance and lifecycle management
 * (Single Responsibility Principle).
 * </p>
 *
 * @author Sonu Singh
 * @version 1.0
 */
public class ChatServer {

    /** Seconds to wait for client threads to finish during shutdown. */
    private static final int SHUTDOWN_GRACE_SECONDS = 5;

    private final int port;
    private final ServerManager serverManager;
    private final ExecutorService clientThreadPool;

    private volatile boolean running;
    private ServerSocket serverSocket;

    /**
     * Creates a chat server with injected collaborators.
     *
     * @param port          the TCP port to listen on
     * @param serverManager the manager responsible for client registry and broadcasting
     */
    public ChatServer(int port, ServerManager serverManager) {
        this.port = port;
        this.serverManager = serverManager;
        this.clientThreadPool = Executors.newCachedThreadPool();
        this.running = false;
    }

    /**
     * Application entry point. Wires dependencies and starts the server.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        ServerManager serverManager = new ServerManager();
        ChatServer chatServer = new ChatServer(Constants.DEFAULT_PORT, serverManager);

        Runtime.getRuntime().addShutdownHook(
                new Thread(chatServer::stop, "chat-server-shutdown-hook"));

        chatServer.start();
    }

    /**
     * Starts the server: binds the listening socket and runs the accept loop.
     * <p>
     * Each accepted connection is wrapped in a {@link ClientHandler} and
     * submitted to the thread pool. A failure on one connection never stops
     * the accept loop; only a failure of the listening socket itself does.
     * </p>
     */
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

    /**
     * Accepts client connections until the server is stopped.
     *
     * @param socket the bound listening socket
     */
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

    /**
     * Stops the server gracefully.
     * <p>
     * Notifies connected clients, closes the listening socket, and shuts the
     * thread pool down — first politely, then forcibly after a grace period.
     * Safe to call more than once.
     * </p>
     */
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

    /**
     * Closes the listening socket, unblocking the accept loop.
     */
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

    /**
     * Shuts the client thread pool down, forcing termination after a grace period.
     */
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
