package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvCartLayerBinding;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartLayerAdapter extends RecyclerView.Adapter<CartLayerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Integer> itemList;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvPrice;
        private View vDeleted;
        private View vAdded;

        ViewHolder(ItemRvCartLayerBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvToppingName;
            tvPrice = binding.tvToppingPrice;
            vDeleted = binding.vDeleted;
            vAdded = binding.vAdded;
        }
    }

    public CartLayerAdapter(Context context, ArrayList<Integer> layerPrices) {
        this.context = context;
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

        holder.tvName.setText(String.format(Locale.US, "%s %d", context.getString(R.string.layer), position + 1));
        holder.tvPrice.setText(String.format(Locale.US, "₪ %.2f", item));

        //todo understand what ot do with change types
//        holder.vDeleted.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_DELETED) ? View.VISIBLE : View.GONE);
//        holder.vAdded.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_NEW) ? View.VISIBLE : View.GONE);
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

