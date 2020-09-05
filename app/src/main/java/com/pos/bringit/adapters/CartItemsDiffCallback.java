package com.pos.bringit.adapters;

import com.pos.bringit.models.ProductItemModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class CartItemsDiffCallback extends DiffUtil.Callback {

    private List<ProductItemModel> oldList;
    private List<ProductItemModel> newList;

    public CartItemsDiffCallback(List<ProductItemModel> oldList, List<ProductItemModel> newList) {
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
        return oldList.get(oldItemPosition).getTypeId().equals(newList.get(newItemPosition).getTypeId());
    }

    @Override //todo fix here
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName()) &&
                oldList.get(oldItemPosition).getTypeName().equals(newList.get(newItemPosition).getTypeName());

    }
}
