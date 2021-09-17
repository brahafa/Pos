package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class TransactionItem {

    @SerializedName("finance_session_id")
    private int financeSessionId;
    @SerializedName("payment_method")
    private String paymentMethod = "cash";
    @SerializedName("category")
    private int category = 2;
    @SerializedName("amount")
    private double amount;

    public int getFinanceSessionId() {
        return financeSessionId;
    }

    public void setFinanceSessionId(int financeSessionId) {
        this.financeSessionId = financeSessionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
