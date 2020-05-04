package com.pos.bringit.adapters;

import com.pos.bringit.models.ToppingModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class ToppingItemsDiffCallback extends DiffUtil.Callback {

    private List<ToppingModel> oldList;
    private List<ToppingModel> newList;

    public ToppingItemsDiffCallback(List<ToppingModel> oldList, List<ToppingModel> newList) {
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
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).isSelected() == newList.get(newItemPosition).isSelected();
    }
}
