package com.example.tcpclientserverquiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Handle Server connection using TCP/IP protocol, sockets and multithreading.
 */
public class QuizServer {
    private ServerController guiController;
    private Map<String, String> questionsAndAnswers = new LinkedHashMap<>();
    private BlockingQueue<AnswerPackage> queue = new LinkedBlockingQueue<>();

    QuizServer(ServerController guiController) {
        this.guiController = guiController;
    }

    public void loadQuestions (String fileName) {
        File questionsAndAnswersFile = new File(fileName);
         try (Scanner myReader = new Scanner(questionsAndAnswersFile)) {
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] parts = line.split("\\|");
                questionsAndAnswers.put(parts[0], parts[1]);
            }
            System.out.println("Questions and Answers successfully loaded from " + fileName);
         } catch (FileNotFoundException e) {
             System.err.println("File" + fileName + " not found.");
         }
    }

    public void startServer() {
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
