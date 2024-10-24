package com.github.JoseAngelGiron.model.xmlDataHandler;

import com.github.JoseAngelGiron.model.entity.*;

import com.github.JoseAngelGiron.persistance.XMLManager;


import java.util.ArrayList;
import java.util.List;

public class FriendshipRequestHandler implements IXMLHandler<FriendshipRequest, FriendshipRequestList>{

    private static final String friendshipFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\friendshipRequests\\friendshipRequests.xml";

    @Override
    public FriendshipRequestList findAll() {
        return XMLManager.readXML(friendshipFilePath, FriendshipRequestList.class);
    }

    @Override
    public FriendshipRequest save(FriendshipRequest entity) {


        FriendshipRequest friendshipRequest = new FriendshipRequest();
        if(checkIfContactExists(entity)){
            FriendshipRequestList friendshipRequestList = findAll();

            if( friendshipRequestList.addRequest(entity) ){

                XMLManager.writeXML(friendshipRequestList, friendshipFilePath);
                friendshipRequest = entity;
            }
        }

        return friendshipRequest;
    }

    private boolean checkIfContactExists(FriendshipRequest entity){
        ContactListHandler contactListHandler = new ContactListHandler();

        ContactList contactList = contactListHandler.findAll(entity.getSender()+"-"+entity.getIdSender());
        boolean contactAlreadyExists= false;
        for (Contact contact: contactList.getContacts()){

            if(contact.getId()==entity.getIdSender()){
                System.out.println(contact.getNameOfContact());
                contactAlreadyExists = true;
                break;
            }
        }
        return contactAlreadyExists;
    }

    @Override
    public boolean update(FriendshipRequest entity) {
        FriendshipRequestList listOfRequests = findAll();
        boolean updated = false;
        for(FriendshipRequest friendshipRequest: listOfRequests.getRequests()){
            if(friendshipRequest.equals(entity)){
                friendshipRequest.setStatus(entity.getStatus());
                friendshipRequest.setTimestamp(entity.getTimestamp());
                updated = true;
                break;

            }
        }
        return updated;
    }

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

    @Override
    public boolean delete(String name) { //Borra todas las peticiones del sender (usuario logueado seguramente)
        //No descarto hacer mas metodos para borrar, como el de receiver
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
