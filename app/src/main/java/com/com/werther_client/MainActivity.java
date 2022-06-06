package com.com.werther_client;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.com.werther_client.connections.InputConnection;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();

        } catch (NullPointerException e) {
            Toast.makeText(this, "NullPointerException into SupportBar action!",
                    Toast.LENGTH_SHORT).show();
        }

        User user = new User();

        Thread inputConnection = new Thread(new InputConnection(user,"getId"));
        inputConnection.start();
        try {
            inputConnection.join();
        } catch (InterruptedException e) {
            user.setId("0");
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            inputConnection.interrupt();
        }



        setContentView(R.layout.activity_main);
        Toast.makeText(this, "NullPointerException into SupportBar action!",
                Toast.LENGTH_SHORT).show();
    }

    private String readFile(String fileName) {
        try {
            FileInputStream fileInputStream = null;
            fileInputStream = openFileInput(fileName);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            String text = new String(bytes);
            fileInputStream.close();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}