# java-advanced-oop-exercises
Place for my university projects related with object-oriented programming in Java.

---

## Projects List

### 1. TCP Client-Server Quiz (`tcp-client-server-quiz`)
A real-time, local network quiz game built using a client-server architecture. 

**Key Technologies & Concepts:**
* **JavaFX:** Used to create the Graphical User Interface (GUI) for both the server and the clients.
* **TCP/IP Sockets:** Enables network communication between the players and the host server.
* **Multithreading:** Implements the **Producer-Consumer** design pattern to handle incoming data concurrently.
* **BlockingQueue:** Safely manages the queue of answers arriving from different client threads.

**How it works:**
The server loads a set of questions from a text file and hosts the game. Multiple clients can run simultaneously, connect to the server, and send their answers. The server listens for incoming connections, evaluates the answers using a consumer thread, announces the fastest correct answer, and automatically moves to the next question.

---

### 2. [Next Project Name]
*(Short description will be added here once the project is finished)*
