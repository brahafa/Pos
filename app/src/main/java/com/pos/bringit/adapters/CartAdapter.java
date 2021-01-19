package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvCartBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.DealItemModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;

import java.util.ArrayList;
import java.util.List;

import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DEAL;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DRINK;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_PIZZA;
import static com.pos.bringit.utils.Utils.countProductPrice;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private final String type;
    private List<ProductItemModel> itemList;
    private AdapterCallback adapterCallback;
    private int selectedPos = 0;

    private ViewHolder lastView = null;

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDelete;
        private TextView tvPrice;
        private TextView tvName;
        private ImageView ivDuplicate;
        private RecyclerView rvToppings;
        private TextView tvComment;
        private Group gSelected;

        ViewHolder(ItemRvCartBinding binding) {
            super(binding.getRoot());
            ivDelete = binding.ivDelete;
            ivDuplicate = binding.ivDuplicate;
            tvName = binding.tvItemName;
            tvPrice = binding.tvItemPrice;
            rvToppings = binding.rvToppings;
            tvComment = binding.tvComment;
            gSelected = binding.gSelected;
        }
    }

    public CartAdapter(Context context, String type, AdapterCallback adapterCallback) {
        this.context = context;
        this.type = type;
        this.itemList = new ArrayList<>();
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCartBinding binding =
                ItemRvCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ProductItemModel item = itemList.get(position);

        holder.itemView.setBackgroundResource(item.getTypeName().equals(BUSINESS_ITEMS_TYPE_DEAL)
                ? R.drawable.selector_cart_deal_bg
                : R.drawable.selector_cart_food_bg);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(String.format("₪ %s", countProductPrice(item, type, false)));

        holder.rvToppings.setLayoutManager(new LinearLayoutManager(context));

        switch (item.getTypeName()) {
            case BUSINESS_ITEMS_TYPE_DEAL:
                CartDealItemsAdapter mCartDealItemsAdapter = new CartDealItemsAdapter(context, item.getDealItems());
                holder.rvToppings.setAdapter(mCartDealItemsAdapter);
                break;
            case BUSINESS_ITEMS_TYPE_PIZZA:
                CartCategoryAdapter mCartCategoryPizzaAdapter = new CartCategoryAdapter(context, item.getCategories(), item.getShape());
                holder.rvToppings.setAdapter(mCartCategoryPizzaAdapter);
                break;
            case BUSINESS_ITEMS_TYPE_DRINK:
            case BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER:
            case BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE:
            default:
                CartCategoryAdapter mCartCategoryAdapter = new CartCategoryAdapter(context, item.getCategories());
                holder.rvToppings.setAdapter(mCartCategoryAdapter);
                break;
        }

        selectItem(holder, selectedPos == position);


        holder.ivDuplicate.setOnClickListener(v -> duplicateItem(item));
        holder.ivDelete.setOnClickListener(
                v -> removeItem(holder.getAdapterPosition(), selectedPos == holder.getAdapterPosition()));
        holder.itemView.setOnClickListener(v -> {
            adapterCallback.onItemClick(item);

            selectedPos = position;
            selectItem(holder, true);

        });
    }

    private void selectItem(ViewHolder holder, boolean isSelected) {
        holder.itemView.setSelected(isSelected);
        holder.gSelected.setVisibility(isSelected ? View.VISIBLE : View.GONE);

        if (lastView != null && lastView != holder) selectItem(lastView, false);
        lastView = holder;
    }

    public void updateList(List<ProductItemModel> newList) {
        CartItemsDiffCallback diffCallback = new CartItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addItem(ProductItemModel item) {
        itemList.add(item);
        notifyItemInserted(getItemCount() - 1);
        selectedPos = getItemCount() - 1;
    }

    public void duplicateItem(ProductItemModel duplicateItem) {
        ProductItemModel item = duplicateItem.clone();

        itemList.add(item);
        notifyItemInserted(getItemCount() - 1);
        selectedPos = getItemCount() - 1;
        adapterCallback.onItemDuplicated();
    }

    public void editItem(ProductItemModel item) {
        itemList.set(selectedPos, item);
        notifyItemChanged(selectedPos);
    }

    private void removeItem(int position, boolean isActive) {
        itemList.remove(position);
        notifyItemRemoved(position);
        if (position < selectedPos) selectedPos--;
        adapterCallback.onActiveItemRemoved(isActive);
    }

    public void emptyCart() {
        itemList.clear();
        notifyDataSetChanged();
        adapterCallback.onActiveItemRemoved(true);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<ProductItemModel> getItems() {
        return itemList;
    }

    public List<ProductItemModel> getClearItems() {
        List<ProductItemModel> clearList = new ArrayList<>(itemList);

        for (ProductItemModel item : clearList) {
            removeEmptyCategories(item);

            for (DealItemModel itemDeal : item.getDealItems()) {
                for (ProductItemModel itemDealProduct : itemDeal.getProducts())
                    removeEmptyCategories(itemDealProduct);
            }

//            set fixed prices
            for (CategoryModel category : item.getCategories())
                if (category.getCategoryHasFixedPrice()) {
                    for (InnerProductsModel filling : category.getProducts()) {
                        int i = category.getProducts().indexOf(filling);
                        if (i < category.getProductsFixedPrice()) {
                            InnerProductsModel fixedFillingItem = filling.clone();
                            fixedFillingItem.setPrice((int) category.getFixedPrice());
                            category.getProducts().set(i, fixedFillingItem);
                        }
                    }
                }
        }

        return clearList;
    }

    private void removeEmptyCategories(ProductItemModel product) {
        List<CategoryModel> categories = product.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            CategoryModel category = categories.get(i);
            if (category.getProducts().isEmpty())
                product.getCategories().remove(category);
            else if (category.isToppingDivided()) {
                for (int j = 0; j < category.getProducts().size(); j++) {
                    category.getProducts().get(j).setPrice(category.getProducts().get(j).getPriceForLayer());
                }
            } else if (category.getCategoryHasFixedPrice()) {
                for (int j = 0; j < category.getProducts().size(); j++) {
                    if (category.getProducts().get(j).isIsPriceFixedOnTheCart()) {
                        category.getProducts().get(j).setPrice(0);
                    }
                }
            }
        }
    }

    public interface AdapterCallback {
        void onItemClick(ProductItemModel fatherItem);

        void onItemDuplicated();

        void onActiveItemRemoved(boolean isActive);
    }

}

