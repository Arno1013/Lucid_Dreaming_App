package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity{

    Button create;
    EditText email, password, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        create = findViewById(R.id.setDetailsBtn);
        email = findViewById(R.id.newEmail);
        password = findViewById(R.id.newPass);
        name = findViewById(R.id.newName);

        create.setOnClickListener(view -> createUser());
    }

    public void createUser(){
        String newEmail = email.getText().toString();
        String newPass = password.getText().toString();
        String newName = name.getText().toString();

        User user = new User (newEmail, newPass, newName);

        registerUser(user);
    }

    public void registerUser(User user){
        ServerRequests requests = new ServerRequests(this);
        requests.storeUserDataInBackground(user, returnedUser -> startActivity(new Intent(Register.this, Login.class)));
    }
}
