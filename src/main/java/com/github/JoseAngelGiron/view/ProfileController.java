package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.ContactList;
import com.github.JoseAngelGiron.model.entity.FriendshipRequest;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.ContactListHandler;
import com.github.JoseAngelGiron.model.xmlDataHandler.UserHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static com.github.JoseAngelGiron.model.xmlDataHandler.FriendshipRequestHandler.build;

import static com.github.JoseAngelGiron.view.AppController.openModal;
import static com.github.JoseAngelGiron.view.AppController.startTimedUpdate;

public class ProfileController extends Controller implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label status;

    @FXML
    private Label email;

    @FXML
    private ImageView photoProfile;

    @FXML
    private TableView<FriendshipRequest> currentRequests;

    @FXML
    private TableColumn<FriendshipRequest, String> sendTo;
    @FXML
    private TableColumn<FriendshipRequest, String> requestStatus;
    @FXML
    private TableColumn<FriendshipRequest, String> date;


    @FXML
    private TableView<User> contacts;

    @FXML
    private TableColumn<User, ImageView> contactPhoto;
    @FXML
    private TableColumn<User, String> nameContact;
    @FXML
    private TableColumn<User, String> statusOfContact;

    private User currentUser;

    private ContactListHandler contactListHandler;
    private ObservableList<User> usersToShow;

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
        contactListHandler = new ContactListHandler();
        setUserData();
        setContacts();
        setRequests();
        startTimedUpdate(this::setUserData, 5);
        startTimedUpdate(this::setContacts, 5);

    }

    @FXML
    public void changeProfilePhoto() throws IOException {
        openModal(Scenes.CHANGEPHOTO, "Seleccione la foto que desea usar", this, null);

    }



    private void setUserData(){
        name.setText(currentUser.getUsername());
        status.setText(currentUser.getStatus());
        email.setText(currentUser.getEmail());

        File filePhoto = new File(currentUser.getPhoto());
        Image profileImage = new Image(filePhoto.toURI().toString());
        photoProfile.setImage(profileImage);
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

    private void setContacts(){

        ContactList contactList = contactListHandler.findAll(currentUser.getUsername());

        Set<User> usersInContact = UserHandler.build().findListOfUsersByID(contactList);

        usersToShow = FXCollections.observableArrayList(usersInContact);
        contacts.setItems(usersToShow);

        contactPhoto.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            File photo = new File(user.getPhoto());
            Image imageToShow = new Image(photo.toURI().toString());
            ImageView imageView = new ImageView(imageToShow);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

            return new SimpleObjectProperty<>(imageView);
        });

        nameContact.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String name = user.getUsername();
            return new SimpleStringProperty(name);

        });

        statusOfContact.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String status = user.getStatus();
            return new SimpleStringProperty(status);
        });



        contacts.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                User selectedUser = contacts.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    try {
                        openModal(Scenes.DELETECONTACT, "¿Desea borrar este contacto de su lista?",this, selectedUser);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }




}
