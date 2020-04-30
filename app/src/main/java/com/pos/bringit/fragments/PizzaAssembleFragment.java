package com.pos.bringit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.bringit.databinding.FragmentPizzaAssembleBinding;

import androidx.fragment.app.Fragment;

public class PizzaAssembleFragment extends Fragment {

    private FragmentPizzaAssembleBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPizzaAssembleBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

}
