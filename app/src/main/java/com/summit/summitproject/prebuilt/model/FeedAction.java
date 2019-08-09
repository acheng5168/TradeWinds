package com.summit.summitproject.prebuilt.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a simple credit card transaction -- containing the merchant and the amount.
 */
public class FeedAction implements Serializable {

    private String name;
    private String date;
    private String stock;
    private double actionPrice;
    private double currentPrice;
    private boolean wasBought;

    public FeedAction(String name, String date, String stock, double actionPrice, double currentPrice, boolean boughtSold) {
        this.name = name;
        this.date = date;
        this.stock = stock;
        this.actionPrice = actionPrice;
        this.currentPrice = currentPrice;

        // bought will be true
        this.wasBought = boughtSold;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public String getStock() {
        return this.stock;
    }

    public double getActionPrice(){
        return this.actionPrice;
    }

    public double getCurrentPrice(){
        return this.currentPrice;
    }

    public boolean wasBought() {
        return this.wasBought;
    }

    @Override
    public String toString() {
        return name + " bought " + stock + " (" + currentPrice + ") on " + date.toString() + " at " + actionPrice;
    }

//    @Override
//    public int compareTo(Object o) {
//        FeedAction otherFriend = (FeedAction) o;
//        return (int) Math.ceil(this.getPercent() - otherFriend.getPercent());
//    }
}
