package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.adapters.FillingAdapter;
import com.pos.bringit.adapters.ToppingAdapter;
import com.pos.bringit.databinding.FragmentPizzaAssembleBinding;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.utils.Constants;

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
    private ProductItemModel mFatherItem;
    private boolean isFromKitchen;
    private String mPizzaType;
    private int mPosition = -1;

    private List<InnerProductsModel> mToppingTypes = new ArrayList<>();
    private List<InnerProductsModel> mDoughTypes = new ArrayList<>();
    private List<InnerProductsModel> mSpecialTypes = new ArrayList<>();

    private List<CartModel> mCartToppings = new ArrayList<>();

    private Set<Integer> fullPizzaToppings = new HashSet<>();

    private Set<Integer> rhPizzaToppings = new HashSet<>();
    private Set<Integer> lhPizzaToppings = new HashSet<>();

    private Set<Integer> trPizzaToppings = new HashSet<>();
    private Set<Integer> tlPizzaToppings = new HashSet<>();
    private Set<Integer> brPizzaToppings = new HashSet<>();
    private Set<Integer> blPizzaToppings = new HashSet<>();

    private Set<Integer> fullPizzaSpecials = new HashSet<>();
    private Set<Integer> fullPizzaDoughs = new HashSet<>();


    private ToppingAdapter mToppingAdapter;
    private FillingAdapter mSpecialsAdapter;
    private FillingAdapter mDoughAdapter;

    private ToppingAddListener listener;

    public PizzaAssembleFragment() {
    }

    public PizzaAssembleFragment(int position, ProductItemModel fatherItem) {
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
        mPizzaType = mFatherItem.getShape();


        initCategories();
        initPizzaType();

        setListeners();

        fillSelected();

        fillRVs();
//        binding.lPizzaRoundTopping.ivPizzaFull.setSelected(true);

        return binding.getRoot();
    }

    private void initCategories() {
        switch (mFatherItem.getSourceCategories().size()) {
            case 3:
                CategoryModel categorySpecial = mFatherItem.getSourceCategories().get(2);
                mSpecialTypes.addAll(categorySpecial.getProducts());
                binding.gSpecials.setVisibility(View.VISIBLE);
                String titleSpecial = categorySpecial.getName();
                titleSpecial += categorySpecial.getProductsLimit() != 0
                        ? ": limit " + categorySpecial.getProductsLimit() : "";
                binding.tvTitleSpecial.setText(titleSpecial);

                mSpecialsAdapter = new FillingAdapter(mSpecialTypes, categorySpecial.getProductsLimit(), this::addSpecial);
                initSpecialsRV();
            case 2:
                CategoryModel categoryDough = mFatherItem.getSourceCategories().get(1);
                mDoughTypes.addAll(categoryDough.getProducts());
                binding.gDoughs.setVisibility(View.VISIBLE);
                String titleDoughs = categoryDough.getName();
                titleDoughs += categoryDough.getProductsLimit() != 0
                        ? ": limit " + categoryDough.getProductsLimit() : "";
                binding.tvTitleDough.setText(titleDoughs);

                mDoughAdapter = new FillingAdapter(mDoughTypes, categoryDough.getProductsLimit(), this::addDough);
                initDoughRV();
            case 1:
                CategoryModel categoryToppings = mFatherItem.getSourceCategories().get(0);
                mToppingTypes.addAll(categoryToppings.getProducts());
                String titleToppings = categoryToppings.getName();
                titleToppings += categoryToppings.getProductsLimit() != 0
                        ? ": limit " + categoryToppings.getProductsLimit() : "";
                binding.tvTitleTopping.setText(titleToppings);

                mToppingAdapter = new ToppingAdapter(mToppingTypes, categoryToppings.getProductsLimit(), this::addTopping);
                initToppingRV();
        }
    }

    private void initPizzaType() {
        binding.gPizzaTypeCircle.setVisibility(
                mPizzaType.equals(Constants.PIZZA_TYPE_CIRCLE) ? View.VISIBLE : View.GONE);
        binding.gPizzaTypeRect.setVisibility(
                mPizzaType.equals(Constants.PIZZA_TYPE_RECTANGLE) ? View.VISIBLE : View.GONE);
        binding.gPizzaTypeSlice.setVisibility(
                mPizzaType.equals(Constants.PIZZA_TYPE_ONE_SLICE) ? View.VISIBLE : View.GONE);
    }

    private void initToppingRV() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvToppings.setLayoutManager(layoutManager);
        binding.rvToppings.setAdapter(mToppingAdapter);
    }

    private void initSpecialsRV() {
        FlexboxLayoutManager specialLayoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvSpecials.setLayoutManager(specialLayoutManager);
        binding.rvSpecials.setAdapter(mSpecialsAdapter);
    }

    private void initDoughRV() {
        FlexboxLayoutManager doughLayoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvDoughTypes.setLayoutManager(doughLayoutManager);
        binding.rvDoughTypes.setAdapter(mDoughAdapter);

    }

    private void fillRVs() {
        if (!mToppingTypes.isEmpty()) updateSelected(PIZZA_TYPE_FULL, fullPizzaToppings);
        if (!mSpecialTypes.isEmpty()) updateSelectedSpecials(PIZZA_TYPE_FULL, fullPizzaSpecials);
        if (!mDoughTypes.isEmpty()) updateSelectedDoughs(PIZZA_TYPE_FULL, fullPizzaDoughs);
    }

    private void setListeners() {
        binding.lPizzaRoundTopping.ivPizzaFull.setOnClickListener(v -> updateSelected(PIZZA_TYPE_FULL, fullPizzaToppings));
        binding.lPizzaRoundTopping.ivPizzaRh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_RH, rhPizzaToppings));
        binding.lPizzaRoundTopping.ivPizzaLh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_LH, lhPizzaToppings));
        binding.lPizzaRoundTopping.ivPizzaTr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TR, trPizzaToppings));
        binding.lPizzaRoundTopping.ivPizzaTl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TL, tlPizzaToppings));
        binding.lPizzaRoundTopping.ivPizzaBr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BR, brPizzaToppings));
        binding.lPizzaRoundTopping.ivPizzaBl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BL, blPizzaToppings));

        binding.ivPizzaSlice.setOnClickListener(v -> updateSelected(PIZZA_TYPE_FULL, fullPizzaToppings));

        binding.lPizzaRectangleTopping.ivPizzaFull.setOnClickListener(v -> updateSelected(PIZZA_TYPE_FULL, fullPizzaToppings));
        binding.lPizzaRectangleTopping.ivPizzaRh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_RH, rhPizzaToppings));
        binding.lPizzaRectangleTopping.ivPizzaLh.setOnClickListener(v -> updateSelected(PIZZA_TYPE_LH, lhPizzaToppings));
        binding.lPizzaRectangleTopping.ivPizzaTr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TR, trPizzaToppings));
        binding.lPizzaRectangleTopping.ivPizzaTl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_TL, tlPizzaToppings));
        binding.lPizzaRectangleTopping.ivPizzaBr.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BR, brPizzaToppings));
        binding.lPizzaRectangleTopping.ivPizzaBl.setOnClickListener(v -> updateSelected(PIZZA_TYPE_BL, blPizzaToppings));
    }

    private void fillSelected() {
        switch (mFatherItem.getCategories().size()) {
            case 3:
                for (InnerProductsModel item : mFatherItem.getCategories().get(2).getProducts()) {
                    int toppingId = item.getId();
                    fullPizzaSpecials.add(toppingId);
                    setToppingCountSpecial(item.getToppingLocation());
                    return;
                }
            case 2:
                if (!mFatherItem.getCategories().get(1).getProducts().isEmpty()) {
                    int doughId = mFatherItem.getCategories().get(1).getProducts().get(0).getId();
                    fullPizzaDoughs.add(doughId);
                }
            case 1:
                for (InnerProductsModel item : mFatherItem.getCategories().get(0).getProducts()) {
                    int toppingId = item.getId();
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
        mToppingAdapter.updateSelected(type, selectedToppingList);
    }

    private void updateSelectedSpecials(String type, Set<Integer> selectedToppingList) {
//        mSpecialsAdapter.updateSelected(type, selectedToppingList); //todo when edit
    }

    private void updateSelectedDoughs(String type, Set<Integer> selectedToppingList) {
//        mDoughAdapter.updateSelected(type, selectedToppingList); //todo when edit
    }

    private void setToppingCount(String type) {
        String size;
        switch (type) {
            case PIZZA_TYPE_FULL:
                size = fullPizzaToppings.size() != 0 ? String.valueOf(fullPizzaToppings.size()) : "";
                binding.lPizzaRoundTopping.tvNumPizzaFull.setText(size);
                binding.lPizzaRectangleTopping.tvNumPizzaFull.setText(size);
                binding.tvNumPizzaSlice.setText(size);
                break;
            case PIZZA_TYPE_RH:
                size = rhPizzaToppings.size() != 0 ? String.valueOf(rhPizzaToppings.size()) : "";
                binding.lPizzaRoundTopping.tvNumPizzaRh.setText(size);
                binding.lPizzaRectangleTopping.tvNumPizzaRh.setText(size);
                break;
            case PIZZA_TYPE_LH:
                size = lhPizzaToppings.size() != 0 ? String.valueOf(lhPizzaToppings.size()) : "";
                binding.lPizzaRoundTopping.tvNumPizzaLh.setText(size);
                binding.lPizzaRectangleTopping.tvNumPizzaLh.setText(size);
                break;
            case PIZZA_TYPE_TR:
                size = trPizzaToppings.size() != 0 ? String.valueOf(trPizzaToppings.size()) : "";
                binding.lPizzaRoundTopping.tvNumPizzaTr.setText(size);
                binding.lPizzaRectangleTopping.tvNumPizzaTr.setText(size);
                break;
            case PIZZA_TYPE_TL:
                size = tlPizzaToppings.size() != 0 ? String.valueOf(tlPizzaToppings.size()) : "";
                binding.lPizzaRoundTopping.tvNumPizzaTl.setText(size);
                binding.lPizzaRectangleTopping.tvNumPizzaTl.setText(size);
                break;
            case PIZZA_TYPE_BR:
                size = brPizzaToppings.size() != 0 ? String.valueOf(brPizzaToppings.size()) : "";
                binding.lPizzaRoundTopping.tvNumPizzaBr.setText(size);
                binding.lPizzaRectangleTopping.tvNumPizzaBr.setText(size);
                break;
            case PIZZA_TYPE_BL:
                size = blPizzaToppings.size() != 0 ? String.valueOf(blPizzaToppings.size()) : "";
                binding.lPizzaRoundTopping.tvNumPizzaBl.setText(size);
                binding.lPizzaRectangleTopping.tvNumPizzaBl.setText(size);
                break;
        }
    }

    private void setToppingCountSpecial(String type) {
        String size;
        size = fullPizzaSpecials.size() != 0 ? String.valueOf(fullPizzaSpecials.size()) : "";
        binding.tvNumPizzaFullSpecial.setText(size);
        binding.tvNumPizzaFullRectSpecial.setText(size);
        binding.tvNumPizzaSliceSpecial.setText(size);
    }

    private void setSelectionIcons(String type) {
//        square pizza
        binding.lPizzaRoundTopping.ivPizzaFull.setSelected(type.equals(PIZZA_TYPE_FULL));

        binding.lPizzaRoundTopping.ivPizzaRh.setSelected(type.equals(PIZZA_TYPE_RH));
        binding.lPizzaRoundTopping.ivPizzaLh.setSelected(type.equals(PIZZA_TYPE_LH));

        binding.lPizzaRoundTopping.ivPizzaTr.setSelected(type.equals(PIZZA_TYPE_TR));
        binding.lPizzaRoundTopping.ivPizzaTl.setSelected(type.equals(PIZZA_TYPE_TL));
        binding.lPizzaRoundTopping.ivPizzaBr.setSelected(type.equals(PIZZA_TYPE_BR));
        binding.lPizzaRoundTopping.ivPizzaBl.setSelected(type.equals(PIZZA_TYPE_BL));

//        rectangle pizza
        binding.lPizzaRectangleTopping.ivPizzaFull.setSelected(type.equals(PIZZA_TYPE_FULL));

        binding.lPizzaRectangleTopping.ivPizzaRh.setSelected(type.equals(PIZZA_TYPE_RH));
        binding.lPizzaRectangleTopping.ivPizzaLh.setSelected(type.equals(PIZZA_TYPE_LH));

        binding.lPizzaRectangleTopping.ivPizzaTr.setSelected(type.equals(PIZZA_TYPE_TR));
        binding.lPizzaRectangleTopping.ivPizzaTl.setSelected(type.equals(PIZZA_TYPE_TL));
        binding.lPizzaRectangleTopping.ivPizzaBr.setSelected(type.equals(PIZZA_TYPE_BR));
        binding.lPizzaRectangleTopping.ivPizzaBl.setSelected(type.equals(PIZZA_TYPE_BL));

//        one slice pizza
        binding.ivPizzaSlice.setSelected(type.equals(PIZZA_TYPE_FULL));
    }


    private void addTopping(String type, InnerProductsModel toppingItem) {
        int toppingId = toppingItem.getId();
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

    private void addSpecial(InnerProductsModel toppingItem) {
        int toppingId = toppingItem.getId();

        if (fullPizzaSpecials.contains(toppingId)) {
            fullPizzaSpecials.remove(toppingId);
            removeFromCart("full", toppingItem);
        } else {
            addToCart("full", toppingItem);
            fullPizzaSpecials.add(toppingId);
        }
        setToppingCountSpecial("full");
    }

    private void addDough(InnerProductsModel toppingItem) {
        int toppingId = toppingItem.getId();

        if (fullPizzaDoughs.contains(toppingId)) {
            fullPizzaDoughs.remove(toppingId);
            removeFromCart("full", toppingItem);
        } else {
            addToCart("full", toppingItem);
            fullPizzaDoughs.add(toppingId);
        }
    }

    private void addToCart(String type, InnerProductsModel toppingItem) {

        toppingItem.setToppingLocation(type);

        for (CategoryModel category : mFatherItem.getCategories())
            if (toppingItem.getCategoryId().equals(category.getId()))
                category.getProducts().add(toppingItem);


        if (mPosition != -1) ((DealAssembleFragment) getParentFragment()).onToppingAdded(mFatherItem, mPosition);
        else listener.onToppingAdded(mFatherItem.clone(), isFromKitchen);
    }

    private void removeFromCart(String type, InnerProductsModel toppingItem) {

        toppingItem.setToppingLocation(type);

        for (CategoryModel category : mFatherItem.getCategories())
            if (toppingItem.getCategoryId().equals(category.getId()))
                category.getProducts().remove(toppingItem);

        if (mPosition != -1) ((DealAssembleFragment) getParentFragment()).onToppingAdded(mFatherItem, mPosition);
        else listener.onToppingAdded(mFatherItem.clone(), isFromKitchen);
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
        void onToppingAdded(ProductItemModel item, boolean fromKitchen);
    }

}
