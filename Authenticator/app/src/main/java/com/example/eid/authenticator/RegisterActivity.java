package com.example.eid.authenticator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        Button registerButton = findViewById(R.id.registerButton);


        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.usernameField);
                EditText password = (EditText)findViewById(R.id.passwordField);
                final String sUsername = username.getText().toString();
                final String spassword = password.getText().toString();
                if(sUsername.matches("")){
                    Toast.makeText(getApplicationContext(),"Please enter a username",Toast.LENGTH_SHORT).show();
                }else if(spassword.matches("")){
                    Toast.makeText(getApplicationContext(),"Please enter a password",Toast.LENGTH_SHORT).show();

                }else if(!spassword.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
                    Toast.makeText(getApplicationContext(),"Password must contain letters and numbers and uppercase",Toast.LENGTH_SHORT).show();


                }
            else if(spassword.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password must be greater than 6 characters",Toast.LENGTH_SHORT).show();


                }

                // TODO Auto-generated method stub
                else {
                    Intent i = new Intent(RegisterActivity.this, waitingPage.class);
                    startActivity(i);
                }
            }
        });
    }
}
