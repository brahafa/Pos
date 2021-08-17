package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.adapters.DrinkAdapter;
import com.pos.bringit.adapters.PizzaAdapter;
import com.pos.bringit.databinding.FragmentDealPizzaBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.DealItemModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class DealPizzaFragment extends Fragment {

    private FragmentDealPizzaBinding binding;
    private Context mContext;
    private DealItemModel mFatherItem;
    private int mPosition;
    private boolean isFromKitchen;

    private List<InnerProductsModel> mFillings = new ArrayList<>();
    private List<ProductItemModel> mProducts = new ArrayList<>();

    private DrinkAdapter mDrinkAdapter = new DrinkAdapter(this::setDrink);
    private PizzaAdapter mPizzaAdapter;

    private ProductItemModel mDrinkItem;

    public DealPizzaFragment(int position, DealItemModel fatherItem, boolean isFromKitchen) {
        mFatherItem = fatherItem;
        mPosition = position;
        this.isFromKitchen = isFromKitchen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDealPizzaBinding.inflate(inflater, container, false);

        mProducts = mFatherItem.getSourceProducts();

//        ProductItemModel product0 = mFatherItem.getProducts().get(0);

//        if (!product0.getCategories().isEmpty()) {
//            for (CategoryModel category : product0.getCategories())
//                for (CategoryModel categorySource : product0.getSourceCategories())
//                    if (category.getId().equals(categorySource.getId())) {
//                        for (InnerProductsModel item : category.getProducts())
//                            for (InnerProductsModel itemSource : categorySource.getProducts())
//                                if (item.getName().equals(itemSource.getName())) {
//                                    itemSource.setSelected(true);
//                                    itemSource.setCount(item.getCount());
//                                    break;
//                                }
//                        break;
//                    }
//        }

        initRV();

        fillRV();

        return binding.getRoot();
    }


    private void initRV() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext, FlexDirection.ROW_REVERSE);
        binding.rvDrinks.setLayoutManager(layoutManager);
        binding.rvDrinks.setAdapter(mDrinkAdapter);
    }

    private void initRVPizza(ProductItemModel drinkItem) {

        mPizzaAdapter = new PizzaAdapter(mContext, drinkItem, new PizzaAdapter.AdapterCallback() {
            @Override
            public void onItemAdded(String location, InnerProductsModel item) {
                addToCart(location, item);
            }

            @Override
            public void onItemRemoved(String location, InnerProductsModel item) {
                removeFromCart(location, item);
            }
        });
        binding.rvFillingTypes.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvFillingTypes.setAdapter(mPizzaAdapter);

    }

    private void fillRV() {

        for (ProductItemModel product : mProducts)
            for (CategoryModel category : product.getCategories()) {
                product.getSourceCategories().add(category.clone());
            }

//        if (!mFatherItem.getProducts().isEmpty()) {
//
//            mDrinkItem = mFatherItem.getProducts().get(0);
//
//            for (ProductItemModel product : mFatherItem.getProducts()) {
//                if (product.getId().equals(mDrinkItem.getSourceProductId())) {
//                    product.setSelected(true);
////                    product.setCategories(new ArrayList<>(mDrinkItem.getCategories()));
//
//                    if (!product.getCategories().isEmpty()) {
//                        for (CategoryModel category : product.getCategories())
//                            for (CategoryModel categorySource : product.getSourceCategories())
//                                if (category.getId().equals(categorySource.getId())) {
//                                    for (InnerProductsModel item : category.getProducts())
//                                        for (InnerProductsModel itemSource : categorySource.getProducts())
//                                            if (item.getName().equals(itemSource.getName())) {
//                                                itemSource.setSelected(true);
//                                                itemSource.setCount(item.getCount());
//                                                break;
//                                            }
//                                    break;
//                                }
//                        mPizzaAdapter.updateList(product);
//                        binding.rvFillingTypes.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        }


        mDrinkAdapter.updateList(mProducts);
    }

    private void setDrink(ProductItemModel drinkItem) {

        if (isFromKitchen) {
            drinkItem.setChangeType(Constants.ORDER_CHANGE_TYPE_NEW);
            drinkItem.setPrice(0);
        }

        ProductItemModel itemModel;

        itemModel = drinkItem.clone();
        for (CategoryModel category : itemModel.getCategories())
            category.getProducts().clear();

        for (CategoryModel category : drinkItem.getCategories())
            itemModel.getSourceCategories().add(category.clone());

        initRVPizza(itemModel);


//        if (!drinkItem.getCategories().isEmpty()) {
//            drinkItem.getSourceCategories().add(drinkItem.getCategories().get(0).clone());

//            for (CategoryModel category : drinkItem.getCategories())
//                drinkItem.getSourceCategories().add(category.clone());
//
//            for (CategoryModel category : drinkItem.getCategories())
//                category.getProducts().clear();

//            mPizzaAdapter.updateList(drinkItem);
//            for (CategoryModel category : drinkItem.getSourceCategories())
//                for (InnerProductsModel item : category.getProducts()) item.setSelected(false);

//        }
        mDrinkItem = itemModel;

        binding.rvFillingTypes.setVisibility(!itemModel.getSourceCategories().isEmpty() ? View.VISIBLE : View.GONE);


        ((DealAssembleFragment) getParentFragment()).isReady(mPosition);
        ((DealAssembleFragment) getParentFragment()).onToppingAdded(itemModel, mPosition);
    }

    private void addToCart(String location, InnerProductsModel toppingItem) {

        toppingItem.setLocation(location);
        if (isFromKitchen) toppingItem.setChangeType(Constants.ORDER_CHANGE_TYPE_NEW);

        for (CategoryModel category : mDrinkItem.getCategories())
            if (toppingItem.getCategoryId().equals(category.getId())) {
                for (InnerProductsModel item : category.getProducts()) {
                    if (item.getName().equals(toppingItem.getName())) {
                        category.getProducts().remove(item);
                        mPizzaAdapter.selectCurrent(location);
                    }
                }
                category.getProducts().add(toppingItem);
            }

        ((DealAssembleFragment) getParentFragment()).onToppingWithToppingsAdded(mDrinkItem, mPosition);
    }

    private void removeFromCart(String location, InnerProductsModel toppingItem) {

        toppingItem.setLocation(location);

        for (CategoryModel category : mDrinkItem.getCategories())
            if (toppingItem.getCategoryId().equals(category.getId()))
                if (isFromKitchen)
                    for (InnerProductsModel topping : category.getProducts()) {
                        if (topping.getSourceProductId() == toppingItem.getId()) {
                            topping.setChangeType(Constants.ORDER_CHANGE_TYPE_DELETED);
                            break;
                        }
                    }
                else if (category.getProducts().contains(toppingItem)) {
                    category.getProducts().remove(toppingItem);
                    break;
                }

        ((DealAssembleFragment) getParentFragment()).onToppingWithToppingsAdded(mDrinkItem, mPosition);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
