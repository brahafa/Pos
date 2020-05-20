package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvPaymentBinding;
import com.pos.bringit.models.PaymentModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private List<PaymentModel> itemList;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPrice;
        private TextView tvType;

        ViewHolder(ItemRvPaymentBinding binding) {
            super(binding.getRoot());

            tvPrice = binding.tvPaymentPrice;
            tvType = binding.tvPaymentType;

        }
    }

    public PaymentAdapter() {
        this.itemList = new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvPaymentBinding binding =
                ItemRvPaymentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PaymentModel item = itemList.get(position);

        holder.tvPrice.setText(String.format("-%s", item.getPrice()));
        holder.tvType.setText(item.getType());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(PaymentModel newItem) {
        itemList.add(newItem);
        notifyItemInserted(getItemCount() - 1);
    }

}

