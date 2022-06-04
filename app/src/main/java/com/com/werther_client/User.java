package com.com.werther_client;


import java.util.ArrayList;

public class User {
    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }

    public User(String id){
        this.id=id;
    }

    public User(){}

    public ArrayList <Request> requestPool = new ArrayList<Request>();

}
