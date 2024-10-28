package com.github.JoseAngelGiron.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModalController extends Controller implements Initializable {

    @FXML
    private Button closeButton;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }


    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    /**
     * Closes the current window.
     */
    @FXML
    public void closeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
