package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        private TextView tvStatus;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvNotPaid;
        private View vLevel;

        ViewHolder(ItemRvDeliveryBinding binding) {
            super(binding.getRoot());

            tvStatus = binding.tvStatus;
            tvName = binding.tvName;
            tvAddress = binding.tvAddress;
            tvNotPaid = binding.tvNotPaid;
            vLevel = binding.vLevel;

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
        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrderModel item = itemList.get(position);

        int statusRes = getStatusRes(item.getStatus());

        holder.tvStatus.setText(statusRes);
        holder.tvName.setText(item.getName());
        holder.tvAddress.setText(String.format("%s %s, %s", item.getStreet(), item.getHouseNum(), item.getCityName()));
        holder.tvNotPaid.setVisibility(item.getOrderIsPaid().equals("0") ? View.VISIBLE : View.GONE);
        holder.vLevel.setVisibility(View.GONE); //todo: understand when to show

        holder.itemView.setOnClickListener(v -> adapterCallback.onItemChoose(item.getOrderId()));

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

}

