package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    Button login;
    EditText email, password;
    StoredUsers storedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        login = findViewById(R.id.submitBtn);
        storedUsers = new StoredUsers(this);
        login.setOnClickListener(view -> validate(email.getText().toString(), password.getText().toString()));
    }

    @SuppressLint("SetTextI18n")
    public void validate (String email, String password){
        User user = new User (email, password, "Aidan");
        authenticate(email, password, user);
    }

    public void authenticate(String email, String password, User user){
        ServerRequests requests = new ServerRequests(this);
        requests.fetchUserDataInBackground(user, returnedUser -> {
            if (email.equals("arwno13@gmail.com") && password.equals("pepper13")){
                logUserIn(user);
            }else{
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Incorrect user details");
        builder.setPositiveButton("Ok", null);
        builder.show();
    }

    private void logUserIn(User user){
        storedUsers.storeData(user);
        storedUsers.setUserLoggedIn(true);
        Intent dash = new Intent(this, Dashboard.class);
        startActivity(dash);
    }
}