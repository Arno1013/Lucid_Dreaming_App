package com.example.testing;
import android.content.Context;
import android.content.SharedPreferences;

public class StoredUsers {
    public static final String username = "userDetails";
    SharedPreferences settings;

    public StoredUsers(Context context){
        settings = context.getSharedPreferences(username, 0);
    }

    public User getLoggedUser(){
        String email = settings.getString("email", "");
        String password = settings.getString("password", "");
        String name =  settings.getString("name", "");

        return new User (email, password, name);
    }

    public void storeData(User user){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("email", user.email);
        editor.putString("password", user.password);
        editor.putString("name", user.name);
        editor.apply();
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("loggedIn", loggedIn);
        editor.apply();
    }

    public void clear(){
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }
}
