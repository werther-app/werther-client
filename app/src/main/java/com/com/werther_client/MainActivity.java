package com.com.werther_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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