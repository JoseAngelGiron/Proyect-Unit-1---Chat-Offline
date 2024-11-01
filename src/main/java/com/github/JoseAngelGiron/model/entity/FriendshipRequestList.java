package com.github.JoseAngelGiron.model.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "friendshipRequests")
public class FriendshipRequestList {

    private List<FriendshipRequest> requests;

    public FriendshipRequestList() {
        this.requests = new ArrayList<>();
    }

    @XmlElementWrapper(name = "requests")
    @XmlElement(name = "request")
    public List<FriendshipRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<FriendshipRequest> requests) {
        this.requests = requests;
    }

    public boolean addRequest(FriendshipRequest request) {
        boolean added = false;

        if(!requests.contains(request)){
            requests.add(request);
            added = true;
        }
        return added;

    }

    public void removeRequest(FriendshipRequest request) {
        requests.remove(request);
    }



}
