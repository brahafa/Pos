package com.pos.bringit.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;

import com.pos.bringit.R;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.ProductItemModel;
import com.sunmi.peripheral.printer.SunmiPrinterService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.pos.bringit.utils.SharedPrefs.getData;

public class PrinterPresenter {
    private Context context;
    private static final String TAG = "PrinterPresenter";
    public SunmiPrinterService printerService;

    public PrinterPresenter(Context context, SunmiPrinterService printerService) {
        this.context = context;
        this.printerService = printerService;
    }

    int fontsizeTitle = 40;
    int fontsizeContent = 30;
    int fontsizeFoot = 35;
    int fontsizeSmall = 30;

    public void print(final List<ProductItemModel> cartModels, final int payMode, String deliveryOption) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //   MenuBean menuBean = JSON.parseObject(json, MenuBean.class);

                String divide = "**************************************" + "\n";

                String divide2 = "--------------------------------------" + "\n";

                divide = "************************" + "\n";
                divide2 = "------------------------------" + "\n";
                String divide3 = "===================================" + "\n";
                //   }


                int width = divide2.length();

                double price = 0;
                try {
                    if (printerService.updatePrinterState() != 1) {
                        return;
                    }
                    printFooter();
                    openDrawer();
                    printerService.setAlignment(1, null);
                    printerService.printTextWithFont("חשבונית מס קבלה 1547" + "\n", "", fontsizeContent, null);

                    printerService.printTextWithFont(divide3 + "\n", "", fontsizeContent, null);

                    printerService.setAlignment(2, null);
                    printerService.printTextWithFont("תאור" + addblank(width - "תאור".length()) + "מחיר" + "\n", "", fontsizeContent, null);
                    for (int i = 0; i < cartModels.size(); i++) {
                        double itemPrice = deliveryOption.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
                                ? cartModels.get(i).getDeliveryPrice()
                                : cartModels.get(i).getNotDeliveryPrice();

                        //printerService.printTextWithFont(cartModels.get(i).getName() + "\n", "", fontsizeContent, null);
                        printerService.printTextWithFont(cartModels.get(i).getName() + addblank(width - cartModels.get(i).getName().length()) + itemPrice + "\n", "", fontsizeContent, null);
                        price += itemPrice;
                    }

                    printerService.setAlignment(1, null);
                    printerService.printTextWithFont(divide3 + "\n", "", fontsizeContent, null);

                    //printerService.sendRAWData(boldOn(), null);
                    printerService.setAlignment(2, null);
                    printerService.printTextWithFont("סך הכל לתשלום: " + price + "\n\n", "", fontsizeContent, null);

                    printerService.printTextWithFont("שיטת תשלום: מזומן", "", fontsizeContent, null);
                    printerService.printText("\n\n", null);
                    // printerService.sendRAWData(boldOff(), null);

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

                    printQRCode();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
        printerService.printTextWithFont("תאריך: " + formatData(new Date()) + "\n", "", fontsizeSmall, null);
        printerService.printTextWithFont("טלפון: " + BusinessModel.getInstance().getBusiness_phone() + "\n", "", fontsizeSmall, null);
        printerService.printTextWithFont(BusinessModel.getInstance().getBusiness_address() + "\n", "", fontsizeSmall, null);

        printerService.printTextWithFont("קופאי: " + getData(Constants.NAME_PREF) + "\n \n", "", fontsizeSmall, null);

    }

    private String formatData(Date nowTime) {
        SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return time.format(nowTime);
    }

    private String addblank(int count) {
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
}