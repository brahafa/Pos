package com.pos.bringit.network;

import android.content.Context;
import android.util.Log;

import com.pos.bringit.POSApplication;
import com.pos.bringit.local_db.DbHandler;
import com.pos.bringit.models.OrderDetailsModel;
import com.pos.bringit.models.OrderModel;
import com.pos.bringit.models.response.AllOrdersResponse;
import com.pos.bringit.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RequestHelper {

    public void getAllOrdersFromDb(final Context context, final Request.RequestAllOrdersCallBack listener) {

        DbHandler dbHandler = new DbHandler(context);

        if (POSApplication.get().isNetworkAvailable()) {
            Request.getInstance().getAllOrders(context, response -> {
                if (response.getOrders() == null)
                    response.setOrders(dbHandler.getAllOrdersFromDb());
                else {
                    updateLocalDB(response.getOrders(), context);
                }
                listener.onDataDone(response);
            });
        } else {
//            Utils.openAlertDialog(context, "You are now offline", "Connection is lost");
            AllOrdersResponse allOrdersResponse = new AllOrdersResponse();
            allOrdersResponse.setOrders(dbHandler.getAllOrdersFromDb());
            listener.onDataDone(allOrdersResponse);
        }

    }

    public void getOrderDetailsByIDFromDb(Context context, String orderId, Request.RequestProductsCallBack listener) {

        DbHandler dbHandler = new DbHandler(context);

        if (POSApplication.get().isNetworkAvailable()) {
            Request.getInstance().getOrderDetailsByID(context, orderId, response -> {

                OrderDetailsModel localOrder = dbHandler.getOrderByIdFromDb(orderId);

                if (response == null) listener.onDataDone(localOrder);
                else {
                    if (localOrder == null)
                        dbHandler.insertOrUpdateOrderDetails(response, false);
                    else if (!response.getActionTime().equals(localOrder.getActionTime()))
                        dbHandler.insertOrUpdateOrderDetails(response, true);
                    listener.onDataDone(response);
                }
            });
        } else {
            Utils.openAlertDialog(context, "You are now offline", "Connection is lost");
            OrderDetailsModel localOrder = dbHandler.getOrderByIdFromDb(orderId);
            listener.onDataDone(localOrder);
        }
    }

    private void updateLocalDB(List<OrderModel> orders, Context context) {
        DbHandler dbHandler = new DbHandler(context);

        List<OrderModel> dbOrders = dbHandler.getAllOrdersFromDb();
        for (int i = dbOrders.size() - 1; i >= 0; i--) {
            OrderModel dbOrder = dbOrders.get(i);
            for (OrderModel order : orders) {
                if (order.getId().equals(dbOrder.getId())) {
                    dbOrders.remove(dbOrder);
                    break;
                }
            }
        }
        for (OrderModel order : dbOrders) dbHandler.deleteOrderFromDb(order.getId());

        List<OrderDetailsModel> orderToInsert = new ArrayList<>();
        List<OrderDetailsModel> orderToUpdate = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            OrderDetailsModel localOrder = dbHandler.getOrderByIdFromDb(orders.get(i).getId());
            if (localOrder == null) {
                orderToInsert.add(new OrderDetailsModel(orders.get(i)));
            } else {
                if (orders.get(i).getActionTime() != Integer.parseInt(localOrder.getActionTime())) {
                    orderToUpdate.add(new OrderDetailsModel(orders.get(i)));
                }
            }
        }
        for (int j = 0; j < orderToInsert.size(); j++) {
            updateOrderDetailsByID(context, orderToInsert.get(j), false);
        }
        for (int j = 0; j < orderToUpdate.size(); j++) {
            updateOrderDetailsByID(context, orderToUpdate.get(j), true);
        }
    }

    private void updateOrderDetailsByID(Context context, OrderDetailsModel orderModel, boolean isUpdate) {
        DbHandler dbHandler = new DbHandler(context);

        Request.getInstance().getOrderDetailsByID(context, orderModel.getOrderId(), response -> {
                    if (response != null) {
                        response.setColor(orderModel.getColor());
                        response.setIsCanceled(orderModel.isCanceled());
                        response.setIsChanged(orderModel.isChanged());
                    } else {
                        response = orderModel;
                    }

                    dbHandler.insertOrUpdateOrderDetails(response, isUpdate);

                }
        );
    }

    public void completeCartFromDb(final Context context, JSONObject data, final Request.RequestCreateOrderCallBack listener) {
        DbHandler dbHandler = new DbHandler(context);

        if (POSApplication.get().isNetworkAvailable()) {
            Request.getInstance().completeCart(context, data, response -> {
                if (response != null) {
                    listener.onDataDone(response);
                    return;
                }
                dbHandler.insertPendingOrderToDb(data);
                startReconnectTimer(context);
            });
        } else {
            dbHandler.insertPendingOrderToDb(data);
            startReconnectTimer(context);
        }

    }

    public void startReconnectTimer(final Context context) {
        Utils.openAlertDialog(context, "Your order will be sent as soon as reconnected", "Connection is lost");

        DbHandler dbHandler = new DbHandler(context);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d("timer", "trying to reconnect..");
                if (POSApplication.get().isNetworkAvailable()) {
                    String data = dbHandler.getPendingOrderFromDb();
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        Request.getInstance().completeCart(context, jsonObject, response -> {
                            if (response != null) {
//                                Utils.openAlertDialog(context, "Your order was sent successfully", "Reconnected");
                                timer.cancel();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.schedule(timerTask, 1000, 1000);
    }

}


