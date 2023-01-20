package com.trifrnd.registration;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    private EditText usernameEditText;
    private EditText passwordEditText;
    Context context;
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.activity_main_usernameEditText);
        passwordEditText = findViewById(R.id.activity_main_passwordEditText);

        Button registerButton = findViewById(R.id.activity_main_registerButton);
        Button forgotPasswordButton = findViewById(R.id.activity_main_forgotPasswordButton);
        Button loginButton = findViewById(R.id.activity_main_loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user="user";
                String password="password";
                boolean condition1 = usernameEditText.getText().toString().equals(user);
                boolean condition2 = passwordEditText.getText().toString().equals(password);
                boolean condition3= isNetworkConnected();
                if(condition3){
                    if ( condition1 && condition2 ) {
                    Intent intent=new Intent(getApplicationContext(),AdminDashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    String toastMessage = "Wrong Username or Password or you are not connected to network";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
                }else{
                    String toastMessage = "You are not connected to network";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);

            }
        });
        forgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });
    }
}