package com.com.werther_client.connections;

import android.content.Context;

import com.com.werther_client.ConfigReader;
import com.com.werther_client.requests.Request;
import com.com.werther_client.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class InputConnection extends Connection implements Runnable {
    private String operationName;
    private User user;
    private Request request;

    private static HashMap <String, String> operations = new HashMap<>();

    //Use for User.id.
    public InputConnection(Context context, User user, String operationName){
        super(user, context);
        this.operationName=operationName;
        operations.put("getId", "/auth?type=client");
        operations.put("getOrder", "/result?order=");
        this.user=user;
        server_protocol =ConfigReader.getConfigValue(context,"server_protocol");
        server_source=ConfigReader.getConfigValue(context,"server_source");
        server_port=ConfigReader.getConfigValue(context,"server_port");
    }

    //Use for Request.body.
    public InputConnection (Context context, User user, String operationName, Request request){
        super(context, user, request);
        this.operationName=operationName;
        operations.put("getId", "/auth?type=client");
        operations.put("getOrder", "/result?order=");
        this.user=user;
        this.request=request;
        server_protocol =ConfigReader.getConfigValue(context,"server_protocol");
        server_source=ConfigReader.getConfigValue(context,"server_source");
        server_port=ConfigReader.getConfigValue(context,"server_port");
    }

    //Use for User.id.
    public String get(String operationName)throws MalformedURLException{
        URL url = null;
        url = new URL(server_protocol +"://"+ server_source +":"+ server_port + operations.get(operationName));
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode()==200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                return bufferedReader.readLine();
            }
            if (httpURLConnection.getResponseCode()==404){
                request.setStatus("RESULT NOT FOUND");
            }

            httpURLConnection.disconnect();
        } catch (IOException e) {
            return "";
        }
        return "";
    }
    //Use for Request.body.
    public String get(String operationName, Request request) throws MalformedURLException {
        URL url;
            url = new URL(server_protocol +"://"+ server_source +":"+ server_port + operations.get(operationName)+
                    user.getId()+"&"+request.getId());
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode()==200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder output = new StringBuilder(bufferedReader.readLine());
                output.append(bufferedReader.readLine()).append("/n");

                request.setStatus("Done!");
                return output.toString();
            }
            if(httpURLConnection.getResponseCode()==203){
                request.setStatus("In progress!");
                return "Try to get it later";
            }

            else{
                request.setStatus("Can't be done!");
                return "Server Error";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }


    @Override
    public void run() {
        try {
            if (this.operationName.equals("getId"))
                this.user.setId(get(this.operationName));
            else
                this.request.setText(get(this.operationName, this.request));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
