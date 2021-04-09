package com.example.logindemo.Retrofit;

public class UserData {

    private final String mName;
    private final String mGender;
    private final String mCountry;
    private final String mAge;
    private final String mDescription;

    public UserData(String name, String gender, String country, String age, String description) {

        mName = name;
        mGender = gender;
        mCountry = country;
        mAge = age;
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public String getGender(){
        return mGender;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getAge() {
        return mAge;
    }

    public String getDescription() {
        return mDescription;
    }
}
