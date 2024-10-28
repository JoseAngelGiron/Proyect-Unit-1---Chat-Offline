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


    @Override
    public boolean create (User userLogged, User userReceived){
        Chat chat = new Chat(userLogged.getUsername(), userReceived.getUsername());
        chat.setFilePath(chatFilePath + userLogged.getUsername() + "-" +
                userLogged.getId() + "_" + userReceived.getUsername() + "-" + userReceived.getId() + ".xml");

        return XMLManager.createXML(chat, chat.getFilePath());
    }
}
