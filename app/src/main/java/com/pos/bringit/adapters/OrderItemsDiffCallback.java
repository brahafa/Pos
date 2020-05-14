package com.pos.bringit.adapters;

import com.pos.bringit.models.OrderModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class OrderItemsDiffCallback extends DiffUtil.Callback {

    private List<OrderModel> oldList;
    private List<OrderModel> newList;

    public OrderItemsDiffCallback(List<OrderModel> oldList, List<OrderModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getOrderId().equals(newList.get(newItemPosition).getOrderId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
