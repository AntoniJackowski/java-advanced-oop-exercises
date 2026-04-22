package com.example.tcpclientserverquiz;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * JavaFX controller for the server GUI.
 * Manages the UI log window and initializes the main QuizServer upon startup.
 */
public class ServerController {

    @FXML
    private TextArea serverTextArea;

    /**
     * Appends a new log message to the server's text area.
     * Note: When calling from background threads, use Platform.runLater().
     *
     * @param log The message to display.
     */
    public void writeLog(String log) {
        serverTextArea.appendText(log + "\n");
    }

    @FXML
    public void initialize() {
        QuizServer server = new QuizServer(this);
        server.startServer();
    }
}