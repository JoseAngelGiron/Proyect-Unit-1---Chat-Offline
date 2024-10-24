package com.github.JoseAngelGiron.view;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MessageListCell extends ListCell<String> {
    private final HBox hBox = new HBox();
    private final Text userNameText = new Text();
    private final Text messageText = new Text();
    private final String currentUser;

    public MessageListCell(String currentUser) {
        this.currentUser = currentUser;
        hBox.setSpacing(10);
        hBox.getChildren().addAll(userNameText, messageText);
    }

    @Override
    protected void updateItem(String message, boolean empty) {
        super.updateItem(message, empty);
        if (empty || message == null) {
            setGraphic(null);  // Ocultar celda vacía
        } else {
            userNameText.setText(currentUser + ": "); // Mostrar el nombre del usuario antes del mensaje
            messageText.setText(message);
            hBox.setAlignment(Pos.CENTER_RIGHT);  // Alinear los mensajes a la derecha
            setGraphic(hBox);  // Mostrar el contenido personalizado
        }
    }
}
