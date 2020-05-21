package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.adapters.DrinkAdapter;
import com.pos.bringit.adapters.FillingAdapter;
import com.pos.bringit.databinding.FragmentDrinkBinding;
import com.pos.bringit.models.BusinessItemModel;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.CartFillingModel;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.models.FillingModel;
import com.pos.bringit.network.Request;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DrinkFragment extends Fragment {

    private FragmentDrinkBinding binding;
    private Context mContext;
    private CartModel mFatherItem;
    private int mPosition;

    private List<CartFillingModel> mFillings = new ArrayList<>();

    private DrinkAdapter mDrinkAdapter = new DrinkAdapter(this::setDrink);
    private FillingAdapter mFillingAdapter = new FillingAdapter(new ArrayList<>(), this::addFilling);

    public DrinkFragment(int position, CartModel fatherItem) {
        mFatherItem = fatherItem;
        mPosition = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDrinkBinding.inflate(inflater, container, false);

        initRV();

        getDrinks();

        return binding.getRoot();
    }

    private void getDrinks() {
        if (BusinessModel.getInstance().getDrinkList().isEmpty()) {
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

        FlexboxLayoutManager layoutFillingManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvFillingTypes.setLayoutManager(layoutFillingManager);
        binding.rvFillingTypes.setAdapter(mFillingAdapter);

    }

    private void fillRV(List<BusinessItemModel> newList) {
        for (BusinessItemModel item : newList) {
            item.setSelected(item.getStringObjectId().equals(mFatherItem.getObjectId()));
        }

        if (mFatherItem.getItem_filling() != null) {
            mFillings = mFatherItem.getItem_filling();
            for (CartFillingModel item : mFillings) item.setSelected(true);
            mFillingAdapter.updateList(mFillings);
            binding.tvTitleFilling.setVisibility(View.VISIBLE);
            binding.rvFillingTypes.setVisibility(View.VISIBLE);
        }

        mDrinkAdapter.updateList(newList);
    }

    private void setDrink(BusinessItemModel drinkItem) {

        mFatherItem.setId(drinkItem.getStringId());
        mFatherItem.setObjectId(drinkItem.getStringObjectId());
        mFatherItem.setName(drinkItem.getName());

        if (drinkItem.getmFilling() != null) {
            List<CartFillingModel> fillingList = new ArrayList<>();
            for (FillingModel itemFilling : drinkItem.getmFilling()) {
                fillingList.add(new CartFillingModel(
                        itemFilling.getName(), "0"));
            }
            mFatherItem.setItem_filling(fillingList);

            mFillings = mFatherItem.getItem_filling();
            for (CartFillingModel item : mFillings) item.setSelected(true);
            mFillingAdapter.updateList(mFillings);

        } else mFatherItem.setItem_filling(null);

        binding.tvTitleFilling.setVisibility(drinkItem.getmFilling() != null ? View.VISIBLE : View.GONE);
        binding.rvFillingTypes.setVisibility(drinkItem.getmFilling() != null ? View.VISIBLE : View.GONE);


        ((DealAssembleFragment) getParentFragment()).isReady(mPosition);
        ((DealAssembleFragment) getParentFragment()).onToppingAdded(mFatherItem, mPosition);
    }

    private void addFilling(CartFillingModel fillingItem) {
        if (mFillings.contains(fillingItem)) mFillings.remove(fillingItem);
        else mFillings.add(fillingItem);

        mFatherItem.setItem_filling(mFillings);
        ((DealAssembleFragment) getParentFragment()).onToppingAdded(mFatherItem, mPosition);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
