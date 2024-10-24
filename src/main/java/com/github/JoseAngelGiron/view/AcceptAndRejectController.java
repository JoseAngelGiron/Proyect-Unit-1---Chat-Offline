package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.Contact;
import com.github.JoseAngelGiron.model.entity.FriendshipRequest;
import com.github.JoseAngelGiron.model.entity.FriendshipRequestStatus;
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


    @FXML
    private void acceptRequest(){
        User userLogged = UserSession.UserSession().getUserLoggedIn();

        User user = UserHandler.build().findByID(friendshipRequest.getIdSender());


        if(user.getId()>=0){
            contactListHandler.save(userLogged.getUsername(), new Contact(user.getId(), user.getUsername()));
            contactListHandler.create(friendshipRequest.getSender(), friendshipRequest.getIdSender());
            contactListHandler.save(friendshipRequest.getSender(),new Contact(userLogged.getId(), userLogged.getUsername()));
            friendshipRequest.setStatus(FriendshipRequestStatus.ACCEPTED);
            build().update(friendshipRequest);
            close();
        }else{
            close();
        }
    }

    @FXML
    private void rejectRequest(){
        //Lógica para cambiar estado o borra petición
        //friendshipRequest.setStatus(friendshipRequest.setStatus(FriendshipRequestStatus.REJECTED));

        close();

    }

    private void close(){


        Stage stage = (Stage) rejectButton.getScene().getWindow();
        stage.close();
    }
}
