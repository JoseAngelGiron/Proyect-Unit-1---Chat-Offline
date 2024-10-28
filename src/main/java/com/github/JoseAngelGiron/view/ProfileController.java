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

    /**
     * Opens a modal window that allows the user to change their profile photo.
     * This method triggers a modal scene defined by the `CHANGESETTINGS` scene identifier,
     * prompting the user to select a new photo to use as their profile picture.
     *
     * @throws IOException If an input or output error occurs while opening the modal window.
     */
    @FXML
    public void changeProfilePhoto() throws IOException {
        openModal(Scenes.CHANGESETTINGS, "Seleccione la foto que desea usar", this, null);

    }


    /**
     * Updates the user interface elements with the current user's data.
     * This method populates the name, status, email, and profile photo fields
     * of the UI with information retrieved from the currently logged-in user.
     *
     * The method retrieves the user's data from the `currentUser` object,
     * converts the photo file path into a URI for the profile image,
     * and updates the respective UI components accordingly.
     */
    private void setUserData(){
        name.setText(currentUser.getUsername());
        status.setText(currentUser.getStatus());
        email.setText(currentUser.getEmail());

        File filePhoto = new File(currentUser.getPhoto());
        Image profileImage = new Image(filePhoto.toURI().toString());
        photoProfile.setImage(profileImage);
    }

    /**
     * Initializes and populates the table view for displaying friendship requests
     * sent by the current user. This method retrieves the list of friendship requests
     * from the data source, formats the relevant details, and sets up the table view
     * columns to display the sender, status, and date of each request.
     *

     *
     * The friendship requests are fetched from the XML using the
     * {@code build().findBySender(currentUser.getUsername())} method, which returns
     * a list of {@code FriendshipRequest} objects. These requests are then converted
     * into an observable list that is set to the current requests table view.
     */
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

    /**
     * Initializes and populates the table view for displaying the user's contact list.
     * This method retrieves all contacts for the current user and sets up the table
     * columns to display each contact's photo, username, and status.
     *
     * Additionally, this method sets up a mouse click event handler for the contacts table
     * that listens for double-click events. Upon a double-click, it prompts the user with
     * a modal dialog asking if they wish to delete the selected contact from their list.
     */
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
