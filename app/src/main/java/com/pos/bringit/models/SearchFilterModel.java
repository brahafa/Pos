package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class SearchFilterModel {

    @SerializedName("street")
    private String street;
    @SerializedName("client")
    private String client;
    @SerializedName("phone")
    private String phone;
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("invoice_number")
    private String invoiceNumber;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("start_date")
    private String startDate;


    public boolean isEmpty() {
        return street == null
                && client == null
                && phone == null
                && orderId == null
                && invoiceNumber == null
                && endDate == null
                && startDate == null;
    }

    public boolean containsOrderId() {
        return orderId != null;
    }

    public boolean containsInvoiceNumber() {
        return invoiceNumber != null;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
