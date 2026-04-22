package com.example.tcpclientserverquiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class QuizServer {
    private ServerController guiController;

    QuizServer(ServerController guiController) {
        this.guiController = guiController;
    }

    public void startServer() {
        Thread serverThread = new Thread(() -> {
            try {
                ServerSocket ss = new ServerSocket(5050);
                while (true) {
                    Socket socket = ss.accept();
                    javafx.application.Platform.runLater(() -> {
                        guiController.writeLog("Someone just connected to server.");
                    });
                    PrintWriter pw = new PrintWriter(socket.getOutputStream());

                    pw.println("Welcome to Quiz Server");
                    pw.close();
                }
            } catch (IOException e) {
                System.err.println("Server startup failed");
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }
}
