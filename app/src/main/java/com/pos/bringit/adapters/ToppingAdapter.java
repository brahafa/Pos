package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvToppingBinding;
import com.pos.bringit.models.BusinessItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ViewHolder> {

    private List<BusinessItemModel> itemList;
    private AdapterCallback adapterCallback;
    private String type = "full";

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        ViewHolder(ItemRvToppingBinding binding) {
            super(binding.getRoot());

            tvName = binding.tvToppingName;

        }
    }

    public ToppingAdapter(AdapterCallback adapterCallback) {
        this.itemList = new ArrayList<>();
        this.adapterCallback = adapterCallback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvToppingBinding binding =
                ItemRvToppingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        BusinessItemModel item = itemList.get(position);

        holder.tvName.setText(item.getName());
        holder.tvName.setSelected(item.isSelected());

        holder.itemView.setOnClickListener(v -> {
            holder.tvName.setSelected(!holder.tvName.isSelected());
            adapterCallback.onItemSelected(type, item.getObjectId());
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemSelected(String type, int orderId);
    }

    public void updateSelected(String type, Set<Integer> selectedToppingList, List<BusinessItemModel> itemListSelected) {
        this.type = type;
        for (BusinessItemModel model : itemListSelected) {
            model.setSelected(selectedToppingList.contains(model.getObjectId()));
        }
        updateList(itemListSelected);
    }

    public void updateList(List<BusinessItemModel> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

}

