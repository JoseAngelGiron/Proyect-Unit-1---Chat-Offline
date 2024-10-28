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


    /**
     * Method invoked when opening the settings view to delete a contact.
     *
     * @param input An object that can be used to pass additional information (not used in this method).
     * @param input2 An object that should be a {@link User} representing the contact to delete.
     * @throws IOException If an error occurs while performing input/output operations.
     */
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

    /**
     * Updates the user interface to display the contact information of the contact to be deleted.
     * This method sets the contact's photo, username, and status in the corresponding UI elements.
     */
    private void setContactToDelete(){
        File photoFile = new File(contactToDelete.getPhoto());
        Image imageToShow = new Image(photoFile.toURI().toString());
        photoContact.setImage(imageToShow);

        name.setText(contactToDelete.getUsername());
        status.setText(contactToDelete.getStatus());
    }

    /**
     * Deletes the selected contact from both the current user's and the contact's contact lists.
     * This method retrieves the current logged-in user, deletes the specified contact
     * from the current user's contact list, and also removes the current user from the contact's list.
     * Finally, it closes the current view.
     */
    @FXML
    private void deleteContact(){

        contactListHandler = new ContactListHandler();
        User currentUser = UserSession.UserSession().getUserLoggedIn();
        contactListHandler.delete(contactToDelete, currentUser.getUsername());
        contactListHandler.delete(currentUser, contactToDelete.getUsername());
        close();

    }

    /**
     * Closes the current stage (window) of the application.
     * This method retrieves the stage associated with the close button and
     * invokes the close method to terminate the window.
     */
    @FXML
    private void close(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}
