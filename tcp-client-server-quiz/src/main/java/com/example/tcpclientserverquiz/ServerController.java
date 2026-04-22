package com.example.tcpclientserverquiz;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ServerController {

    @FXML
    private TextArea serverTextArea;

    public void writeLog(String log) {
        serverTextArea.appendText(log + "\n");
    }

    @FXML
    public void initialize() {
        QuizServer server = new QuizServer(this);
        server.loadQuestions("questions.txt");
        server.startServer();
    }
}
