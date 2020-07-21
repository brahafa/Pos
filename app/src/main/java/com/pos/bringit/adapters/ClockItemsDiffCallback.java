package com.pos.bringit.adapters;

import com.pos.bringit.models.ClocksModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class ClockItemsDiffCallback extends DiffUtil.Callback {

    private List<ClocksModel> oldList;
    private List<ClocksModel> newList;

    public ClockItemsDiffCallback(List<ClocksModel> oldList, List<ClocksModel> newList) {
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
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
