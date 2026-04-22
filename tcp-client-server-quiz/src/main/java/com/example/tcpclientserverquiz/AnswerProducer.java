package com.example.tcpclientserverquiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;


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
