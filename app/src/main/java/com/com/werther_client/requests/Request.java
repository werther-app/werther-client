package com.com.werther_client.requests;

public class Request {

    private String text;

    private String id;

    private String link;


    private String status;

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

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}
