package com.github.JoseAngelGiron.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.github.JoseAngelGiron.App;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.xmldatahandler.UserHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.github.JoseAngelGiron.view.AppController.openModal;

public class RegisterController extends Controller implements Initializable {

    @FXML
    private TextField nickField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;

    @FXML
    private Label nickLabel1;
    @FXML
    private Label nickLabel2;
    @FXML
    private Label nickLabel3;


    private User userToRegister;



    private final static String DEFAULTIMAGE = "C:\\Users\\the_l\\IdeaProjects\\Proyecto-3TR\\src\\main\\resources\\com\\github\\JoseAngelGiron\\view\\IMG\\Huevito.png";



    @Override
    public void onOpen(Object input, Object data) throws IOException {


    }

    @Override
    public void onClose(Object output) {


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        App.scene.getWindow().setHeight(400);
        App.scene.getWindow().setWidth(400);

    }


    /**
     * Registers a new user with the provided information.
     * @throws IOException If an I/O error occurs while retrieving default image, encrypting password, or displaying a modal.
     */
    @FXML
    private void registerUser() throws IOException {
        userToRegister = new User(nickField.getText(), passwordField.getText(), emailField.getText());

        UserHandler userHandler = new UserHandler();

        if(!userHandler.findAll().ifUserExists(userToRegister) && checkFields()){

            userHandler.save(userToRegister);

            //campo o ventana modal confirmando registro


        }else{
            openModal(Scenes.USERALREADYREGISTER, "Ya existe un usuario con ese nombre...", this, null);
        }

    }

    /**
     * Returns to the login screen.
     * @throws IOException If an I/O error occurs while navigating to the login scene.
     */
    @FXML
    private void returnToLogin() throws IOException {
        App.setRoot(Scenes.LOGIN.getURL());
        App.scene.getWindow().setWidth(480);
        App.scene.getWindow().setHeight(320);
    }


    /**
     * Checks if all input fields are valid.
     * @return true if all fields are valid, false otherwise.
     */
    private boolean checkFields(){
        boolean valid = false;

        if (validateNick(nickField.getText()) && validatePassword(passwordField.getText()) &&
                validateEmail(emailField.getText())) {

            valid = true;
        }else{
            if(!validateNick(nickField.getText())){
                nickLabel1.setVisible(true);
                nickLabel2.setVisible(true);
                nickLabel3.setVisible(true);
            }else{
                nickLabel1.setVisible(false);
                nickLabel2.setVisible(false);
                nickLabel3.setVisible(false);
            }
            if (!validateEmail(emailField.getText())){

            }
        }
        return valid;
    }

    /**
     * Validates a user's nickname.
     * @param nick The nickname to validate.
     * @return true if the nickname is valid, false otherwise.
     */
    private static boolean validateNick(String nick){
        Pattern pattern = Pattern.compile("^(?!.*\\s)\\w{4,12}$");
        return pattern.matcher(nick).matches();

    }
    /**
     * Validates a user's password.
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    private static boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?!.*\\s).{8,}$");
        return pattern.matcher(password).matches();

    }
    /**
     * Validates an email address.
     * @param email The email address to validate.
     * @return true if the email address is valid, false otherwise.
     */
    private static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^(?!.*\\s).+@.+\\.(com|es)$");
        return pattern.matcher(email).matches();
    }

}