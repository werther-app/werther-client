package com.com.werther_client.connections;

import com.com.werther_client.Request;
import com.com.werther_client.User;

public class OutputConnection extends Connection implements Runnable{

    public OutputConnection(User user, Request request) {
        super(user, request);
    }
}
