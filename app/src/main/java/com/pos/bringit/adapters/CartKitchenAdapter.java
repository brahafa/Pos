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
import com.pos.bringit.databinding.ItemRvKitchenCartBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.DealItemModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DEAL;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DRINK;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_PIZZA;
import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_DELETED;
import static com.pos.bringit.utils.Utils.countProductPrice;

public class CartKitchenAdapter extends RecyclerView.Adapter<CartKitchenAdapter.ViewHolder> {

    private Context context;
    private final String type;
    private List<ProductItemModel> itemList;
    private int selectedPos = 0;
    private AdapterCallback adapterCallback;

    public enum addOrRemove {ADD, REMOVE}

    private ViewHolder lastView = null;

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDelete;
        private TextView tvPrice;
        private TextView tvReturnToOrder;
        private TextView tvName;
        private RecyclerView rvToppings;
        private Group gSelected;

        ViewHolder(ItemRvKitchenCartBinding binding) {
            super(binding.getRoot());
            ivDelete = binding.ivDelete;
            tvReturnToOrder = binding.tvReturnToOrder;
            tvName = binding.tvItemName;
            tvPrice = binding.tvItemPrice;
            rvToppings = binding.rvToppings;
            gSelected = binding.gSelected;
        }
    }

    public CartKitchenAdapter(Context context, String type, AdapterCallback adapterCallback) {
        this.context = context;
        this.type = type;
        this.adapterCallback = adapterCallback;
        this.itemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvKitchenCartBinding binding =
                ItemRvKitchenCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ProductItemModel item = itemList.get(position);

//        holder.itemView.setBackgroundResource(item.getTypeName().equals(BUSINESS_ITEMS_TYPE_DEAL)
//                ? R.drawable.selector_cart_deal_bg
//                : R.drawable.selector_cart_food_bg);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(String.format(Locale.US,"₪ %.2f", countProductPrice(item, type, true)));

        holder.rvToppings.setLayoutManager(new LinearLayoutManager(context));

        if (item.getChangeType().equals(ORDER_CHANGE_TYPE_DELETED)) {
            holder.tvReturnToOrder.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.INVISIBLE);
            holder.ivDelete.setVisibility(View.INVISIBLE);
        } else {
            holder.tvReturnToOrder.setVisibility(View.GONE);
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.ivDelete.setVisibility(View.VISIBLE);
        }
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

        holder.ivDelete.setOnClickListener(
                v -> removeItem(holder.getAdapterPosition()));
        holder.tvPrice.setOnClickListener(
                v -> removeItem(holder.getAdapterPosition()));

        holder.itemView.setOnClickListener(v -> {
            adapterCallback.onItemClick(item);

            selectedPos = position;
            selectItem(holder, true);
        });

        holder.tvReturnToOrder.setOnClickListener(v -> returnToOrder(position));
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
        setCounts();
        diffResult.dispatchUpdatesTo(this);
    }

    private void setCounts() {
        for (ProductItemModel item : itemList) {
            for (CategoryModel category : item.getCategories()) {
                for (InnerProductsModel topping : category.getProducts()) {
                    for (InnerProductsModel topping2 : category.getProducts()) {
                        if (topping.getName().equals(topping2.getName()) &&
                                topping.getPrice() == topping2.getPrice() &&
                                topping.getId() != topping2.getId()) {
                            topping.setCount(topping.getCount() + 1);
                        }
                    }
                }
            }
        }
    }


    private void returnToOrder(int position) {
        itemList.get(position).setChangeType("");
        notifyItemChanged(position);
        adapterCallback.onItemStatusChange(addOrRemove.ADD);
    }

    private void removeItem(int position) {
        itemList.get(position).setChangeType(ORDER_CHANGE_TYPE_DELETED);
        notifyItemChanged(position);
        adapterCallback.onItemStatusChange(addOrRemove.REMOVE);
    }

    public void editItem(ProductItemModel item) {
        itemList.set(selectedPos, item);
        notifyItemChanged(selectedPos);
    }

    public List<ProductItemModel> getClearItems() {
        List<ProductItemModel> clearList = new ArrayList<>(itemList.size());

        for (ProductItemModel item : itemList) clearList.add(item.clone());

        for (ProductItemModel item : clearList) {
            removeEmptyCategories(item);

            for (DealItemModel itemDeal : item.getDealItems()) {
                for (ProductItemModel itemDealProduct : itemDeal.getProducts())
                    removeEmptyCategories(itemDealProduct);
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

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<ProductItemModel> getItems() {
        return itemList;
    }

    public interface AdapterCallback {
        void onItemClick(ProductItemModel fatherItem);

        void onItemStatusChange(addOrRemove addOrRemove);
    }
}

