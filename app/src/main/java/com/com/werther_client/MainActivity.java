package com.com.werther_client;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.com.werther_client.connections.InputConnection;
import com.com.werther_client.connections.OutputConnection;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText url_Enter_textBox;
    User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        load();
        if (user.getList()==null)
            user.setList(new ArrayList());
        if (user.getId()==null)
            getId();
    }

    @Override
    protected void onUserLeaveHint(){
        save();
        Toast.makeText(this, "WORK", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy(){
        save();
    }

    private void save(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("list", user.convertListToString());
        editor.putString("id", user.getId());
        editor.apply();
    }

    private void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("list", null);
        String id = sharedPreferences.getString("id", null);
        user.convertStringToList(json);
        user.setId(id);
    }

    public void getId(){
        Thread inputConnection = new Thread(new InputConnection(getApplicationContext(), user,"getId"));
        inputConnection.start();
        try {
            inputConnection.join();
        } catch (InterruptedException e) {
            user.setId("0");
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            Toast toast = Toast.makeText(getApplicationContext(),user.getId()+"\nis your id",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            inputConnection.interrupt();
        }
    }

    public void post(View view){
        url_Enter_textBox = findViewById(R.id.url_Enter_textBox);

        Request request = new Request(null, url_Enter_textBox.getText().toString());

        Thread outputConnection = new Thread(new OutputConnection(getApplicationContext(),user,request));
        outputConnection.start();
        try {
            outputConnection.join();
        }
        catch (InterruptedException e){
            request.setStatus(e.getMessage());
        }
        finally {
            outputConnection.interrupt();
            user.addToTheList(request);
        }

    }

}