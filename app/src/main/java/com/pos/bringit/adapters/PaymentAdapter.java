package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.pos.bringit.databinding.ItemRvPaymentBinding;
import com.pos.bringit.models.PaymentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.pos.bringit.utils.Constants.PAYMENT_METHOD_CARD;
import static com.pos.bringit.utils.Constants.PAYMENT_METHOD_CASH;

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

        holder.tvPrice.setText(String.format(Locale.US, "-%.2f", Double.parseDouble(item.getPrice())));

        String type;
        switch (item.getType()) {
            case PAYMENT_METHOD_CASH:
                type = "מזומן";
                break;
            case PAYMENT_METHOD_CARD:
                type = "אשראי";
                break;
            default:
                type = "";
        }

        holder.tvType.setText(type);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(PaymentModel newItem) {
        itemList.add(newItem);
        notifyItemInserted(getItemCount() - 1);
    }

    public void updateList(List<PaymentModel> newList) {
        PaymentItemsDiffCallback diffCallback = new PaymentItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

}

