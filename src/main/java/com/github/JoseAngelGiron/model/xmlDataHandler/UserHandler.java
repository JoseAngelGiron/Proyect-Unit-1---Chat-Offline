package com.github.JoseAngelGiron.model.xmlDataHandler;

import com.github.JoseAngelGiron.model.entity.Contact;
import com.github.JoseAngelGiron.model.entity.ContactList;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.entity.UserList;
import com.github.JoseAngelGiron.persistance.XMLManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.JoseAngelGiron.model.xmlDataHandler.ContactListHandler.build;


public class UserHandler implements IXMLHandler<User, UserList>{

    private static final String usersFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\users\\users.xml";

    /**
     * Retrieves all users from the XML file.
     *
     * This method reads the XML file containing user information and returns
     * a UserList object populated with all the users. It uses the XMLManager
     * to handle the reading and parsing of the XML file.
     *
     * @return UserList A UserList object containing all users retrieved from the XML file.
     *                  If the file does not exist or is empty, an empty list may be returned.
     */
    @Override
    public UserList findAll() {
        return XMLManager.readXML(usersFilePath, UserList.class);
    }

    /**
     * Retrieves a set of users whose IDs match the contacts in the given contact list.
     *
     * This method iterates through the contacts in the provided ContactList and compares
     * their IDs with the IDs of users retrieved from the user list. It returns a set of
     * User objects that correspond to the matching contacts.
     *
     * @param contactList The ContactList containing contacts whose IDs are to be matched with users.
     * @return Set<User> A set of User objects that match the IDs of the contacts in the provided
     *                   ContactList. If no matches are found, an empty set is returned.
     */
    public Set<User> findListOfUsersByID(ContactList contactList){
        UserList userList = findAll();

        Set<User> listToReturn = new HashSet<>();
        for(Contact contact : contactList.getContacts()){

            for(User user: userList.getListOfUsers()) {
                if(contact.getId()==user.getId()){
                    listToReturn.add(user);
                }
            }
        }

        return listToReturn;
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * This method searches through the list of users and returns the User object
     * that matches the specified ID. If no user with the given ID is found, it
     * returns an empty User object.
     *
     * @param id The unique identifier of the user to be retrieved.
     * @return User The User object that matches the specified ID;
     *              if no match is found, an empty User object is returned.
     */
    public User findByID(int id){
        UserList userList = findAll();
        User userToReturn = new User();
        for(User user: userList.getListOfUsers()){
            if(user.getId()==id){
                userToReturn = user;
            }
        }
        return userToReturn;
    }

    /**
     * Retrieves a list of users whose usernames contain the specified name.
     *
     * This method searches through the list of users and returns a list of User objects
     * whose usernames contain the given name. It excludes the user specified by the
     * `yourself` parameter from the results.
     *
     * @param name    The substring to search for within usernames.
     * @param yourself The User object representing the current user, who should be excluded from the results.
     * @return List<User> A list of User objects whose usernames contain the specified name,
     *                    excluding the current user. If no matches are found, an empty list is returned.
     */
    public List<User> findByName(String name, User yourself){
        UserList userList = findAll();
        List<User> usersToReturn = new ArrayList<>();

        for(User user: userList.getListOfUsers()){
            if(user.getUsername().contains(name) && !yourself.getUsername().contains(name)){
                usersToReturn.add(user);
            }

        }
        return usersToReturn;
    }

    /**
     * Checks if a user with the specified username exists.
     *
     * This method searches through the list of users and checks if there is a
     * User object whose username matches the specified name, ignoring case.
     * It returns true if a matching user is found; otherwise, it returns false.
     *
     * @param name The username to search for.
     * @return boolean True if a user with the specified username exists;
     *                 false if no such user is found.
     */
    public boolean findByName(String name){
        UserList userList = findAll();
        boolean userFind = false;

        for(User user: userList.getListOfUsers()){
            if (user.getUsername().equalsIgnoreCase(name)) {
                userFind = true;
                break;
            }

        }
        return userFind;
    }

    /**
     * Retrieves a user by their email address.
     *
     * This method searches through the list of users and returns the User object
     * whose email matches the email of the provided User object, ignoring case.
     * If no user with the specified email is found, it returns an empty User object.
     *
     * @param userToCompare The User object containing the email to search for.
     * @return User The User object that matches the specified email;
     *              if no match is found, an empty User object is returned.
     */
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

    /**
     * Retrieves a user by their email and password.
     *
     * This method searches through the list of users and returns the User object
     * whose email and password match those of the provided User object. If no user
     * with the specified email and password is found, it returns an empty User object.
     *
     * @param userToCheck The User object containing the email and password to search for.
     * @return User The User object that matches the specified email and password;
     *              if no match is found, an empty User object is returned.
     */
    public User findByEmailAndPassword(User userToCheck) {
        UserList userList = findAll();
        User userToReturn = new User();

        for(User user: userList.getListOfUsers()) {

            if (user.getEmail().equals(userToCheck.getEmail()) &&
                    user.getPassword().equals(userToCheck.getPassword())){

                userToReturn = user;
                break;
            }
        }
        return userToReturn;
    }


    /**
     * Saves a new user to the user list and assigns a unique ID.
     *
     * This method adds a new User to the user list, assigning it a unique ID based
     * on the current maximum user ID. If the user is successfully added, the updated
     * user list is saved to the XML file, and the saved User object is returned. If
     * the save operation fails, an empty User object is returned.
     *
     * @param entity The User object to be saved.
     * @return User The User object that was saved with an assigned ID; if the user
     *              could not be saved, an empty User object is returned.
     */
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

    /**
     * Finds the next unique user ID based on the current maximum ID in the user list.
     *
     * This method iterates through the user list to identify the highest user ID
     * and returns the next ID increment. If the user list is empty, it returns 0
     * as the initial ID.
     *
     * @param userList The UserList containing the users from which to determine the maximum ID.
     * @return int The next unique ID to be assigned to a new user.
     */
    private int findMaxUserId(UserList userList) {
        int maxId = -1;

        for (User user : userList.getListOfUsers()) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId +1;
    }

    /**
     * Updates an existing user in the user list.
     *
     * This method searches for the specified User in the user list. If found, it
     * removes the old User instance and adds the updated User object. The updated
     * user list is then saved to the XML file. The method returns true if the
     * update was successful, otherwise false.
     *
     * @param entity The User object containing updated information.
     * @return boolean True if the user was successfully updated; false if the user
     *                 was not found in the list.
     */
    @Override
    public boolean update(User entity) {

        boolean updated = false;
        UserList userList = findAll();
        for(User user: userList.getListOfUsers()){
            if(user.equals(entity)){
                userList.getListOfUsers().remove(user);
                userList.getListOfUsers().add(entity);
                XMLManager.writeXML(userList, usersFilePath);
                updated = true;
                break;
            }
        }
        return updated;
    }
    /**
     * Deletes a user from the user list by their username.
     *
     * This method searches for a User in the user list with a username that matches
     * the specified name. If found, the user is removed from the list, and the updated
     * user list is saved to the XML file. The method returns true if the deletion was
     * successful, otherwise false.
     *
     * @param name The username of the User to be deleted.
     * @return boolean True if the user was successfully deleted; false if no user
     *                 with the specified username was found.
     */
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

    /**
     * Creates a new, empty user list and saves it to an XML file.
     *
     * This method initializes a new instance of UserList and saves it as an XML
     * file at the specified file path. The method returns true if the XML file
     * is successfully created, otherwise false.
     *
     * @return boolean True if the XML file is successfully created; false if the
     *                 creation fails.
     */
    @Override
    public boolean create() {
        UserList userList = new UserList();
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
