package com.example.eid.authenticator;

import android.content.Intent;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Object;


public class RegisterActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView mTextView = (TextView) findViewById(R.id.text);
        // Request a string response from the provided URL.



        Button registerButton = findViewById(R.id.registerButton);


        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.usernameField);
                EditText password = (EditText)findViewById(R.id.passwordField);
                EditText password2 = (EditText)findViewById(R.id.passwordField2);
                final String sUsername = username.getText().toString();
                final String sPassword = password.getText().toString();
                final String sPassword2 = password2.getText().toString();
                if(sUsername.matches("")){
                    Toast.makeText(getApplicationContext(),"Please enter a username",Toast.LENGTH_SHORT).show();
                }else if(sPassword.matches("")){
                    Toast.makeText(getApplicationContext(),"Please enter a password",Toast.LENGTH_SHORT).show();

                }else if(sPassword.matches("^([^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$")){
                    Toast.makeText(getApplicationContext(),"Password must contain Uppercase,Lowercase,Digit and special character",Toast.LENGTH_SHORT).show();



                }
            else if(sPassword.length() <= 7){
                    Toast.makeText(getApplicationContext(),"Password must be greater than 7 characters",Toast.LENGTH_SHORT).show();


                }
                else if(!sPassword.equals(sPassword2)){
                    Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();

                }

                // TODO Auto-generated method stub
                else {
                    Toast.makeText(getApplicationContext(),"Registration Succesful",Toast.LENGTH_SHORT).show();

                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                        final String URL = "https://dos-auth.us/create_account";
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("Username", sUsername);
                        jsonBody.put("Password", sPassword);
                        final String requestBody = jsonBody.toString();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("VOLLEY", response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VOLLEY", error.toString());
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }

                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                try {
                                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                                } catch (UnsupportedEncodingException uee) {
                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                    return null;
                                }
                            }

                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                String responseString = "";
                                if (response != null) {
                                    responseString = String.valueOf(response.statusCode);
                                    Log.d("Code: ", responseString);
                                }
                                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                            }
                        };

                        requestQueue.add(stringRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(RegisterActivity.this, UserAreaActivity.class);
                    startActivity(i);
                    };




                }

        });
    }


}
