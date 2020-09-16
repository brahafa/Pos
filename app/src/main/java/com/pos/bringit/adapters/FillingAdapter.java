package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvToppingBinding;
import com.pos.bringit.models.InnerProductsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FillingAdapter extends RecyclerView.Adapter<FillingAdapter.ViewHolder> {

    private List<InnerProductsModel> itemList;
    private AdapterCallback adapterCallback;
    private int limit;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        ViewHolder(ItemRvToppingBinding binding) {
            super(binding.getRoot());

            tvName = binding.tvToppingName;

        }
    }

    public FillingAdapter(List<InnerProductsModel> itemList, int limit, AdapterCallback adapterCallback) {
        this.itemList = itemList;
        this.limit = limit;
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
        InnerProductsModel item = itemList.get(position);

        holder.tvName.setText(item.getName());
        holder.tvName.setSelected(item.isSelected());

        holder.itemView.setOnClickListener(v -> {

            int selectedCount = 0;
            if (!holder.tvName.isSelected()) {
                for (InnerProductsModel topping : itemList) {
                    if (topping.isSelected()) {
                        if (++selectedCount == limit)
                            return;
                    }
                }
            }

            item.setSelected(!item.isSelected());
            holder.tvName.setSelected(!holder.tvName.isSelected());
            adapterCallback.onItemSelected(item);
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemSelected(InnerProductsModel orderItem);
    }

    public void updateList(List<InnerProductsModel> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}

