package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvToppingBinding;
import com.pos.bringit.models.InnerProductsModel;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ViewHolder> {

    private List<InnerProductsModel> itemList;
    private AdapterCallback adapterCallback;
    private String type = "full";
    private int limit;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        ViewHolder(ItemRvToppingBinding binding) {
            super(binding.getRoot());

            tvName = binding.tvToppingName;

        }
    }

    public ToppingAdapter(List<InnerProductsModel> itemList, int limit, AdapterCallback adapterCallback) {
        this.itemList = itemList;
        this.limit = limit;
        this.adapterCallback = adapterCallback;

        Collections.sort(this.itemList, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
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
        InnerProductsModel item = itemList.get(position).clone();

        holder.tvName.setText(item.getName());
        holder.itemView.setSelected(item.isSelected());

        holder.itemView.setOnClickListener(v -> {

            int selectedCount = 0;
            if (!holder.itemView.isSelected()) {
                for (InnerProductsModel topping : itemList) {
                    if (topping.isSelected()) {
                        if (++selectedCount == limit)
                            return;
                    }
                }
            }

            item.setSelected(!item.isSelected());
            holder.itemView.setSelected(!holder.itemView.isSelected());
            adapterCallback.onItemSelected(type, item);
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemSelected(String type, InnerProductsModel orderItem);
    }

    public void updateSelected(String type, Set<Integer> selectedToppingList) {
        this.type = type;
        for (InnerProductsModel model : itemList) {
            model.setSelected(selectedToppingList.contains(model.getId()));
        }
        notifyDataSetChanged();
    }
}

