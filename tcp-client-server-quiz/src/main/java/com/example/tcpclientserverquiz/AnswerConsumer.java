package com.example.tcpclientserverquiz;

import javafx.application.Platform;

import java.util.concurrent.BlockingQueue;

public class AnswerConsumer implements Runnable {
    private BlockingQueue<AnswerPackage> queue;
    private ServerController guiController;

    public AnswerConsumer(BlockingQueue<AnswerPackage> queue, ServerController guiController) {
        this.queue = queue;
        this.guiController = guiController;
    }

    @ Override
    public void run() {
        try {
            while (true) {
                // Blocking wait for new answer
                AnswerPackage answer = queue.take();

                // Update UI safely
                Platform.runLater(() -> {
                    guiController.writeLog("RECEIVED: " + answer.toString());
                });
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
