package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pos.bringit.databinding.ItemRvToppingBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;

import java.util.List;

public class FillingAdapter extends RecyclerView.Adapter<FillingAdapter.ViewHolder> {

    private List<InnerProductsModel> itemList;
    private AdapterCallback adapterCallback;
    private int limit;
    private boolean isMultiple;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private ImageView ivUp;
        private TextView tvCount;
        private ImageView ivDown;

        ViewHolder(ItemRvToppingBinding binding) {
            super(binding.getRoot());

            tvName = binding.tvToppingName;
            ivUp = binding.ivCountUp;
            tvCount = binding.tvToppingCount;
            ivDown = binding.ivCountDown;

        }
    }

    public FillingAdapter(CategoryModel item, AdapterCallback adapterCallback) {
        this.itemList = item.getProducts();
        this.limit = item.getProductsLimit();
        this.isMultiple = item.isMultipleSelection();
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

        if (item.getCount() > 1) {
            holder.ivUp.setVisibility(View.VISIBLE);
            holder.tvCount.setVisibility(View.VISIBLE);
            holder.ivDown.setVisibility(View.VISIBLE);
        }

        holder.tvCount.setText(String.valueOf(item.getCount()));

        holder.ivUp.setOnClickListener(v -> {

            int selectedCount = 0;
            for (InnerProductsModel topping : itemList) {
                if (topping.isSelected()) {
                    selectedCount += topping.getCount();
                    if (selectedCount == limit)
                        return;
                }
            }

            String countText = holder.tvCount.getText().toString();
            int count = Integer.parseInt(countText);
            count++;
            holder.tvCount.setText(String.valueOf(count));
            item.setCount(count);
            adapterCallback.onItemAdded(item);
        });

        holder.ivDown.setOnClickListener(v -> {
            String countText = holder.tvCount.getText().toString();
            int count = Integer.parseInt(countText);
            if (count > 1) {
                count--;
                holder.tvCount.setText(String.valueOf(count));
                item.setCount(count);
                adapterCallback.onItemRemoved(item);
            }
        });

        holder.tvName.setText(item.getName());
        holder.tvName.setSelected(item.isSelected());


        holder.tvName.setOnClickListener(v -> {

            int selectedCount = 0;
            if (!holder.tvName.isSelected()) {
                for (int i = 0; i < itemList.size(); i++) {
                    InnerProductsModel topping = itemList.get(i);
                    if (topping.isSelected()) {
                        if (limit == 1) {
                            topping.setSelected(false);
                            adapterCallback.onItemRemoved(topping);
                            notifyItemChanged(i);
                            break;
                        }
                        selectedCount += topping.getCount();
                        if (selectedCount == limit)
                            return;
                    }
                }
            }

            item.setSelected(!item.isSelected());
            holder.tvName.setSelected(!holder.tvName.isSelected());

            if (holder.tvName.isSelected() && isMultiple && limit != 1) {
                holder.ivUp.setVisibility(View.VISIBLE);
                holder.tvCount.setVisibility(View.VISIBLE);
                holder.ivDown.setVisibility(View.VISIBLE);
            } else {
                holder.ivUp.setVisibility(View.GONE);
                holder.tvCount.setVisibility(View.GONE);
                holder.ivDown.setVisibility(View.GONE);
            }


            if (item.isSelected()) adapterCallback.onItemAdded(item);
            else for (int i = item.getCount() - 1; i >= 0; i--) {
                item.setCount(1);
                adapterCallback.onItemRemoved(item);
            }
            holder.tvCount.setText(String.valueOf(item.getCount()));
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemAdded(InnerProductsModel orderItem);

        void onItemRemoved(InnerProductsModel orderItem);
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

