package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pos.bringit.adapters.DealAdapter;
import com.pos.bringit.adapters.ViewPagerAdapter;
import com.pos.bringit.databinding.FragmentDealAssembleBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.DealInnerModel;
import com.pos.bringit.models.DealItemModel;
import com.pos.bringit.models.ProductItemModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DRINK;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_PIZZA;
import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_DELETED;

public class DealAssembleFragment extends Fragment {

    private FragmentDealAssembleBinding binding;
    private Context mContext;

    private ProductItemModel mFatherItem;
    private boolean isFromKitchen;

    private List<DealInnerModel> mDealItems;

//    private List<DealItemModel> mDealInnerItems;

    private DealAdapter mDealAdapter;
    private ViewPagerAdapter mPagerAdapter;

    private DealItemsAddListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDealAssembleBinding.inflate(inflater, container, false);

        mFatherItem = DealAssembleFragmentArgs.fromBundle(getArguments()).getFatherItem().clone();
        isFromKitchen = DealAssembleFragmentArgs.fromBundle(getArguments()).getFromKitchen();

        mDealItems = fillDealItems(mFatherItem.getSourceDealItems());
        mDealItems.get(0).setSelected(true);

        if (isFromKitchen) markComplete();
        else
            for (DealInnerModel item : mDealItems)
                if (item.getDealItem().getTypeName().equals(BUSINESS_ITEMS_TYPE_PIZZA))
                    item.setComplete(true);

        initRV();
        initAndFillVP();

        openPage(0);

        return binding.getRoot();
    }

    private void initRV() {
        mDealAdapter = new DealAdapter(mDealItems, this::openPage);
        binding.rvDealItems.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, true));
        binding.rvDealItems.setAdapter(mDealAdapter);
    }

    private void initAndFillVP() {
        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        binding.vpFragments.setAdapter(mPagerAdapter);

        List<DealItemModel> reverseSourceList = new ArrayList<>(mFatherItem.getSourceDealItems());
        Collections.reverse(reverseSourceList);

//        mDealInnerItems = new ArrayList<>();

        List<DealItemModel> existingItems = new ArrayList<>(mFatherItem.getDealItems());
        Collections.reverse(existingItems);

        for (int i = 0; i < existingItems.size(); i++) {
            DealItemModel model = existingItems.get(i);
            DealItemModel sourceModel = reverseSourceList.get(i);

//            mDealInnerItems.add(model);

            switch (model.getTypeName()) {
                case BUSINESS_ITEMS_TYPE_PIZZA:
                    model.setSourceProducts(sourceModel.getProducts());

                    ProductItemModel itemModel;
                    if (!model.getProducts().isEmpty()) {
                        itemModel = model.getProducts().get(0);
                    } else {
                        itemModel = sourceModel.getProducts().get(0).clone();
                        for (CategoryModel category : itemModel.getCategories())
                            category.getProducts().clear();
                    }


                    for (CategoryModel category : sourceModel.getProducts().get(0).getCategories())
                        itemModel.getSourceCategories().add(category.clone());

                    mPagerAdapter.addFrag(new PizzaAssembleFragment((existingItems.size() - 1 - i), itemModel, isFromKitchen));
                    break;
                case BUSINESS_ITEMS_TYPE_DRINK:
                case BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER:
                case BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE:
                    model.setSourceProducts(sourceModel.getSourceProducts());

                    mPagerAdapter.addFrag(new DrinkFragment((existingItems.size() - 1 - i), model, isFromKitchen));

                    break;
            }
        }

//        Collections.reverse(mDealInnerItems);
//        mFatherItem.setDealItems(mDealInnerItems);
        listener.onDealItemsAdded(mFatherItem.clone(), isFromKitchen);

        binding.vpFragments.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    private List<DealInnerModel> fillDealItems(List<DealItemModel> dealValues) {
        List<DealInnerModel> dealItems = new ArrayList<>();
        for (DealItemModel item : dealValues) dealItems.add(new DealInnerModel(item));
        return dealItems;
    }

    private void markComplete() {
        for (DealInnerModel item : mDealItems) item.setComplete(true);
    }

    private void openPage(int position) {
        setCurrentInCart(position);
        binding.vpFragments.setCurrentItem(mPagerAdapter.getCount() - 1 - position);
    }

    private void setCurrentInCart(int position) {
        for (int i = 0; i < mFatherItem.getDealItems().size(); i++) {
            mFatherItem.getDealItems().get(i).setSelected(i == position);
        }
        listener.onDealItemsAdded(mFatherItem.clone(), isFromKitchen);
    }

    public void isReady(int position) {
        mDealAdapter.markComplete(position);
    }

    public void onToppingAdded(ProductItemModel cartModel, int position) {

        cartModel.setProductId(mFatherItem.getId());

        if (isFromKitchen) {
            for (ProductItemModel item : mFatherItem.getDealItems().get(position).getProducts()) {
                item.setChangeType(ORDER_CHANGE_TYPE_DELETED);
            }
        } else {
            mFatherItem.getDealItems().get(position).getProducts().clear();
        }
        mFatherItem.getDealItems().get(position).getProducts().add(cartModel);

        listener.onDealItemsAdded(mFatherItem.clone(), isFromKitchen);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            listener = (DealItemsAddListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DealItemsAddListener");
        }
    }

    public interface DealItemsAddListener {
        void onDealItemsAdded(ProductItemModel item, boolean fromKitchen);

    }

}
