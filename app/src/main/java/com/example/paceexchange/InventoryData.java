package com.example.paceexchange;

public class InventoryData {

    private int mImage;
    private String mItemName;

    public InventoryData(int image, String name){

        mImage=image;
        mItemName=name;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmItemName() {
        return mItemName;
    }

    public void setmItemName(String mItemName) {
        this.mItemName = mItemName;
    }
}
