package com.pos.bringit.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvDeliveryBinding;
import com.pos.bringit.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {

    private List<OrderModel> itemList = new ArrayList<>();
    private AdapterCallback adapterCallback;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSystem;
        private TextView tvStatus;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvNotPaid;
        private ImageView ivLevel;

        ViewHolder(ItemRvDeliveryBinding binding) {
            super(binding.getRoot());

            tvSystem = binding.tvSystem;
            tvStatus = binding.tvStatus;
            tvName = binding.tvName;
            tvAddress = binding.tvAddress;
            tvNotPaid = binding.tvNotPaid;
            ivLevel = binding.ivLevel;

        }
    }

    public DeliveryAdapter(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvDeliveryBinding binding =
                ItemRvDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        binding.getRoot().setBackgroundResource(
                viewType == 0 ? R.drawable.background_order_field_white : R.drawable.background_order_field_purple);

        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrderModel item = itemList.get(position);

        if (item.getScheduledTime() != null)
            holder.tvStatus.setText(item.getScheduledTime());
        else if (item.isScheduled())
            holder.tvStatus.setText("Saved");
        else {
            int statusRes = getStatusRes(item.getStatus());
            holder.tvStatus.setText(statusRes);
        }

        holder.tvSystem.setText(item.getAddedBySystem());

        if (item.getClient() != null) {
            holder.tvName.setText(item.getClient().getFName());
            holder.tvAddress.setText(String.format("%s %s, %s",
                    item.getClient().getAddress().getStreet(),
                    item.getClient().getAddress().getHouseNum(),
                    item.getClient().getAddress().getCity() != null ? item.getClient().getAddress().getCity() : "אשדוד"));
        }

        holder.tvNotPaid.setVisibility(item.getIsPaid() != 1 ? View.VISIBLE : View.GONE);
        if (item.getIsPaid() == 2) holder.tvNotPaid.setText("תשלום חלקי");

        if (item.getColor() != null && !item.getColor().isEmpty()) {
            holder.ivLevel.setColorFilter(Color.parseColor(item.getColor()));
            holder.ivLevel.setVisibility(View.VISIBLE);
        } else
            holder.ivLevel.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> adapterCallback.onItemChoose(item.getId()));

    }

    private int getStatusRes(String status) {
        switch (status) {
            case "sent":
                return R.string.sent;
            case "packing":
                return R.string.packing;
            case "cooking":
                return R.string.cooking;
            case "preparing":
                return R.string.preparing;
            case "received":
                return R.string.received;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemChoose(String orderId);
    }

    public void updateList(List<OrderModel> newList) {
        OrderItemsDiffCallback diffCallback = new OrderItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

}

