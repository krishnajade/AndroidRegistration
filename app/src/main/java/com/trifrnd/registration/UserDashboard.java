package com.trifrnd.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class UserDashboard extends AppCompatActivity {

    TextView userName;
    GoogleSignInOptions gOptions;
    GoogleSignInClient gClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        userName=findViewById(R.id.userName);
        gOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient= GoogleSignIn.getClient(this,gOptions);

        GoogleSignInAccount gAccount=GoogleSignIn.getLastSignedInAccount(this);
        if(gAccount!=null){
            String gName=gAccount.getDisplayName();
            userName.setText(gName);
        }

    }
}