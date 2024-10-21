package com.github.JoseAngelGiron.test;

import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.entity.UserList;
import com.github.JoseAngelGiron.persistance.XMLManager;

import static com.github.JoseAngelGiron.model.security.Security.encryptPassword;


public class test1XMLGenerator {

    private static final String usersFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\users\\users.xml";

    public static void main(String[] args) {
        UserList users = new UserList();
        System.out.println(XMLManager.createXML(users, usersFilePath));


        User userTest = new User("namepepep", encryptPassword("prueba1234"), "example2@gmail.com", "Hi, I'm using whatsapp !");
        User userTest2 = new User("name2", encryptPassword("prueba123455"), "example44@gmail.com", "Hi, I'm using whatsapp !");
        userTest2.setId(1);
        users.getListOfUsers().add(userTest);
        users.getListOfUsers().add(userTest2);
        XMLManager.writeXML(users, usersFilePath);

    }
}
