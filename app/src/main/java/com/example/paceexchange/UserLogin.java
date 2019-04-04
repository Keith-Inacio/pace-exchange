package com.example.paceexchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private TextView mLoginTitle, mRegistrationLink;
    private Button mLoginButton;
    private FirebaseAuth mUserAuthorization;
    private ProgressDialog mProgressUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mEmail = findViewById(R.id.emailInput);
        mPassword = findViewById(R.id.passwordInput);
        mLoginTitle = findViewById(R.id.loginTitle);
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

                startActivity(new Intent(UserLogin.this, UserRegistration.class));
            }

        });

    }

    /**
     * This method validates user email and password, which is stored in firebase. If login accepted, redirect to User Profile
     **/
    private void loginClient() {

        if ((mEmail.getText().toString().isEmpty()) || (!mEmail.getText().toString().contains("@")) && (!mEmail.getText().toString().contains(".com") ||
                !mEmail.getText().toString().contains(".edu"))) {

            Toast.makeText(UserLogin.this, R.string.empty_login, Toast.LENGTH_LONG).show();

        } else if (mPassword.getText().toString().isEmpty()) {

            Toast.makeText(UserLogin.this, R.string.empty_password, Toast.LENGTH_LONG).show();

        } else {

            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            mProgressUpdate.setMessage("Logging In...");
            mProgressUpdate.show();

            mUserAuthorization.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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

