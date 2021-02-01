package com.pos.bringit.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;

import com.pos.bringit.R;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.OrderDetailsModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.models.UserDetailsModel;
import com.sunmi.peripheral.printer.SunmiPrinterService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.pos.bringit.utils.Constants.*;

import static com.pos.bringit.utils.SharedPrefs.getData;

public class PrinterPresenter {
    private Context context;
    public SunmiPrinterService printerService;
    private int width = 1;
    private String id1;
    private double total1;
    private String printDate;
    PrinterCallback printerCallback;

    public PrinterPresenter(Context context, SunmiPrinterService printerService) {
        this.context = context;
        this.printerService = printerService;
    }

    int fontsizeTitle = 40;
    int fontsizeContent = 30;
    int fontSize25 = 25;
    int fontsizeSmall = 30;
    int fontSizeRegular = 20;

    public void print(OrderDetailsModel orderDetailsModel, PrinterCallback printerCallback) {
        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setPhone(orderDetailsModel.getPhone());
        userDetailsModel.setName(orderDetailsModel.getFName());
        userDetailsModel.setLastName(orderDetailsModel.getLName());
        UserDetailsModel.NotesBean notesBean = new UserDetailsModel.NotesBean();
        notesBean.setDelivery(orderDetailsModel.getDeliveryNotes());
        notesBean.setOrder(orderDetailsModel.getOrderNotes());
        userDetailsModel.setNotes(notesBean);
        UserDetailsModel.AddressBean addressBean = new UserDetailsModel.AddressBean();
        addressBean.setStreet(orderDetailsModel.getAddress());
        userDetailsModel.setAddress(addressBean);
        printDate = orderDetailsModel.getOrderTime();

        print(new ArrayList<>(), orderDetailsModel.getOrderItems(), orderDetailsModel.getTotal(), orderDetailsModel.getOrderId(), orderDetailsModel.getClient(), 1, orderDetailsModel.getDeliveryOption(), printerCallback);
    }

    public void print(List<ProductItemModel> cartModels, final List<ProductItemModel> kitchenModels, double total, String id, UserDetailsModel mUserDetails, final int payMode, String deliveryOption, PrinterCallback printerCallback) {
        total1 = total;
        this.id1 = id;
        this.printerCallback = printerCallback;
        new Thread(new Runnable() {

            @Override
            public void run() {
                String divide = "**************************************" + "\n";
                String divide2 = "--------------------------------------" + "\n";
                divide = "************************" + "\n";
                divide2 = "------------------------------" + "\n";
                String divide3 = "===================================" + "\n";

                width = divide2.length();
                try {
                    if (printerService.updatePrinterState() != 1) {
                        return;
                    }
                    printFooter();
                    openDrawer();
                    printerService.setAlignment(1, null);
                    printerService.printTextWithFont("מספר הזמנה " + id1 + "\n", "", fontsizeContent, null);

                    printerService.printTextWithFont(divide3 + "\n", "", fontsizeContent, null);

                    printerService.setAlignment(2, null);
                    printerService.printTextWithFont("תאור" + addBlank(width - "תאור".length()) + "מחיר" + "\n", "", fontsizeContent, null);
                    List<ProductItemModel> productItemModels = cartModels;
                    productItemModels.addAll(kitchenModels);

                    for (int i = 0; i < productItemModels.size(); i++) {
                        double itemPrice = Utils.countProductPrice(productItemModels.get(i), deliveryOption, i > cartModels.size());

                        if (itemPrice == 0) {
                            printerService.printTextWithFont(productItemModels.get(i).getName() + addBlank(width - productItemModels.get(i).getName().length()) + "חינם" + "\n", "", fontsizeContent, null);
                        } else {
                            printerService.printTextWithFont(productItemModels.get(i).getName() + addBlank(width - productItemModels.get(i).getName().length()) + itemPrice + "₪" + "\n", "", fontsizeContent, null);

                        }
                        if (productItemModels.get(i).getCategories().size() > 0) {
                            printCategory(productItemModels.get(i).getCategories());
                        }
                        if (productItemModels.get(i).getDealItems().size() > 0) {
                            for (int j = 0; j < productItemModels.get(i).getDealItems().size(); j++) {
                                for (int k = 0; k < productItemModels.get(i).getDealItems().get(j).getProducts().size(); k++) {
                                    printerService.printTextWithFont("  " + productItemModels.get(i).getDealItems().get(j).getProducts().get(k).getName() + addBlank(width - productItemModels.get(i).getDealItems().get(j).getProducts().get(k).getName().length()) + "\n", "", fontsizeContent, null);
                                    if (productItemModels.get(i).getDealItems().get(j).getProducts().get(k).getCategories().size() > 0) {
                                        printCategory(productItemModels.get(i).getDealItems().get(j).getProducts().get(k).getCategories());
                                    }
                                }
                            }
                        }
                        printerService.printTextWithFont("\n", "", fontSizeRegular, null);

                    }

                    printerService.setAlignment(1, null);
                    printerService.printTextWithFont(divide3 + "\n", "", fontsizeContent, null);
                    printerService.setAlignment(2, null);
                    if (deliveryOption.equals(NEW_ORDER_TYPE_DELIVERY)) {
                        printerService.printTextWithFont("עלות משלוח: " + (BusinessModel.getInstance().getBusiness_delivery_cost()) + "\n", "", fontsizeContent, null);
                        total1 += BusinessModel.getInstance().getBusiness_delivery_cost();
                    }
                    printerService.printTextWithFont("סך הכל לתשלום: " + total1 + "\n\n", "", fontsizeContent, null);

                    //user
                    printerService.printTextWithFont("פרטי לקוח: " + "\n", "", fontSizeRegular, null);
                    if (deliveryOption.equals(NEW_ORDER_TYPE_DELIVERY)) {
                        printerService.printTextWithFont(" שם: " + mUserDetails.getName() + " כתובת: " + mUserDetails.getAddress().getCityName() + " " + mUserDetails.getAddress().getStreet() +
                                "\n" + " מספר:" + mUserDetails.getAddress().getHouseNum() + " קומה: " + mUserDetails.getAddress().getFloor() + " דירה: " + mUserDetails.getAddress().getAptNum() + "\n"
                                + " טלפון: " + mUserDetails.getPhone()
                                + "\n", "", fontSizeRegular, null);
                    } else {
                        printerService.printTextWithFont(" שם: " + mUserDetails.getName() + " " + mUserDetails.getLastName() + "\n" +
                                " טלפון: " + mUserDetails.getPhone()
                                + "\n", "", fontSizeRegular, null);
                    }
                    if (mUserDetails.getNotes().getOrder() != null && !mUserDetails.getNotes().getOrder().equals("")) {
                        printerService.printTextWithFont("הערות להזמנה: " + mUserDetails.getNotes().getOrder() + "\n", "", fontSizeRegular, null);

                    }
                    if (mUserDetails.getNotes().getDelivery() != null && !mUserDetails.getNotes().getDelivery().equals("")) {
                        printerService.printTextWithFont("הערות למשלוח: " + mUserDetails.getNotes().getDelivery() + "\n", "", fontSizeRegular, null);

                    }

                    printerService.printText("\n\n", null);
                    printerService.setAlignment(1, null);
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bringit_logo);
                    if (bitmap.getWidth() > 200) {
                        int newHeight = (int) (1.0 * bitmap.getHeight() * 200 / bitmap.getWidth());
                        bitmap = Utils.scale(bitmap, 200, newHeight);
                    }
                    printerService.printBitmap(bitmap, null);
                    printerService.printText("\n\n", null);

                    printerService.lineWrap(0, null);
                    printerService.cutPaper(null);
                    //  printQRCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (printerCallback != null)
                    printerCallback.onFinished();
            }
        }).start();

    }

    private void printCategory(List<CategoryModel> categories) throws RemoteException {
        for (int i = 0; i < categories.size(); i++) {
            printerService.printTextWithFont("   " + categories.get(i).getName() + addBlank(width - categories.get(i).getName().length()) + "\n", "", fontSize25, null);
            for (int j = 0; j < categories.get(i).getProducts().size(); j++) {
                printInnerCategory(categories.get(i).getProducts().get(j), categories.get(i).isToppingDivided(), categories.get(i).getFixedPrice());
            }
        }

    }

    private void printInnerCategory(InnerProductsModel innerProductsModel, boolean toppingDivided, double fixPrice) throws RemoteException {
        String location = "";
        if (innerProductsModel.getLocation() != null && toppingDivided) {
            switch (innerProductsModel.getLocation()) {
                case PIZZA_TYPE_TL:
                    location = "(רבע שמאלי עליון)";
                    break;
                case PIZZA_TYPE_TR:
                    location = "(רבע ימני עליון)";
                    break;
                case PIZZA_TYPE_BL:
                    location = "(רבע שמאלי תחתון)";
                    break;
                case PIZZA_TYPE_BR:
                    location = "(רבע ימני תחתון)";
                    break;
                case PIZZA_TYPE_LH:
                    location = "(חצי שמאלי)";
                    break;
                case PIZZA_TYPE_RH:
                    location = "(חצי ימני)";
                    break;
                case PIZZA_TYPE_FULL:
                    location = "(מלא)";
                    break;
            }
        }
        String innerCategoryName = "    " + innerProductsModel.getName() + " " + location;
        if (innerProductsModel.isIsPriceFixedOnTheCart()){
            printerService.printTextWithFont(innerCategoryName + addBlank((width + width / 3) - (innerCategoryName.length())) + fixPrice  + "₪" + "\n", "", fontSizeRegular, null);
        }
        else if (innerProductsModel.getPrice() == 0) {
            printerService.printTextWithFont(innerCategoryName + addBlank((width + width / 3) - (innerCategoryName.length())) + "חינם" + "\n", "", fontSizeRegular, null);
        } else {
            printerService.printTextWithFont(innerCategoryName + addBlank((width + width / 3) - (innerCategoryName.length())) + innerProductsModel.getPrice() + "₪" + "\n", "", fontSizeRegular, null);
        }
    }

    private void printQRCode() throws RemoteException {
        //qrcode
        printerService.printTextWithFont("למעקב אחרי ההזמנה", "", fontsizeContent, null);
        printerService.printText("\n\n \n", null);
        // printerService.sendRAWData(boldOff(), null);

        printerService.setAlignment(1, null);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.qrcode);
        if (bitmap.getWidth() > 200) {
            int newHeight = (int) (1.0 * bitmap.getHeight() * 200 / bitmap.getWidth());
            bitmap = Utils.scale(bitmap, 200, newHeight);
        }
        printerService.printBitmap(bitmap, null);
        printerService.lineWrap(3, null);
        printerService.cutPaper(null);
    }

    private void printFooter() throws RemoteException {
        printerService.sendRAWData(boldOn(), null);
        printerService.setAlignment(1, null);
        printerService.printTextWithFont(BusinessModel.getInstance().getBusiness_name_commercial() + "\n \n", "", fontsizeTitle, null);
        printerService.sendRAWData(boldOff(), null);
        printerService.setAlignment(2, null);
        printerService.printTextWithFont("תאריך הדפסה: " + formatData(new Date()) + "\n", "", fontsizeSmall, null);
        if (printDate != null)
            printerService.printTextWithFont("תאריך הזמנה: " + printDate + "\n", "", fontsizeSmall, null);
        printerService.printTextWithFont("טלפון: " + BusinessModel.getInstance().getBusiness_phone() + "\n", "", fontsizeSmall, null);
        printerService.printTextWithFont(BusinessModel.getInstance().getBusiness_address() + "\n", "", fontsizeSmall, null);
        printerService.printTextWithFont("קופאי: " + getData(Constants.NAME_PREF) + "\n \n", "", fontsizeSmall, null);

    }

    private String formatData(Date nowTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return time.format(nowTime);
    }

    private String addBlank(int count) {
        String st = "";
        if (count < 0) {
            count = 0;
        }
        for (int i = 0; i < count; i++) {
            st = st + " ";
        }
        return st;
    }

    private static final byte ESC = 0x1B;// Escape

    /**
     * 字体加粗
     */
    private byte[] boldOn() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0xF;
        return result;
    }

    /**
     * 取消字体加粗
     */
    private byte[] boldOff() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0;
        return result;
    }

    private boolean isZh() {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    private int String_length(String rawString) {
        return rawString.replaceAll("[\\u4e00-\\u9fa5]", "SH").length();
    }

    public void openDrawer() {
        byte[] aa = new byte[5];

        aa[0] = 0x10;
        aa[1] = 0x14;
        aa[2] = 0x00;
        aa[3] = 0x00;
        aa[4] = 0x00;

        try {
            printerService.sendRAWData(aa, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public interface PrinterCallback {
        void onFinished();
    }
}