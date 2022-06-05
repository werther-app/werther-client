package com.com.werther_client;


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

    public Request (String text, String link){
        this.text=text;
        this.link=link;
    }

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public void setText (String text){
    this.text = text;
    }

}
