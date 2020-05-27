package com.pos.bringit.utils;

public class Constants {
    public static String TOKEN_PREF = "token";
    public static String NAME_PREF = "name";
    public static String ROLE_PREF = "role";
    public static String USER_ALREADY_CONNECTED_PREF = "connected";

    public static final String NEW_ORDER_TYPE_TAKEAWAY = "pickup";
    public static final String NEW_ORDER_TYPE_DELIVERY = "delivery";
    public static final String NEW_ORDER_TYPE_ITEM = "item";

    public static final String ORDER_CHANGE_TYPE_NEW = "NEW";
    public static final String ORDER_CHANGE_TYPE_DELETED = "DELETED";

    public static final String BUSINESS_ITEMS_TYPE_FOOD = "food";
    public static final String BUSINESS_ITEMS_TYPE_TOPPING = "topping";
    public static final String BUSINESS_ITEMS_TYPE_DRINK = "drink";
    public static final String BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER = "additionalOffer";
    public static final String BUSINESS_ITEMS_TYPE_SPECIAL = "special";
    public static final String BUSINESS_ITEMS_TYPE_DEAL = "deal";

    public static final String PIZZA_TYPE_FULL = "full";
    public static final String PIZZA_TYPE_RH = "rightHalfPizza";
    public static final String PIZZA_TYPE_LH = "leftHalfPizza";
    public static final String PIZZA_TYPE_TL = "tl";
    public static final String PIZZA_TYPE_TR = "tr";
    public static final String PIZZA_TYPE_BL = "bl";
    public static final String PIZZA_TYPE_BR = "br";

    public static final int ITEM_TYPE_FOLDER = 0;
    public static final int ITEM_TYPE_FOLDER_END = 1;
    public static final int ITEM_TYPE_FOOD = 2;
    public static final int ITEM_TYPE_DEAL = 3;

    public static final String PIZZA_TYPE_CIRCLE = "circle";
    public static final String PIZZA_TYPE_RECTANGLE = "rectangle";
    public static final String PIZZA_TYPE_ONE_SLICE = "one_slice";


    //URLS
    public static String IMAGES_BASE_URL = "https://api.bringit.co.il/public/images/";
    public static String DRINKS_URL = IMAGES_BASE_URL + "drink/";
    public static String ADDITIONAL_URL = IMAGES_BASE_URL + "loka/additionalOffer/";
    public static String FOOD_URL = IMAGES_BASE_URL + "food/";

}
