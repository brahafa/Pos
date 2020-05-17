package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.bringit.adapters.DealAdapter;
import com.pos.bringit.adapters.ViewPagerAdapter;
import com.pos.bringit.databinding.FragmentDealAssembleBinding;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.models.DealInnerModel;
import com.pos.bringit.models.FolderItemModel.DealValuesModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DealAssembleFragment extends Fragment {

    private FragmentDealAssembleBinding binding;
    private Context mContext;

    private CartModel mFatherItem;
    private boolean isFromKitchen;

    private List<DealInnerModel> mDealItems;
    private List<CartModel> mDealInnerItems;

    private DealAdapter mDealAdapter;
    private ViewPagerAdapter mPagerAdapter;

    private DealItemsAddListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDealAssembleBinding.inflate(inflater, container, false);

        mFatherItem = DealAssembleFragmentArgs.fromBundle(getArguments()).getFatherItem().clone();
        isFromKitchen = DealAssembleFragmentArgs.fromBundle(getArguments()).getFromKitchen();

        mDealItems = fillDealItems(mFatherItem.getValueJson());
        mDealItems.get(0).setSelected(true);

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

        List<DealInnerModel> reverseList = new ArrayList<>(mDealItems);
        Collections.reverse(reverseList);

        mDealInnerItems = new ArrayList<>();

        List<CartModel> existingItems = mFatherItem.getDealItems();
        Collections.reverse(existingItems);

        for (int i = 0; i < reverseList.size(); i++) {
            DealInnerModel model = reverseList.get(i);
            int cartPos = mFatherItem.getPosition() * 100 + 100 + i;

            CartModel cartModel;
            if (!existingItems.isEmpty()) cartModel = existingItems.get(i);
            else cartModel = new CartModel(
                    model.getObjectId(),
                    cartPos,
                    model.getObjectType(),
                    model.getName(),
                    0,
                    model.getObjectId(),
                    mFatherItem.getCartId());

            mDealInnerItems.add(cartModel);

            switch (model.getObjectType()) {
                case "Food":
                    mPagerAdapter.addFrag(new PizzaAssembleFragment((reverseList.size() - 1 - i), cartModel));
                    break;
                case "Drink":
                    mPagerAdapter.addFrag(new DrinkFragment((reverseList.size() - 1 - i), cartModel));
                    break;
                case "AdditionalOffer":
                    mPagerAdapter.addFrag(new AdditionalOfferFragment(reverseList.size() - 1 - i));
                    break;
            }
        }

        Collections.reverse(mDealInnerItems);
        mFatherItem.setDealItems(mDealInnerItems);
        listener.onDealItemsAdded(mFatherItem.clone(), isFromKitchen);

        binding.vpFragments.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    private List<DealInnerModel> fillDealItems(DealValuesModel dealValues) {
        List<DealInnerModel> dealItems = new ArrayList<>();
        if (!dealValues.getFood().isEmpty()) {
            for (int i = 0; i < dealValues.getFood().get(0).getQuantity(); i++) {
                dealItems.add(new DealInnerModel("Food", "פיצה " + dealValues.getFood().get(0).getType(), dealValues.getFood().get(0).getType()));
            }
        }
        if (!dealValues.getDrink().isEmpty()) {
            for (int i = 0; i < dealValues.getDrink().get(0).getQuantity(); i++) {
                dealItems.add(new DealInnerModel("Drink", "שתיה"));
            }
        }
        if (!dealValues.getAdditionalOffer().isEmpty()) {
            for (int i = 0; i < dealValues.getAdditionalOffer().get(0).getQuantity(); i++) {
                dealItems.add(new DealInnerModel("AdditionalOffer", dealValues.getAdditionalOffer().get(0).getName(), dealValues.getFood().get(0).getType()));
            }
        }
        return dealItems;
    }


    private void openPage(int position) {
        setCurrentInCart(position);
        binding.vpFragments.setCurrentItem(mPagerAdapter.getCount() - 1 - position);
    }

    private void setCurrentInCart(int position) {
        for (int i = 0; i < mDealInnerItems.size(); i++) {
            mDealInnerItems.get(i).setSelected(i == position);
        }
        mFatherItem.setDealItems(mDealInnerItems);
        listener.onDealItemsAdded(mFatherItem.clone(), isFromKitchen);
    }

    public void isReady(int position) {
        mDealAdapter.markComplete(position);
    }

    public void onToppingAdded(CartModel cartModel, int position) {
        mDealInnerItems.set(position, cartModel);
        mFatherItem.setDealItems(mDealInnerItems);
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
        void onDealItemsAdded(CartModel item, boolean fromKitchen);

    }

}
