package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.Contact;
import com.github.JoseAngelGiron.model.entity.FriendshipRequest;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.ContactListHandler;

import com.github.JoseAngelGiron.model.xmlDataHandler.UserHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import static com.github.JoseAngelGiron.model.xmlDataHandler.FriendshipRequestHandler.build;


public class AcceptAndRejectController extends Controller implements Initializable {

    @FXML
    private Button rejectButton;
    @FXML
    private Label questionLabel;

    private FriendshipRequest friendshipRequest;
    private ContactListHandler contactListHandler;

    /**
     * Initializes the view and loads a friendship request when the view is opened.
     *
     * This method sets up the initial state when the view is opened, initializing
     * a new instance of ContactListHandler and displaying a prompt with the sender’s
     * name from the provided FriendshipRequest. The method may throw an IOException
     * if an error occurs during the initialization.
     *
     * @param input   An object passed as input (not used in this method).
     * @param input2  The FriendshipRequest object containing the sender’s information
     *                to display in the view.
     * @throws IOException If an I/O error occurs during the initialization.
     */
    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        friendshipRequest = (FriendshipRequest) input2;
        contactListHandler = new ContactListHandler();
        questionLabel.setText(questionLabel.getText() + friendshipRequest.getSender() + "?");
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Accepts a friendship request and updates contact lists accordingly.
     *
     * This method adds the sender of the friendship request to the logged-in user's
     * contact list and vice versa. Once the contacts are added, it deletes the friendship
     * request from the pending list and closes the current view.
     */
    @FXML
    private void acceptRequest(){
        User userLogged = UserSession.UserSession().getUserLoggedIn();

        User user = UserHandler.build().findByID(friendshipRequest.getIdSender());


        if(user.getId()>=0){
            contactListHandler.save(userLogged.getUsername(), new Contact(user.getId(), user.getUsername()));

            contactListHandler.create(friendshipRequest.getSender(), friendshipRequest.getIdSender());
            contactListHandler.save(friendshipRequest.getSender(), new Contact(userLogged.getId(), userLogged.getUsername()));

            build().deleteOneRequest(friendshipRequest);
            close();
        }else{
            close();
        }
    }

    /**
     * Rejects a friendship request and removes it from the list.
     *
     * This method deletes the friendship request from the list of pending requests
     * and closes the current view.
     */
    @FXML
    private void rejectRequest(){
        build().deleteOneRequest(friendshipRequest);
        close();

    }

    /**
     * Closes the current view.
     *
     * This helper method retrieves the current stage from the reject button's scene
     * and closes the window.
     */
    private void close(){


        Stage stage = (Stage) rejectButton.getScene().getWindow();
        stage.close();
    }
}
