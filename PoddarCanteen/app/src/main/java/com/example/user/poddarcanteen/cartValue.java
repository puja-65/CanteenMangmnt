package com.example.user.poddarcanteen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class cartValue implements Serializable {
    private String cartID;
    private String userID;

    public ArrayList<cartfood> foodList = new ArrayList<cartfood>();



    public cartValue(String cartID,String userID,ArrayList food){
        this.cartID=cartID;
        this.userID=userID;
        this.foodList=food;

    }

    public cartValue() {
    }

    public String getCartID(){
        return cartID;
    }
    public String getUserID(){
        return userID;
    }
    public ArrayList<cartfood> getFoodList(){
        return foodList;
    }

}