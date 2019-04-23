package com.example.user.poddarcanteen;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class user {

    public String userId;
    public String username;
    public String password;
    public String email;
    public String role;

    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public user(String userId, String username,String password,String email, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;

    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("username", username);
        result.put("email", email);
        result.put("role", role);
        return result;
    }


}