package com.example.paceexchange;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class AuctionActivity extends AppCompatActivity {

    private AuctionFragment mItemDisplay;
    private FragmentManager mFragmentManager;
    private Handler mainThreadHandler;
    private Button mStartBidButton, mNextItemButton;
    private TextView mText, mUserBidItem;
    private int mTimer = 60;
    private int nextClickCounter=0;
    private Bundle args;
    private FirebaseFirestore mDatabase;
    private DatabaseReference mUserDatabase;


    public static final String BID_ITEM_MESSAGE = "com.example.paceexchange.ITEMMESSAGE";

    HashMap<String, Object> generalInventoryAddition;
    CollectionReference totalInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        mUserBidItem = findViewById(R.id.userBidItem);
        mDatabase = FirebaseFirestore.getInstance();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child("-LbdV3uBhsq7xfV4ZQrb").child("Inventory");

        String mSelectedTradeItemName = getIntent().getStringExtra(CurrentInventoryActivity.EXTRA_MESSAGE_TRADE_ITEM);
        String mSelectedTradeItemValue = getIntent().getStringExtra(CurrentInventoryActivity.EXTRA_MESSAGE_TRADE_ITEM_VALUE);
        mUserBidItem.setText(getResources().getString(R.string.auctio_user_item, mSelectedTradeItemName, mSelectedTradeItemValue));

        mItemDisplay = new AuctionFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.itemContainer, mItemDisplay).commit();

        mStartBidButton = findViewById(R.id.startButton);
        mNextItemButton = findViewById(R.id.nextItemButton);
        mText = findViewById(R.id.number);
        mainThreadHandler = new Handler(Looper.getMainLooper());

        totalInventory = mDatabase.collection("item_inventory");
        generalInventoryAddition= new HashMap<>();
        totalInventory.document("2").set(generalInventoryAddition);


        setButtonClickListeners();
    }

    public void startCountdown() {

        final Thread mClock = new Thread() {
            @Override
            public void run() {

                mTimer--;

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        mText.setText(getResources().getString(R.string.seconds_display, String.valueOf(mTimer)));

                        if (mTimer >= 0) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            startCountdown();
                        } else {
                            mText.setText(getResources().getString(R.string.auction_ended));
                        }
                    }
                });

            }

        };

        mClock.start();

    }

    public void setButtonClickListeners(){

        mNextItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                args = new Bundle();
                AuctionFragment fragment = new AuctionFragment();
                nextClickCounter++;
                args.putInt(BID_ITEM_MESSAGE, nextClickCounter);
                fragment.setArguments(args);

                mFragmentManager.beginTransaction().replace(R.id.itemContainer, fragment).commit();

            }
        });

        mStartBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountdown();
            }
        });


    }

}
