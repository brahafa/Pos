package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pos.bringit.adapters.PizzaAdapter;
import com.pos.bringit.databinding.FragmentPizzaAssembleBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;

public class PizzaAssembleFragment extends Fragment {

    private FragmentPizzaAssembleBinding binding;
    private Context mContext;
    private ProductItemModel mFatherItem;
    private boolean isFromKitchen;
    private int mPosition = -1;

    private ToppingAddListener listener;

    private PizzaAdapter mPizzaAdapter;


    public PizzaAssembleFragment() {
    }

    public PizzaAssembleFragment(int position, ProductItemModel fatherItem) {
        mPosition = position;
        Bundle args = new Bundle();
        args.putParcelable("father_item", fatherItem);
        this.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPizzaAssembleBinding.inflate(inflater, container, false);

        mFatherItem = PizzaAssembleFragmentArgs.fromBundle(getArguments()).getFatherItem().clone();
        isFromKitchen = PizzaAssembleFragmentArgs.fromBundle(getArguments()).getFromKitchen();

        if (!mFatherItem.getCategories().isEmpty()) {
            for (CategoryModel category : mFatherItem.getCategories())
                for (CategoryModel categorySource : mFatherItem.getSourceCategories())
                    if (category.getId().equals(categorySource.getId())) {
                        for (InnerProductsModel item : category.getProducts())
                            for (InnerProductsModel itemSource : categorySource.getProducts())
                                if (item.getName().equals(itemSource.getName())) {
                                    itemSource.setSelected(true);
                                    itemSource.setCount(item.getCount());
                                    break;
                                }
                        break;
                    }
        }

        initRV();

        return binding.getRoot();
    }

    private void initRV() {
        mPizzaAdapter = new PizzaAdapter(mContext, mFatherItem, new PizzaAdapter.AdapterCallback() {
            @Override
            public void onItemAdded(String location, InnerProductsModel item) {
                addToCart(location, item);
            }

            @Override
            public void onItemRemoved(String location, InnerProductsModel item) {
                removeFromCart(location, item);
            }
        });
        binding.rvPizzas.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvPizzas.setAdapter(mPizzaAdapter);

    }

    private void addToCart(String location, InnerProductsModel toppingItem) {

        toppingItem.setLocation(location);

        for (CategoryModel category : mFatherItem.getCategories())
            if (toppingItem.getCategoryId().equals(category.getId()))
                category.getProducts().add(toppingItem);

        if (mPosition != -1) ((DealAssembleFragment) getParentFragment()).onToppingAdded(mFatherItem, mPosition);
        else listener.onToppingAdded(mFatherItem.clone(), isFromKitchen);
    }

    private void removeFromCart(String location, InnerProductsModel toppingItem) {

        toppingItem.setLocation(location);

        for (CategoryModel category : mFatherItem.getCategories())
            if (toppingItem.getCategoryId().equals(category.getId()))
                if (category.getProducts().contains(toppingItem)) {
                    category.getProducts().remove(toppingItem);
                    break;
                }

        if (mPosition != -1) ((DealAssembleFragment) getParentFragment()).onToppingAdded(mFatherItem, mPosition);
        else listener.onToppingAdded(mFatherItem.clone(), isFromKitchen);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            listener = (ToppingAddListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ToppingAddListener");
        }
    }

    public interface ToppingAddListener {
        void onToppingAdded(ProductItemModel item, boolean fromKitchen);
    }

}
