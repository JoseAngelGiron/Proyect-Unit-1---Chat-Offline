package com.github.JoseAngelGiron.model.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement(name = "friendshipRequest")
public class FriendshipRequest {

    private String sender;
    private String receiver;
    private FriendshipRequestStatus status;
    private LocalDateTime timestamp;


    public FriendshipRequest() {

    }

    public FriendshipRequest(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = FriendshipRequestStatus.PENDING;
        this.timestamp = LocalDateTime.now();
    }

    @XmlElement
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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
                ", timestamp=" + timestamp +
                '}';
    }
}
