package com.pos.bringit.utils;

public class Constants {
    public static String TOKEN_PREF = "token";
    public static String NAME_PREF = "name";
    public static String ROLE_PREF = "role";
    public static String USER_ALREADY_CONNECTED_PREF = "connected";

    public static final String PAYMENT_METHOD_CASH = "cash";
    public static final String PAYMENT_METHOD_CARD = "visa";

    public static final String NEW_ORDER_TYPE_TAKEAWAY = "pickup";
    public static final String NEW_ORDER_TYPE_DELIVERY = "delivery";
    public static final String NEW_ORDER_TYPE_TABLE = "table";
    public static final String NEW_ORDER_TYPE_ITEM = "item";

    public static final String DELIVERY_OPTION_TAKEAWAY = "0";
    public static final String DELIVERY_OPTION_DELIVERY = "1";
    public static final String DELIVERY_OPTION_TABLE = "2";

    public static final String ORDER_CHANGE_TYPE_NEW = "NEW";
    public static final String ORDER_CHANGE_TYPE_DELETED = "CANCELED";

    public static final String BUSINESS_ITEMS_TYPE_FOOD = "food";
    public static final String BUSINESS_ITEMS_TYPE_PIZZA = "pizza";
    public static final String BUSINESS_ITEMS_TYPE_TOPPING = "topping";
    public static final String BUSINESS_ITEMS_TYPE_DRINK = "drink";
    public static final String BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER = "additionalOffer";
    public static final String BUSINESS_ITEMS_TYPE_SPECIAL = "special";
    public static final String BUSINESS_ITEMS_TYPE_DEAL = "deal";
    public static final String BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE = "additionalCharge";

    public static final String PIZZA_TYPE_FULL = "full";
    public static final String PIZZA_TYPE_RH = "rightHalfPizza";
    public static final String PIZZA_TYPE_LH = "leftHalfPizza";
    public static final String PIZZA_TYPE_TL = "tl";
    public static final String PIZZA_TYPE_TR = "tr";
    public static final String PIZZA_TYPE_BL = "bl";
    public static final String PIZZA_TYPE_BR = "br";

    public static final String COLOR_GREEN = "#18cec3";
    public static final String COLOR_PURPLE = "#fb3dff";
    public static final String COLOR_RED = "#ff0000";
    public static final String COLOR_ORANGE = "#f27405";
    public static final String COLOR_YELLOW = "#f2e206";

    public static final int ITEM_TYPE_FOLDER = 0;
    public static final int ITEM_TYPE_FOLDER_END = 1;
    public static final int ITEM_TYPE_FOOD = 2;
    public static final int ITEM_TYPE_DEAL = 3;

    public static final String PIZZA_TYPE_CIRCLE = "circle";
    public static final String PIZZA_TYPE_RECTANGLE = "rectangle";
    public static final String PIZZA_TYPE_ONE_SLICE = "slice";

    public static final String BUSINESS_TOPPING_TYPE_QUARTER = "quarter";
    public static final String BUSINESS_TOPPING_TYPE_LAYER = "layer";
    public static final String BUSINESS_TOPPING_TYPE_FIXED = "fixed";

    public static final String PATTERN_DATE_FROM_SERVER = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE_TO_SHOW = "dd/MM/yyyy";
    public static final String PATTERN_DATE_TO_SEND = "yyyy-MM-dd";


    //URLS
    public static String IMAGES_BASE_URL = "https://api.bringit.co.il/public/images/";
    public static String DRINKS_URL = IMAGES_BASE_URL + "drink/";
    public static String ADDITIONAL_URL = IMAGES_BASE_URL + "loka/additionalOffer/";
    public static String FOOD_URL = IMAGES_BASE_URL + "food/";

}
