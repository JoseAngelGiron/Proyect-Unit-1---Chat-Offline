package com.github.JoseAngelGiron.model.xmlDataHandler;

import com.github.JoseAngelGiron.model.entity.FriendshipRequest;
import com.github.JoseAngelGiron.model.entity.FriendshipRequestList;

import com.github.JoseAngelGiron.persistance.XMLManager;

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


        if(friendshipRequestList.getRequests().add(entity)){

            XMLManager.writeXML(friendshipRequestList, friendshipFilePath);
            friendshipRequest = entity;
        }
        return friendshipRequest;
    }

    @Override
    public boolean update(FriendshipRequest entity) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public boolean create() {
        FriendshipRequestList fr = new FriendshipRequestList();
        return XMLManager.createXML(fr, friendshipFilePath);
    }
}
