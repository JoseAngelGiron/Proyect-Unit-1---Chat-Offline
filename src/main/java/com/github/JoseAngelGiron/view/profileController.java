package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.FriendshipRequest;
import com.github.JoseAngelGiron.model.entity.FriendshipRequestList;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.FriendshipRequestHandler;
import com.github.JoseAngelGiron.persistance.XMLManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.xmlDataHandler.FriendshipRequestHandler.build;

public class profileController extends Controller implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label status;

    @FXML
    private Label email;

    @FXML
    private ImageView photo;

    @FXML
    private TableView<FriendshipRequest> currentRequests;

    @FXML
    private TableColumn<FriendshipRequest, String> sendTo;
    @FXML
    private TableColumn<FriendshipRequest, String> requestStatus;
    @FXML
    private TableColumn<FriendshipRequest, String> date;


    private User currentUser;

    private ObservableList<FriendshipRequest> friendshipRequestListObservableList;


    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = UserSession.UserSession().getUserLoggedIn();
        setUserData();
        setRequests();

    }


    private void setUserData(){
        name.setText(name.getText() + " " + currentUser.getUsername());
        status.setText(status.getText() + " " +currentUser.getStatus());
        email.setText(email.getText() +" " + currentUser.getEmail());

        File filePhoto = new File(currentUser.getPhoto());
        Image profileImage = new Image(filePhoto.toURI().toString());
        photo.setImage(profileImage);
    }

    private void setRequests(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        List<FriendshipRequest> friendshipRequestList = build().findBySender(currentUser.getUsername());
        friendshipRequestListObservableList = FXCollections.observableArrayList(friendshipRequestList);
        currentRequests.setItems(friendshipRequestListObservableList);

        sendTo.setCellValueFactory(cellData -> {
                FriendshipRequest fr = cellData.getValue();
                String receiver = fr.getReceiver();
                return new SimpleStringProperty(receiver);
            }
        );

        requestStatus.setCellValueFactory(cellData -> {
                    FriendshipRequest fr = cellData.getValue();
                    String status = fr.getStatus().toString();
                    return new SimpleStringProperty(status);
                }

        );

        date.setCellValueFactory(cellData -> {
                    FriendshipRequest fr = cellData.getValue();
                    String date = fr.getDate().format(formatter);
                    return new SimpleStringProperty(date);
                }
        );


    }


}
