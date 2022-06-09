package com.com.werther_client.connections;

import android.content.Context;

import com.com.werther_client.Request;
import com.com.werther_client.User;

public class OutputConnection extends Connection implements Runnable{

    private String link;

    public OutputConnection(Context context, User user, Request request) {
        super(context,user,request);


    }
    public void post (String link){

    }

    @Override
    public void run() {

    }
}
