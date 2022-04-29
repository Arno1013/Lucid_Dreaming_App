package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Title extends AppCompatActivity {
    Button launchLogin, launchRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchLogin = findViewById(R.id.loginBtn);
        launchRegister = findViewById(R.id.createBtn);

        launchLogin.setOnClickListener(view -> launchLogin());
        launchRegister.setOnClickListener(view -> launchCreate());
    }


    public void launchLogin(){

        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }

    public void launchCreate(){
        Intent create = new Intent(this, Register.class);
        startActivity(create);
    }


}