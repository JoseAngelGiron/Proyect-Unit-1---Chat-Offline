package com.github.JoseAngelGiron.controller;

import java.io.IOException;

import com.github.JoseAngelGiron.App;
import javafx.fxml.FXML;

public class LoginController {

    /**
     * Changes the current scene to the registration scene.
     * @throws IOException If an I/O error occurs while setting the root scene.
     */
    @FXML
    private void changeToRegister() throws IOException {
        App.setRoot(Scenes.REGISTER.getURL());

    }
}
