package com.github.JoseAngelGiron.model.xmlDataHandler;

import com.github.JoseAngelGiron.model.entity.Chat;
import com.github.JoseAngelGiron.model.entity.Message;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.persistance.XMLManager;

import java.io.File;

public class ChatHandler implements IChatHandler<Message, Chat> {

    private static final String chatFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\chats\\";

    /**
     * No usages
     * @param name
     * @return
     */
    @Override
    public Chat findAll(String name) {
        return null;
    }

    /**
     * Saves a message in the chat between two users.
     *
     * This method retrieves the chat between the logged-in user and the user to write to.
     * It sets the participants of the chat, checks if the message already exists in the chat,
     * and if not, adds it to the chat's message list. The updated chat is then saved to an XML file.
     *
     * @param entity The Message object to be saved. This is the message that the logged-in user
     *               wants to send.
     * @param userLogged The User object representing the currently logged-in user.
     * @param userToWrite The User object representing the recipient of the message.
     * @return Message The saved Message object if it was added successfully, or an empty Message
     *                 object if the message already existed in the chat.
     */
    @Override
    public Message save(Message entity, User userLogged, User userToWrite) {
        Chat chat = retrieveChat(userLogged, userToWrite);

        chat.setUserParticipant1(userLogged.getUsername());
        chat.setUserParticipant2(userToWrite.getUsername());

        Message messageToReturn = new Message();
        if(!chat.getMessages().contains(entity)){
            chat.getMessages().add(entity);
            messageToReturn = entity;
            XMLManager.writeXML(chat,chat.getFilePath());
        }


        return messageToReturn;
    }

    /**
     * No usages
     * @param entity
     * @return
     */
    @Override
    public boolean update(Message entity) {
        return false;
    }

    /**
     * No usages
     * @param name
     * @return
     */
    @Override
    public boolean delete(String name) {
        return false;
    }

    /**
     * Retrieves the last message ID from the chat between two users.
     *
     * This method accesses the chat between the logged-in user and the user to write to.
     * It iterates through the messages in the chat to find the highest message ID.
     * If no messages exist, it returns 1 as the next ID.
     *
     * @param userLogged The User object representing the currently logged-in user.
     * @param userToWrite The User object representing the other participant in the chat.
     * @return int The next available message ID, which is one greater than the highest existing
     *             message ID in the chat. If no messages are present, it returns 1.
     */
    public int retrieveLastIDMessage(User userLogged, User userToWrite){
        Chat chat = retrieveChat(userLogged, userToWrite);
        int numberToReturn = 0;
        if(chat!=null){
            for(Message message: chat.getMessages()){

                if(message.getId()>numberToReturn){
                    numberToReturn = message.getId();
                }
            }
        }

        return numberToReturn+1;

    }

    /**
     * Retrieves the chat between two users.
     *
     * This method looks for the chat file corresponding to the logged-in user and the user
     * receiving the message. It searches through the files in the specified chat directory,
     * checking for a file that contains both user IDs in its name. If found, it reads the
     * chat from the XML file and sets the file path accordingly.
     *
     * @param userLogged The User object representing the currently logged-in user.
     * @param userReceived The User object representing the other participant in the chat.
     * @return Chat The Chat object containing the messages exchanged between the two users.
     *              If no chat file is found, an empty Chat object is returned.
     */
    public Chat retrieveChat(User userLogged, User userReceived) {
        Chat chatToReturn = new Chat();

        File chatDirectory = new File(chatFilePath);
        File[] files = chatDirectory.listFiles();

        String idUserLogged = String.valueOf(userLogged.getId());
        String idUserReceived = String.valueOf(userReceived.getId());

        for (File file : files) {
            if (file.getName().contains(idUserLogged) && file.getName().contains(idUserReceived)) {
                chatToReturn = XMLManager.readXML(chatToReturn, file.getPath());
                chatToReturn.setFilePath(file.getAbsolutePath());
                break;
            }

        }
        return chatToReturn;

    }

    /**
     * Creates a new chat between two users.
     *
     * This method initializes a new Chat object with the usernames of the logged-in user
     * and the user receiving the message. It sets the file path for the chat XML file
     * based on the usernames and user IDs. Finally, it attempts to create the XML file
     * for the chat using the XMLManager.
     *
     * @param userLogged The User object representing the currently logged-in user.
     * @param userReceived The User object representing the other participant in the chat.
     * @return boolean True if the chat was successfully created and the XML file was generated,
     *                 false otherwise.
     */
    @Override
    public boolean create (User userLogged, User userReceived){
        Chat chat = new Chat(userLogged.getUsername(), userReceived.getUsername());
        chat.setFilePath(chatFilePath + userLogged.getUsername() + "-" +
                userLogged.getId() + "_" + userReceived.getUsername() + "-" + userReceived.getId() + ".xml");

        return XMLManager.createXML(chat, chat.getFilePath());
    }
}
