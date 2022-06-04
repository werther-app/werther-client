package com.com.werther_client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.NetworkStatsManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

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
        Connection connection = new Connection("78.107.248.219:8080",user);
        try {
            user.setId(connection.get("getId",null));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,user.getId(),Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_main);
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