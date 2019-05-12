package com.example.paceexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class CurrentInventoryActivity extends AppCompatActivity {

    private Button mTradeItemButton, mAddNewItemButton, mRemoveItemButton;

    private FirebaseFirestore mFirebaseDatabase;
    private CollectionReference mFirebaseInventoryCollection;

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ArrayList<InventoryData> mlist;
    private int mRowClickPosition;
    private String mUserIdentification;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Intent intent = getIntent();
        mUserIdentification = intent.getStringExtra(UserProfileActivity.USER_IDENTIFICATION_INVENTORY_MESSAGE);

        mTradeItemButton = findViewById(R.id.tradeSelectedItemButton);
        mAddNewItemButton = findViewById(R.id.addInventoryItemButton);
        mRemoveItemButton = findViewById(R.id.removeInventoryItemButton);

        mFirebaseDatabase = FirebaseFirestore.getInstance();
        mFirebaseInventoryCollection = mFirebaseDatabase.collection("inventory");

        mlist = new ArrayList<>();


        setButtonClickListener();
        setRecyclerView();
        iterateFirebaseInventory();
    }

    public void setRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new RecyclerAdapter(CurrentInventoryActivity.this, mlist, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRowClickPosition = (int) v.getTag();
                InventoryData display = mAdapter.getItem(mRowClickPosition);

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    public void setButtonClickListener() {

        mAddNewItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(CurrentInventoryActivity.this, AddInventoryItemActivity.class));
            }
        });

        mRemoveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeItemFromInventory(mRowClickPosition);
            }
        });

        mTradeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CurrentInventoryActivity.this, AuctionActivity.class);
                //intent.putExtra(EXTRA_MESSAGE_TRADE_ITEM, mAdapter.getItem(mRowClickPosition).getmItemName());
                //intent.putExtra(EXTRA_MESSAGE_TRADE_ITEM_VALUE, mAdapter.getItem(mRowClickPosition).getmItemValue());
                startActivity(intent);

            }
        });
    }

    public void iterateFirebaseInventory() {

        mFirebaseInventoryCollection.document(mUserIdentification).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Map<String, Object> map = task.getResult().getData();
                if (map.get("Items") != null) {
                    ArrayList<Object> newArray = (ArrayList<Object>) map.get("Items");
                    Iterator i = newArray.iterator();
                    while (i.hasNext()) {
                        Log.d("AVTAR", i.next() + "");

                    }

                    getData(newArray);
                }
            }
        });
    }

    private void getData(ArrayList<Object> list) {

        for(int i=0; i<list.size(); i++) {

            JSONArray arr = new JSONArray(list);
            JSONObject json = arr.optJSONObject(i);
            String tradeIn = json.optString("tradeInFor");
            String category = json.optString("category");
            String title = json.optString("title");
            String itemID = json.optString("itemId");
            String url = json.optString("url");

            mlist.add(new InventoryData(category, title, tradeIn, itemID, url));
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeItemFromInventory(int posiiton){

        mlist.remove(posiiton);
        mAdapter.notifyDataSetChanged();

        mFirebaseInventoryCollection.document(mUserIdentification).update("items", FieldValue.delete());


    }

}

