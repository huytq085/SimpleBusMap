package com.quanghuy.busmap.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Huy on 3/25/2018.
 */
public class User implements Serializable {
    private String userName;
    private String password;
    private String userId;
    private String firstName;
    private String lastName;
    private String gender;

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("userName", userName);
        result.put("password", password);
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("gender", gender);
        return result;
    }
    public User() {
    }
    public User(String userId, String userName, String password, String firstName, String lastName, String gender) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
