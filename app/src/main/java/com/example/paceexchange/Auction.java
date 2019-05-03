package com.example.paceexchange;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Auction extends AppCompatActivity {

    private AuctionFragment mItemDisplay;
    private FragmentManager mFragmentManager;
    private Handler mainThreadHandler;
    private Button mStartBidButton, mNextItemButton;
    private TextView mText;
    private int mTimer = 100;
    private int nextClickCounter=0;
    private Bundle args;
    public static final String BID_ITEM_MESSAGE = "com.example.paceexchange.ITEMMESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        mItemDisplay = new AuctionFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.itemContainer, mItemDisplay).commit();

        mStartBidButton = findViewById(R.id.startButton);
        mNextItemButton = findViewById(R.id.nextItemButton);
        mText = findViewById(R.id.number);
        mainThreadHandler = new Handler(Looper.getMainLooper());

        setButtonClickListeners();
    }


    /*I am trying to get data to remain on up button click back to user profile here - NOT WORKING
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
        */


    public void startCountdown() {

        final Thread mClock = new Thread() {
            @Override
            public void run() {

                mTimer--;

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        mText.setText(String.valueOf(mTimer) + " Seconds");

                        if (mTimer >= 0) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            startCountdown();
                        } else {
                            mText.setText("AUCTION ENDED");
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
