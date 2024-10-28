package com.github.JoseAngelGiron.model.xmlDataHandler;

import com.github.JoseAngelGiron.model.entity.*;

import com.github.JoseAngelGiron.persistance.XMLManager;


import java.util.ArrayList;
import java.util.List;

public class FriendshipRequestHandler implements IXMLHandler<FriendshipRequest, FriendshipRequestList>{

    private static final String friendshipFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\friendshipRequests\\friendshipRequests.xml";

    /**
     * Retrieves all friendship requests from the XML file.
     *
     * This method reads the XML file containing friendship requests and returns
     * a FriendshipRequestList object populated with the requests.
     * It uses the XMLManager to handle the reading and parsing of the XML file.
     *
     * @return FriendshipRequestList A FriendshipRequestList object containing all friendship
     *                                requests retrieved from the XML file.
     *                                If the file does not exist or is empty, an empty list may be returned.
     */
    @Override
    public FriendshipRequestList findAll() {
        return XMLManager.readXML(friendshipFilePath, FriendshipRequestList.class);
    }

    /**
     * Retrieves a list of friendship requests sent by a specific user.
     *
     * This method searches through all friendship requests and returns a list
     * containing only the requests that were sent by the user with the specified name.
     * It uses the findAll method to get the complete list of requests and then filters
     * them based on the sender's name.
     *
     * @param name The name of the user whose friendship requests are to be retrieved.
     * @return List<FriendshipRequest> A list of FriendshipRequest objects sent by the user
     *                                  with the specified name. If no requests are found,
     *                                  an empty list is returned.
     */
    public List<FriendshipRequest> findBySender(String name){

        FriendshipRequestList listOfRequests = findAll();
        List<FriendshipRequest> userListOfRequests = new ArrayList<>();

        for(FriendshipRequest fr : listOfRequests.getRequests()){
            if(fr.getSender().equals(name)){
                userListOfRequests.add(fr);
            }
        }
        return userListOfRequests;
    }

    /**
     * Retrieves a list of friendship requests received by a specific user.
     *
     * This method searches through all friendship requests and returns a list
     * containing only the requests that were received by the user with the specified name.
     * It uses the findAll method to obtain the complete list of requests and then filters
     * them based on the receiver's name.
     *
     * @param name The name of the user whose received friendship requests are to be retrieved.
     * @return List<FriendshipRequest> A list of FriendshipRequest objects received by the user
     *                                  with the specified name. If no requests are found,
     *                                  an empty list is returned.
     */
    public List<FriendshipRequest> findByReceiver(String name){

        FriendshipRequestList listOfRequests = findAll();
        List<FriendshipRequest> userListOfRequests = new ArrayList<>();

        for(FriendshipRequest fr : listOfRequests.getRequests()){

            if(fr.getReceiver().equals(name)){
                userListOfRequests.add(fr);
            }
        }
        return userListOfRequests;
    }

    /**
     * Deletes all friendship requests sent by a specific user.
     *
     * This method retrieves the list of all friendship requests and filters out
     * the requests sent by the user with the specified name. If any requests are found
     * and successfully removed from the list, the method returns true; otherwise, it returns false.
     *
     * @param name The name of the user whose friendship requests are to be deleted.
     * @return boolean True if all friendship requests sent by the user were successfully
     *                 deleted; false if no requests were found or deleted.
     */
    @Override
    public boolean delete(String name) {
        FriendshipRequestList frList = findAll();
        List<FriendshipRequest> listOfRequest = findBySender(name);

        boolean deleted = true;

        for(FriendshipRequest fr : frList.getRequests()){
            for(FriendshipRequest fr2 : listOfRequest){

                if(fr.getSender().equals(fr2.getSender())){
                    frList.removeRequest(fr);
                    deleted = false;
                }
            }
        }



        return deleted;
    }

    /**
     * Deletes a specific friendship request from the list.
     *
     * This method searches for the given FriendshipRequest in the list of all friendship requests.
     * If the request is found, it is removed from the list, and the updated list is saved back
     * to the XML file. The method returns true if the request was successfully deleted; otherwise, false.
     *
     * @param friendshipRequest The FriendshipRequest object to be deleted from the list.
     * @return boolean True if the specified friendship request was successfully deleted;
     *                 false if the request was not found in the list.
     */
    public boolean deleteOneRequest(FriendshipRequest friendshipRequest) {

        FriendshipRequestList frList = findAll();
        boolean deleted = false;

        for(FriendshipRequest frRequest : frList.getRequests()){

            if(frRequest.equals(friendshipRequest)){
                frList.removeRequest(frRequest);
                deleted = true;
                break;
            }

        }

        XMLManager.writeXML(frList ,friendshipFilePath);
        return deleted;
    }


    /**
     * Checks if a contact already exists in the user's contact list.
     *
     * This method verifies whether the receiver of a FriendshipRequest is already present
     * in the sender's contact list. It retrieves the contact list associated with the sender
     * and checks if the contact's name matches the receiver's name (case-insensitive).
     *
     * @param entity The FriendshipRequest object containing the sender and receiver information.
     * @return boolean True if the contact already exists in the contact list;
     *                 false if the contact does not exist.
     */
    private boolean checkIfContactNotExists(FriendshipRequest entity){
        ContactListHandler contactListHandler = new ContactListHandler();

        ContactList contactList = contactListHandler.findAll(entity.getSender());
        boolean contactAlreadyExists= false;
        for (Contact contact: contactList.getContacts()){

            if(contact.getNameOfContact().equalsIgnoreCase(entity.getReceiver())){

                contactAlreadyExists = true;
                break;
            }
        }
        return contactAlreadyExists;
    }

    /**
     * Saves a friendship request if the recipient is not already a contact.
     *
     * This method checks if the receiver of the given FriendshipRequest already exists in
     * the sender's contact list. If the receiver does not exist, the friendship request is
     * added to the list of friendship requests, and the updated list is saved to the XML file.
     * The method returns the saved FriendshipRequest if successful; otherwise, it returns an empty
     * FriendshipRequest object.
     *
     * @param entity The FriendshipRequest object to be saved.
     * @return FriendshipRequest The saved FriendshipRequest object if successfully added;
     *                           otherwise, an empty FriendshipRequest object.
     */
    @Override
    public FriendshipRequest save(FriendshipRequest entity) {


        FriendshipRequest friendshipRequest = new FriendshipRequest();
        if(!checkIfContactNotExists(entity)){
            FriendshipRequestList friendshipRequestList = findAll();

            if( friendshipRequestList.addRequest(entity) ){

                XMLManager.writeXML(friendshipRequestList, friendshipFilePath);
                friendshipRequest = entity;
            }
        }

        return friendshipRequest;
    }

    /**
     * Updates the status and date of a specific friendship request.
     *
     * This method searches for the given FriendshipRequest in the list of all friendship requests.
     * If a matching request is found, its status and date are updated with the values from the
     * provided entity. The method returns true if the request was successfully updated;
     * otherwise, it returns false.
     *
     * @param entity The FriendshipRequest object containing the updated status and date.
     * @return boolean True if the friendship request was successfully updated;
     *                 false if no matching request was found.
     */
    @Override
    public boolean update(FriendshipRequest entity) {
        FriendshipRequestList listOfRequests = findAll();
        boolean updated = false;
        for(FriendshipRequest friendshipRequest: listOfRequests.getRequests()){
            if(friendshipRequest.equals(entity)){
                friendshipRequest.setStatus(entity.getStatus());
                friendshipRequest.setDate(entity.getDate());
                updated = true;
                break;

            }
        }
        return updated;
    }


    /**
     * Creates a new friendship request list and saves it to the XML file.
     *
     * This method initializes a new FriendshipRequestList object and uses the
     * XMLManager to create and save the corresponding XML file at the specified path.
     * It returns true if the creation was successful; otherwise, it returns false.
     *
     * @return boolean True if the friendship request list was successfully created
     *                 and saved; false otherwise.
     */
    @Override
    public boolean create() {
        FriendshipRequestList fr = new FriendshipRequestList();
        return XMLManager.createXML(fr, friendshipFilePath);
    }

    /**
     * Builds and returns a new instance of the FrienshipRequestHandler class.
     *
     * @return A new instance of the FrienshipRequestHandler class.
     */
    public static FriendshipRequestHandler build(){
        return new FriendshipRequestHandler();
    }
}
