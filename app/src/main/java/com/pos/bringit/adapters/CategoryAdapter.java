package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.databinding.ItemRvCategoryBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> itemList;
    private AdapterCallback adapterCallback;


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private RecyclerView rvToppings;

        ViewHolder(ItemRvCategoryBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvTitleCategory;
            rvToppings = binding.rvToppings;

        }
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


            FillingAdapter mFillingAdapter = new FillingAdapter(item.getProducts(), item.getProductsLimit(), this::addFilling);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context, FlexDirection.ROW_REVERSE);
            holder.rvToppings.setLayoutManager(layoutManager);
            holder.rvToppings.setAdapter(mFillingAdapter);

        }
    }

    private void addFilling(InnerProductsModel item) {
        adapterCallback.onItemSelected(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<CategoryModel> getItems() {
        return itemList;
    }

    public interface AdapterCallback {
        void onItemSelected(InnerProductsModel item);
    }
}

