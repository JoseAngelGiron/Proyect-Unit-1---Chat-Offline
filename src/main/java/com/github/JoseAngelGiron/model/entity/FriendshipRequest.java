package com.github.JoseAngelGiron.model.entity;

import com.github.JoseAngelGiron.persistance.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement(name = "friendshipRequest")
@XmlType(propOrder = { "sender", "idSender", "receiver", "status", "date"})
public class FriendshipRequest {

    private String sender;
    private int idSender;
    private String receiver;
    private FriendshipRequestStatus status;
    private LocalDateTime date;


    public FriendshipRequest() {

    }

    public FriendshipRequest(String sender,int idSender, String receiver) {
        this.sender = sender;
        this.idSender = idSender;
        this.receiver = receiver;
        this.status = FriendshipRequestStatus.PENDING;
        this.date = LocalDateTime.now();

    }

    @XmlElement
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @XmlElement
    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    @XmlElement
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    @XmlElement
    public FriendshipRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipRequestStatus status) {
        this.status = status;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendshipRequest)) return false;
        FriendshipRequest that = (FriendshipRequest) o;
        return Objects.equals(sender, that.sender) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver);
    }

    @Override
    public String toString() {
        return "FriendshipRequest{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", status='" + status + '\'' +
                ", timestamp=" + date +
                '}';
    }
}
