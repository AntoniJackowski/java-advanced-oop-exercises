package com.example.tcpclientserverquiz;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main orchestrator for the Quiz Server infrastructure.
 * Responsible for loading the question database from a file, initializing
 * the shared BlockingQueue, and starting both the network listener (Producer)
 * and the game logic processor (Consumer) on background daemon threads.
 */
public class QuizServer {
    private ServerController guiController;
    private Map<String, String> questionsAndAnswers = new LinkedHashMap<>();
    private BlockingQueue<AnswerPackage> queue = new LinkedBlockingQueue<>();

    QuizServer(ServerController guiController) {
        this.guiController = guiController;
    }

    /**
     * Reads questions and answers from given file (Format: question?|answer).
     * Puts them in HashMap 'questionsAndAnswers'.
     *
     * @param fileName  file name (e.g. questions.txt) located in directory 'resources'
     */
    private void loadQuestions (String fileName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = loader.getResourceAsStream(fileName)) {
            if (is == null) {
                System.err.println("File not found in resources: " + fileName);
                return;
            }
            try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts =  line.split("\\|");

                    // Check if there is both (question and answer)
                    if (parts.length == 2) {
                        questionsAndAnswers.put(parts[0], parts[1]);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + fileName);
            e.printStackTrace();
        }
    }

    public void startServer() {
        loadQuestions("questions.txt");

        // Create the workers
        AnswerProducer producer = new AnswerProducer(queue, 5050);
        AnswerConsumer consumer = new AnswerConsumer(queue, guiController, questionsAndAnswers);

        // Run them in separate threads
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.setDaemon(true);
        consumerThread.setDaemon(true);

        producerThread.start();
        consumerThread.start();
    }
}
