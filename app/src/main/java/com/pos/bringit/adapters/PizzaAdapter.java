package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvPizzaBinding;
import com.pos.bringit.databinding.PizzaRectangleToppingLayoutBinding;
import com.pos.bringit.databinding.PizzaRoundToppingLayoutBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.RtlGridLayoutManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BR;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_FULL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_LH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_RH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TR;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> itemList;
    private List<CategoryModel> itemFilledList;
    private String shape;
    private AdapterCallback adapterCallback;

    private ToppingAdapter mToppingAdapter;

    class ViewHolder extends RecyclerView.ViewHolder {

        private Set<Integer> fullPizzaToppings;
        private Set<Integer> rhPizzaToppings;
        private Set<Integer> lhPizzaToppings;
        private Set<Integer> trPizzaToppings;
        private Set<Integer> tlPizzaToppings;
        private Set<Integer> brPizzaToppings;
        private Set<Integer> blPizzaToppings;

        private TextView tvName;
        private TextView tvMandatory;
        private RecyclerView rvToppings;

        private PizzaRoundToppingLayoutBinding lPizzaRoundTopping;
        private PizzaRectangleToppingLayoutBinding lPizzaRectangleTopping;
        private ImageView ivPizzaSlice;
        private TextView tvNumPizzaSlice;

        ViewHolder(ItemRvPizzaBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvTitleTopping;
            tvMandatory = binding.tvMandatory;
            rvToppings = binding.rvToppings;

            lPizzaRoundTopping = binding.lPizzaRoundTopping;
            lPizzaRectangleTopping = binding.lPizzaRectangleTopping;
            ivPizzaSlice = binding.ivPizzaSlice;
            tvNumPizzaSlice = binding.tvNumPizzaSlice;
        }
    }

    public PizzaAdapter(Context context, ProductItemModel item, AdapterCallback adapterCallback) {
        this.context = context;
        this.itemList = item.getSourceCategories();
        this.itemFilledList = item.getCategories();
        this.shape = item.getShape();
        this.adapterCallback = adapterCallback;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvPizzaBinding binding =
                ItemRvPizzaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.fullPizzaToppings = new HashSet<>();
        holder.rhPizzaToppings = new HashSet<>();
        holder.lhPizzaToppings = new HashSet<>();
        holder.trPizzaToppings = new HashSet<>();
        holder.tlPizzaToppings = new HashSet<>();
        holder.brPizzaToppings = new HashSet<>();
        holder.blPizzaToppings = new HashSet<>();

        CategoryModel item = itemList.get(position);
        CategoryModel itemFilled = itemFilledList.get(position);

        if (!item.getProducts().isEmpty()) {

            String titleFillings = item.getName();
            titleFillings += item.getProductsLimit() != 0
                    ? ": limit " + item.getProductsLimit() : "";
            holder.tvName.setText(titleFillings);

            holder.tvMandatory.setVisibility(item.isMandatory() ? View.VISIBLE : View.GONE);

            initPizzaType(item, holder);
            initRV(item, holder);
            if (item.isToppingDivided()) {
                setListeners(holder);
                fillSelected(itemFilled, holder);
                updateSelected(PIZZA_TYPE_FULL, holder.fullPizzaToppings, holder);
            }
        }
    }

    private void initPizzaType(CategoryModel item, ViewHolder holder) {
        holder.lPizzaRoundTopping.getRoot().setVisibility(
                shape.equals(Constants.PIZZA_TYPE_CIRCLE) && item.isToppingDivided() ? View.VISIBLE : View.GONE);
        holder.lPizzaRectangleTopping.getRoot().setVisibility(
                shape.equals(Constants.PIZZA_TYPE_RECTANGLE) && item.isToppingDivided() ? View.VISIBLE : View.GONE);
        holder.ivPizzaSlice.setVisibility(
                shape.equals(Constants.PIZZA_TYPE_ONE_SLICE) ? View.VISIBLE : View.GONE);
        holder.tvNumPizzaSlice.setVisibility(
                shape.equals(Constants.PIZZA_TYPE_ONE_SLICE) ? View.VISIBLE : View.GONE);
    }

    private void initRV(CategoryModel item, ViewHolder holder) {
        holder.rvToppings.setLayoutManager(new RtlGridLayoutManager(context, 5, RecyclerView.VERTICAL, true));

        if (item.isToppingDivided()) {
            mToppingAdapter = new ToppingAdapter(item.getProducts(), item.getProductsLimit(),
                    (type, orderItem) -> addTopping(type, orderItem, holder));
            holder.rvToppings.setAdapter(mToppingAdapter);
        } else {
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
            holder.rvToppings.setAdapter(mFillingAdapter);
        }
    }

    private void setListeners(ViewHolder holder) {
        holder.lPizzaRoundTopping.ivPizzaFull.setOnClickListener(v -> updateSelected(PIZZA_TYPE_FULL, holder.fullPizzaToppings, holder));
        holder.lPizzaRoundTopping.ivPizzaRh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_RH, holder.rhPizzaToppings, holder));
        holder.lPizzaRoundTopping.ivPizzaLh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_LH, holder.lhPizzaToppings, holder));
        holder.lPizzaRoundTopping.ivPizzaTr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TR, holder.trPizzaToppings, holder));
        holder.lPizzaRoundTopping.ivPizzaTl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TL, holder.tlPizzaToppings, holder));
        holder.lPizzaRoundTopping.ivPizzaBr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BR, holder.brPizzaToppings, holder));
        holder.lPizzaRoundTopping.ivPizzaBl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BL, holder.blPizzaToppings, holder));

        holder.ivPizzaSlice.setOnClickListener(v -> updateSelected(PIZZA_TYPE_FULL, holder.fullPizzaToppings, holder));

        holder.lPizzaRectangleTopping.ivPizzaFull.setOnClickListener(v -> updateSelected(PIZZA_TYPE_FULL, holder.fullPizzaToppings, holder));
        holder.lPizzaRectangleTopping.ivPizzaRh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_RH, holder.rhPizzaToppings, holder));
        holder.lPizzaRectangleTopping.ivPizzaLh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_LH, holder.lhPizzaToppings, holder));
        holder.lPizzaRectangleTopping.ivPizzaTr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TR, holder.trPizzaToppings, holder));
        holder.lPizzaRectangleTopping.ivPizzaTl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TL, holder.tlPizzaToppings, holder));
        holder.lPizzaRectangleTopping.ivPizzaBr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BR, holder.brPizzaToppings, holder));
        holder.lPizzaRectangleTopping.ivPizzaBl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BL, holder.blPizzaToppings, holder));
    }

    private void fillSelected(CategoryModel category, ViewHolder holder) {
        for (InnerProductsModel product : category.getProducts()) {
            int toppingId = product.getSourceProductId() != -1 ? product.getSourceProductId() : product.getId();
            switch (product.getLocation()) {
                case PIZZA_TYPE_FULL:
                    holder.fullPizzaToppings.add(toppingId);
                    break;
                case PIZZA_TYPE_RH:
                    holder.rhPizzaToppings.add(toppingId);
                    break;
                case PIZZA_TYPE_LH:
                    holder.lhPizzaToppings.add(toppingId);
                    break;
                case PIZZA_TYPE_TR:
                    holder.trPizzaToppings.add(toppingId);
                    break;
                case PIZZA_TYPE_TL:
                    holder.tlPizzaToppings.add(toppingId);
                    break;
                case PIZZA_TYPE_BR:
                    holder.brPizzaToppings.add(toppingId);
                    break;
                case PIZZA_TYPE_BL:
                    holder.blPizzaToppings.add(toppingId);
                    break;
            }
            setToppingCount(product.getLocation(), holder);
        }
    }

    private void setToppingCount(String type, ViewHolder holder) {
        String size;
        switch (type) {
            case PIZZA_TYPE_FULL:
                size = holder.fullPizzaToppings.size() != 0 ? String.valueOf(holder.fullPizzaToppings.size()) : "";
                holder.lPizzaRoundTopping.tvNumPizzaFull.setText(size);
                holder.lPizzaRectangleTopping.tvNumPizzaFull.setText(size);
                holder.tvNumPizzaSlice.setText(size);
                break;
            case PIZZA_TYPE_RH:
                size = holder.rhPizzaToppings.size() != 0 ? String.valueOf(holder.rhPizzaToppings.size()) : "";
                holder.lPizzaRoundTopping.tvNumPizzaRh.setText(size);
                holder.lPizzaRectangleTopping.tvNumPizzaRh.setText(size);
                break;
            case PIZZA_TYPE_LH:
                size = holder.lhPizzaToppings.size() != 0 ? String.valueOf(holder.lhPizzaToppings.size()) : "";
                holder.lPizzaRoundTopping.tvNumPizzaLh.setText(size);
                holder.lPizzaRectangleTopping.tvNumPizzaLh.setText(size);
                break;
            case PIZZA_TYPE_TR:
                size = holder.trPizzaToppings.size() != 0 ? String.valueOf(holder.trPizzaToppings.size()) : "";
                holder.lPizzaRoundTopping.tvNumPizzaTr.setText(size);
                holder.lPizzaRectangleTopping.tvNumPizzaTr.setText(size);
                break;
            case PIZZA_TYPE_TL:
                size = holder.tlPizzaToppings.size() != 0 ? String.valueOf(holder.tlPizzaToppings.size()) : "";
                holder.lPizzaRoundTopping.tvNumPizzaTl.setText(size);
                holder.lPizzaRectangleTopping.tvNumPizzaTl.setText(size);
                break;
            case PIZZA_TYPE_BR:
                size = holder.brPizzaToppings.size() != 0 ? String.valueOf(holder.brPizzaToppings.size()) : "";
                holder.lPizzaRoundTopping.tvNumPizzaBr.setText(size);
                holder.lPizzaRectangleTopping.tvNumPizzaBr.setText(size);
                break;
            case PIZZA_TYPE_BL:
                size = holder.blPizzaToppings.size() != 0 ? String.valueOf(holder.blPizzaToppings.size()) : "";
                holder.lPizzaRoundTopping.tvNumPizzaBl.setText(size);
                holder.lPizzaRectangleTopping.tvNumPizzaBl.setText(size);
                break;
        }
    }

    private void updateSelected(String type, Set<Integer> selectedToppingList, ViewHolder holder) {
        setSelectionIcons(type, holder);
        mToppingAdapter.updateSelected(type, selectedToppingList);
    }

    private void setSelectionIcons(String type, ViewHolder holder) {
//        square pizza
        holder.lPizzaRoundTopping.ivPizzaFull.setSelected(type.equals(PIZZA_TYPE_FULL));

        holder.lPizzaRoundTopping.ivPizzaRh.setSelected(type.equals(PIZZA_TYPE_RH));
        holder.lPizzaRoundTopping.ivPizzaLh.setSelected(type.equals(PIZZA_TYPE_LH));

        holder.lPizzaRoundTopping.ivPizzaTr.setSelected(type.equals(PIZZA_TYPE_TR));
        holder.lPizzaRoundTopping.ivPizzaTl.setSelected(type.equals(PIZZA_TYPE_TL));
        holder.lPizzaRoundTopping.ivPizzaBr.setSelected(type.equals(PIZZA_TYPE_BR));
        holder.lPizzaRoundTopping.ivPizzaBl.setSelected(type.equals(PIZZA_TYPE_BL));

//        rectangle pizza
        holder.lPizzaRectangleTopping.ivPizzaFull.setSelected(type.equals(PIZZA_TYPE_FULL));

        holder.lPizzaRectangleTopping.ivPizzaRh.setSelected(type.equals(PIZZA_TYPE_RH));
        holder.lPizzaRectangleTopping.ivPizzaLh.setSelected(type.equals(PIZZA_TYPE_LH));

        holder.lPizzaRectangleTopping.ivPizzaTr.setSelected(type.equals(PIZZA_TYPE_TR));
        holder.lPizzaRectangleTopping.ivPizzaTl.setSelected(type.equals(PIZZA_TYPE_TL));
        holder.lPizzaRectangleTopping.ivPizzaBr.setSelected(type.equals(PIZZA_TYPE_BR));
        holder.lPizzaRectangleTopping.ivPizzaBl.setSelected(type.equals(PIZZA_TYPE_BL));

//        one slice pizza
        holder.ivPizzaSlice.setSelected(type.equals(PIZZA_TYPE_FULL));
    }

    private void addTopping(String location, InnerProductsModel item, ViewHolder holder) {
        int toppingId = item.getId();
        switch (location) {
            case PIZZA_TYPE_FULL:
                if (holder.fullPizzaToppings.contains(toppingId)) {
                    holder.fullPizzaToppings.remove(toppingId);
                    adapterCallback.onItemRemoved(location, item);
                } else {
                    holder.fullPizzaToppings.add(toppingId);
                    adapterCallback.onItemAdded(location, item);
                }
                break;
            case PIZZA_TYPE_RH:
                if (holder.rhPizzaToppings.contains(toppingId)) {
                    holder.rhPizzaToppings.remove(toppingId);
                    adapterCallback.onItemRemoved(location, item);
                } else {
                    holder.rhPizzaToppings.add(toppingId);
                    adapterCallback.onItemAdded(location, item);
                }
                break;
            case PIZZA_TYPE_LH:
                if (holder.lhPizzaToppings.contains(toppingId)) {
                    holder.lhPizzaToppings.remove(toppingId);
                    adapterCallback.onItemRemoved(location, item);
                } else {
                    holder.lhPizzaToppings.add(toppingId);
                    adapterCallback.onItemAdded(location, item);
                }
                break;
            case PIZZA_TYPE_TR:
                if (holder.trPizzaToppings.contains(toppingId)) {
                    holder.trPizzaToppings.remove(toppingId);
                    adapterCallback.onItemRemoved(location, item);
                } else {
                    holder.trPizzaToppings.add(toppingId);
                    adapterCallback.onItemAdded(location, item);
                }
                break;
            case PIZZA_TYPE_TL:
                if (holder.tlPizzaToppings.contains(toppingId)) {
                    holder.tlPizzaToppings.remove(toppingId);
                    adapterCallback.onItemRemoved(location, item);
                } else {
                    holder.tlPizzaToppings.add(toppingId);
                    adapterCallback.onItemAdded(location, item);
                }
                break;
            case PIZZA_TYPE_BR:
                if (holder.brPizzaToppings.contains(toppingId)) {
                    holder.brPizzaToppings.remove(toppingId);
                    adapterCallback.onItemRemoved(location, item);
                } else {
                    holder.brPizzaToppings.add(toppingId);
                    adapterCallback.onItemAdded(location, item);
                }
                break;
            case PIZZA_TYPE_BL:
                if (holder.blPizzaToppings.contains(toppingId)) {
                    holder.blPizzaToppings.remove(toppingId);
                    adapterCallback.onItemRemoved(location, item);
                } else {
                    holder.blPizzaToppings.add(toppingId);
                    adapterCallback.onItemAdded(location, item);
                }
                break;
        }
        setToppingCount(location, holder);
    }

    private void addFilling(InnerProductsModel item) {
        adapterCallback.onItemAdded(PIZZA_TYPE_FULL, item);
    }

    private void removeFilling(InnerProductsModel item) {
        adapterCallback.onItemRemoved(PIZZA_TYPE_FULL, item);
    }

    @Override
    public int getItemCount() {
        return itemFilledList.size();
    }

    public List<CategoryModel> getItems() {
        return itemFilledList;
    }

    public interface AdapterCallback {
        void onItemAdded(String location, InnerProductsModel item);

        void onItemRemoved(String location, InnerProductsModel item);
    }
}

