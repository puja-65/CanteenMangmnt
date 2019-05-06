package com.example.user.poddarcanteen;

import java.util.ArrayList;

public class Food {
    private String foodName;
    private String foodPrice;
    private String foodType;
    public ArrayList<String> serverurlList = new ArrayList<String>();

    public Food(String foodName,String foodPrice,String foodType,ArrayList<String> url){
        this.foodName=foodName;
        this.foodPrice=foodPrice;
        this.foodType=foodType;
        this.serverurlList=url;
    }

    public Food() {
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