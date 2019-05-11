package com.example.user.poddarcanteen;

import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable {
    private String foodId;
    private String foodName;
    private String foodPrice;
    private String foodType;
    public ArrayList<String> serverurlList = new ArrayList<String>();

    public Food(String foodId,String foodName,String foodPrice,String foodType,ArrayList<String> url){
        this.foodId=foodId;
        this.foodName=foodName;
        this.foodPrice=foodPrice;
        this.foodType=foodType;
        this.serverurlList=url;
    }

    public Food() {
    }

    public String getFoodId(){
        return foodId;
    }
    public String getFoodName(){
        return foodName;
    }
    public String getFoodPrice(){
        return foodPrice;
    }
    public String getFoodType(){
        return foodType;
    }
    public ArrayList<String> getserverurlList(){
        return serverurlList;
    }
}