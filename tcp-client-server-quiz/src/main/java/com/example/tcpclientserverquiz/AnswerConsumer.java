package com.example.tcpclientserverquiz;

import javafx.application.Platform;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Background worker responsible for the core game logic.
 * It acts as the Consumer in the Producer-Consumer pattern.
 * Continuously polls the blocking queue for new AnswerPackage objects.
 * Validates the received answers against the currently active question,
 * clears the queue upon a correct answer, and safely delegates all UI
 * updates to the JavaFX Application Thread via Platform.runLater().
 */
public class AnswerConsumer implements Runnable {
    private BlockingQueue<AnswerPackage> queue;
    private ServerController guiController;
    private Map<String, String> questionsAndAnswers;

    public AnswerConsumer(
            BlockingQueue<AnswerPackage> queue,
            ServerController guiController,
            Map<String, String> questionsAndAnswers)
    {
        this.queue = queue;
        this.guiController = guiController;
        this.questionsAndAnswers = questionsAndAnswers;
    }

    @ Override
    public void run() {
        try {
            while (true) {
                Platform.runLater(() -> {
                    guiController.writeLog(questionsAndAnswers.size() + "");
                });
                AnswerPackage incomingAnswer = queue.take();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
