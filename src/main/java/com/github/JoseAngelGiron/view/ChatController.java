package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.Chat;
import com.github.JoseAngelGiron.model.entity.Message;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.ChatHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController extends Controller implements Initializable {

    @FXML
    private ImageView photoContact;

    @FXML
    private Text nameContact;

    @FXML
    private Text statusContact;

    @FXML
    private ListView<String> messageWindow;

    @FXML
    private TextArea sendBar;

    private ObservableList<String> messages;
    private User userToWrite;
    private User userLogged;
    private ChatHandler chatHandler;
    private Chat chatNow;


    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        userToWrite = (User) input;
        setUserToWrite();
        checkIfAChatAlreadyExists();


    }

    private void checkIfAChatAlreadyExists(){

        chatHandler = new ChatHandler();
        Chat temporaryChat = chatHandler.retrieveChat(userLogged, userToWrite);

        if (temporaryChat.getUserParticipant1() ==null || temporaryChat.getUserParticipant2() == null){
            chatNow = new Chat();
            chatHandler.create(userLogged, userToWrite);
        }else{
            chatNow = temporaryChat;
            loadExistingMessages(chatNow);
        }
    }

    private void loadExistingMessages(Chat chat) {
        for (Message message : chat.getMessages()) {
            String formattedMessage = message.getSender() + ": " + message.getText();
            messages.add(formattedMessage);
        }
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messages = FXCollections.observableArrayList();
        messageWindow.setItems(messages);
        userLogged = UserSession.UserSession().getUserLoggedIn();
        messageWindow.setCellFactory(lv -> new MessageListCell(userLogged.getUsername()));

    }

    private void setUserToWrite(){

        File photo = new File(userToWrite.getPhoto());
        Image imageToShow = new Image(photo.toURI().toString());
        ImageView imageView = new ImageView(imageToShow);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        photoContact.setImage(imageView.getImage());

        nameContact.setText(userToWrite.getUsername());
        statusContact.setText(userToWrite.getStatus());
    }

    @FXML
    private void sendMessage() {

        String text = sendBar.getText().trim();

        if (!text.isEmpty()) {
            messages.add(text);
            sendBar.clear();
            int id = chatHandler.retrieveLastIDMessage(userLogged, userToWrite);

            Message messageToSave = new Message(id, userLogged.getUsername(), userToWrite.getUsername(), text);
            chatHandler.save(messageToSave, userLogged, userToWrite);
        }
    }
}
