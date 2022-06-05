package com.com.werther_client;

import android.os.Bundle;
import android.widget.TextView;
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
        Thread connection = new Thread(new InputConnection(user,"getId",null));//String ipAddress, User user, String operationName, String requestId
        connection.start();
        TextView request_text_label = (TextView) findViewById(R.id.request_text_label);
        request_text_label.setText("lol");
        request_text_label.setText(user.getId());

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