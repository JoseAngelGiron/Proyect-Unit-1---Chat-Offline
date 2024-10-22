package com.github.JoseAngelGiron.model.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@XmlRootElement(name = "ContactList")
@XmlType(propOrder = { "id", "nameOfUser", "names"})
public class ContactList {

    private int id;
    private String nameOfUser;
    private List<String> names;

    public ContactList() {


    }

    public ContactList(int id, String nameOfUser) {
        this.id = id;
        this.nameOfUser = nameOfUser;
        this.names = new ArrayList<>();
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlElement
    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    @XmlElement
    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactList)) return false;
        ContactList that = (ContactList) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ContactList{" +
                "id=" + id +
                ", nameOfUser='" + nameOfUser + '\'' +
                '}';
    }
}
