package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.pos.bringit.R;
import com.pos.bringit.adapters.ToppingAdapter;
import com.pos.bringit.databinding.FragmentPizzaAssembleBinding;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.models.ToppingModel;
import com.pos.bringit.network.Request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PizzaAssembleFragment extends Fragment {

    private final String PIZZA_TYPE_FULL = "full";
    private final String PIZZA_TYPE_RH = "rightHalfPizza";
    private final String PIZZA_TYPE_LH = "leftHalfPizza";
    private final String PIZZA_TYPE_TL = "tl";
    private final String PIZZA_TYPE_TR = "tr";
    private final String PIZZA_TYPE_BL = "bl";
    private final String PIZZA_TYPE_BR = "br";

    private FragmentPizzaAssembleBinding binding;
    private Context mContext;
    private String fatherId;
    private List<ToppingModel> mToppingList = new ArrayList<>();

    private Set<Integer> fullPizzaToppings = new HashSet<>();

    private Set<Integer> rhPizzaToppings = new HashSet<>();
    private Set<Integer> lhPizzaToppings = new HashSet<>();

    private Set<Integer> trPizzaToppings = new HashSet<>();
    private Set<Integer> tlPizzaToppings = new HashSet<>();
    private Set<Integer> brPizzaToppings = new HashSet<>();
    private Set<Integer> blPizzaToppings = new HashSet<>();

    private ToppingAdapter mToppingAdapter = new ToppingAdapter(this::addTopping);
    private ToppingAdapter mDoughAdapter = new ToppingAdapter(this::chooseDough);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPizzaAssembleBinding.inflate(inflater, container, false);

        fatherId = PizzaAssembleFragmentArgs.fromBundle(getArguments()).getFatherId();

        initRV();
        setListeners();
        getTopping();

        return binding.getRoot();
    }

    private void getTopping() {
        Request.getInstance().getToppings(mContext, response -> {
            mToppingList = response.getMessage();
            fillRV(response.getMessage());
            binding.ivPizzaFull.setSelected(true);
        });
    }

    private void initRV() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_END);
        binding.rvToppings.setLayoutManager(layoutManager);
        binding.rvToppings.setAdapter(mToppingAdapter);

        FlexboxLayoutManager doughLayoutManager = new FlexboxLayoutManager(mContext);
        doughLayoutManager.setFlexDirection(FlexDirection.ROW);
        doughLayoutManager.setJustifyContent(JustifyContent.FLEX_END);
        binding.rvDoughTypes.setLayoutManager(doughLayoutManager);
        binding.rvDoughTypes.setAdapter(mDoughAdapter);

    }

    private void fillRV(List<ToppingModel> newList) {
//        mDoughAdapter.updateList(newList);
        mToppingAdapter.updateList(newList);
    }

    private void setListeners() {
        binding.ivPizzaFull.setOnClickListener(v -> updateSelected(PIZZA_TYPE_FULL, fullPizzaToppings));
        binding.ivPizzaRh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_RH, rhPizzaToppings));
        binding.ivPizzaLh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_LH, lhPizzaToppings));
        binding.vPizzaTr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TR, trPizzaToppings));
        binding.vPizzaTl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TL, tlPizzaToppings));
        binding.vPizzaBr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BR, brPizzaToppings));
        binding.vPizzaBl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BL, blPizzaToppings));
    }

    private void updateSelected(String type, Set<Integer> selectedToppingList) {
        setSelectionIcons(type);
        mToppingAdapter.updateSelected(type, selectedToppingList, mToppingList);
    }

    private void setToppingCount(String type) {
        String size;
        switch (type) {
            case PIZZA_TYPE_FULL:
                size = fullPizzaToppings.size() != 0 ? String.valueOf(fullPizzaToppings.size()) : "";
                binding.tvNumPizzaFull.setText(size);
                break;
            case PIZZA_TYPE_RH:
                size = rhPizzaToppings.size() != 0 ? String.valueOf(rhPizzaToppings.size()) : "";
                binding.tvNumPizzaRh.setText(size);
                break;
            case PIZZA_TYPE_LH:
                size = lhPizzaToppings.size() != 0 ? String.valueOf(lhPizzaToppings.size()) : "";
                binding.tvNumPizzaLh.setText(size);
                break;
            case PIZZA_TYPE_TR:
                size = trPizzaToppings.size() != 0 ? String.valueOf(trPizzaToppings.size()) : "";
                binding.tvNumPizzaTr.setText(size);
                break;
            case PIZZA_TYPE_TL:
                size = tlPizzaToppings.size() != 0 ? String.valueOf(tlPizzaToppings.size()) : "";
                binding.tvNumPizzaTl.setText(size);
                break;
            case PIZZA_TYPE_BR:
                size = brPizzaToppings.size() != 0 ? String.valueOf(brPizzaToppings.size()) : "";
                binding.tvNumPizzaBr.setText(size);
                break;
            case PIZZA_TYPE_BL:
                size = blPizzaToppings.size() != 0 ? String.valueOf(blPizzaToppings.size()) : "";
                binding.tvNumPizzaBl.setText(size);
                break;
        }
    }

    private void setSelectionIcons(String type) {
        binding.ivPizzaFull.setSelected(type.equals(PIZZA_TYPE_FULL));

        binding.ivPizzaRh.setSelected(type.equals(PIZZA_TYPE_RH));
        binding.ivPizzaLh.setSelected(type.equals(PIZZA_TYPE_LH));

        switch (type) {
            case PIZZA_TYPE_TR:
                binding.ivPizzaQuaters.setImageResource(R.drawable.ic_pizza_tr_active);
                break;
            case PIZZA_TYPE_TL:
                binding.ivPizzaQuaters.setImageResource(R.drawable.ic_pizza_tl_active);
                break;
            case PIZZA_TYPE_BR:
                binding.ivPizzaQuaters.setImageResource(R.drawable.ic_pizza_br_active);
                break;
            case PIZZA_TYPE_BL:
                binding.ivPizzaQuaters.setImageResource(R.drawable.ic_pizza_bl_active);
                break;
            default:
                binding.ivPizzaQuaters.setImageResource(R.drawable.ic_pizza_quaters_inactive);
        }
    }


    private void addTopping(String type, int toppingId) {
        switch (type) {
            case PIZZA_TYPE_FULL:
                if (fullPizzaToppings.contains(toppingId)) fullPizzaToppings.remove(toppingId);
                else fullPizzaToppings.add(toppingId);
                break;
            case PIZZA_TYPE_RH:
                if (rhPizzaToppings.contains(toppingId)) rhPizzaToppings.remove(toppingId);
                else rhPizzaToppings.add(toppingId);
                break;
            case PIZZA_TYPE_LH:
                if (lhPizzaToppings.contains(toppingId)) lhPizzaToppings.remove(toppingId);
                else lhPizzaToppings.add(toppingId);
                break;
            case PIZZA_TYPE_TR:
                if (trPizzaToppings.contains(toppingId)) trPizzaToppings.remove(toppingId);
                else trPizzaToppings.add(toppingId);
                break;
            case PIZZA_TYPE_TL:
                if (tlPizzaToppings.contains(toppingId)) tlPizzaToppings.remove(toppingId);
                else tlPizzaToppings.add(toppingId);
                break;
            case PIZZA_TYPE_BR:
                if (brPizzaToppings.contains(toppingId)) brPizzaToppings.remove(toppingId);
                else brPizzaToppings.add(toppingId);
                break;
            case PIZZA_TYPE_BL:
                if (blPizzaToppings.contains(toppingId)) blPizzaToppings.remove(toppingId);
                else blPizzaToppings.add(toppingId);
                break;
        }
        setToppingCount(type);
        addToCart(type, String.valueOf(toppingId));
    }

    private void addToCart(String location, String toppingId) {
        Request.getInstance().addToCart(mContext, new CartModel("Topping", toppingId, fatherId, location), isDataSuccess -> {
        });

    }

    private void chooseDough(String s, int i) {
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
