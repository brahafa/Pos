package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.bringit.adapters.CategoryAdapter;
import com.pos.bringit.databinding.FragmentAdditionalOfferBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AdditionalOfferFragment extends Fragment {

    private FragmentAdditionalOfferBinding binding;
    private Context mContext;

    private ProductItemModel mFatherItem;
    private boolean isFromKitchen;

    private FillingSelectListener listener;

    private CategoryAdapter mCategoryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdditionalOfferBinding.inflate(inflater, container, false);

        mFatherItem = AdditionalOfferFragmentArgs.fromBundle(getArguments()).getFatherItem();
        isFromKitchen = AdditionalOfferFragmentArgs.fromBundle(getArguments()).getFromKitchen();


        if (!mFatherItem.getCategories().isEmpty()) {
            for (InnerProductsModel item : mFatherItem.getCategories().get(0).getProducts()) item.setSelected(true);
        }

        initRV();

        return binding.getRoot();
    }

    private void initRV() {
        mCategoryAdapter = new CategoryAdapter(mContext, mFatherItem.getSourceCategories(), this::addFilling);
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvCategories.setAdapter(mCategoryAdapter);

    }

    private void addFilling(InnerProductsModel fillingItem) {

        for (CategoryModel category : mFatherItem.getCategories())
            if (fillingItem.getCategoryId().equals(category.getId()))
                if (category.getProducts().contains(fillingItem)) category.getProducts().remove(fillingItem);
                else category.getProducts().add(fillingItem);

        listener.onFillingSelected(mFatherItem, isFromKitchen);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            listener = (FillingSelectListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FillingSelectListener");
        }
    }

    public interface FillingSelectListener {
        void onFillingSelected(ProductItemModel item, boolean fromKitchen);
    }

}
