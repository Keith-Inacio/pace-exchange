package com.example.paceexchange;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends AppCompatActivity {

    private TextView mUserName, mEmail, mGraduationDate, mUserReputation;
    private Button mLogoutButton, mAuctionButton, mInventoryButton, mAvailableTradeItemsButton;
    private FirebaseAuth mFirebaseAuthorization;
    private String mUserIdentification;

    private FirebaseFirestore mFirestoreInventoryDatabase;
    private CollectionReference mFirestoreInventoryCollection;

    public static final String USER_IDENTIFICATION_INVENTORY_MESSAGE = "com.example.paceexchange.USERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        mUserIdentification = intent.getStringExtra(LoginActivity.USER_IDENTIFICATION_PROFILE_MESSAGE);

        mFirebaseAuthorization = FirebaseAuth.getInstance();
        mFirestoreInventoryDatabase = FirebaseFirestore.getInstance();
        mFirestoreInventoryCollection = mFirestoreInventoryDatabase.collection("profiles");

        mUserName = findViewById(R.id.name);
        mGraduationDate = findViewById(R.id.graduation);
        mEmail = findViewById(R.id.email);
        mUserReputation = findViewById(R.id.rating);
        mLogoutButton = findViewById(R.id.logoutButton);
        mAuctionButton = findViewById(R.id.auctionButton);
        mInventoryButton = findViewById(R.id.inventoryButton);
        mAvailableTradeItemsButton = findViewById(R.id.availableItems);

        setButtonClickListener();
        setProfileDataFromFirebase();
    }

    public void setProfileDataFromFirebase() {

        DocumentReference docRef = mFirestoreInventoryCollection.document(mUserIdentification);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mEmail.setText(getResources().getString(R.string.profile_email, document.getData().get("Email").toString()));
                        mUserName.setText(getResources().getString(R.string.profile_name_display, document.getData().get("First Name"), document.getData().get("Last Name")).toUpperCase());
                        mGraduationDate.setText(getResources().getString(R.string.profile_grad_year, document.getData().get("Graduation").toString()));
                        mUserReputation.setText(getResources().getString(R.string.profile_rating, document.getData().get("Reputation").toString()));
                        setUserReputation(Integer.parseInt(document.getData().get("Reputation").toString()));
                    } else {
                        Log.d("KLEITH", "No such document");
                    }
                } else {
                    Log.d("LEITH", "get failed with ", task.getException());
                }
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
                mFirebaseAuthorization.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });

        mAuctionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AuctionActivity.class);
                intent.putExtra(USER_IDENTIFICATION_INVENTORY_MESSAGE, mUserIdentification);
                startActivity(intent);
            }
        });

        mInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CurrentInventoryActivity.class);
                intent.putExtra(USER_IDENTIFICATION_INVENTORY_MESSAGE, mUserIdentification);
                startActivity(intent);

            }
        });

        mAvailableTradeItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ItemsForTradeActivity.class);
                intent.putExtra(USER_IDENTIFICATION_INVENTORY_MESSAGE, mUserIdentification);
                startActivity(intent);

            }
        });


    }

}


