package com.example.tcpclientserverquiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Entry point for the Quiz Server application.
 * Initializes and displays the main JavaFX window based on the FXML layout.
 */
public class ServerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApplication.class.getResource("server-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 350);
        stage.setTitle("QUIZ SERVER");
        stage.setScene(scene);
        stage.show();
    }
}