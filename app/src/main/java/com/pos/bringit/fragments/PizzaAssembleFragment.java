package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.adapters.CategoryAdapter;
import com.pos.bringit.adapters.FillingAdapter;
import com.pos.bringit.adapters.PizzaAdapter;
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
import androidx.recyclerview.widget.LinearLayoutManager;

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

    private PizzaAdapter mPizzaAdapter;


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

        initRV();

        return binding.getRoot();
    }

    private void initRV() {
        mPizzaAdapter = new PizzaAdapter(mContext, mFatherItem, this::addToCart);
        binding.rvPizzas.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvPizzas.setAdapter(mPizzaAdapter);

    }

    private void addToCart(String location, InnerProductsModel toppingItem) {

        toppingItem.setLocation(location);

        for (CategoryModel category : mFatherItem.getCategories())
            if (toppingItem.getCategoryId().equals(category.getId()))
                if (category.getProducts().contains(toppingItem)) category.getProducts().remove(toppingItem);
                else category.getProducts().add(toppingItem);

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
