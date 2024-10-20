package com.github.JoseAngelGiron.model.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@XmlRootElement
@XmlType(propOrder = { "id", "messages", "users" })
public class Group {
    private int id;
    private List<Message> messages;
    private Set<User> users;

    public Group(int id) {
        this.id = id;
        this.messages = new ArrayList<>();
        this.users = new HashSet<>();
    }
    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @XmlElement
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
