package com.example.paceexchange;

import java.util.ArrayList;

public class Student{

    private static String mStudentID, mFirstName, mLastName, mUserEmail;
    private int mGraduationYear, mNewUserDefaultReputation;
    private static ArrayList<String> mRegisteredStudents = new ArrayList();
   // private static ArrayList<Inventory> mStudentInventory = new ArrayList<>();

    public Student(String identification, String firstName, String lastName, String email, int gradDate, int reputation) {

        mStudentID = identification;
        mFirstName=firstName;
        mLastName=lastName;
        mUserEmail = email;
        mGraduationYear=gradDate;
        mNewUserDefaultReputation=reputation;

        mRegisteredStudents.add(mStudentID);

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

    public static ArrayList<String> getmRegisteredStudents() {
        return mRegisteredStudents;
    }

    public void setmRegisteredStudents(ArrayList<String> mRegisteredStudents) {
        this.mRegisteredStudents = mRegisteredStudents;
    }
}
