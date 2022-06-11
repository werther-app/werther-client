package com.com.werther_client.connections;

import android.content.Context;

import com.com.werther_client.requests.Request;
import com.com.werther_client.User;

public abstract class Connection {
    protected String server_protocol;
    protected String server_source;
    protected String server_port;

    private User user;

    private Request request;

    private Context context;

    public Connection(Context context, User user, Request request) {
        this.user=user;
        this.request = request;
        this.context = this.context;
    }

    public Connection(User user, Context context) {
    }

}
