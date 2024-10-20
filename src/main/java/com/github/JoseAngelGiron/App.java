package com.github.JoseAngelGiron;


import com.github.JoseAngelGiron.controller.Scenes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(Scenes.LOGIN.getURL()), 480, 320);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("CHAT LOGIN TEMPORAL NAME");
        stage.show();

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}