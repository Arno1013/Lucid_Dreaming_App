package com.example.testing;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServerRequests {
    ProgressDialog progress;
    public static final String SERVER_ADDRESS = "https://luciddreaminguserdatabase.000webhostapp.com/";

    public ServerRequests(Context context){
        progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.setTitle("Processing");
        progress.setMessage("Please wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback callBack){
        progress.show();
        new StoreUserDataAsyncTask(user, callBack).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback callBack){
        progress.show();
        new FetchUserDataAsyncTask(user, callBack).execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void>{
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback callBack){
            this.user = user;
            this.userCallback = callBack;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(SERVER_ADDRESS + "Register.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("name", user.name)
                        .appendQueryParameter("username", user.email)
                        .appendQueryParameter("password", user.password);

                String query          = builder.build().getEncodedQuery();
                OutputStream os       = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        public FetchUserDataAsyncTask(User user, GetUserCallback callBack) {
            this.user = user;
            this.userCallback = callBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            User returnedUser;
            try {
                URL url = new URL(SERVER_ADDRESS + "Fetch.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("email", user.email)
                        .appendQueryParameter("password", user.password);

                String query          = builder.build().getEncodedQuery();
                OutputStream os       = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                InputStream in = new BufferedInputStream(conn.getInputStream());

                String response = IOUtils.toString(in, StandardCharsets.UTF_8);
                JSONObject jResponse = new JSONObject(response);

                if (jResponse.length() == 0) {
                    returnedUser = null;
                } else {
                    String name = jResponse.getString("name");
                    returnedUser = new User(user.email, user.password, name);
                }

                return returnedUser;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            progress.dismiss();
            userCallback.done(user);
            super.onPostExecute(user);
        }
    }
}
