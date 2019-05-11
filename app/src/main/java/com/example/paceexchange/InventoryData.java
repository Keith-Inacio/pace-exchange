package com.example.paceexchange;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class InventoryData {

    private String category, itemID, title, url, tradeInFor;
    private FirebaseFirestore mFirestoreDatabase;

    public InventoryData() {

    }

    public InventoryData(String category, String title, String tradeInFor) {

        mFirestoreDatabase = FirebaseFirestore.getInstance();
        this.category=category;
        itemID=FirebaseDatabase.getInstance().getReference().push().getKey();
        this.title=title;
        this.tradeInFor=tradeInFor;
        url=null;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTradeInFor() {
        return tradeInFor;
    }

    public void setTradeInFor(String tradeInFor) {
        this.tradeInFor = tradeInFor;
    }

}
