package com.pos.bringit.models;


public class BusinessModel {

    private static BusinessModel instance = null;

    private int business_id;
    private String business_name_formal;
    private String business_name_commercial;
    private String business_email;
    private String business_register_date;
    private String business_last_login;
    private String business_phone;
    private String business_address;
    private String business_delivery_time;
    private double business_delivery_cost;
    private String additional_delivery_time_in_minute;
    private String logo_url;
    private String topping_method_id;
    private String topping_method_display;
    private String language;

    private String emv_url;
    private String emv_terminal_id;
    private String emv_terminal_number;

    public static BusinessModel getInstance() {
        if (instance == null) {
            instance = new BusinessModel();
        }
        return instance;
    }

    private BusinessModel() {

    }

    public void initData(BusinessModel businessModel) {
        instance = businessModel;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getBusiness_name_formal() {
        return business_name_formal;
    }

    public void setBusiness_name_formal(String business_name_formal) {
        this.business_name_formal = business_name_formal;
    }

    public String getBusiness_name_commercial() {
        return business_name_commercial;
    }

    public void setBusiness_name_commercial(String business_name_commercial) {
        this.business_name_commercial = business_name_commercial;
    }

    public String getBusiness_email() {
        return business_email;
    }

    public void setBusiness_email(String business_email) {
        this.business_email = business_email;
    }

    public String getBusiness_register_date() {
        return business_register_date;
    }

    public void setBusiness_register_date(String business_register_date) {
        this.business_register_date = business_register_date;
    }

    public String getBusiness_last_login() {
        return business_last_login;
    }

    public void setBusiness_last_login(String business_last_login) {
        this.business_last_login = business_last_login;
    }

    public String getBusiness_phone() {
        return business_phone;
    }

    public void setBusiness_phone(String business_phone) {
        this.business_phone = business_phone;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getBusiness_delivery_time() {
        return business_delivery_time;
    }

    public void setBusiness_delivery_time(String business_delivery_time) {
        this.business_delivery_time = business_delivery_time;
    }

    public String getAdditional_delivery_time_in_minute() {
        return additional_delivery_time_in_minute;
    }

    public void setAdditional_delivery_time_in_minute(String additional_delivery_time_in_minute) {
        this.additional_delivery_time_in_minute = additional_delivery_time_in_minute;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getTopping_method_id() {
        return topping_method_id;
    }

    public void setTopping_method_id(String topping_method_id) {
        this.topping_method_id = topping_method_id;
    }

    public String getTopping_method_display() {
        return topping_method_display;
    }

    public void setTopping_method_display(String topping_method_display) {
        this.topping_method_display = topping_method_display;
    }

    public double getBusiness_delivery_cost() {
        return business_delivery_cost;
    }

    public void setBusiness_delivery_cost(double business_delivery_cost) {
        this.business_delivery_cost = business_delivery_cost;
    }

    public String getEmv_url() {
        return emv_url;
    }

    public void setEmv_url(String emv_url) {
        this.emv_url = emv_url;
    }

    public String getEmv_terminal_id() {
        return emv_terminal_id;
    }

    public void setEmv_terminal_id(String emv_terminal_id) {
        this.emv_terminal_id = emv_terminal_id;
    }

    public String getEmv_terminal_number() {
        return emv_terminal_number;
    }

    public void setEmv_terminal_number(String emv_terminal_number) {
        this.emv_terminal_number = emv_terminal_number;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
