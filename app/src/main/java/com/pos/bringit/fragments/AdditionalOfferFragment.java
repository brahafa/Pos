package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.adapters.FillingAdapter;
import com.pos.bringit.databinding.FragmentAdditionalOfferBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AdditionalOfferFragment extends Fragment {

    private FragmentAdditionalOfferBinding binding;
    private Context mContext;

    private ProductItemModel mFatherItem;
    private boolean isFromKitchen;
    private List<InnerProductsModel> mFillings = new ArrayList<>();

    private FillingSelectListener listener;

    private FillingAdapter mFillingAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdditionalOfferBinding.inflate(inflater, container, false);

        mFatherItem = AdditionalOfferFragmentArgs.fromBundle(getArguments()).getFatherItem();
        isFromKitchen = AdditionalOfferFragmentArgs.fromBundle(getArguments()).getFromKitchen();

        binding.tvTitleFilling.setText(mFatherItem.getSourceCategories().get(0).getName());

        mFillings.addAll(mFatherItem.getSourceCategories().get(0).getProducts());

        if (!mFatherItem.getCategories().isEmpty()) {
            for (InnerProductsModel item : mFatherItem.getCategories().get(0).getProducts()) item.setSelected(true);
        }

        initRV();

        return binding.getRoot();
    }

    private void initRV() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvFillingTypes.setLayoutManager(layoutManager);

        mFillingAdapter = new FillingAdapter(mFillings, this::addFilling);
        binding.rvFillingTypes.setAdapter(mFillingAdapter);

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
