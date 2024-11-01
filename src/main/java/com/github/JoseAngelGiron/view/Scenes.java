package com.github.JoseAngelGiron.view;

public enum Scenes {
    ROOT("view/layout"),

    LOGIN("view/login"),
    REGISTER("view/register"),
    USERALREADYREGISTER("view/userAlreadyRegistered"),

    START("view/start"),
    FRIENDSHIPREQUEST("view/sendFriendshipRequest"),

    REQUESTRECEIVED("view/requestsReceived"),
    ACCEPTANDREJECT("view/acceptAndReject"),

    CHAT("view/chat"),

    PROFILE("view/profile"),
    CHANGESETTINGS("view/changeSettings"),
    DELETECONTACT("view/deleteContact");

    private String url;

    Scenes(String url){
        this.url=url;
    }

    public String getURL(){
        return url;
    }


}
