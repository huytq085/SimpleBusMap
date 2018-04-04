package com.quanghuy.busmap.entity;

/**
 * Created by Huy on 3/25/2018.
 */
public class User {
    private String userName;
    private String password;
    private long userId;
    private String firstName;
    private String lastName;
    private String gender;
    public User() {
    }
    public User(long userId, String userName, String password, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
