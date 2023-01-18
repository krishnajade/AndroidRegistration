package com.trifrnd.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private Button forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.activity_main_usernameEditText);
        passwordEditText = findViewById(R.id.activity_main_passwordEditText);
        loginButton = findViewById(R.id.activity_main_loginButton);
        registerButton=findViewById(R.id.activity_main_registerButton);
        forgotPasswordButton=findViewById(R.id.activity_main_forgotPasswordButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user="user";
                String password="password";
                if (usernameEditText.getText().toString().equals(user) && passwordEditText.getText().toString().equals(password)) {
                   Intent intent=new Intent(getApplicationContext(),AdminDashboard.class);
                   startActivity(intent);
                    finish();
                } else {
                    String toastMessage = "Wrong Username or Password ";
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