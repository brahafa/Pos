package com.pos.bringit.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvTakeAwayBinding;
import com.pos.bringit.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class TakeAwayAdapter extends RecyclerView.Adapter<TakeAwayAdapter.ViewHolder> {

    private List<OrderModel> itemList = new ArrayList<>();
    private AdapterCallback adapterCallback;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvStatus;
        private TextView tvNumber;
        private TextView tvName;
        private TextView tvNotPaid;
        private View vLevel;

        ViewHolder(ItemRvTakeAwayBinding binding) {
            super(binding.getRoot());

            tvStatus = binding.tvStatus;
            tvNumber = binding.tvNumber;
            tvName = binding.tvName;
            tvNotPaid = binding.tvNotPaid;
            vLevel = binding.vLevel;

        }
    }

    public TakeAwayAdapter(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvTakeAwayBinding binding =
                ItemRvTakeAwayBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrderModel item = itemList.get(position);

        int statusRes = getStatusRes(item.getStatus());

        holder.tvStatus.setText(statusRes);
        if (item.getClient() != null) {
            holder.tvNumber.setText(item.getClient().getPhone());
            holder.tvName.setText(item.getClient().getFName());
        } else {
            holder.tvNumber.setText("");
            holder.tvName.setText("");
        }
        holder.tvNotPaid.setVisibility(item.getIsPaid() != 1 ? View.VISIBLE : View.GONE);
        if (item.getIsPaid() == 2) holder.tvNotPaid.setText("Partly paid");

        if (item.getColor() != null)
            holder.vLevel.setBackgroundColor(Color.parseColor(item.getColor()));
        else
            holder.vLevel.setBackgroundColor(Color.WHITE);

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

}

