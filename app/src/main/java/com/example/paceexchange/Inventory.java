package com.example.paceexchange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private DatabaseReference mFireData;
    private FirebaseAuth mUserAuthorization;
    private ArrayList<InventoryData> mInventoryList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        mRecyclerView = findViewById(R.id.recyclerView);
        setRecyclerView();


        mFireData = FirebaseDatabase.getInstance().getReference().child("Student").child("-Ldi9uOY6N0qwnS4q3PG").child("mUserInventory");
        //mFireData.push().setValue("TextBook");
       // mFireData.push().setValue("Pencil");
        //mFireData.push().setValue("Laptop");

        mUserAuthorization = FirebaseAuth.getInstance();
        mUserAuthorization.getCurrentUser();

    }

    public void setRecyclerView(){

        mInventoryList = new ArrayList<>();
        mInventoryList.add(new InventoryData(R.drawable.javabook, "Java Book"));
        mInventoryList.add(new InventoryData(R.drawable.airpods, "Apple Airpods"));
        mInventoryList.add(new InventoryData(R.drawable.python_book, "Python Book"));
        mInventoryList.add(new InventoryData(R.drawable.hp_graph_calc, "HP Graphing Calculator"));
        mInventoryList.add(new InventoryData(R.drawable.google_giftcard, "$10 Google Play Gift Card"));

        mAdapter = new RecyclerAdapter(Inventory.this, mInventoryList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = (int) v.getTag();
                //not finished
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(Inventory.this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        }
}
