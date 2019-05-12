package com.example.paceexchange;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class AvailableTradeItemInventory extends AppCompatActivity {

    private FirebaseFirestore mFirebaseDatabase;
    private CollectionReference mFirebaseInventoryCollection;
    private String mUserIdentification;
    private ArrayList<InventoryData> mlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseDatabase = FirebaseFirestore.getInstance();
        mFirebaseInventoryCollection = mFirebaseDatabase.collection("inventory");

        mlist = new ArrayList<>();

        getUsersCurrentFirebaseInventory();

    }

    public void getUsersCurrentFirebaseInventory() {

        mFirebaseInventoryCollection.document(mUserIdentification).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

                    storeUserCurrentInventory(newArray);
                }
            }
        });
    }

    private void storeUserCurrentInventory(ArrayList<Object> list) {

        for (int i = 0; i < list.size(); i++) {

            JSONArray arr = new JSONArray(list);
            JSONObject json = arr.optJSONObject(i);
            String tradeIn = json.optString("tradeInFor");
            String category = json.optString("category");
            String title = json.optString("title");
            String itemID = json.optString("itemID");
            String url = json.optString("url");

            mlist.add(new InventoryData(category, title, tradeIn, itemID, url));
            Log.d("KEITH", mlist.toString());
        }

    }
}
