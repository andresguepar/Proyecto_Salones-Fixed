package com.proyecto_salones;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

public class Appp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        URI uri = Paths.get("src\\main\\resources\\com\\proyecto_salones\\App.fxml").toAbsolutePath().toUri();

        System.out.println("URI: " +uri.toString() );
        Parent root = FXMLLoader.load(uri.toURL());

        Scene scene = new Scene(root);

        stage.setTitle("Classroom Assigment");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}