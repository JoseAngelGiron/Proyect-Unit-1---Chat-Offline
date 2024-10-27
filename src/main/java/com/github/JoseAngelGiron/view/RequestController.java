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
    private Timeline timeline;




    @Override
    public void onOpen(Object input, Object input2) throws IOException {

        friendshipRequestsReceived = (List<FriendshipRequest>) input;
        changeLabel();
        timeline.play();



    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> refreshRequestTable())); // Cada 5 segundos
        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    private void changeLabel(){
        if(!friendshipRequestsReceived.isEmpty()){
            showRequests(friendshipRequestsReceived);
        }else{
            labelRequests.setText("No tienes peticiones de amistad actualmente");
        }
    }

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


    private void refreshRequestTable() {

        List<FriendshipRequest> updatedRequests = getUpdatedFriendshipRequests();
        showRequests(updatedRequests);
    }


    private List<FriendshipRequest> getUpdatedFriendshipRequests() {
        FriendshipRequestHandler friendshipRequestHandler = new FriendshipRequestHandler();
        friendshipRequestHandler.findByReceiver(UserSession.UserSession().getUserLoggedIn().getUsername());
        return friendshipRequestsReceived; //
    }
}
