package com.example.user.poddarcanteen;

import java.io.Serializable;

public class cartfood implements Serializable {
    private String foodID;
    private String foodName;
    private String quantity;
    private String total;



    public cartfood(String foodName,String foodID,String quantity,String total){
        this.foodName = foodName;
        this.foodID=foodID;
        this.quantity=quantity;
        this.total=total;

    }

    public cartfood() {
    }
    public void setfoodName(String foodname){
        this.foodName=foodname; }

    public void setfoodID(String foodID){
        this.foodID=foodID; }

    public void setquantity(String quantity){
        this.quantity=quantity; }

        public void settotal(String total){
        this.total=total; }


    public String getfoodID(){
        return foodID;
    }
    public String getquantity(){
        return quantity;
    }
    public String gettotal(){
        return total;
    }
    public String getFoodName(){
        return foodName;
    }


}