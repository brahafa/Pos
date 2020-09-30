package com.pos.bringit.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;

import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;

import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_PIZZA;
import static com.pos.bringit.utils.Constants.BUSINESS_TOPPING_TYPE_FIXED;
import static com.pos.bringit.utils.Constants.BUSINESS_TOPPING_TYPE_LAYER;
import static com.pos.bringit.utils.Constants.BUSINESS_TOPPING_TYPE_QUARTER;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BR;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_FULL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_LH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_RH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TR;

public class Utils {

    public static void openAlertDialog(Context context, String msg, String title) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static Bitmap scale(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    public static double countProductPrice(ProductItemModel item, String orderType) {
        double totalPriceSum = 0;

        totalPriceSum += orderType.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
                ? item.getDeliveryPrice()
                : item.getNotDeliveryPrice();
//        toppings price
        if (!item.getCategories().isEmpty())
            for (CategoryModel category : item.getCategories()) {
                int layerQuartersCount = 0;
                for (int i = 0; i < category.getProducts().size(); i++) {
                    InnerProductsModel topping = category.getProducts().get(i);

//          checking of fixed price
                    if (category.getCategoryHasFixedPrice() == 1 && i < category.getProductsFixedPrice())
                        totalPriceSum += category.getFixedPrice();

//          pizza toppings price due to business topping method
                    else if (item.getTypeName().equals(BUSINESS_ITEMS_TYPE_PIZZA)) {
                        if (topping.getLocation() != null)
                            switch (BusinessModel.getInstance().getTopping_method_display()) {
                                case BUSINESS_TOPPING_TYPE_QUARTER:
                                    switch (topping.getLocation()) {
                                        case PIZZA_TYPE_TR:
                                        case PIZZA_TYPE_TL:
                                        case PIZZA_TYPE_BR:
                                        case PIZZA_TYPE_BL:
                                            totalPriceSum += ((double) topping.getPrice()) / 4; //quarter price
                                            break;
                                        case PIZZA_TYPE_RH:
                                        case PIZZA_TYPE_LH:
                                            totalPriceSum += ((double) topping.getPrice()) / 2; //half price
                                            break;
                                        case PIZZA_TYPE_FULL:
                                        default:
                                            totalPriceSum += topping.getPrice();
                                            break;
                                    }
                                    break;
                                case BUSINESS_TOPPING_TYPE_LAYER:
                                    switch (topping.getLocation()) {
                                        case PIZZA_TYPE_TR:
                                        case PIZZA_TYPE_TL:
                                        case PIZZA_TYPE_BR:
                                        case PIZZA_TYPE_BL:
                                            if (++layerQuartersCount % 4 == 1)
                                                totalPriceSum += topping.getPrice();
                                            else {
                                                InnerProductsModel lastTopping = category.getProducts().get(i - 1);
                                                if (topping.getPrice() > lastTopping.getPrice()) {
                                                    totalPriceSum += topping.getPrice() - lastTopping.getPrice();
                                                    lastTopping.setPrice(0);
                                                } else
                                                    topping.setPrice(0);
                                            }
                                            break;
                                        case PIZZA_TYPE_RH:
                                        case PIZZA_TYPE_LH:
                                            layerQuartersCount += 2;
                                            if (layerQuartersCount % 4 == 2 || layerQuartersCount % 4 == 1)
                                                totalPriceSum += topping.getPrice();
                                            else {
                                                InnerProductsModel lastTopping = category.getProducts().get(i - 1);
                                                if (topping.getPrice() > lastTopping.getPrice()) {
                                                    totalPriceSum += topping.getPrice() - lastTopping.getPrice();
                                                    lastTopping.setPrice(0);
                                                } else
                                                    topping.setPrice(0);
                                            }
                                            break;
                                        case PIZZA_TYPE_FULL:
                                        default:
                                            layerQuartersCount += 4;
                                            totalPriceSum += topping.getPrice();
                                            break;
                                    }
                                    break;
                                case BUSINESS_TOPPING_TYPE_FIXED:
                                default:
                                    totalPriceSum += topping.getPrice();
                                    break;
                            }
                    } else
                        totalPriceSum += topping.getPrice();
                }
            }

//        deal items price //todo learn how to count deal price right
//        if (!item.getDealItems().isEmpty()) {
//            for (DealItemModel dealItem : item.getDealItems()) {
//
//                if (!dealItem.getProducts().isEmpty()) {
//                    ProductItemModel dealProduct = dealItem.getProducts().get(0);
//
//                    totalPriceSum += type.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
//                            ? dealProduct.getDeliveryPrice()
//                            : dealProduct.getNotDeliveryPrice();
//
////                deal toppings price
//                    if (!dealProduct.getCategories().isEmpty())
//                        for (CategoryModel category : dealProduct.getCategories())
//                            for (InnerProductsModel topping : category.getProducts())
//                                totalPriceSum += topping.getPrice();
//                }
//            }
//        }

        return totalPriceSum;
    }


}
