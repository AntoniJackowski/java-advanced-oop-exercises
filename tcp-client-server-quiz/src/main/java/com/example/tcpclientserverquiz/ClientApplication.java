package com.example.tcpclientserverquiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

/**
 * Entry point for the Quiz Client application.
 * Initializes and displays the JavaFX user interface.
 */
public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("client-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 180);
        stage.setTitle("CLIENT");
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();
    }
}