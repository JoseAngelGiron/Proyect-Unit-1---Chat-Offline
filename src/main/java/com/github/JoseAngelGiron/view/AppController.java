package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.App;
import com.github.JoseAngelGiron.model.entity.ContactList;
import com.github.JoseAngelGiron.model.entity.FriendshipRequest;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.ContactListHandler;
import com.github.JoseAngelGiron.model.xmlDataHandler.FriendshipRequestHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static com.github.JoseAngelGiron.model.xmlDataHandler.UserHandler.build;


public class AppController extends Controller implements Initializable {
    @FXML
    private Pane mainWindow;
    @FXML
    private Button administrationButton;

    @FXML
    private Label userName;
    @FXML
    private Label userStatus;

    @FXML
    private TableView<User> contacts;

    @FXML
    private TableColumn<User, String> nameUsers;
    @FXML
    private TableColumn<User, ImageView> photoUsers;

    @FXML
    private ImageView photoUser;

    private User userLogged;
    private ContactListHandler contactListHandler;
    private ObservableList<User> usersToShow;


    public static Controller centerController;
    public static Controller modalController;


    @Override
    public void onOpen(Object input, Object data) throws IOException {


    }

    @Override
    public void onClose(Object output) {

    }

    /** //recomentar
     * Initializes the controller after its root element has been completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserSession userSession = UserSession.UserSession();
        User userLogged = userSession.getUserLoggedIn();


        contactListHandler = new ContactListHandler();
        contactListHandler.create(userLogged.getUsername(), userLogged.getId());

        hideAdministrationButton();
        setUserData();
        setContacts();
        startTimedUpdate(this::updateProfilePicture, 5);
        startTimedUpdate(this::setContacts, 5);
        try {
            changeToStart();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void startTimedUpdate(Runnable functionToExecute, int intervalSeconds) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(intervalSeconds), event -> functionToExecute.run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateProfilePicture() {
        File photo = new File(userLogged.getPhoto());
        Image profileImage = new Image(photo.toURI().toString());
        photoUser.setImage(profileImage);
    }

    /**
     * Opens a modal dialog window.
     * @param scene The scene to be displayed in the modal window.
     * @param title The title of the modal window.
     * @param parent The parent controller of the modal window.
     * @param data Additional data to be passed to the modal window.
     * @throws IOException If an error occurs while loading the FXML for the modal window.
     */
    public static void openModal(Scenes scene, String title, Controller parent, Object data) throws IOException {
        View view = loadFXML(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(parent, data);
        stage.showAndWait();
        modalController=view.controller;
    }


    /**
     * Changes the scene to the store view.
     * @throws IOException If an error occurs while loading the store view.
     */
    @FXML
    public void changeToStart() throws IOException {
        changeScene(Scenes.START, mainWindow,null);
    }



    /**
     * Changes the scene to the personal area.
     * @throws IOException If an error occurs while loading the personal area view.
     */
    @FXML
    public void changeToProfile() throws IOException {
        changeScene(Scenes.PROFILE, mainWindow,null);
    }

    /**
     * Changes the scene to the request area.
     * @throws IOException If an error occurs while loading the request view.
     */
    @FXML
    public void changeToRequests() throws IOException {
        FriendshipRequestHandler friendshipRequestHandler = new FriendshipRequestHandler();
        friendshipRequestHandler.create();

        List<FriendshipRequest> fr = friendshipRequestHandler.findByReceiver(userLogged.getUsername());
        if(fr != null){
            changeScene(Scenes.REQUESTRECEIVED, mainWindow, fr);
        }

    }

    /**
     * Changes the scene to the administration area.
     * @throws IOException If an error occurs while loading the administration view.
     */
    @FXML
    public void changeToAdminArea() throws IOException {
        //changeScene(Scenes.ADMIN, mainWindow, null);
    }

    @FXML
    public void changeToChat(User user) throws IOException {
        changeScene(Scenes.CHAT, mainWindow,user);
    }


    /**
     * Hides the administration button based on the user's role.
     */
    public void hideAdministrationButton() {

         UserSession session = UserSession.UserSession();
          if (!session.getUserLoggedIn().isAdmin()) {
              administrationButton.setVisible(false);
          }
    }


    private void setUserData(){
        userLogged = UserSession.UserSession().getUserLoggedIn();

        userName.setText(userLogged.getUsername());
        userStatus.setText(userLogged.getStatus());

        File photo = new File(userLogged.getPhoto());
        Image profileImage = new Image(photo.toURI().toString());
        photoUser.setImage(profileImage);
    }


    private void setContacts(){

        ContactList contactList = contactListHandler.findAll(userLogged.getUsername());

        Set<User> usersInContact = build().findListOfUsersByID(contactList);

        usersToShow = FXCollections.observableArrayList(usersInContact);
        contacts.setItems(usersToShow);

        nameUsers.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String name = user.getUsername();
            return new SimpleStringProperty(name);

        });

        photoUsers.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            File photo = new File(user.getPhoto());
            Image imageToShow = new Image(photo.toURI().toString());
            ImageView imageView = new ImageView(imageToShow);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

            return new SimpleObjectProperty<>(imageView);
        });

        contacts.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                User selectedUser = contacts.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    try {
                        changeToChat(selectedUser);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    /**
     * Changes the displayed scene in a Pane container with a new scene by loading an FXML file
     * and associating it with its controller.
     *
     * @param scene The scene to be loaded.
     * @param pane  The Pane container in which the new scene will be shown.
     * @param data  Optional data to be passed to the controller of the new scene.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public static void changeScene(Scenes scene, Pane pane, Object data) throws IOException {
        pane.getChildren().clear();
        View view = loadFXML(scene);
        pane.getChildren().add(view.scene);
        centerController = view.controller;

        centerController.onOpen(data, null);
    }
    /**
     * Displays all users in a table view.
     */
    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url + ".fxml"));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene=p;
        view.controller=c;
        return view;
    }

    @FXML
    private void closeApp(){
        System.exit(0);
    }


}
