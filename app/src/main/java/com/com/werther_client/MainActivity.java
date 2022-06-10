package com.com.werther_client;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.com.werther_client.connections.InputConnection;
import com.com.werther_client.connections.OutputConnection;
import com.com.werther_client.requests.Request;
import com.com.werther_client.requests.RequestAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText url_Enter_textBox;
    private RequestAdapter requestAdapter;
    private User user = new User();

    private RecyclerView rv_requests;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        load();
        if (user.getList()==null)
            user.setList(new ArrayList());
        if (user.getId()==null)
            getId();

        changeAdapter();
    }

    @Override
    protected void onUserLeaveHint(){
        super.onUserLeaveHint();
        save();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        save();
    }

    private void changeAdapter(){
        rv_requests = findViewById(R.id.rv_requests);
        rv_requests.setLayoutManager(layoutManager);

        requestAdapter = new RequestAdapter(getApplicationContext(),user);
        rv_requests.setAdapter(requestAdapter);
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
        Toast.makeText(this, "WELCOME BACK\n"+user.getId(), Toast.LENGTH_SHORT).show();
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
            //Renew adater
            requestAdapter.notifyItemInserted(user.getListSize()-1);
            //Hide keyboard after unfocus editText
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            //Remove text from textEdit
            url_Enter_textBox.setText("");
            if(request.getId()!=null)
                Toast.makeText(this, "You sucessfully sended your link\n" + request.getId() + "\nid - link", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Oooopss! Something goes wrong", Toast.LENGTH_SHORT).show();
        }

    }

}