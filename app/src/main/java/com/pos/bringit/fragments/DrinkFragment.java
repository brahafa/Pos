package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.adapters.CategoryAdapter;
import com.pos.bringit.adapters.DrinkAdapter;
import com.pos.bringit.databinding.FragmentDrinkBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.DealItemModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;

import java.util.ArrayList;
import java.util.List;

public class DrinkFragment extends Fragment {

    private FragmentDrinkBinding binding;
    private Context mContext;
    private DealItemModel mFatherItem;
    private int mPosition;

    private List<InnerProductsModel> mFillings = new ArrayList<>();
    private List<ProductItemModel> mProducts = new ArrayList<>();

    private DrinkAdapter mDrinkAdapter = new DrinkAdapter(this::setDrink);
    private CategoryAdapter mCategoryAdapter;

    private ProductItemModel mDrinkItem;

    public DrinkFragment(int position, DealItemModel fatherItem) {
        mFatherItem = fatherItem;
        mPosition = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDrinkBinding.inflate(inflater, container, false);

        mProducts = mFatherItem.getSourceProducts();

        initRV();

        fillRV();

        return binding.getRoot();
    }


    private void initRV() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvDrinks.setLayoutManager(layoutManager);
        binding.rvDrinks.setAdapter(mDrinkAdapter);

        mCategoryAdapter = new CategoryAdapter(mContext, new CategoryAdapter.AdapterCallback() {
            @Override
            public void onItemAdded(InnerProductsModel item) {
                addFilling(item);
            }

            @Override
            public void onItemRemoved(InnerProductsModel item) {
                removeFilling(item);
            }
        });
        binding.rvFillingTypes.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvFillingTypes.setAdapter(mCategoryAdapter);

    }

    private void fillRV() {
        if (!mFatherItem.getProducts().isEmpty()) {
            for (ProductItemModel product : mProducts) {
                product.setSelected(product.getId().equals(mFatherItem.getProducts().get(0).getId()));
            }

//            if (!mFatherItem.getProducts().get(0).getCategories().isEmpty()) {
//                mFillings = mFatherItem.getProducts().get(0).getCategories().get(0).getProducts();
//                for (InnerProductsModel item : mFillings) item.setSelected(true);
//
////                mFillingAdapter.setLimit(mFatherItem.getProducts().get(0).getCategories().get(0).getProductsLimit());
////                mFillingAdapter.updateList(mFillings);
//
//                binding.rvFillingTypes.setVisibility(View.VISIBLE);
//            }
        }

        for (ProductItemModel product : mProducts)
            for (CategoryModel category : product.getCategories()) {
                product.getSourceCategories().add(category.clone());
            }

        mDrinkAdapter.updateList(mProducts);
    }

    private void setDrink(ProductItemModel drinkItem) {

        if (!drinkItem.getCategories().isEmpty()) {
//            drinkItem.getSourceCategories().add(drinkItem.getCategories().get(0).clone());

            for (CategoryModel category : drinkItem.getCategories())
                category.getProducts().clear();

            mCategoryAdapter.updateList(drinkItem.getSourceCategories());
//            mFillings = drinkItem.getSourceCategories().get(0).getProducts();
            for (CategoryModel category : drinkItem.getSourceCategories())
                for (InnerProductsModel item : category.getProducts()) item.setSelected(false);
//            mFillingAdapter.setLimit(drinkItem.getSourceCategories().get(0).getProductsLimit());
//            mFillingAdapter.updateList(mFillings);

        }
        mDrinkItem = drinkItem;

        binding.rvFillingTypes.setVisibility(!drinkItem.getCategories().isEmpty() ? View.VISIBLE : View.GONE);


        ((DealAssembleFragment) getParentFragment()).isReady(mPosition);
        ((DealAssembleFragment) getParentFragment()).onToppingAdded(drinkItem, mPosition);
    }

    private void addFilling(InnerProductsModel fillingItem) {

        for (CategoryModel category : mDrinkItem.getCategories())
            if (fillingItem.getCategoryId().equals(category.getId()))
                category.getProducts().add(fillingItem);

        ((DealAssembleFragment) getParentFragment()).onToppingAdded(mDrinkItem, mPosition);
    }

    private void removeFilling(InnerProductsModel fillingItem) {

        for (CategoryModel category : mDrinkItem.getCategories())
            if (fillingItem.getCategoryId().equals(category.getId()))
                if (category.getProducts().contains(fillingItem)) {
                    category.getProducts().remove(fillingItem);
                    break;
                }

        ((DealAssembleFragment) getParentFragment()).onToppingAdded(mDrinkItem, mPosition);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
