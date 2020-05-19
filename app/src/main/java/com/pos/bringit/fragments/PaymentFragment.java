package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.bringit.adapters.DrinkAdapter;
import com.pos.bringit.databinding.FragmentPaymentBinding;
import com.pos.bringit.utils.PriceCountKeyboardView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;
    private Context mContext;


    private DrinkAdapter mDrinkAdapter = new DrinkAdapter(item -> {
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);

        initRV();
        initListeners();

        return binding.getRoot();
    }


    private void initRV() {
        binding.rvPayments.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvPayments.setAdapter(mDrinkAdapter);

    }

    private void initListeners() {
        binding.priceCountKeyboardView.keyListener(new PriceCountKeyboardView.KeyPressListener() {
            @Override
            public void onKeyPress(String keyTxt) {

            }

            @Override
            public void onPricePress(int keyValue) {

            }
        });

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
