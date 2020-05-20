package com.pos.bringit.models;

public class PaymentModel {
    private String price;
    private String type;

    public PaymentModel(String price, String type) {
        this.price = price;
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
