package com.example.tcpclientserverquiz;

import javafx.application.Platform;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Consumer thread that validates incoming answers against the current question.
 * Updates the JavaFX UI and manages the game state.
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

    @Override
    public void run() {
        try {
            Iterator<Map.Entry<String, String>> iterator = questionsAndAnswers.entrySet().iterator();

            if (!iterator.hasNext()) {
                Platform.runLater(() -> guiController.writeLog("Error: no questions or answers found."));
                return;
            }

            Map.Entry<String, String> currentEntry = iterator.next();
            String currentQuestion = currentEntry.getKey();
            String currentAnswer = currentEntry.getValue();
            int questionNumber = 1;

            final String firstQ = "Nr " + questionNumber + ") " + currentQuestion;
            Platform.runLater(() -> guiController.writeLog(firstQ));

            // Main consumer loop
            while (true) {
                AnswerPackage incomingAnswer = queue.take();

                String nick = incomingAnswer.getNickname();
                String answerText = incomingAnswer.getAnswerText();

                // Validate answer (case-insensitive and ignoring spaces)
                if (answerText.trim().equalsIgnoreCase(currentAnswer.trim())) {

                    final String successMsg = nick + " (" + incomingAnswer.getIpAddress() + ") " + "answered correctly!";
                    Platform.runLater(() -> guiController.writeLog(successMsg));

                    // Crucial: clear late answers for the already solved question
                    queue.clear();

                    if (iterator.hasNext()) {
                        currentEntry = iterator.next();
                        currentQuestion = currentEntry.getKey();
                        currentAnswer = currentEntry.getValue();
                        questionNumber++;

                        final String nextQ = "\nNr " + questionNumber + ") " + currentQuestion;
                        Platform.runLater(() -> guiController.writeLog(nextQ));
                    } else {
                        Platform.runLater(() -> guiController.writeLog("\nAll the questions have already been answered. Game over!"));
                        break;
                    }

                } else {
                    final String errorMsg = "Incorrect answer received...";
                    Platform.runLater(() -> guiController.writeLog(errorMsg));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}