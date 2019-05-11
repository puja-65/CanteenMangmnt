package com.example.user.poddarcanteen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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

    public String getCartID(){
        return foodID;
    }
    public String getUserID(){
        return quantity;
    }
    public String gettotal(){
        return total;
    }


}