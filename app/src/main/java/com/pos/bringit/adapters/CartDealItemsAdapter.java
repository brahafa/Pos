package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvCartDealBinding;
import com.pos.bringit.models.DealItemModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_DELETED;

public class CartDealItemsAdapter extends RecyclerView.Adapter<CartDealItemsAdapter.ViewHolder> {
    private Context context;

    private List<DealItemModel> itemList;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private RecyclerView rvToppings;

        ViewHolder(ItemRvCartDealBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvItemName;
            rvToppings = binding.rvToppings;
        }
    }

    public CartDealItemsAdapter(Context context, List<DealItemModel> toppings) {
        this.context = context;
        this.itemList = toppings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCartDealBinding binding =
                ItemRvCartDealBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!itemList.get(position).getProducts().isEmpty()) {

            for (ProductItemModel item : itemList.get(position).getProducts())
                if (!item.getChangeType().equals(ORDER_CHANGE_TYPE_DELETED)) {

                    holder.tvName.setText(item.getName().isEmpty() ? itemList.get(position).getTypeName() : item.getName());
                    holder.itemView.setSelected(itemList.get(position).isSelected());

//        todo change types
//        if (item.getChangeType().equals(ORDER_CHANGE_TYPE_NEW))
//            holder.tvName.setBackgroundResource(R.color.mint_green_light_B0FFAE);


//            holder.rvToppings.setLayoutManager(new FlexboxLayoutManager(context, FlexDirection.ROW_REVERSE));
                    holder.rvToppings.setLayoutManager(new LinearLayoutManager(context));

                    if (item.getTypeName().equals(Constants.BUSINESS_ITEMS_TYPE_PIZZA)) {
                        CartCategoryAdapter mCartCategoryPizzaAdapter = new CartCategoryAdapter(context, item.getCategories(), item.getShape());
                        holder.rvToppings.setAdapter(mCartCategoryPizzaAdapter);
//                CartToppingAdapter mCartToppingAdapter = new CartToppingAdapter(item.getCategories().get(0), item.getShape());//fixme
//                holder.rvToppings.setAdapter(mCartToppingAdapter);
                    } else {
                        CartCategoryAdapter mCartCategoryAdapter = new CartCategoryAdapter(context, item.getCategories());
                        holder.rvToppings.setAdapter(mCartCategoryAdapter);
//                CartFillingAdapter mCartFillingAdapter = new CartFillingAdapter(item.getCategories().get(0)); //fixme
//                holder.rvToppings.setAdapter(mCartFillingAdapter);
                    }

                    break;
                }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

