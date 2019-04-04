package com.example.paceexchange;

public class Student{

    String mStudentID, mFirstName, mLastName, mUserEmail;
    int mGraduationYear;

    public Student(String identification, String firstName, String lastName, String email, int gradDate) {

        mStudentID = identification;
        mFirstName=firstName;
        mLastName=lastName;
        mUserEmail = email;
        mGraduationYear=gradDate;

    }
}
