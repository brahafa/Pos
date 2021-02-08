package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pos.bringit.databinding.ItemRvCartLayerBinding;

import java.util.ArrayList;
import java.util.Locale;

public class CartLayerAdapter extends RecyclerView.Adapter<CartLayerAdapter.ViewHolder> {

    private ArrayList<Integer> itemList;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private View vDeleted;
        private View vAdded;

        ViewHolder(ItemRvCartLayerBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvToppingName;
            vDeleted = binding.vDeleted;
            vAdded = binding.vAdded;
        }
    }

    public CartLayerAdapter(ArrayList<Integer> layerPrices) {
        this.itemList = layerPrices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCartLayerBinding binding =
                ItemRvCartLayerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        double item = itemList.get(position);

        holder.tvName.setText(String.format(Locale.US, "שכבת תוספות %d  ₪%.2f", position + 1, item));

        //todo understand what ot do with change types
//        holder.vDeleted.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_DELETED) ? View.VISIBLE : View.GONE);
//        holder.vAdded.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_NEW) ? View.VISIBLE : View.GONE);
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

