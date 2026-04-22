package com.example.tcpclientserverquiz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Handle Server connection using TCP/IP protocol, sockets and multithreading.
 */
public class QuizServer {
    private ServerController guiController;
    private HashMap<String, String> questionsAndAnswers;
    private BlockingQueue<AnswerPackage> queue = new LinkedBlockingQueue<>();

    QuizServer(ServerController guiController) {
        this.guiController = guiController;
    }

    /**
     * Starts the quiz server on a background daemon thread.
     * * This method creates an infinite loop listening for incoming TCP connections
     * on port 5050. It runs on a separate thread to prevent blocking the main
     * JavaFX Application Thread. When a client connects, it safely delegates
     * UI updates (like logging) back to the GUI controller.
     */
    public void startServer() {
        // Create the workers
        AnswerProducer producer = new AnswerProducer(queue, 5050);
        AnswerConsumer consumer = new AnswerConsumer(queue, guiController);

        // Run them in separate threads
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.setDaemon(true);
        consumerThread.setDaemon(true);

        producerThread.start();
        consumerThread.start();
    }
}
