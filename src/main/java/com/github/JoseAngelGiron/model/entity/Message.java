package com.github.JoseAngelGiron.model.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement(name = "message")
@XmlType(propOrder = {  "emisor", "receptor", "text", "dateTime" })
public class Message {


    private String emisor; //Para BBBD usar USER
    private String receptor; //Para BBBD usar USER
    private String text;
    private LocalDateTime dateTime;

    public Message(){

    }


    public Message(String emisor, String receptor, String text, LocalDateTime dateTime) {

        this.emisor = emisor;
        this.receptor = receptor;
        this.text = text;
        this.dateTime = dateTime;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getTexto() {
        return text;
    }

    public void setTexto(String texto) {
        this.text = texto;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(emisor, message.emisor) && Objects.equals(receptor, message.receptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emisor, receptor);
    }
}
