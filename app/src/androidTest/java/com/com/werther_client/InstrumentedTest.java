package com.com.werther_client;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void testAppContext() {
        assertEquals("com.com.werther_client", appContext.getPackageName());
    }

    @Test
    public void testServerProtocol() throws Exception{
        try {
            String server_protocol = ConfigReader.getConfigValue(appContext, "server_protocol");
            if (server_protocol.equals("http"))
                System.out.print("all right: we use http");
            else
                throw new IllegalArgumentException();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    @Test
    public void testServerPort() throws Exception{
        try {
            String server_port = ConfigReader.getConfigValue(appContext, "server_port");
            if (server_port.equals("8080"))
                System.out.print("all right: we use 8080 port");
            else
                throw new IllegalArgumentException();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    @Test
    public void checkServerIpa() throws Exception{
        try {
            String server_source = ConfigReader.getConfigValue(appContext, "server_source");
            if (server_source.equals("78.107.248.219"))
                System.out.print("all right: we used ipa is correct");
            else
                throw new IllegalArgumentException();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    @Test
    public void checkWerther() throws Exception{
        URL url = new URL("https://werther.tech/");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        if (httpURLConnection.getResponseCode()==200)
            System.out.print("all right");
        else
            throw new IOException(httpURLConnection.getResponseMessage());
    }

    @Test
    public void checkWertherConfig()throws Exception{
        URL url = new URL("https://werther.tech/status.html");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        if(httpURLConnection.getResponseCode()==200){
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str = bufferedReader.readLine();
            if (str != null && str.equals("<!DOCTYPE html>"))
                System.out.print("all right");
            else
                throw new IOException(str);
        }
        else throw new IOException(String.valueOf(httpURLConnection.getResponseCode()));
    }
}