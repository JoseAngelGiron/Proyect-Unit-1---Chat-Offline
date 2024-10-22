package com.github.JoseAngelGiron.model.xmlDataHandler;

import com.github.JoseAngelGiron.model.entity.FriendshipRequest;
import com.github.JoseAngelGiron.model.entity.FriendshipRequestList;

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
        FriendshipRequestList friendshipRequestList = findAll();

        if( friendshipRequestList.addRequest(entity) ){

            XMLManager.writeXML(friendshipRequestList, friendshipFilePath);
            friendshipRequest = entity;
        }
        return friendshipRequest;
    }

    @Override
    public boolean update(FriendshipRequest entity) {
        return false;
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
