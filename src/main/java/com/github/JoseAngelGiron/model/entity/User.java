package com.github.JoseAngelGiron.model.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

import static com.github.JoseAngelGiron.model.security.Security.encryptPassword;


@XmlRootElement(name = "user")
@XmlType(propOrder = { "id", "username", "email", "password" ,"status", "admin", "photo"})
public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String status;
    private boolean admin;
    private String photo;


    public User() {
        id= -1;

    }

    public User(String username, String password, String email, String status) {
        this.username = username;
        setPassword(password);
        this.email = email;
        this.status = status;
        this.photo = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\resources\\com\\github\\JoseAngelGiron\\view\\assets\\Huevito.png";
    }


    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() != 64) {
            this.password = encryptPassword(password);
        } else {
            this.password = password;
        }
    }
    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @XmlElement
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
