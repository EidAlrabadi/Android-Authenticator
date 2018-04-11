package com.example.eid.authenticator;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.*;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserAreaActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
    String service = "";
    String checkService = "";
    int authCode = 0;
    int checkAuthCode = 0;
    private EditText usernameField, authcodeField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        usernameField = (EditText) findViewById(R.id.serviceText);
        authcodeField = (EditText) findViewById(R.id.authCodeText);
        usernameField.setEnabled(false);
        authcodeField.setEnabled(false);
        usernameField.setTextColor(Color.BLACK);
        authcodeField.setTextColor(Color.BLACK);
        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    while (true) {
                        sleep(2000);
                        new JSONAsyncTask().execute("https://dos-auth.us/my_services");


                        sleep(10000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet("https://dos-auth.us/my_services");
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jsonArray = jsono.getJSONArray("services");
                    for(int i = 0 ;i<jsonArray.length();i+=2) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i+1);
                            service = jsonObject.getString("name");
                            authCode = jsonObject.getInt("authCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }





                    return true;
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
          ;
            if(!service.equals(checkService) || authCode != checkAuthCode){
                checkService = service;
                checkAuthCode = authCode;
                usernameField.setText("Service: " + service);
                authcodeField.setText("Authcode: " + Integer.toString(authCode));


            }

        }


    }
}





