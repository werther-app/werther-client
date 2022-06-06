package com.com.werther_client.connections;

import com.com.werther_client.Request;
import com.com.werther_client.User;

public class OutputConnection extends Connection implements Runnable{

    private String link;

    public OutputConnection(User user, Request request, String link) {
        super(user,request);


    }
    public void post (String link){

    }

    @Override
    public void run() {

    }
}
