package com.com.werther_client;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.com.werther_client.connections.InputConnection;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        User user = new User();

        Thread inputConnection = new Thread(new InputConnection(getApplicationContext(), user,"getId"));
        inputConnection.start();
        try {
            inputConnection.join();
        } catch (InterruptedException e) {
            user.setId("0");
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            inputConnection.interrupt();
        }

    }

    private void getUser(User user){

    }


}