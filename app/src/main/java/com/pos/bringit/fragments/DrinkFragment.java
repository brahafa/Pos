package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.adapters.DrinkAdapter;
import com.pos.bringit.databinding.FragmentDrinkBinding;
import com.pos.bringit.models.BusinessItemModel;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.network.Request;

import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DrinkFragment extends Fragment {

    private FragmentDrinkBinding binding;
    private Context mContext;
    private int mPosition;

    public DrinkFragment(int position) {
        mPosition = position;
    }

    private DrinkAdapter mDrinkAdapter = new DrinkAdapter(this::setDrink);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDrinkBinding.inflate(inflater, container, false);

        initRV();

        getDrinks();

        return binding.getRoot();
    }

    private void getDrinks() {
        if (BusinessModel.getInstance().getToppingList().isEmpty()) {
            Request.getInstance().getDrinks(mContext, response -> {
                BusinessModel.getInstance().setDrinkList(response.getMessage());
                fillRV(response.getMessage());
            });
        } else {
            fillRV(BusinessModel.getInstance().getDrinkList());
        }
    }

    private void initRV() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvDrinks.setLayoutManager(layoutManager);
        binding.rvDrinks.setAdapter(mDrinkAdapter);

    }

    private void fillRV(List<BusinessItemModel> newList) {
        mDrinkAdapter.updateList(newList);
    }

    private void updateSelected(String type, Set<Integer> selectedToppingList) {
//        mDrinkAdapter.updateSelected(type, selectedToppingList, BusinessModel.getInstance().getToppingList());
    }


    private void setDrink(int drinkId) {
        ((DealAssembleFragment) getParentFragment()).isReady(mPosition);
//        addToCart(type, String.valueOf(toppingId));
    }

    private void addToCart(String location, String toppingId) {
        Request.getInstance().addToCart(mContext, new CartModel("Topping", toppingId, /*fatherId*/"", location), isDataSuccess -> {
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
