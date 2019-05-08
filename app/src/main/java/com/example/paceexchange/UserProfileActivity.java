package com.example.paceexchange;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private TextView mFirstName, mLastName, mEmail, mGraduationDate, mUserReputation;
    private Button mLogoutButton, mAuctionButton, mInventoryButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mUserAuthorization;
    private int mCurrentReputationValue;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        if(getIntent().getStringExtra(LoginActivity.EXTRA_MESSAGE)!=null) {
            ID = getIntent().getStringExtra(LoginActivity.EXTRA_MESSAGE);
            Log.d("KEITH", ID);
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("ID", ID);
            editor.commit();

        }else{
            SharedPreferences sharedPrefOne = this.getPreferences(Context.MODE_PRIVATE);
            ID = sharedPrefOne.getString("ID", "DEFAULT");
            Log.d("INACIO", ID);
        }



        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child(ID);
        mUserAuthorization = FirebaseAuth.getInstance();
        mUserAuthorization.getCurrentUser();

        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mGraduationDate = findViewById(R.id.graduation);
        mEmail = findViewById(R.id.email);
        mUserReputation = findViewById(R.id.rating);
        mLogoutButton = findViewById(R.id.logoutButton);
        mAuctionButton = findViewById(R.id.auctionButton);
        mInventoryButton = findViewById(R.id.inventoryButton);

        setButtonClickListener();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFirstName.setText(dataSnapshot.child("mFirstName").getValue().toString());
                mLastName.setText(dataSnapshot.child("mLastName").getValue().toString());
                mGraduationDate.setText(getString(R.string.profile_grad_year, dataSnapshot.child("mGraduationYear").getValue().toString()));
                mEmail.setText(getString(R.string.profile_email, dataSnapshot.child("mUserEmail").getValue().toString()));
                mCurrentReputationValue = Integer.parseInt(dataSnapshot.child("mNewUserDefaultReputation").getValue().toString());
                setUserReputation(mCurrentReputationValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method sets the color and reputation rating displayed in the user profile
     **/

    public void setUserReputation(int userRating) {

        if (userRating < 60) {
            mUserReputation.setText(R.string.poor_reputation);
            mUserReputation.setTextColor(Color.RED);
        } else if (userRating >= 60 && userRating < 80) {
            mUserReputation.setText(R.string.average_reputation);
            mUserReputation.setTextColor(Color.MAGENTA);
        } else if (userRating >= 80 && userRating < 90) {
            mUserReputation.setText(R.string.very_good_reputation);
            mUserReputation.setTextColor(Color.BLUE);
        } else if (userRating >= 90 && userRating <= 100) {
            mUserReputation.setText(R.string.excellent_reputation);
            mUserReputation.setTextColor(Color.GREEN);
        }


    }

    /**
     * This method sets a click listener to the logout, auction, and invetory buttons
     **/

    public void setButtonClickListener() {

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserAuthorization.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });

        mAuctionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AuctionActivity.class));

            }
        });

        mInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CurrentInventoryActivity.class));

            }
        });
    }

}


