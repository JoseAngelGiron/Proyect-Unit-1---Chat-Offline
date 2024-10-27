package com.github.JoseAngelGiron.model.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XmlRootElement(name = "Chat")
@XmlType(propOrder = {"userParticipant1", "userParticipant2", "filePath", "messages",  })
public class Chat {

    private ArrayList<Message> messages;
    private String userParticipant1;
    private String userParticipant2;
    private String filePath;


    public Chat() {
        this.messages = new ArrayList<>();
    }

    public Chat( String user1, String user2) {

        this.messages = new ArrayList<>();
        this.userParticipant1 = user1;
        this.userParticipant2 = user2;
    }

    @XmlElement(name = "message")
    @XmlElementWrapper(name = "messages")
    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    @XmlElement
    public String getUserParticipant1() {
        return userParticipant1;
    }

    public void setUserParticipant1(String userParticipant1) {
        this.userParticipant1 = userParticipant1;
    }

    @XmlElement
    public String getUserParticipant2() {
        return userParticipant2;
    }

    public void setUserParticipant2(String userParticipant2) {
        this.userParticipant2 = userParticipant2;
    }

    @XmlElement
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(userParticipant1, chat.userParticipant1) && Objects.equals(userParticipant2, chat.userParticipant2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userParticipant1, userParticipant2);
    }
}
