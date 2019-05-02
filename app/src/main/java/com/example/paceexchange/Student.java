package com.example.paceexchange;

import java.util.ArrayList;
import java.util.HashMap;

public class Student{

    private static String mStudentID, mFirstName, mLastName, mUserEmail;
    private String mUserInventory;
    private int mGraduationYear, mNewUserDefaultReputation;

    public Student(String identification, String firstName, String lastName, String email, int gradDate, int reputation, String userInventory) {

        mStudentID = identification;
        mFirstName=firstName;
        mLastName=lastName;
        mUserEmail = email;
        mGraduationYear=gradDate;
        mNewUserDefaultReputation=reputation;
        mUserInventory = userInventory;

    }

    public static String getmStudentID() {
        return mStudentID;
    }

    public void setmStudentID(String mStudentID) {
        this.mStudentID = mStudentID;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmUserEmail() {
        return mUserEmail;
    }

    public void setmUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public int getmGraduationYear() {
        return mGraduationYear;
    }

    public void setmGraduationYear(int mGraduationYear) {
        this.mGraduationYear = mGraduationYear;
    }

    public int getmNewUserDefaultReputation() {
        return mNewUserDefaultReputation;
    }

    public void setmNewUserDefaultReputation(int mNewUserDefaultReputation) {
        this.mNewUserDefaultReputation = mNewUserDefaultReputation;
    }

    public String getmUserInventory() {
        return mUserInventory;
    }

    public void setmUserInventory(String mUserInventory) {
        this.mUserInventory = mUserInventory;
    }


}
