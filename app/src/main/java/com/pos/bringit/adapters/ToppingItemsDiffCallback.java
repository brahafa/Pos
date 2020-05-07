package com.pos.bringit.adapters;

import com.pos.bringit.models.BusinessItemModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class ToppingItemsDiffCallback extends DiffUtil.Callback {

    private List<BusinessItemModel> oldList;
    private List<BusinessItemModel> newList;

    public ToppingItemsDiffCallback(List<BusinessItemModel> oldList, List<BusinessItemModel> newList) {
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
