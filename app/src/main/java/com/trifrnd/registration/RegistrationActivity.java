package com.trifrnd.registration;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.developer.gbuttons.GoogleSignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class RegistrationActivity extends AppCompatActivity {
    GoogleSignInButton googleBtn;
    GoogleSignInOptions gOptions;
    GoogleSignInClient gClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        googleBtn=findViewById(R.id.googleBtn);

        gOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient= GoogleSignIn.getClient(this,gOptions);


        GoogleSignInAccount gAccount=GoogleSignIn.getLastSignedInAccount(this);
        if(gAccount != null){
            finish();
            Intent intent =new Intent(RegistrationActivity.this,UserDashboard.class);
        startActivity(intent);
        }
        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data=result.getData();
                        Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            task.getResult(ApiException.class);
                            finish();
                            Intent intent=new Intent(RegistrationActivity.this,UserDashboard.class);
                            startActivity(intent);
                        } catch (ApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent=gClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);
            }
        });
    }
}