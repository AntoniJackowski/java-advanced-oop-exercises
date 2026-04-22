package com.example.tcpclientserverquiz;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.Socket;

public class ClientController {
    @FXML
    private TextField clientNick;

    @FXML
    private TextField clientAnswer;

    @FXML
    private Button sendBtn;

    @FXML
    private void sendAnswer() {
        try {
            Socket socket = new Socket("127.0.0.1", 5050);
            System.out.println("Connection attempt successful!");
        } catch (IOException e) {
            System.err.println("Failed to send answer to server");
        }
    }

}
