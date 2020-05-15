package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.adapters.ToppingAdapter;
import com.pos.bringit.databinding.FragmentPizzaAssembleBinding;
import com.pos.bringit.models.BusinessItemModel;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.network.Request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BR;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_FULL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_LH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_RH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TR;

public class PizzaAssembleFragment extends Fragment {


    private FragmentPizzaAssembleBinding binding;
    private Context mContext;
    private CartModel mFatherItem;
    private boolean isFromKitchen;
    private int mCartNum;
    private int mPosition = -1;

    private List<CartModel> mCartToppings = new ArrayList<>();

    private Set<Integer> fullPizzaToppings = new HashSet<>();

    private Set<Integer> rhPizzaToppings = new HashSet<>();
    private Set<Integer> lhPizzaToppings = new HashSet<>();

    private Set<Integer> trPizzaToppings = new HashSet<>();
    private Set<Integer> tlPizzaToppings = new HashSet<>();
    private Set<Integer> brPizzaToppings = new HashSet<>();
    private Set<Integer> blPizzaToppings = new HashSet<>();

    private ToppingAdapter mToppingAdapter = new ToppingAdapter(this::addTopping);
    private ToppingAdapter mDoughAdapter = new ToppingAdapter(this::chooseDough);

    private ToppingAddListener listener;

    public PizzaAssembleFragment() {
    }

    public PizzaAssembleFragment(int position, CartModel fatherItem) {
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
        mCartNum = mFatherItem.getPosition() * 1000 + 1000;

        initRV();
        setListeners();

        fillSelected();

        getTopping();
//        binding.ivPizzaFull.performClick();

        return binding.getRoot();
    }

    private void getTopping() {
        binding.ivPizzaFull.setSelected(true);
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
        binding.rvToppings.setLayoutManager(layoutManager);
        binding.rvToppings.setAdapter(mToppingAdapter);

        FlexboxLayoutManager doughLayoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvDoughTypes.setLayoutManager(doughLayoutManager);
        binding.rvDoughTypes.setAdapter(mDoughAdapter);

    }

    private void fillRV(List<BusinessItemModel> newList) {
//        mDoughAdapter.updateList(newList);
        mToppingAdapter.updateList(newList);

        updateSelected(PIZZA_TYPE_FULL, fullPizzaToppings);
    }

    private void setListeners() {
        binding.ivPizzaFull.setOnClickListener(v -> updateSelected(PIZZA_TYPE_FULL, fullPizzaToppings));
        binding.ivPizzaRh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_RH, rhPizzaToppings));
        binding.ivPizzaLh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_LH, lhPizzaToppings));
        binding.ivPizzaTr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TR, trPizzaToppings));
        binding.ivPizzaTl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TL, tlPizzaToppings));
        binding.ivPizzaBr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BR, brPizzaToppings));
        binding.ivPizzaBl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BL, blPizzaToppings));
    }

    private void fillSelected() {
        mCartToppings = mFatherItem.getToppings();
        if (!mFatherItem.getToppings().isEmpty()) {

            mCartNum = mFatherItem.getToppings().get(mFatherItem.getToppings().size() - 1).getPosition() + 1;

            for (CartModel item : mFatherItem.getToppings()) {
                int toppingId = Integer.parseInt(item.getObjectId());
                switch (item.getToppingLocation()) {
                    case PIZZA_TYPE_FULL:
                        fullPizzaToppings.add(toppingId);
                        break;
                    case PIZZA_TYPE_RH:
                        rhPizzaToppings.add(toppingId);
                        break;
                    case PIZZA_TYPE_LH:
                        lhPizzaToppings.add(toppingId);
                        break;
                    case PIZZA_TYPE_TR:
                        trPizzaToppings.add(toppingId);
                        break;
                    case PIZZA_TYPE_TL:
                        tlPizzaToppings.add(toppingId);
                        break;
                    case PIZZA_TYPE_BR:
                        brPizzaToppings.add(toppingId);
                        break;
                    case PIZZA_TYPE_BL:
                        blPizzaToppings.add(toppingId);
                        break;
                }
                setToppingCount(item.getToppingLocation());
            }

        }
    }

    private void updateSelected(String type, Set<Integer> selectedToppingList) {
        setSelectionIcons(type);
        mToppingAdapter.updateSelected(type, selectedToppingList, BusinessModel.getInstance().getToppingList());
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

        binding.ivPizzaTr.setSelected(type.equals(PIZZA_TYPE_TR));
        binding.ivPizzaTl.setSelected(type.equals(PIZZA_TYPE_TL));
        binding.ivPizzaBr.setSelected(type.equals(PIZZA_TYPE_BR));
        binding.ivPizzaBl.setSelected(type.equals(PIZZA_TYPE_BL));
    }


    private void addTopping(String type, BusinessItemModel toppingItem) {
        int toppingId = toppingItem.getObjectId();
        switch (type) {
            case PIZZA_TYPE_FULL:
                if (fullPizzaToppings.contains(toppingId)) {
                    fullPizzaToppings.remove(toppingId);
                    removeFromCart(type, toppingItem);
                } else {
                    addToCart(type, toppingItem);
                    fullPizzaToppings.add(toppingId);
                }
                break;
            case PIZZA_TYPE_RH:
                if (rhPizzaToppings.contains(toppingId)) {
                    rhPizzaToppings.remove(toppingId);
                    removeFromCart(type, toppingItem);
                } else {
                    addToCart(type, toppingItem);
                    rhPizzaToppings.add(toppingId);
                }
                break;
            case PIZZA_TYPE_LH:
                if (lhPizzaToppings.contains(toppingId)) {
                    lhPizzaToppings.remove(toppingId);
                    removeFromCart(type, toppingItem);
                } else {
                    addToCart(type, toppingItem);
                    lhPizzaToppings.add(toppingId);
                }
                break;
            case PIZZA_TYPE_TR:
                if (trPizzaToppings.contains(toppingId)) {
                    trPizzaToppings.remove(toppingId);
                    removeFromCart(type, toppingItem);
                } else {
                    addToCart(type, toppingItem);
                    trPizzaToppings.add(toppingId);
                }
                break;
            case PIZZA_TYPE_TL:
                if (tlPizzaToppings.contains(toppingId)) {
                    tlPizzaToppings.remove(toppingId);
                    removeFromCart(type, toppingItem);
                } else {
                    addToCart(type, toppingItem);
                    tlPizzaToppings.add(toppingId);
                }
                break;
            case PIZZA_TYPE_BR:
                if (brPizzaToppings.contains(toppingId)) {
                    brPizzaToppings.remove(toppingId);
                    removeFromCart(type, toppingItem);
                } else {
                    addToCart(type, toppingItem);
                    brPizzaToppings.add(toppingId);
                }
                break;
            case PIZZA_TYPE_BL:
                if (blPizzaToppings.contains(toppingId)) {
                    blPizzaToppings.remove(toppingId);
                    removeFromCart(type, toppingItem);
                } else {
                    addToCart(type, toppingItem);
                    blPizzaToppings.add(toppingId);
                }
                break;
        }
        if (mPosition != -1) ((DealAssembleFragment) getParentFragment()).isReady(mPosition);
        setToppingCount(type);
    }

    private void addToCart(String type, BusinessItemModel toppingItem) {

        CartModel cartModel = new CartModel(
                toppingItem.getStringId(),
                mCartNum,
                toppingItem.getObjectType(),
                toppingItem.getName(),
                toppingItem.getDefaultPrice(),
                toppingItem.getStringObjectId(),
                mFatherItem.getCartId(),
                type);
        mCartNum++;

        mCartToppings.add(cartModel);
        mFatherItem.setToppings(mCartToppings);

        if (mPosition != -1) ((DealAssembleFragment) getParentFragment()).onToppingAdded(mFatherItem, mPosition);
        else listener.onToppingAdded(mFatherItem.clone(), isFromKitchen);
    }

    private void removeFromCart(String type, BusinessItemModel toppingItem) {

        CartModel cartModel = new CartModel(
                toppingItem.getStringObjectId(),
                type);

        mCartToppings.remove(cartModel);
        mFatherItem.setToppings(mCartToppings);
        if (mPosition != -1) ((DealAssembleFragment) getParentFragment()).onToppingAdded(mFatherItem, mPosition);
        else listener.onToppingAdded(mFatherItem.clone(), isFromKitchen);
    }

    private void chooseDough(String type, BusinessItemModel item) {
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
        void onToppingAdded(CartModel item, boolean fromKitchen);
    }

}
