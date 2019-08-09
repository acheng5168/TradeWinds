package com.summit.summitproject;

public class APIResponse {
    String lastSalePrice;

    public APIResponse(String price) {
        this.lastSalePrice = price;
    }

    public String getPrice() {
        return lastSalePrice;
    }

    public void setPrice(String price) {
        this.lastSalePrice = price;
    }
}
