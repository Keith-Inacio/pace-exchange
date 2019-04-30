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

    Handler mainThreadHandler;
    Button mStartBid;
    TextView mText;
    int count = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        AuctionFragment mItemDisplay = new AuctionFragment();
        FragmentManager mManager = getSupportFragmentManager();
        mManager.beginTransaction().add(R.id.itemContainer, mItemDisplay).commit();

        mStartBid = findViewById(R.id.startButton);
        mText = findViewById(R.id.number);
        mainThreadHandler = new Handler(Looper.getMainLooper());

        mStartBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountdown();
            }
        });
    }

    /**I am trying to get data to remain on up button click back to user profile here//
     */
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }


    public void startCountdown() {

        final Thread mClock = new Thread() {
            @Override
            public void run() {

                count--;

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        mText.setText(String.valueOf(count) + " Seconds");

                        if (count >= 0) {
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

}
