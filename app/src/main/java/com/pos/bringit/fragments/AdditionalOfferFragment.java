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
import com.pos.bringit.models.CartFillingModel;
import com.pos.bringit.models.CartModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AdditionalOfferFragment extends Fragment {

    private FragmentAdditionalOfferBinding binding;
    private Context mContext;

    private CartModel mFatherItem;
    private boolean isFromKitchen;
    private List<CartFillingModel> mFillings = new ArrayList<>();

    private FillingSelectListener listener;

    private FillingAdapter mFillingAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdditionalOfferBinding.inflate(inflater, container, false);

        mFatherItem = AdditionalOfferFragmentArgs.fromBundle(getArguments()).getFatherItem();
        isFromKitchen = AdditionalOfferFragmentArgs.fromBundle(getArguments()).getFromKitchen();

        mFillings.addAll(mFatherItem.getItem_filling());

        initRV();

        return binding.getRoot();
    }

    private void initRV() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvFillingTypes.setLayoutManager(layoutManager);

        for (CartFillingModel item : mFillings) item.setSelected(true);
        mFillingAdapter = new FillingAdapter(mFillings, this::addFilling);
        binding.rvFillingTypes.setAdapter(mFillingAdapter);

    }

    private void addFilling(CartFillingModel fillingItem) {
        if (mFillings.contains(fillingItem)) mFillings.remove(fillingItem);
        else mFillings.add(fillingItem);

        mFatherItem.setItem_filling(mFillings);
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
        void onFillingSelected(CartModel item, boolean fromKitchen);
    }

}
