package com.example.paceexchange;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomUniversalInventoryItem {

    private FirebaseFirestore mDatabase;
    HashMap<String, Object> generalInventoryAddition;
    CollectionReference totalInventory;
    int itemInventoryCounter;
    ArrayList<InventoryData> datalist = new ArrayList<>();
    InventoryData databaser;


     RandomUniversalInventoryItem(int nextItemCounter){

         generalInventoryAddition = new HashMap<>();
         mDatabase = FirebaseFirestore.getInstance();
         totalInventory = mDatabase.collection("item_inventory");
         itemInventoryCounter=nextItemCounter;

     }

    public void getFireStoreItem() {

        DocumentReference reference = mDatabase.collection("item_inventory").document(String.valueOf(itemInventoryCounter));
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("DUDE", "DocumentSnapshot data: " + document.getData().get("name"));
                    } else {
                        Log.d("DUDE", "No such document");
                    }
                } else {
                    Log.d("DUDE", "get failed with ", task.getException());
                }
            }
        });
    }

        public void getFireStoreObject () {
            DocumentReference areference = mDatabase.collection("item_inventory").document(String.valueOf(itemInventoryCounter));
            areference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    InventoryData data = documentSnapshot.toObject(InventoryData.class);
                    getFirebaseValue(data);
                }
            });

            return;

        }

    public void getFirebaseValue(InventoryData data) {

         databaser = data;
    }

    public InventoryData getOBject(){

         return databaser;
    }

    public ArrayList<InventoryData> getListItem(){
         datalist.add(databaser);
         return datalist;
    }



}
