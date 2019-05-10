package com.example.paceexchange;

public class InventoryData {

    private int mImage;
    private String mItemName, mItemValue, mItemCondition, mItemCategory, mReturn, mURL, mValue, mOwner;

    public InventoryData() {

    }

    public InventoryData(int image, String name, String value, String condition, String category) {

        mImage = image;
        mItemName = name;
        mItemValue = value;
        mItemCondition = condition;
        mItemCategory = category;

    }

    public InventoryData(String category, String condition, String name, String requested, String URL, String value, String owner) {

        mItemName = name;
        mItemValue = value;
        mItemCondition = condition;
        mItemCategory = category;
        mReturn = requested;
        mURL = URL;
        mValue = value;
        mOwner = owner;
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

    public String getmReturn() {
        return mReturn;
    }

    public void setmReturn(String mReturn) {
        this.mReturn = mReturn;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public String getmOwner() {
        return mOwner;
    }

    public void setmOwner(String mOwner) {
        this.mOwner = mOwner;
    }
}
