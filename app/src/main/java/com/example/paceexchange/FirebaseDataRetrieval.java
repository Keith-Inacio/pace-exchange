package com.example.paceexchange;


import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FirebaseDataRetrieval {

    private FirebaseFirestore mTotalInventoryDatabase;
    private HashMap<String, Object> mGeneralInventoryAddition;
    private CollectionReference mTotalInventoryCollection;
    private ArrayList<InventoryData> mInventorylist;
    private int mInventoryItemCounter = 1;
    InventoryData mdata;

    FirebaseDataRetrieval() {

        mdata = new InventoryData();
        mGeneralInventoryAddition = new HashMap<>();
        mTotalInventoryDatabase = FirebaseFirestore.getInstance();
        mTotalInventoryCollection = mTotalInventoryDatabase.collection("item_inventory");
        mInventorylist = new ArrayList<>();

    }

    public void getFireStoreItem() {

        DocumentReference reference = mTotalInventoryDatabase.collection("item_inventory").document(String.valueOf(mInventoryItemCounter));
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String owner = document.getData().get("owner").toString();
                        String category = document.getData().get("category").toString();
                        String condition = document.getData().get("condition").toString();
                        String name = document.getData().get("name").toString();
                        String returnRequest = document.getData().get("return").toString();
                        String url = document.getData().get("url").toString();
                        String value = document.getData().get("value").toString();

                        getFirebaseValue(category, condition, name, returnRequest, url, value, owner);

                    }
                }

            }
        });
    }


    public void getFirebaseValue(String category, String condition, String name, String returnRequest, String url, String value, String owner) {

       // InventoryData mDataObject = new InventoryData(category, condition, name, returnRequest, url, value, owner);
       // mInventorylist.add(mDataObject);

    }

    public ArrayList<InventoryData> getListItem() {

        mInventoryItemCounter++;

        return mInventorylist;
    }


    public void updateFirebaseArrayAfterAuction(String user, String adversary){

       mTotalInventoryCollection.document(user).update("items", FieldValue.arrayUnion(new InventoryData()));

       mTotalInventoryCollection.document(user).update("items", FieldValue.arrayRemove(new InventoryData()));

    }

}