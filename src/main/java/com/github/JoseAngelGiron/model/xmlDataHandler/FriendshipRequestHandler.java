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
