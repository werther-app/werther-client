package com.com.werther_client;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    private ArrayList list = new ArrayList<Request>();

    //NOT good idea, but easiest way now
    public synchronized void addToTheList(Request request){
        list.add(request);
    } //TODO change concurrency politics.

    private Request getFromTheList(int count){
       return (Request) list.get(count);
    }

    public String convertListToString(){
        Gson gson = new Gson();
        return gson.toJson(this.list);
    }

    public void convertStringToList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Request>>() {}.getType();
        list=gson.fromJson(json, type);
    }

}
