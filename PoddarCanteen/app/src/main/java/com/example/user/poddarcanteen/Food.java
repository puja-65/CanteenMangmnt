package com.example.user.poddarcanteen;

public class Food {
    private String foodName;
    private String foodPrice;
    private String foodType;
    private String url;

    public Food(String foodName,String foodPrice,String foodType,String url){
        this.foodName=foodName;
        this.foodPrice=foodPrice;
        this.foodType=foodType;
        this.url=url;
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
    public String url(){
        return url;
    }
}