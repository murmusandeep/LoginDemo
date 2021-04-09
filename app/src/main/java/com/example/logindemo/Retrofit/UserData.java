package com.example.logindemo.Retrofit;

public class UserData {

    private String mName;
    private String mGender;
    private String mCountry;
    private String mAge;
    private String mDescription;

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
