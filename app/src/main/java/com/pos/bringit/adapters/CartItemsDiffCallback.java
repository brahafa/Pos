package com.pos.bringit.adapters;

import com.pos.bringit.models.CartModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class CartItemsDiffCallback extends DiffUtil.Callback {

    private List<CartModel> oldList;
    private List<CartModel> newList;

    public CartItemsDiffCallback(List<CartModel> oldList, List<CartModel> newList) {
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
        return oldList.get(oldItemPosition).getObjectId().equals(newList.get(newItemPosition).getObjectId());
    }

    @Override //todo fix here
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName()) &&
                oldList.get(oldItemPosition).getObject_type().equals(newList.get(newItemPosition).getObject_type());

    }
}
