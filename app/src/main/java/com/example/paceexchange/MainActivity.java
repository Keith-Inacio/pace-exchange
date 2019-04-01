package com.example.paceexchange;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail, mPassword, mFirstName, mLastName, mGradDate;
    private Button mRegisterButton;
    private FirebaseAuth mUserAuthorization;
    private ProgressDialog mProgressUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.passwordInput);
        mFirstName = findViewById(R.id.firstname);
        mLastName = findViewById(R.id.lastname);
        mGradDate = findViewById(R.id.graduation);

        mRegisterButton = findViewById(R.id.registerButton);
        mProgressUpdate = new ProgressDialog(this);

        mUserAuthorization = FirebaseAuth.getInstance();

        setOnClickListener();
    }

    /**This method sets a click listener to the registration button**/

    public void setOnClickListener() {

        mRegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                registerClient();
            }

        });

    }
        /**This method validates username and password input and assigns same to String variables**/

        private void registerClient() {

            if (mEmail.getText().toString().isEmpty() || !mEmail.getText().toString().contains("@") || !mEmail.getText().toString().contains(".com")) {

                Toast.makeText(MainActivity.this, R.string.empty_login, Toast.LENGTH_LONG).show();

            } else if (mPassword.getText().toString().isEmpty()) {

                Toast.makeText(MainActivity.this, R.string.empty_password, Toast.LENGTH_LONG).show();

            } else {

                /** NOTE: registration authorization set up right now, but we need to get all data including name and grad date into database...for user profile**/

                String firstName = mFirstName.getText().toString().trim();
                String lastName = mLastName.getText().toString().trim();
                int gradDate = Integer.parseInt(mGradDate.getText().toString().trim());
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

               mProgressUpdate.setMessage("Completing Registration");
               mProgressUpdate.show();

                mUserAuthorization.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, R.string.register_success, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, R.string.register_fail, Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }

    }

