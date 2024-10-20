package com.github.JoseAngelGiron.controller;

import java.io.IOException;

import com.github.JoseAngelGiron.App;
import javafx.fxml.FXML;

public class RegisterController {

    /**
     * Changes the current scene to the registration scene.
     * @throws IOException If an I/O error occurs while setting the root scene.
     */
    @FXML
    private void returnToLogin() throws IOException {
        App.setRoot(Scenes.LOGIN.getURL());

    }
}