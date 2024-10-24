package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.FriendshipRequest;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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




    @Override
    public void onOpen(Object input, Object input2) throws IOException {

        friendshipRequestsReceived = (List<FriendshipRequest>) input;

        changeLabel();

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


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
            LocalDateTime name = fr.getTimestamp();

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
