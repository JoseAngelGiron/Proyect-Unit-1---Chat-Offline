package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.Message;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MessageListCell extends ListCell<Message> {
    private final HBox hBox = new HBox();
    private final Text userNameText = new Text();
    private final Text messageText = new Text();
    private final String currentUser;

    public MessageListCell(String currentUser) {
        this.currentUser = currentUser;
        hBox.setSpacing(10);
        hBox.getChildren().addAll(userNameText, messageText);
    }

    /**
     * Updates the cell with the given message.
     *
     * @param message The Message object to display.
     * @param empty Indicates if the cell is empty.
     */
    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);

        if (empty || message == null) {
            setGraphic(null);
        } else {

            if (message.getSender().equals(currentUser)) {

                userNameText.setText("");
                hBox.setAlignment(Pos.CENTER_RIGHT);
            } else {

                userNameText.setText(message.getSender() + ": ");
                hBox.setAlignment(Pos.CENTER_LEFT);
            }
            messageText.setText(message.getText());
            setGraphic(hBox);
        }
    }
}
