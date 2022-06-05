package com.com.werther_client.connections;

import com.com.werther_client.Request;
import com.com.werther_client.User;

public abstract class Connection implements Runnable {

    private User user;

    private Request request;

    public Connection(User user, Request request) {
        this.user=user;
        this.request = request;
    }


    @Override
    public void run() {}
}
