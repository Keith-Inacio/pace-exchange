package com.example.paceexchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

import java.util.ArrayList;
import java.util.Map;

public class UserLogin extends AppCompatActivity {

    private EditText mEmailInput, mPasswordInput;
    private TextView mRegistrationLink;
    private Button mLoginButton;
    private FirebaseAuth mUserAuthorization;
    private ProgressDialog mProgressUpdate;
    private String mEmailValidate, mPasswordValidate;
    private DatabaseReference mUserDatabase;
    public static final String EXTRA_MESSAGE = "com.example.paceexchange.message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mEmailInput = findViewById(R.id.emailInput);
        mPasswordInput = findViewById(R.id.passwordInput);
        mRegistrationLink = findViewById(R.id.registerLink);
        mLoginButton = findViewById(R.id.loginButton);

        mProgressUpdate = new ProgressDialog(this);

        mUserAuthorization = FirebaseAuth.getInstance();

        /**If Statement: Check if user is already logged in. If yes, redirect to User Profile**/

        if (mUserAuthorization.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        }

        setOnClickListener();
    }

    /**
     * This method sets click listener on login button and new user registration link
     **/
    public void setOnClickListener() {

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                loginClient();
            }

        });

        mRegistrationLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), UserRegistration.class));
            }

        });

    }

    /**
     * This method validates user email and password, which is stored in firebase. If login accepted, redirect to User Profile
     **/
    private void loginClient() {

        if ((mEmailInput.getText().toString().isEmpty()) || (!mEmailInput.getText().toString().contains("@")) && (!mEmailInput.getText().toString().contains(".com") ||
                !mEmailInput.getText().toString().contains(".edu"))) {

            Toast.makeText(UserLogin.this, R.string.empty_login, Toast.LENGTH_LONG).show();

        } else if (mPasswordInput.getText().toString().isEmpty()) {

            Toast.makeText(UserLogin.this, R.string.empty_password, Toast.LENGTH_LONG).show();

        } else {

            mEmailValidate = mEmailInput.getText().toString().trim();
            mPasswordValidate = mPasswordInput.getText().toString().trim();

            mProgressUpdate.setMessage("Logging In...");
            mProgressUpdate.show();

            mUserAuthorization.signInWithEmailAndPassword(mEmailValidate, mPasswordValidate).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        mProgressUpdate.dismiss();
                        finish();
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                    } else {
                        mProgressUpdate.dismiss();
                        Toast.makeText(UserLogin.this, R.string.login_fail, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
}
