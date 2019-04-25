package com.example.paceexchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {

    private TextView mFirstName, mLastName, mEmail, mGraduationDate;
    private Button mLogoutButton, mAuctionButton;
    private DatabaseReference mFireData;
    private FirebaseAuth mUserAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        /**We need to get child ID below from login...right now the id is set manually to first user.
         */
        mFireData = FirebaseDatabase.getInstance().getReference().child("Student").child("-LbdV3uBhsq7xfV4ZQrb");
        mUserAuthorization=FirebaseAuth.getInstance();
        mUserAuthorization.getCurrentUser();

        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mGraduationDate = findViewById(R.id.graduation);
        mEmail = findViewById(R.id.email);
        mLogoutButton = findViewById(R.id.logoutButton);
        mAuctionButton = findViewById(R.id.auctionButton);

        //add reputation!!!!!!

        mFireData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFirstName.setText(dataSnapshot.child("mFirstName").getValue().toString());
                mLastName.setText(dataSnapshot.child("mLastName").getValue().toString());
                mGraduationDate.setText(dataSnapshot.child("mGraduationYear").getValue().toString());
                mEmail.setText(dataSnapshot.child("mUserEmail").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /** There Is a bug in here for logout **/

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserAuthorization.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), UserLogin.class));

            }
        });

        mAuctionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), Auction.class));

            }
        });



    }

}


