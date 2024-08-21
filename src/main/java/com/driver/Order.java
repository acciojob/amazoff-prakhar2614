package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        int time = Integer.parseInt(deliveryTime);
        int hour = time/100;
        int min = time%100;
        this.id = id;
        this.deliveryTime = hour*60 + min;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
