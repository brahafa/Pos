package com.pos.bringit.adapters;

import androidx.recyclerview.widget.DiffUtil;

import com.pos.bringit.models.PaymentModel;

import java.util.List;

public class PaymentItemsDiffCallback extends DiffUtil.Callback {

    private List<PaymentModel> oldList;
    private List<PaymentModel> newList;

    public PaymentItemsDiffCallback(List<PaymentModel> oldList, List<PaymentModel> newList) {
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
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getPrice().equals(newList.get(newItemPosition).getPrice()) &&
                oldList.get(oldItemPosition).getType().equals(newList.get(newItemPosition).getType());

    }
}
