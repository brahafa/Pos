package com.pos.bringit.adapters;

import com.pos.bringit.models.FinanceItem;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class FinanceItemsDiffCallback extends DiffUtil.Callback {

    private List<FinanceItem> oldList;
    private List<FinanceItem> newList;

    public FinanceItemsDiffCallback(List<FinanceItem> oldList, List<FinanceItem> newList) {
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
        return oldList.get(oldItemPosition).getFinanceId().equals(newList.get(newItemPosition).getFinanceId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
