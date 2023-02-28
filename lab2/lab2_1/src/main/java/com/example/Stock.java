package com.example;

/**
 * Hello world!
 *
 */
public class Stock {
    String label;
    int quantity;
    //constructor
    public Stock(String label, int quantity){
        this.label = label;
        this.quantity = quantity;
    }

    public String getlabel(){
        return label;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setlabel(String label){
        this.label = label;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}
