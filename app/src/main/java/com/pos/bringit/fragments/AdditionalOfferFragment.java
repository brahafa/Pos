package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pos.bringit.adapters.CategoryAdapter;
import com.pos.bringit.databinding.FragmentAdditionalOfferBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.utils.Constants;

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
        mCategoryAdapter = new CategoryAdapter(mContext, mFatherItem.getSourceCategories(), new CategoryAdapter.AdapterCallback() {
            @Override
            public void onItemAdded(InnerProductsModel item) {
                addFilling(item);
            }

            @Override
            public void onItemRemoved(InnerProductsModel item) {
                removeFilling(item);
            }
        });
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvCategories.setAdapter(mCategoryAdapter);

    }

    private void addFilling(InnerProductsModel fillingItem) {

        boolean changed = false;

        for (CategoryModel category : mFatherItem.getCategories())
            if (fillingItem.getCategoryId().equals(category.getId())) {
                if (isFromKitchen) {
                    for (InnerProductsModel topping : category.getProducts()) {
                        if (topping.getSourceProductId() == fillingItem.getId()) {
                            topping.setChangeType(Constants.ORDER_CHANGE_TYPE_NEW);
                            changed = true;
                            break;
                        }
                    }
                    if (!changed) {
                        fillingItem.setChangeType(Constants.ORDER_CHANGE_TYPE_NEW);
                        fillingItem.setSourceProductId(fillingItem.getStringId());
                        category.getProducts().add(fillingItem);
                    }
                } else {
                    category.getProducts().add(fillingItem);
                }
            }

        listener.onFillingSelected(mFatherItem, isFromKitchen);
    }

    private void removeFilling(InnerProductsModel fillingItem) {

        for (CategoryModel category : mFatherItem.getCategories())
            if (fillingItem.getCategoryId().equals(category.getId())) {
                if (isFromKitchen)
                    for (InnerProductsModel topping : category.getProducts()) {
                        if (topping.getSourceProductId() == fillingItem.getId()) {
                            topping.setChangeType(Constants.ORDER_CHANGE_TYPE_DELETED);
                            break;
                        }
                    }
                else if (category.getProducts().contains(fillingItem)) {
                    category.getProducts().remove(fillingItem);
                    break;
                }
            }

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
