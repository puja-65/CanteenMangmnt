package com.example.user.poddarcanteen;

import java.io.Serializable;

public class cartfood implements Serializable {
    private String foodID;
    private String quantity;
    private String total;



    public cartfood(String foodID,String quantity,String total){
        this.foodID=foodID;
        this.quantity=quantity;
        this.total=total;

    }

    public cartfood() {
    }

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


}