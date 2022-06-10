package com.com.werther_client.connections;

import android.content.Context;

import com.com.werther_client.ConfigReader;
import com.com.werther_client.Request;
import com.com.werther_client.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class OutputConnection extends Connection implements Runnable{

    private String link;
    private User user;
    private Context context;
    private Request request;

    public OutputConnection(Context context, User user, Request request) {
        super(context,user,request);
        this.context=context;
        this.request=request;
        this.user=user;
        this.link=request.getLink();
        server_protocol = ConfigReader.getConfigValue(context,"server_protocol");
        server_source=ConfigReader.getConfigValue(context,"server_source");
        server_port=ConfigReader.getConfigValue(context,"server_port");
    }
    private String post (String link){
        String answer = null;

        Map<String,String> postBody = new HashMap<>();

        URL url=null;
        HttpURLConnection httpURLConnection=null;
        OutputStream outputStream=null;
        InputStreamReader inputStreamReader=null;
        BufferedReader buffer=null;

        try{
            String urlParameters = "link="+link+"&id="+user.getId();
            byte[] output = urlParameters.getBytes(StandardCharsets.UTF_8);

            url=new URL(server_protocol+"://"+server_source+":"+server_port+"/order");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            httpURLConnection.connect();

            try {
                outputStream=httpURLConnection.getOutputStream();
                outputStream.write(output);
            }
            catch (IOException e){
                return null;
            }


            if(httpURLConnection.getResponseCode()==200){
                request.setStatus("SEND");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder input = new StringBuilder(bufferedReader.readLine());

                return input.toString();
            }
            else
                request.setStatus("ERROR");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
        httpURLConnection.disconnect();
        return answer;
    }

    @Override
    public void run() {
        post(link);
        if (request.getStatus().equals("SEND"))
            request.setId(post(link));
    }
}
