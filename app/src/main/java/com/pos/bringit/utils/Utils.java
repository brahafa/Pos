package com.pos.bringit.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;

import com.pos.bringit.R;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.models.QuarterModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DEAL;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DRINK;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_PIZZA;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_TOPPING;
import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_DELETED;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BR;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_FULL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_LH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_RH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TR;

public class Utils {

    public static void openPermissionAlertDialog(Context context) {
        openAlertDialog(context, "You don't have permission for this action", "Permission denied");
    }

    public static void openAlertDialog(Context context, String msg, String title, DialogListener dialogListener) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(title)
                .setTitle(msg)
                .setPositiveButton("כן", (dialog, which) -> dialogListener.onRetry(true))
                .setNegativeButton("לא", (dialog, which) -> dialogListener.onRetry(false))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
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

    public static void openAlertDialogRetry(Context context, DialogListener listener) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage("בדוק חיבור לאינטרנט")
                .setPositiveButton("Retry", (dialog, which) -> listener.onRetry(true))
                .setNegativeButton(R.string.cancel, (dialog, which) -> listener.onRetry(false))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public interface DialogListener {
        void onRetry(boolean isRetry);
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


    public static double calculateCartTotalPrice(ProductItemModel item, String orderType, boolean isItemFromKitchen) {
        double amount = 0;
        amount += orderType.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
                ? item.getDeliveryPrice()
                : item.getNotDeliveryPrice();

        if (item.getDealItems().size() > 0) {// DEAL
            for (int j = 0; j < item.getDealItems().size(); j++) {
                // amount += item.getDealItems().get(j).get();
                if (item.getDealItems().get(j).getProducts().size() > 0) {
                    for (int i = 0; i < item.getDealItems().get(j).getProducts().size(); i++) {
                        amount += calculateToppingsInCart(item.getDealItems().get(j).getProducts().get(i), isItemFromKitchen);
                    }

                }
            }
        } else if (item.getCategories().size() > 0) {
            amount += calculateToppingsInCart(item, isItemFromKitchen);
        }
        return amount;
    }

    private static double calculateToppingsInCart(ProductItemModel product, boolean isItemFromKitchen) {
        double categoryAmount = 0;

        for (int i = 0; i < product.getCategories().size(); i++) {//FOR CATEGORY
            if (product.getCategories().get(i).isToppingDivided()) {
                if (!isItemFromKitchen)
                    sortToppingsRecDESC(product.getCategories().get(i).getProducts());
                CategoryModel category = (product.getCategories().get(i));
                categoryAmount += calculateDividedToppings(category);
            } else if (product.getCategories().get(i).getCategoryHasFixedPrice()) {
                sortToppings(product.getCategories().get(i).getProducts());
                int j = 0;
                while (j < product.getCategories().get(i).getProductsFixedPrice() && j < product.getCategories().get(i).getProducts().size()) {
                    product.getCategories().get(i).getProducts().get(j).setIsPriceFixedOnTheCart(true);
                    //  product.getCategories().get(i).getProducts().get(j).setPrice((int) product.getCategories().get(i).getFixedPrice());
                    categoryAmount += product.getCategories().get(i).getFixedPrice();
                    j++;
                }
                for (; j < product.getCategories().get(i).getProducts().size(); j++) {
                    product.getCategories().get(i).getProducts().get(j).setIsPriceFixedOnTheCart(false);
                    categoryAmount += product.getCategories().get(i).getProducts().get(j).getPrice();
                }
            } else {//NO FIX PRICE
                for (int j = 0; j < product.getCategories().get(i).getProducts().size(); j++) {
                    product.getCategories().get(i).getProducts().get(j).setIsPriceFixedOnTheCart(false);
                    categoryAmount += product.getCategories().get(i).getProducts().get(j).getPrice();
                }
            }
        }
        return categoryAmount;
    }


    private static double calculateDividedToppings(CategoryModel category) {
        double sum = 0;
        ArrayList<Integer> layers = new ArrayList<>(setLayersPrices(getNumberOfQuarters(category.getProducts()), category));
        category.setLayerPrices(layers);

        for (int j = 0; j < layers.size(); j++) {
            sum += layers.get(j);
        }
        return sum;
    }

    // get list Of Quarters
    private static List<QuarterModel> getNumberOfQuarters(List<InnerProductsModel> toppings) {
        List<QuarterModel> quarters = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < toppings.size(); i++) {
            switch (toppings.get(i).getLocation()) {
                case PIZZA_TYPE_TL:
                case PIZZA_TYPE_TR:
                case PIZZA_TYPE_BL:
                case PIZZA_TYPE_BR:
                    num += 1;
                    break;
                case PIZZA_TYPE_LH:
                case PIZZA_TYPE_RH:
                    num += 2;
                    break;
                case PIZZA_TYPE_FULL:
                    num += 4;
                    break;
                case "":
                    num = 0;

            }
            while (num > 0) {
                quarters.add(new QuarterModel(i, toppings.get(i).getPrice()));
                num--;
            }
        }
        return quarters;
    }

    private static List<Integer> setLayersPrices(List<QuarterModel> quarters, CategoryModel category) {
        List<Integer> layers = new ArrayList<>();
        if (quarters == null || quarters.size() == 0) {
            return layers;
        }

        int round = 0;
        int prevIndex = 0;
        for (int i = 0; i < quarters.size(); i++) {
            // first quarter in the layer
            if (round % 4 == 0) {
                layers.add((round / 4), quarters.get(i).getPrice());
                // category.getProducts().get(quarters.get(i).getIndex()).setFirstInLayer(true);
                category.getProducts().get(quarters.get(i).getIndex()).setPriceForLayer(quarters.get(i).getPrice());
                prevIndex = quarters.get(i).getIndex();
            } else {
                // set item price to zero if the quarter is not the same quarter of topping
                if (prevIndex != quarters.get(i).getIndex()) {
                    category.getProducts().get(quarters.get(i).getIndex()).setPriceForLayer(0);
                    //    category.getProducts().get(quarters.get(i).getIndex()).setFirstInLayer(false);
                    prevIndex = quarters.get(i).getIndex();
                }
            }
            round++;
        }


        return layers;
    }

    private static void sortToppingsRecDESC(List<InnerProductsModel> toppingModels) {
        Collections.sort(toppingModels, (u1, u2) -> (u2.getPrice() - u1.getPrice()));
    }

    private static void sortToppings(List<InnerProductsModel> toppingModels) {
        Collections.sort(toppingModels, (u1, u2) -> (u1.getPrice() - u2.getPrice()));
    }

    public static double countProductPrice(ProductItemModel item, String orderType, boolean isItemFromKitchen) {
        if (item.isCanceled() || item.getChangeType().equals(ORDER_CHANGE_TYPE_DELETED)) {
            return 0;
        }
        return calculateCartTotalPrice(item, orderType, isItemFromKitchen);
        /*
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
                    if (category.getCategoryHasFixedPrice() && i < category.getProductsFixedPrice())
                        totalPriceSum += category.getFixedPrice();

//          pizza toppings price due to business topping method
                    else if (category.isToppingDivided()) {
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

        return totalPriceSum;*/
    }

    public static String getVersionApp(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getImageRes(String objectType) {
        int resID;

        switch (objectType) {
            case BUSINESS_ITEMS_TYPE_PIZZA:
                resID = R.drawable.ic_pizza;
                break;
            case BUSINESS_ITEMS_TYPE_DRINK:
                resID = R.drawable.selector_drink_icon;
                break;
            case BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER:
                resID = R.drawable.selector_food_icon;
                break;
            case BUSINESS_ITEMS_TYPE_DEAL:
                resID = R.drawable.ic_deal;
                break;
            case BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE:
            case BUSINESS_ITEMS_TYPE_TOPPING:
            default:
                resID = R.drawable.ic_topping;
        }
        return resID;
    }

}
