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
    public String getOperation(String operationName){
        return null;
    }

    public InputConnection(User user, String operationName, Request request){
        super(user, request);
        this.operationName=operationName;
        operations.put("getId", "/auth?type=client");
        operations.put("getOrder", "/result?order=");
        operations.put("postOrder","/order?video=");
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
        User user = new User();
        URL url = null;
        url = new URL(protocol+"://"+source+":"+port + operations.get(operationName));
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode()==200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder output = new StringBuilder(bufferedReader.readLine());

                User user1 = new User(output.toString());
                return user1.getId();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return user.getId();
        }

        return user.getId();
    }

    public void post(String operationName, String link){

    }


    @Override
    public void run() {
        try {
            if (user.getId()==null)
                user.setId(get(this.operationName));
            else
                request.setText(get(this.operationName, request));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
