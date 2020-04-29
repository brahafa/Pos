package com.pos.bringit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.bringit.databinding.FragmentFoldersBinding;

import androidx.fragment.app.Fragment;

public class FoldersFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFoldersBinding binding = FragmentFoldersBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

}
