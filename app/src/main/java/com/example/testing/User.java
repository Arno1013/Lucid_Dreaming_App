package com.example.testing;

public class User {
    final String email;
    final String password;
    final String name;

    public User (String email, String pass, String name){
        this.name = name;
        this.email = email;
        this.password = pass;
    }
}
