package com.example.paceexchange;

public class InventoryData {

    private int mImage;
    private String mItemName, mItemValue, mItemCondition, mItemCategory;

    public InventoryData(int image, String name, String value, String condition, String category){

        mImage=image;
        mItemName=name;
        mItemValue=value;
        mItemCondition=condition;
        mItemCategory=category;
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

    public String getmItemValue() {
        return mItemValue;
    }

    public void setmItemValue(String mItemValue) {
        this.mItemValue = mItemValue;
    }

    public String getmItemCondition() {
        return mItemCondition;
    }

    public void setmItemCondition(String mItemCondition) {
        this.mItemCondition = mItemCondition;
    }

    public String getmItemCategory() {
        return mItemCategory;
    }

    public void setmItemCategory(String mItemCategory) {
        this.mItemCategory = mItemCategory;
    }
}
