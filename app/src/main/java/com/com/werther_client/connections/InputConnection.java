package com.com.werther_client.connections;

import com.com.werther_client.Request;
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
    private String protocol="http"; //TODO Remove HARDCODE
    private String source="78.107.248.219";//TODO Remove HARDCODE
    private String port="8080";//TODO Remove HARDCODE
    private String operationName;
    private User user;
    private Request request;

    private static HashMap <String, String> operations = new HashMap<>();

    public InputConnection(User user, String operationName, Request request){
        super(user, request);
        this.operationName=operationName;
        operations.put("getId", "/auth?type=client");
        operations.put("getOrder", "/result?order=");
        operations.put("postOrder","/order?video=");
        this.user=user;
        this.request=request;
    }

    public InputConnection(User user, String operationName){
        super(user);
        this.operationName=operationName;
        operations.put("getId", "/auth?type=client");
        operations.put("getOrder", "/result?order=");
        this.user=user;
    }

    //Use for all requests
    public String get(String operationName, Request request) throws MalformedURLException {
        URL url = null;
            url = new URL(protocol+"://"+source+":"+port + operations.get(operationName)+
                    user.getId()+"&"+request.getId());
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode()==200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder output = new StringBuilder(bufferedReader.readLine());
                output.append(bufferedReader.readLine()).append("/n");

                return output.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
        return null;
    }

    public String get(String operationName)throws MalformedURLException{
        String income="";
        URL url = null;
        url = new URL(protocol+"://"+source+":"+port + operations.get(operationName));
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode()==200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder output = new StringBuilder(bufferedReader.readLine());

                income = output.toString();
                return income;
            }
        } catch (IOException e) {
            income=e.toString();
            return income;
        }

        return income;
    }


    @Override
    public void run() {
        try {
            if (this.operationName == "getId")
                this.user.setId(get(this.operationName));
            else
                this.request.setText(get(this.operationName, this.request));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
