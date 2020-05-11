package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.adapters.ToppingAdapter;
import com.pos.bringit.databinding.FragmentAdditionalOfferBinding;
import com.pos.bringit.models.BusinessItemModel;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.network.Request;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AdditionalOfferFragment extends Fragment {

    private FragmentAdditionalOfferBinding binding;
    private Context mContext;
    private int mPosition;

    private Set<Integer> fullPizzaToppings = new HashSet<>();

    private ToppingAdapter mFillingAdapter = new ToppingAdapter(this::addFilling);

    public AdditionalOfferFragment(int position) {
        mPosition = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdditionalOfferBinding.inflate(inflater, container, false);

        initRV();

        getTopping();

        return binding.getRoot();
    }

    private void getTopping() {
        if (BusinessModel.getInstance().getToppingList().isEmpty()) {
            Request.getInstance().getToppings(mContext, response -> {
                BusinessModel.getInstance().setToppingList(response.getMessage());
                fillRV(response.getMessage());
            });
        } else {
            fillRV(BusinessModel.getInstance().getToppingList());
        }
    }

    private void initRV() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvFillingTypes.setLayoutManager(layoutManager);
        binding.rvFillingTypes.setAdapter(mFillingAdapter);

    }

    private void fillRV(List<BusinessItemModel> newList) {
        mFillingAdapter.updateList(newList);
    }

    private void updateSelected(String type, Set<Integer> selectedToppingList) {
        mFillingAdapter.updateSelected(type, selectedToppingList, BusinessModel.getInstance().getToppingList());
    }


    private void addFilling(String type, BusinessItemModel toppingItem) {
        int toppingId = toppingItem.getObjectId();

        if (fullPizzaToppings.contains(toppingId)) fullPizzaToppings.remove(toppingId);
        else {
            fullPizzaToppings.add(toppingId);
            ((DealAssembleFragment) getParentFragment()).isReady(mPosition);
        }
//        addToCart(type, String.valueOf(toppingId));
    }

    private void addToCart(String location, String toppingId) {
//        Request.getInstance().addToCart(mContext, new CartModel("Topping", toppingId, /*fatherId*/"", location), isDataSuccess -> {
//        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
