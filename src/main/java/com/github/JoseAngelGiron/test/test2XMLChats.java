package com.github.JoseAngelGiron.test;

import com.github.JoseAngelGiron.model.entity.Chat;
import com.github.JoseAngelGiron.model.entity.User;

import com.github.JoseAngelGiron.persistance.XMLManager;


public class test2XMLChats {

    private static final String chatsFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\chats\\chat1.xml";
     //para el algoritmo de conversaciones, coger y usar appends 1.xml, 2.xml. ... etc
    public static void main(String[] args) {



        //Chat chat = XMLManager.readXML(chatsFilePath, chatsFilePath);
        User userTest = new User("namepepep", "prueba1234", "example@gmail.com");
        User userTest2 = new User("name2", "prueba1234", "example@gmail.com");
        userTest2.setId(1);
        //userList.getListOfUsers().add(userTest);
        //userList.getListOfUsers().add(userTest2);
        //XMLManager.writeXML(userList, usersFilePath);

    }
}
