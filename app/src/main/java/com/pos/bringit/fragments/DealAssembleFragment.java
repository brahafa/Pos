package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.bringit.adapters.DealAdapter;
import com.pos.bringit.adapters.ViewPagerAdapter;
import com.pos.bringit.databinding.FragmentDealAssembleBinding;
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

    private String fatherId;
    private List<DealInnerModel> dealItems;

    private DealAdapter mDealAdapter;
    private ViewPagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDealAssembleBinding.inflate(inflater, container, false);

        fatherId = DealAssembleFragmentArgs.fromBundle(getArguments()).getFatherId();
        DealValuesModel dealValues = DealAssembleFragmentArgs.fromBundle(getArguments()).getDealValues();

        dealItems = fillDealItems(dealValues);
        dealItems.get(0).setSelected(true);

        initRV();
        initAndFillVP();

        openPage("", 0);

        return binding.getRoot();
    }

    private void initRV() {
        mDealAdapter = new DealAdapter(dealItems, this::openPage);
        binding.rvDealItems.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, true));
        binding.rvDealItems.setAdapter(mDealAdapter);
    }

    private void initAndFillVP() {
        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        binding.vpFragments.setAdapter(mPagerAdapter);

        List<DealInnerModel> reverseList = new ArrayList<>(dealItems);
        Collections.reverse(reverseList);

        for (int i = 0; i < reverseList.size(); i++) {
            DealInnerModel model = reverseList.get(i);
            switch (model.getObjectType()) {
                case "Food":
                    mPagerAdapter.addFrag(new PizzaAssembleFragment(reverseList.size() - 1 - i));
                    break;
                case "Drink":
                    mPagerAdapter.addFrag(new DrinkFragment(reverseList.size() - 1 - i));
                    break;
                case "AdditionalOffer":
                    mPagerAdapter.addFrag(new AdditionalOfferFragment(reverseList.size() - 1 - i));
                    break;
            }
        }
        binding.vpFragments.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    private List<DealInnerModel> fillDealItems(DealValuesModel dealValues) {
        List<DealInnerModel> dealItems = new ArrayList<>();
        if (!dealValues.getFood().isEmpty()) {
            for (int i = 0; i < dealValues.getFood().get(0).getQuantity(); i++) {
                dealItems.add(new DealInnerModel("Food", "פיצה " + dealValues.getFood().get(0).getType()));
            }
        }
        if (!dealValues.getDrink().isEmpty()) {
            for (int i = 0; i < dealValues.getDrink().get(0).getQuantity(); i++) {
                dealItems.add(new DealInnerModel("Drink", "שתיה"));
            }
        }
        if (!dealValues.getAdditionalOffer().isEmpty()) {
            for (int i = 0; i < dealValues.getAdditionalOffer().get(0).getQuantity(); i++) {
                dealItems.add(new DealInnerModel("AdditionalOffer", dealValues.getAdditionalOffer().get(0).getName()));
            }
        }
        return dealItems;
    }


    private void openPage(String type, int position) {
        binding.vpFragments.setCurrentItem(mPagerAdapter.getCount() - 1 - position);
    }

    public void isReady(int position) {
        mDealAdapter.markComplete(position);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
