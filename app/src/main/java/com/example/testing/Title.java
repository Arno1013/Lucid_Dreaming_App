package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Title extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchLogin(View v){

        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }

    public void launchCreate(View v){
        Intent create = new Intent(this, Register.class);
        startActivity(create);
    }


}