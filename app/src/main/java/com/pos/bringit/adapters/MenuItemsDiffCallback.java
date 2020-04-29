package com.pos.bringit.adapters;

import com.pos.bringit.models.BreadcrumbModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class MenuItemsDiffCallback extends DiffUtil.Callback {

    private List<BreadcrumbModel> oldList;
    private List<BreadcrumbModel> newList;

    public MenuItemsDiffCallback(List<BreadcrumbModel> oldList, List<BreadcrumbModel> newList) {
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
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName()) &&
                oldList.get(oldItemPosition).getFatherId().equals(newList.get(newItemPosition).getFatherId());
    }
}
