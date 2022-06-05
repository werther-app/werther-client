package com.com.werther_client;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private List list = Collections.synchronizedList(new ArrayList());

    public void addToTheList(Request request){
        list.add(request);
    }

    private Request getFromTheList(int count){
       return (Request) list.get(count);
    }

}
