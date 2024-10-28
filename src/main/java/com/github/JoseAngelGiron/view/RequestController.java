package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.FriendshipRequest;

import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.FriendshipRequestHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.view.AppController.openModal;
import static com.github.JoseAngelGiron.view.AppController.startTimedUpdate;

public class RequestController extends Controller implements Initializable {

    @FXML
    private TableView<FriendshipRequest> friendshipRequestTableView;

    @FXML
    private TableColumn<FriendshipRequest, String> nameUser;

    @FXML
    private TableColumn<FriendshipRequest, String> userStatus;

    @FXML
    private TableColumn<FriendshipRequest, String> localDateTimeRequestTableColumn;

    @FXML
    private Label labelRequests;

    private ObservableList<FriendshipRequest> friendshipRequests;
    private List<FriendshipRequest> friendshipRequestsReceived;




    /**
     * This method is called when the controller is opened.
     * It initializes the list of friendship requests received
     * by the current user and updates the label accordingly.
     *
     * @param input The input parameter containing the list of friendship requests.
     * @param input2 Not used in this implementation.
     * @throws IOException If an error occurs while handling the input.
     */
    @Override
    public void onOpen(Object input, Object input2) throws IOException {

        friendshipRequestsReceived = (List<FriendshipRequest>) input;
        changeLabel();

    }

    @Override
    public void onClose(Object output) {

    }

    /**
     * Initializes the controller class.
     * This method sets up a timed update that refreshes the friendship request table every 5 seconds.
     *
     * @param url Not used in this implementation.
     * @param resourceBundle Not used in this implementation.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTimedUpdate(this::refreshRequestTable, 5);

    }



    /**
     * Updates the label displaying the friendship request status.
     * If there are friendship requests, it calls {@code showRequests} to display them;
     * otherwise, it updates the label to indicate no current requests.
     */
    private void changeLabel(){
        if(!friendshipRequestsReceived.isEmpty()){
            showRequests(friendshipRequestsReceived);
        }else{
            labelRequests.setText("No tienes peticiones de amistad actualmente");
        }
    }

    /**
     * Retrieves the updated list of friendship requests for the logged-in user.
     *
     * @return A list of updated friendship requests.
     */
    private List<FriendshipRequest> getUpdatedFriendshipRequests() {
        FriendshipRequestHandler friendshipRequestHandler = new FriendshipRequestHandler();
        friendshipRequestsReceived = friendshipRequestHandler.findByReceiver(UserSession.UserSession().getUserLoggedIn().getUsername());
        return friendshipRequestsReceived;
    }


    /**
     * Refreshes the friendship request table by fetching the latest requests
     * and updating the displayed list.
     */
    private void refreshRequestTable() {

        List<FriendshipRequest> updatedRequests = getUpdatedFriendshipRequests();
        showRequests(updatedRequests);
    }

    /**
     * Displays the list of friendship requests in the table view.
     * This method populates the table with user names, statuses, and request dates.
     *
     * @param list A list of friendship requests to display.
     */
    private void showRequests(List<FriendshipRequest> list) {
        friendshipRequests = FXCollections.observableArrayList(list);
        friendshipRequestTableView.setItems(friendshipRequests);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        nameUser.setCellValueFactory(cellData -> {
            FriendshipRequest fr = cellData.getValue();
            String name = fr.getSender();
            return new SimpleStringProperty(name);

        });

        userStatus.setCellValueFactory(cellData -> {
            FriendshipRequest fr = cellData.getValue();
            String name = fr.getStatus().getDisplayText();
            return new SimpleStringProperty(name);

        });

        localDateTimeRequestTableColumn.setCellValueFactory(cellData -> {
            FriendshipRequest fr = cellData.getValue();
            LocalDateTime name = fr.getDate();

            String formattedDate = name.format(formatter);

            return new SimpleStringProperty(formattedDate);
        });

        friendshipRequestTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                FriendshipRequest selectedRequest = friendshipRequestTableView.getSelectionModel().getSelectedItem();
                if (selectedRequest != null) {
                    try {
                        openModal(Scenes.ACCEPTANDREJECT, "¿Deseas aceptar la petición?", this, selectedRequest);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }



}
