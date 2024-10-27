package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.Chat;
import com.github.JoseAngelGiron.model.entity.Message;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.ChatHandler;
import com.github.JoseAngelGiron.persistance.XMLManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ChatController extends Controller implements Initializable {

    @FXML
    private ImageView photoContact;

    @FXML
    private Text nameContact;

    @FXML
    private Text statusContact;

    @FXML
    private ListView<Message> messageWindow;

    @FXML
    private TextArea sendBar;

    private ObservableList<Message> messages;
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

    @FXML
    private void sendMessage() {

        String text = sendBar.getText().trim();

        if (!text.isEmpty()) {

            sendBar.clear();
            int id = chatHandler.retrieveLastIDMessage(userLogged, userToWrite);

            Message messageToSave = new Message(id, userLogged.getUsername(), userToWrite.getUsername(), text);
            messages.add(messageToSave);
            chatHandler.save(messageToSave, userLogged, userToWrite);
        }
    }

    @FXML
    public void generateConversation() {
        Chat chat = XMLManager.readXML(chatNow, chatNow.getFilePath());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Conversación");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {

                String filePath = file.getAbsolutePath();
                if (filePath.endsWith(".csv")) {
                    exportChatToCSV(chat, file);
                } else if (filePath.endsWith(".docx")) {
                    exportAsTxt(file, chat);
                }
        }
    }


    private void exportAsTxt(File file, Chat chat) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Message message : chat.getMessages()) {
                String formattedMessage = String.format("[%s] %s: %s",
                        message.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                        message.getSender(),
                        message.getText());
                writer.write(formattedMessage);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void exportChatToCSV(Chat chat, File file) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {

            writer.write("Remitente;Receptor;Texto;Fecha y hora\n");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            for (Message message : chat.getMessages()) {
                writer.write(String.format("%s;%s;\"%s\";%s\n",
                        message.getSender(),
                        message.getReceiver(),
                        message.getText().replace("\"", "\"\""),
                        message.getDateTime().format(formatter)
                ));
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    @FXML
    public void analyzeConversation() {
        Chat chat = XMLManager.readXML(chatNow, chatNow.getFilePath());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Análisis");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            exportAnalisisToTXT(chat, file);
        }
    }


    private void exportAnalisisToTXT(Chat chat, File file) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("Análisis de Conversación");
            writer.newLine();
            writer.newLine();

            int totalMessages = chat.getMessages().size();
            writer.write("Total de Mensajes: " + totalMessages);
            writer.newLine();



            List<String> senders = getListOfSenders(chat);

            Set<String> uniqueUsers = new HashSet<>(senders);
            for (String user : uniqueUsers) {
                long count = senders.stream().filter(sender -> sender.equals(user)).count();
                writer.write(user + ": " + count);
                writer.newLine();
            }
            writer.newLine();

            LocalDateTime startTime = chat.getMessages().get(1).getDateTime();
            LocalDateTime endTime = chat.getMessages().get(chat.getMessages().size() - 1).getDateTime();
            Duration duration = Duration.between(startTime, endTime);

            if (startTime != null && endTime != null) {
                writer.write("Duración de la conversación: " +
                        duration.toDaysPart() + " dias " + duration.toHoursPart() + " horas" + duration.toMinutesPart()+
                "minutos");
                writer.newLine();
                writer.write("El inicio de la conversación fue en: "+ startTime.format(formatter));
                writer.newLine();
                writer.write("El ultimo mensaje de la conversación fue en: "+ endTime.format(formatter));
            } else {
                writer.write("No hay mensajes en la conversación.");
            }

            writer.newLine();
            writer.newLine();


            String mostFrequentWord = getMostFrequentWord(chat);
            writer.write("Palabra Más Repetida: " + mostFrequentWord);
            writer.newLine();
            writer.newLine();

            List<String> commonWords = getMostCommonWords(chat);

            writer.write("Palabras Más Comunes:");
            writer.newLine();
            for (String commonWord : commonWords) {
                writer.write(commonWord);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al exportar el análisis a TXT.");
        }
    }

    private List<String> getListOfSenders(Chat chat){
        List<String> senders = new ArrayList<>();
        for (Message message : chat.getMessages()) {
            senders.add(message.getSender());
        }
        return senders;
    }

    private String getMostFrequentWord(Chat chat) {
        List<String> words = getWords(chat);


        String mostFrequent = null;
        int maxCount = 0;

        for (String word : words) {
            int count = Collections.frequency(words, word);
            if (count > maxCount) {
                maxCount = count;
                mostFrequent = word;
            }
        }
        return mostFrequent != null ? mostFrequent : "No hay palabras";
    }

    private List<String> getWords(Chat chat) {
        List<String> words = new ArrayList<>();
        for (Message message : chat.getMessages()) {
            String[] messageWords = message.getText().split("\\W+");
            for (String word : messageWords) {
                if (!word.isEmpty()) {
                    words.add(word.toLowerCase());
                }
            }
        }
        return words;
    }


    private List<String> getMostCommonWords(Chat chat) {

        List<String> words = new ArrayList<>();

        for (Message message : chat.getMessages()) {
            String[] messageWords = message.getText().toLowerCase().split("\\W+");
            for (String word : messageWords) {
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
        }

        List<String> uniqueWords = new ArrayList<>(new HashSet<>(words)); // Obtener palabras únicas
        List<String> mostCommonWords = new ArrayList<>();


        for (String uniqueWord : uniqueWords) {
            int count = (int) words.stream().filter(word -> word.equals(uniqueWord)).count(); // Contar ocurrencias
            mostCommonWords.add(uniqueWord + ": " + count + " veces"); // Almacenar la palabra y su conteo
        }

        for (int i = 0; i < mostCommonWords.size(); i++) {
            for (int j = 0; j < mostCommonWords.size() - 1; j++) {

                String[] partsA = mostCommonWords.get(j).split(": ");
                String[] partsB = mostCommonWords.get(j + 1).split(": ");
                int countA = Integer.parseInt(partsA[1].split(" ")[0]); // Obtener solo el número
                int countB = Integer.parseInt(partsB[1].split(" ")[0]); // Obtener solo el número


                if (countA < countB) {
                    String temp = mostCommonWords.get(j);
                    mostCommonWords.set(j, mostCommonWords.get(j + 1));
                    mostCommonWords.set(j + 1, temp);
                }
            }
        }

        return mostCommonWords;
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
            messages.add(message);
        }
    }{}

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


}
