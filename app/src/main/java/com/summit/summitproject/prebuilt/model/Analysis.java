package com.summit.summitproject.prebuilt.model;

import java.io.Serializable;

/**
 * Represents a simple credit card transaction -- containing the merchant and the amount.
 */
public class Analysis implements Serializable {

    private final String stock;
    private final double percentage;

    public Analysis(String stock, double percentage) {
        this.stock = stock;
        this.percentage = percentage;
    }

    public String getStock() {
        return this.stock;
    }

    public double getPercentage() {
        return this.percentage;
    }

}
