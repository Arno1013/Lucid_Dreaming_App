package com.example.testing;

public class User {
    String email, password, name;

    public User (String email, String pass, String name){
        this.name = name;
        this.email = email;
        this.password = pass;
    }
    public User (String email, String pass){
        this.name = name;
        this.email = email;
        this.password = pass;
    }
}
