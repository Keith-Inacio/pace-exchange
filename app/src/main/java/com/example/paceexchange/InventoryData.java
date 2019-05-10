package com.example.paceexchange;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class InventoryData {

    private String mCategory, mItemID, mTitle, mUrl, mTradeInFor;
    private FirebaseFirestore mFirestoreDatabase;

    public InventoryData() {

    }

    public InventoryData(String category, String title, String tradeInFor) {

        mFirestoreDatabase = FirebaseFirestore.getInstance();
        mCategory=category;
        mItemID=FirebaseDatabase.getInstance().getReference().getKey();
        mTitle=title;
        mTradeInFor=tradeInFor;
        mUrl=null;

    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmItemID() {
        return mItemID;
    }

    public void setmItemID(String mItemID) {
        this.mItemID = mItemID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmTradeInFor() {
        return mTradeInFor;
    }

    public void setmTradeInFor(String mTradeInFor) {
        this.mTradeInFor = mTradeInFor;
    }
}
