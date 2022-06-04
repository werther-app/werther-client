package com.com.werther_client;

import android.telephony.mbms.MbmsErrors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Request {

    private String text;

    private String id;

    private final String link;

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public Request (String text, String link, String id){
        this.text=text;
        this.link=link;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addText (String text){
    this.text = text;
    }

}
