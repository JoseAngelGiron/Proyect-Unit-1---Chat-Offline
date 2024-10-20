package com.github.JoseAngelGiron.model.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XmlRootElement(name = "users")
public class UserList {

    private Set<User> listOfUsers;

    public UserList() {
        this.listOfUsers = new HashSet<>();
    }

    @XmlElement(name = "user")
    public Set<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(Set<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserList)) return false;
        UserList userList = (UserList) o;
        return Objects.equals(listOfUsers, userList.listOfUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(listOfUsers);
    }

    @Override
    public String toString() {
        return "UserList{" +
                "listOfUsers=" + listOfUsers +
                '}';
    }
}
