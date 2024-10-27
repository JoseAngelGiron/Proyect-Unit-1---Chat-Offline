package com.github.JoseAngelGiron.model.entity;

import com.github.JoseAngelGiron.persistance.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement(name = "message")
@XmlType(propOrder = { "id", "sender", "receiver", "text", "dateTime" })
public class Message {

    int id;
    private String sender; //Para BBBD usar USER
    private String receiver; //Para BBBD usar USER
    private String text;
    private LocalDateTime dateTime;

    public Message(){

    }


    public Message(int id, String sender, String receiver, String text) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.dateTime = LocalDateTime.now();
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlElement
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    @XmlElement
    public String getSender() {
        return sender;
    }

    public void setSender(String emisor) {
        this.sender = emisor;
    }
    @XmlElement
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
