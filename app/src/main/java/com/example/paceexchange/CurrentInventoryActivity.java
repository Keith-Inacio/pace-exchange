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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CurrentInventoryActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestoreInventoryDatabase;
    private HashMap<String, Object> mFireStoreMap;
    private CollectionReference mFirestoreInventoryCollection;

    private Button mTradeItemButton, mAddNewItemButton, mRemoveItemButton;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private DatabaseReference mDatabase;
    private ArrayList<InventoryData> mInventoryList;
    private int mRowClickPosition;

    public static final String EXTRA_MESSAGE_TRADE_ITEM = "com.example.paceexchange.ITEM_NAME";
    public static final String EXTRA_MESSAGE_TRADE_ITEM_VALUE = "com.example.paceexchange.ITEM_VALUE";

    ArrayList<InventoryData> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        mFireStoreMap = new HashMap<>();
        mFirestoreInventoryDatabase = FirebaseFirestore.getInstance();
        mFirestoreInventoryCollection = mFirestoreInventoryDatabase.collection("item_inventory");

        mTradeItemButton = findViewById(R.id.tradeSelectedItemButton);
        mAddNewItemButton = findViewById(R.id.addInventoryItemButton);
        mRemoveItemButton = findViewById(R.id.removeInventoryItemButton);
        mRecyclerView = findViewById(R.id.recyclerView);

        mInventoryList = new ArrayList<>();
        dataList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child("-LbdV3uBhsq7xfV4ZQrb").child("Inventory");

        setButtonClickListener();
        iterateFirebaseInventory();
        setRecyclerView();
    }

    public void setRecyclerView() {


        mAdapter = new RecyclerAdapter(CurrentInventoryActivity.this, dataList, new View.OnClickListener() {
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

        /*
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key= getItem(child.getKey());

                    String condition = dataSnapshot.child(key).child("Condition").getValue().toString();
                    String category = dataSnapshot.child(key).child("Category").getValue().toString();
                    String value = dataSnapshot.child(key).child("Trade Value").getValue().toString();
                    mInventoryList.add(new InventoryData(R.drawable.javabook, key, value, condition, category));
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        */

        mFirestoreInventoryCollection.document("Keith Inacio").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();

                //getFirestoreListData((ArrayList<InventoryData>) document.get("items"));

                Map<String, Object> map = document.getData();
                for (Map.Entry<String, Object> fireMap : map.entrySet()) {
                    if (fireMap.getKey().equals("items")) {
                        Log.d("PACE", fireMap.getValue().toString());

                       // getFirestoreListData((ArrayList<InventoryData>)fireMap.getValue());
                    }
                }
            }
        });


        }

    private String getItem(String key) {

        return key;

    }

    private void getFirestoreListData(ArrayList<InventoryData> list) {

        dataList = list;

        Log.d("KEITH", String.valueOf(dataList.size()));
        Log.d("KEITH", list.toString());
       // Log.d("KEITH", dataList.get(1).getmItemName());


    }

    private void removeInventoryItem(int position) {

        //mDatabase.child(mInventoryList.get(position).getmItemName()).removeValue();
        mAdapter.removeInventoryItem(position);
        mAdapter.notifyDataSetChanged();

    }
}
