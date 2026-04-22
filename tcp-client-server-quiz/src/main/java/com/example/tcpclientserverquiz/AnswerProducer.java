package com.example.tcpclientserverquiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Background worker responsible for handling incoming network connections.
 * It acts as the Producer in the Producer-Consumer pattern.
 * This thread listens on a specified TCP port, reads raw strings from clients,
 * parses them into AnswerPackage objects, and safely puts them into a blocking queue.
 * Note: This class does NOT evaluate whether an answer is correct.
 */
public class AnswerProducer implements Runnable {
    private BlockingQueue<AnswerPackage> queue;
    private int port;

    public AnswerProducer(BlockingQueue<AnswerPackage> queue, int port) {
        this.queue = queue;
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String rawData = reader.readLine();  // Format: Nick|Answer
                if (rawData != null && rawData.contains("|")) {
                    String[] parts = rawData.split("\\|");
                    AnswerPackage answer = new AnswerPackage(parts[0], parts[1]);

                    // Put it into the queue for the consumer
                    queue.put(answer);
                }
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
