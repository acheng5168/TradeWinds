package com.summit.summitproject.prebuilt.model;

import java.io.Serializable;

/**
 * Represents a simple credit card transaction -- containing the merchant and the amount.
 */
public class Action implements Serializable {

    private final String stock;
    private final String date;
    private double priceBought;
    private double priceCurrent;
    private int type;

    public Action(String stock, String date, double priceBought, double priceCurrent, int type) {
        this.stock = stock;
        this.date = date;
        this.priceBought = priceBought;
        this.priceCurrent = priceCurrent;
        this.type = type;
    }

    public String getStock() {
        return this.stock;
    }

    public String getDate() {
        return this.date;
    }

    public int getType() {
        return this.type;
    }

    public double getPriceBought() {
        return this.priceBought;
    }

    public double getPriceCurrent() {
        return this.priceCurrent;
    }
}
