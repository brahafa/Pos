package com.pos.bringit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.bringit.databinding.FragmentMainBinding;

import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {


    private FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        initListeners();

        return binding.getRoot();
    }

    private void initListeners() {
        binding.llAddTakeAway.setOnClickListener(v -> {
        });
        binding.llAddDelivery.setOnClickListener(v -> {
        });
    }
}
