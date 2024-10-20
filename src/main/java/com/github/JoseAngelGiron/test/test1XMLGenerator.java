package com.github.JoseAngelGiron.test;

import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.entity.UserList;
import com.github.JoseAngelGiron.persistance.XMLManager;



public class test1XMLGenerator {

    private static final String usersFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\users\\users.xml";

    public static void main(String[] args) {
        UserList users = new UserList();
        System.out.println(XMLManager.createXML(users, usersFilePath));

        UserList userList = XMLManager.readXML(usersFilePath);
        User userTest = new User("namepepep", "prueba1234", "example@gmail.com");
        User userTest2 = new User("name2", "prueba1234", "example@gmail.com");
        userTest2.setId(1);
        userList.getListOfUsers().add(userTest);
        userList.getListOfUsers().add(userTest2);
        XMLManager.writeXML(userList, usersFilePath);

    }
}
