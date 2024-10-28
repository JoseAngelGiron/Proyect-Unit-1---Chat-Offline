package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.model.xmlDataHandler.UserHandler.build;
import static com.github.JoseAngelGiron.view.AppController.openModal;

public class StartController extends Controller implements Initializable {
    @FXML
    private TextField searchBar;

    @FXML
    private TableView<User> resultsOfSearch;

    @FXML
    private TableColumn<User, String> nameOfUser;
    @FXML
    private TableColumn<User, String> stateOfUser;

    private ObservableList<User> users;


    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    /**
     * Initializes the controller after its root element has been
     * completely processed. Sets up the table click listener.
     *
     * @param url            The location used to resolve relative paths
     *                       for the root object, or null if the
     *                       location is not known.
     * @param resourceBundle The resources used to localize the root
     *                       object, or null if the root object was
     *                       not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickOnTable();

    }


    /**
     * Searches for friends based on the input from the search bar.
     * Retrieves a list of users matching the search criteria and
     * displays them in the results table.
     */
    @FXML
    public void searchFriends(){
        String nameOfUser = searchBar.getText();
        UserSession userlogged = UserSession.UserSession();
        List<User> listOfUserToShow = build().findByName(nameOfUser, userlogged.getUserLoggedIn());
        showUsers(listOfUserToShow);
    }


    /**
     * Displays a list of users in the results table.
     *
     * @param list The list of users to display.
     */
    private void showUsers(List<User> list){
        users = FXCollections.observableArrayList(list);
        resultsOfSearch.setItems(users);

        nameOfUser.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String name = user.getUsername();
            return new SimpleStringProperty(name);

        });

        stateOfUser.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String status = user.getStatus();
            return new SimpleStringProperty(status);

        });
    }

    /**
     * Sets up a mouse click event listener for the results table.
     * If a user double-clicks on a row, a friendship request modal
     * is opened for the selected user.
     */
    private void clickOnTable(){
        resultsOfSearch.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                User selectedUser = resultsOfSearch.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    try {
                        openModal(Scenes.FRIENDSHIPREQUEST, "¿Desea envíar una petición de amistad?", this, selectedUser);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
