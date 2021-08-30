package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvCategoryBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.utils.RtlGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> itemList;
    private AdapterCallback adapterCallback;


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvMandatory;
        private RecyclerView rvToppings;

        ViewHolder(ItemRvCategoryBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvTitleCategory;
            tvMandatory = binding.tvMandatory;
            rvToppings = binding.rvToppings;

        }
    }

    public CategoryAdapter(Context context, AdapterCallback adapterCallback) {
        this.context = context;
        this.itemList = new ArrayList<>();
        this.adapterCallback = adapterCallback;
    }

    public CategoryAdapter(Context context, List<CategoryModel> categories, AdapterCallback adapterCallback) {
        this.context = context;
        this.itemList = categories;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCategoryBinding binding =
                ItemRvCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CategoryModel item = itemList.get(position);

        if (!item.getProducts().isEmpty()) {

            String titleFillings = item.getName();
            titleFillings += item.getProductsLimit() != 0
                    ? ": limit " + item.getProductsLimit() : "";
            holder.tvName.setText(titleFillings);

            holder.tvMandatory.setVisibility(item.isMandatory() ? View.VISIBLE : View.GONE);


            FillingAdapter mFillingAdapter = new FillingAdapter(item, new FillingAdapter.AdapterCallback() {
                @Override
                public void onItemAdded(InnerProductsModel orderItem) {
                    addFilling(orderItem);
                }

                @Override
                public void onItemRemoved(InnerProductsModel orderItem) {
                    removeFilling(orderItem);
                }
            });

            holder.rvToppings.setLayoutManager(new RtlGridLayoutManager(
                    context, item.isMultipleSelection() ? 4 : 5, RecyclerView.VERTICAL, true));
            holder.rvToppings.setAdapter(mFillingAdapter);

        }
    }

    private void addFilling(InnerProductsModel item) {
        adapterCallback.onItemAdded(item);
    }

    private void removeFilling(InnerProductsModel item) {
        adapterCallback.onItemRemoved(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(List<CategoryModel> categories) {
        itemList.clear();
        itemList.addAll(categories);
        notifyDataSetChanged();
    }

    public List<CategoryModel> getItems() {
        return itemList;
    }

    public interface AdapterCallback {
        void onItemAdded(InnerProductsModel item);

        void onItemRemoved(InnerProductsModel item);
    }
}

