package com.example.paceexchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    private ImageView mPaceLoginImage;
    private EditText mEmailInput, mPasswordInput;
    private TextView mRegistrationLink;
    private Button mLoginButton;
    private FirebaseAuth mUserAuthorization;
    private ProgressDialog mProgressUpdate;
    private String mEmailValidate, mPasswordValidate, mCurrentUserID;
    private DatabaseReference mUserDatabase;
    public static final String EXTRA_MESSAGE = "com.example.paceexchange.MESSAGE";

    private final String mImageURL = "https://media.karousell.com/media/photos/products/2018/08/25/barter_trade__exchange_your_goods_with_mine_1535126758_21a31c740";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mEmailInput = findViewById(R.id.emailInput);
        mPasswordInput = findViewById(R.id.passwordInput);
        mRegistrationLink = findViewById(R.id.registerLink);
        mLoginButton = findViewById(R.id.loginButton);
        mPaceLoginImage = findViewById(R.id.paceImage);
        mCurrentUserID = "";

        mProgressUpdate = new ProgressDialog(this);

        mUserAuthorization = FirebaseAuth.getInstance();

        setOnClickListener();
        loadLoginImage();
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

                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }

        });

    }

    /**
     * This method validates user email and password, which is stored in firebase. If login accepted, redirect to User Profile
     **/
    private void loginClient() {

        if ((mEmailInput.getText().toString().isEmpty()) || (!mEmailInput.getText().toString().contains("@")) && (!mEmailInput.getText().toString().contains(".com") ||
                !mEmailInput.getText().toString().contains(".edu"))) {

            Toast.makeText(LoginActivity.this, R.string.empty_login, Toast.LENGTH_LONG).show();

        } else if (mPasswordInput.getText().toString().isEmpty()) {

            Toast.makeText(LoginActivity.this, R.string.empty_password, Toast.LENGTH_LONG).show();

        } else {


            mEmailValidate = mEmailInput.getText().toString().trim();
            mPasswordValidate = mPasswordInput.getText().toString().trim();

            mProgressUpdate.setMessage("Logging In...");
            mProgressUpdate.show();

            getUserIDKey();

            mUserAuthorization.signInWithEmailAndPassword(mEmailValidate, mPasswordValidate).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        mProgressUpdate.dismiss();
                        Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                        intent.putExtra(EXTRA_MESSAGE, mCurrentUserID);
                        startActivity(intent);
                    } else {
                        mProgressUpdate.dismiss();
                        Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void getUserIDKey() {

        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Student");

        mUserDatabase.orderByChild("mUserEmail").equalTo(mEmailValidate).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = getFirebaseValue(child.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private String getFirebaseValue(String key) {

        mCurrentUserID = key;

        return mCurrentUserID;
    }

    private void loadLoginImage(){

        Picasso.get().load(mImageURL).fit().centerCrop().into(mPaceLoginImage);

    }
}