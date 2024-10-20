package com.github.JoseAngelGiron.controller;

public enum Scenes {
    ROOT("view/layout"),

    LOGIN("view/login"),
    REGISTER("view/register");


    private String url;

    Scenes(String url){
        this.url=url;
    }

    public String getURL(){
        return url;
    }


}
