package com.example.paceexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CurrentInventoryActivity extends AppCompatActivity {

    private Button mTradeItemButton, mAddNewItemButton, mRemoveItemButton;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private DatabaseReference mDatabase;
    private FirebaseAuth mUserAuthorization;
    private ArrayList<InventoryData> mInventoryList;
    private int mRowClickPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // mUserAuthorization = FirebaseAuth.getInstance();
        // mUserAuthorization.getCurrentUser();

        mTradeItemButton = findViewById(R.id.tradeSelectedItemButton);
        mAddNewItemButton = findViewById(R.id.addInventoryItemButton);
        mRemoveItemButton = findViewById(R.id.removeInventoryItemButton);
        mRecyclerView = findViewById(R.id.recyclerView);

        mInventoryList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child("-LbdV3uBhsq7xfV4ZQrb").child("Inventory");

        setButtonClickListener();
        setRecyclerView();
        iterateFirebaseInventory();
    }

    public void setRecyclerView() {


        mAdapter = new RecyclerAdapter(CurrentInventoryActivity.this, mInventoryList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRowClickPosition = (int) v.getTag();
                InventoryData display = mAdapter.getItem(mRowClickPosition);

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(CurrentInventoryActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    public void setButtonClickListener() {


        mAddNewItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CurrentInventoryActivity.this, AddInventoryItemActivity.class));
            }
        });

        mRemoveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeInventoryItem(mRowClickPosition);
            }
        });
    }

    public void iterateFirebaseInventory() {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key= getItem(child.getKey());

                    String conditon = dataSnapshot.child(key).child("Condition").getValue().toString();
                    String category = dataSnapshot.child(key).child("Category").getValue().toString();
                    String value = dataSnapshot.child(key).child("Trade Value").getValue().toString();
                    mInventoryList.add(new InventoryData(R.drawable.javabook, key, value, conditon, category));
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private String getItem(String key) {


      // mInventoryList.add(new InventoryData(R.drawable.javabook, key, value, conditon, category));

      // mAdapter.notifyDataSetChanged();

        return key;

    }

    private void removeInventoryItem(int position) {

        mDatabase.child(mInventoryList.get(position).getmItemName()).removeValue();
        mAdapter.removeInventoryItem(position);
        mAdapter.notifyDataSetChanged();

    }
}
