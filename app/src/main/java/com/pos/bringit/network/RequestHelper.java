package com.pos.bringit.network;

import android.content.Context;

import com.pos.bringit.local_db.DbHandler;
import com.pos.bringit.models.OrderDetailsModel;
import com.pos.bringit.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class RequestHelper {

    public void getAllOrdersFromDb(final Context context, final Request.RequestAllOrdersCallBack listener) {

        DbHandler dbHandler = new DbHandler(context);

        Request.getInstance().getAllOrders(context, response -> {
            if (response.getOrders() == null) response.setOrders(dbHandler.getAllOrdersFromDb());
            else {
                updateLocalDB(response.getOrders(), context);
            }
            listener.onDataDone(response);
        });
    }

    public void getOrderDetailsByIDFromDb(Context context, String orderId, Request.RequestProductsCallBack listener) {

        DbHandler dbHandler = new DbHandler(context);

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
    }

    private void updateLocalDB(List<OrderModel> orders, Context context) {
        DbHandler dbHandler = new DbHandler(context);

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

    public void updateOrderDetailsByID(Context context, OrderDetailsModel orderModel, boolean isUpdate) {
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

}


