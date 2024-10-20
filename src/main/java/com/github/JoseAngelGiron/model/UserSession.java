package com.github.JoseAngelGiron.model;

import com.github.JoseAngelGiron.model.entity.User;

public class UserSession {

    private static UserSession _instance;
    private User userLoggedIn;

    private UserSession(){
        userLoggedIn = new User();
    }

    public static UserSession UserSession(){
        if(_instance==null){
           _instance= new UserSession();
        }
        return _instance;

    }

    public void setUserIntoSession(User user){
        this.userLoggedIn = user;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

}
