package com.github.JoseAngelGiron.model.xmldatahandler;

import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.entity.UserList;
import com.github.JoseAngelGiron.persistance.XMLManager;




public class UserHandler implements IXMLHandler<User, UserList>{

    private static final String usersFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\users\\users.xml";




    @Override
    public UserList findAll() {
        return XMLManager.readXML(usersFilePath, UserList.class);
    }

    public User findByID(User user){
        UserList userList = findAll();
        for(User user1: userList.getListOfUsers()){
            //user.
        }

        return user;
    }

    public User findByEmail(User userToCompare){
        UserList userList = findAll();
        User userToReturn = new User();

        for(User user: userList.getListOfUsers()){
            if(user.getEmail().equalsIgnoreCase(userToCompare.getEmail())){
                userToReturn = user;
                break;
            }
        }

        return  userToReturn;
    }



    @Override
    public User save(User entity) {

        User userToReturn = new User();
        UserList userList = findAll();
        int maxId = findMaxUserId(userList);
        entity.setId(maxId);

        if(userList.getListOfUsers().add(entity)){

            XMLManager.writeXML(userList, usersFilePath);
            userToReturn = entity;
        }
        return userToReturn;
    }

    private int findMaxUserId(UserList userList) {
        int maxId = -1;

        for (User user : userList.getListOfUsers()) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId +1;
    }

    @Override
    public boolean update(User entity) {

        boolean updated = false;
        UserList userList = findAll();
        for(User user: userList.getListOfUsers()){
            if(userList.getListOfUsers().contains(entity)){
                userList.getListOfUsers().remove(user);
                userList.getListOfUsers().add(user);
                XMLManager.writeXML(userList, usersFilePath);
                updated = true;
                break;
            }
        }

        return updated;

    }

    @Override
    public boolean delete(String name) {
        boolean deleted = false;
        User userToDelete = new User();
        userToDelete.setUsername(name);

        UserList userList = findAll();
        for(User user: userList.getListOfUsers()){
            if(user.getUsername().equals(userToDelete.getUsername())){
                userList.getListOfUsers().remove(user);
                XMLManager.writeXML(userList, usersFilePath);
                deleted = true;
                break;
            }
        }

        return deleted;
    }

    @Override
    public boolean create() {
        UserList userList = findAll();
        return XMLManager.createXML(userList, usersFilePath);
    }

    /**
     * Builds and returns a new instance of the UserHandler class.
     *
     * @return A new instance of the UserHandler class.
     */
    public static UserHandler build(){
        return new UserHandler();
    }
}
