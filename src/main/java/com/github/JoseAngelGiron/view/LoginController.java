package com.github.JoseAngelGiron.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.github.JoseAngelGiron.App;

import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.xmldatahandler.UserHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class LoginController extends Controller implements Initializable {

    @FXML
    private TextField emailText;
    @FXML
    private TextField passwordText;

    private User userToLogin;


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
     * Logs in the user with the provided credentials.
     * @throws IOException If an I/O error occurs while navigating to the root scene or displaying a modal.
     */
    @FXML
    private void LogUser() throws IOException {
        userToLogin = new User();
        userToLogin.setEmail(emailText.getText());
        userToLogin.setPassword((passwordText.getText()));

        UserHandler userHandler = new UserHandler();
        userHandler.create();

        if(userHandler.findAll().ifUserExists(userToLogin)) {
            UserSession session = UserSession.UserSession();
            session.setUserIntoSession(userToLogin);


            //App.setRoot(Scenes.ROOT.getURL());
            resizeWindow();
            changeToRegister();

        }else{
            //openModal(Scenes.USERNOTFOUND, "No se encontro el usuario", this, null);
        }
    }

    /**
     * Changes the current scene to the registration scene.
     * @throws IOException If an I/O error occurs while setting the root scene.
     */
    @FXML
    private void changeToRegister() throws IOException {
        App.setRoot(Scenes.REGISTER.getURL());

    }

    /**
     * Resizes the application window to fill the entire screen.
     * It sets the stage position to the top-left corner and adjusts the width and height to match the screen size.
     * The window becomes resizable after resizing.
     */
    public static void resizeWindow() {

        Stage stage = (Stage) App.scene.getWindow();

        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

        stage.setX(screenSize.getMinX());
        stage.setY(screenSize.getMinY());
        stage.setWidth(screenSize.getWidth());
        stage.setHeight(screenSize.getHeight());

        stage.setResizable(true);
    }
}