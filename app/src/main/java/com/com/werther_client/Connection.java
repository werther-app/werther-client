package com.com.werther_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Connection {
    private String ipAddress=null;
    private String uri="http://"+ipAddress;
    private User user;

    private static HashMap <String, String> operations = new HashMap<>();
    public String getOperation(String operationName){
        return null;
    }

    public Connection (String ipAddress, User user){
        this.ipAddress = ipAddress;
        operations.put("getId", "/auth?type=client");
        operations.put("getOrder", "/result?order=");
        operations.put("postOrder","/order?video=");
        this.user=user;
    }

    //Use for all requests
    public String get(String operationName, String requestId) throws MalformedURLException {
        URL url = null;
        if (user.getId()==null)
            url = new URL(uri + operations.get(operationName));
        else
            url = new URL(uri + operations.get(operationName)+user.getId()+"&"+requestId);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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

    public void post(String operationName, String link){

    }

}
