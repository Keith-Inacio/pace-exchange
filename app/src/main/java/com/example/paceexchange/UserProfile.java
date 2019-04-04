package com.example.paceexchange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class UserProfile extends AppCompatActivity {

    private TextView mFirstName, mLastName, mEmail, mGraduationDate;
    private Button mLogoutButton;
    private FirebaseAuth mUserAuthorization;
    private FirebaseUser mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mFirstName = findViewById(R.id.firstname);
        mLastName = findViewById(R.id.lastname);
        mGraduationDate = findViewById(R.id.graduation);
        mEmail = findViewById(R.id.email);
        mLogoutButton = findViewById(R.id.logoutButton);

        mUserAuthorization = FirebaseAuth.getInstance();

        mClient = mUserAuthorization.getCurrentUser();

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserAuthorization.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), UserLogin.class));
            }
        });

    }
}
