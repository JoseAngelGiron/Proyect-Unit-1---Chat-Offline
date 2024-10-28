package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.ContactListHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteContact extends Controller implements Initializable {

    @FXML
    private ImageView photoContact;
    @FXML
    private Label name;
    @FXML
    private Label status;

    @FXML
    private Button closeButton;

    private User contactToDelete;
    private ContactListHandler contactListHandler;



    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        contactToDelete = (User) input2;
        setContactToDelete();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setContactToDelete(){
        File photoFile = new File(contactToDelete.getPhoto());
        Image imageToShow = new Image(photoFile.toURI().toString());
        photoContact.setImage(imageToShow);

        name.setText(contactToDelete.getUsername());
        status.setText(contactToDelete.getStatus());
    }

    @FXML
    private void deleteContact(){

        contactListHandler = new ContactListHandler();
        User currentUser = UserSession.UserSession().getUserLoggedIn();
        contactListHandler.delete(contactToDelete, currentUser.getUsername());
        contactListHandler.delete(currentUser, contactToDelete.getUsername());
        close();

    }

    @FXML
    private void close(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}
