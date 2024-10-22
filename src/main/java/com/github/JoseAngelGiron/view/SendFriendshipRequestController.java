package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.FriendshipRequest;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.FriendshipRequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.xmlDataHandler.FriendshipRequestHandler.build;

public class SendFriendshipRequestController extends Controller implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button sendButton;

    @FXML
    private Label errorLabel;

    private User userToSendRequest;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {

        userToSendRequest = (User) input2;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void sendRequest(){
        String actualUsername = UserSession.UserSession().getUserLoggedIn().getUsername();
        FriendshipRequest friendshipRequest = new FriendshipRequest(actualUsername, userToSendRequest.getUsername());

        FriendshipRequestHandler fr = new FriendshipRequestHandler();

        fr.create();
        if(fr.save(friendshipRequest).getSender()==null){
            errorLabel.setVisible(true);
        }else{
            closeWindow();
        }

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
