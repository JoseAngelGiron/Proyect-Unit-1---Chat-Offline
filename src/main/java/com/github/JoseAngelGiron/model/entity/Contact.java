package com.github.JoseAngelGiron.model.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement(name = "Contact")
@XmlType(propOrder = { "id", "nameOfContact"})
public class Contact {

    private int id;
    private String nameOfContact;

    public Contact() {

    }

    public Contact(int id, String nameOfContact) {
        this.id = id;
        this.nameOfContact = nameOfContact;
    }
    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlElement
    public String getNameOfContact() {
        return nameOfContact;
    }

    public void setNameOfContact(String nameOfContact) {
        this.nameOfContact = nameOfContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return id == contact.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", nameOfContact='" + nameOfContact + '\'' +
                '}';
    }
}
