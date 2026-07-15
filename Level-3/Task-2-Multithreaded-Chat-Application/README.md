# Multithreaded Chat Application

A production-quality **client-server chat application** built with Core Java sockets and
multithreading. One server accepts unlimited clients; every message a client sends is
broadcast to all connected users in real time, with timestamps, join/leave notifications,
and chat commands.

Developed as **Level 3 – Task 2** of the **Java Development Internship at Codveda Technologies**.

---

## Project Overview

| | |
|---|---|
| **Project Name** | Multithreaded Chat Application |
| **Type** | Console Application (Client + Server) |
| **Language** | Java 21 (Java 17 compatible) |
| **Concurrency** | `ExecutorService`, one thread per client, background receiver thread |
| **Networking** | `ServerSocket`, `Socket`, blocking line-based protocol |

The server listens on port `5000`, accepts each incoming client on a dedicated thread
(`ClientHandler`), and fans out every message through a thread-safe `ServerManager`.
Each client runs two flows in parallel: the main thread reads keyboard input while a
`MessageReceiver` daemon thread prints everything arriving from the server.

---

## Features

### Server
- Accepts **multiple simultaneous clients** using a cached thread pool
- **Unique username handshake** — duplicate names (case-insensitive) are rejected
- **Broadcasts** every chat message to all connected users
- Announces **join / leave notifications** with timestamps
- Handles abrupt client disconnects without affecting other users
- **Graceful shutdown** (Ctrl+C) — notifies all clients before closing

### Client
- Connects with a **5-second timeout** and clear error messages
- **Simultaneous send & receive** via a background receiver thread
- Timestamped message format:

  ```
  [12:40 PM] Sonu:
  Hello everyone
  ```

- Chat commands:

  | Command | Action |
  |---------|--------|
  | `/help` | Show available commands |
  | `/list` | List all online users |
  | `/clear` | Clear the console screen |
  | `/exit` | Leave the chat gracefully |

- ANSI console colors when the terminal supports them

---

## Technologies Used

- **Java 21** (works on Java 17+)
- **Java Sockets** — `ServerSocket`, `Socket`, `InetSocketAddress`
- **Multithreading** — `ExecutorService`, `Thread`, `volatile`, `ConcurrentHashMap`
- **Java I/O** — `BufferedReader`, `PrintWriter`
- **java.time** — `LocalTime`, `DateTimeFormatter`

No external libraries — 100% Core Java.

---

## Folder Structure

```
Task-2-Multithreaded-Chat-Application
│
├── src
│   ├── server
│   │      ChatServer.java        # Entry point: accepts clients, owns thread pool
│   │      ClientHandler.java     # One thread per client: handshake + message loop
│   │      ServerManager.java     # Thread-safe registry + broadcaster
│   │
│   ├── client
│   │      ChatClient.java        # Entry point: connect, handshake, input loop
│   │      MessageReceiver.java   # Background thread printing server messages
│   │
│   ├── model
│   │      Message.java           # Immutable message with timestamp formatting
│   │      User.java              # Immutable connected-user representation
│   │
│   └── util
│          Constants.java         # Ports, commands, protocol markers, formats
│          ConsoleHelper.java     # Banners, colors, prompts, screen clearing
│
├── screenshots
├── README.md
└── .gitignore
```

---

## How to Run

### 1. Compile

From the `Task-2-Multithreaded-Chat-Application` folder:

```bash
javac -d out src/util/*.java src/model/*.java src/server/*.java src/client/*.java
```

### 2. Start the Server (Terminal 1)

```bash
java -cp out server.ChatServer
```

### 3. Start one or more Clients (Terminal 2, 3, ...)

```bash
java -cp out client.ChatClient
```

Optionally connect to a different host/port:

```bash
java -cp out client.ChatClient 192.168.1.10 5000
```

### 4. Chat!

Enter a unique username when prompted, then type messages. Open more terminals
and start more clients to chat between them.

---

## Sample Output

### Server Console

```
=============================================
          MULTITHREADED CHAT SERVER
=============================================

Server started on port 5000.
Waiting for clients... (Ctrl+C to stop)
Incoming connection from /127.0.0.1:52144
Sonu joined the chat
Incoming connection from /127.0.0.1:52147
Priya joined the chat
Priya left the chat
```

### Client Console

```
=============================================
          MULTITHREADED CHAT CLIENT
=============================================

Connecting to localhost:5000 ...
Enter username: Sonu
Connected. Type /help for commands, /exit to leave.

[09:14 AM] SERVER:
Sonu joined the chat
[09:14 AM] SERVER:
Priya joined the chat
Hello everyone
[09:15 AM] Sonu:
Hello everyone
[09:15 AM] Priya:
Hi Sonu!
/exit
You have left the chat. Goodbye!
```

---

## Screenshots

| Screenshot | Description |
|------------|-------------|
| `server-startup.png` | Server running and accepting connections |
| `client-chat.png` | Two clients chatting in real time |
| `user-list.png` | `/list` command showing online users |
| `duplicate-username.png` | Duplicate username rejected |

*(Add screenshots to the `screenshots/` folder.)*

---

## Architecture Notes

- **Single Responsibility** — networking (`ChatServer`), per-client session
  (`ClientHandler`), shared state (`ServerManager`), presentation
  (`ConsoleHelper`), and domain models (`Message`, `User`) are all separate classes.
- **Constructor Injection** — `ChatServer` receives its `ServerManager`;
  `ClientHandler` receives its socket and manager; `MessageReceiver` receives its
  reader and disconnect callback.
- **Thread Safety** — client registry uses `ConcurrentHashMap` with atomic
  `putIfAbsent` registration; lifecycle flags are `volatile`.
- **Fail Isolation** — an exception in one client's handler never impacts
  other connected clients.

---

## Future Improvements

- Private messages (`/msg <user> <text>`)
- Chat rooms / channels
- Message history persisted to a file or database
- Encryption (TLS sockets)
- Heartbeat pings to detect dead connections faster
- JavaFX or web-based GUI front end

---

## Author

**Sonu Singh** — Java Development Intern @ Codveda Technologies
