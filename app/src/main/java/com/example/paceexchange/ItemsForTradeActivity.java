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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ItemsForTradeActivity extends AppCompatActivity {

    private FirebaseFirestore mFirebaseDatabase;
    private CollectionReference mFirebaseInventoryCollection;
    private String mSelectedUserIdentification, mUserIdentification;
    private ArrayList<InventoryData> mAllItemsList;

    private String mCurrentItemSelectionID;
    private int mRowClickPosition;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_trade_items);

        Intent intent = getIntent();
        mUserIdentification = intent.getStringExtra(CurrentInventoryActivity.USER_IDENTIFICATION_ADD_ITEM_MESSAGE);

        mFirebaseDatabase = FirebaseFirestore.getInstance();
        mFirebaseInventoryCollection = mFirebaseDatabase.collection("inventory");


        mAllItemsList = new ArrayList<>();

        setRecyclerView();
        getAllFireStoreUserIDs();

    }

    public void getAllFireStoreUserIDs() {


        mFirebaseInventoryCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        mSelectedUserIdentification = document.getId();
                        Log.d("KEITH", mSelectedUserIdentification);
                        getUsersCurrentFirebaseInventory(mSelectedUserIdentification);

                        //Log.d("KEITH", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("KEITH", "Error getting documents: ", task.getException());
                }
            }
        });


    }

    public void getUsersCurrentFirebaseInventory(String id) {

        mFirebaseInventoryCollection.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Map<String, Object> map = task.getResult().getData();
                if (map.get("Items") != null) {
                    ArrayList<Object> newArray = (ArrayList<Object>) map.get("Items");
                    Iterator i = newArray.iterator();
                    while (i.hasNext()) {
                        Log.d("AVTAR", i.next() + "");
                        //  Object firstKey = map.keySet().toArray()[0];
                        //  Object valueForFirstKey = map.get(firstKey);

                    }

                    storeUniversalInventory(newArray);
                }
            }
        });
    }

    private void storeUniversalInventory(ArrayList<Object> list) {

        for (int i = 0; i < list.size(); i++) {

            JSONArray arr = new JSONArray(list);
            JSONObject json = arr.optJSONObject(i);
            String tradeIn = json.optString("tradeInFor");
            String category = json.optString("category");
            String title = json.optString("title");
            String itemID = json.optString("itemID");
            String url = json.optString("url");

            mAllItemsList.add(new InventoryData(category, title, tradeIn, itemID, url));
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new RecyclerAdapter(ItemsForTradeActivity.this, mAllItemsList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRowClickPosition = (int) v.getTag();
                InventoryData display = mAdapter.getItem(mRowClickPosition);
                mCurrentItemSelectionID = display.getItemID();
                //Toast.makeText(getApplicationContext(), String.valueOf(mCurrentItemSelectionID), Toast.LENGTH_LONG).show();
            }

        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }
}
