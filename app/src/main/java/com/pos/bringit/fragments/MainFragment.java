package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.pos.bringit.adapters.DeliveryAdapter;
import com.pos.bringit.adapters.TakeAwayAdapter;
import com.pos.bringit.databinding.FragmentMainBinding;
import com.pos.bringit.dialog.PasswordDialog;
import com.pos.bringit.models.OrderModel;
import com.pos.bringit.network.Request;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainFragment extends Fragment {

    private final int REQUEST_REPEAT_INTERVAL = 10 * 1000;

    private FragmentMainBinding binding;

    private TakeAwayAdapter mTakeAwayAdapter = new TakeAwayAdapter(this::openOrderDetails);
    private DeliveryAdapter mDeliveryAdapter = new DeliveryAdapter(this::openOrderDetails);

    private List<OrderModel> takeAwayOrdersOpen = new ArrayList<>();
    private List<OrderModel> deliveryOrdersOpen = new ArrayList<>();
    private List<OrderModel> takeAwayOrdersClosed = new ArrayList<>();
    private List<OrderModel> deliveryOrdersClosed = new ArrayList<>();

    private Context mContext;

    private final Handler mHandler = new Handler();

    private Runnable mRunnable = () -> Request.getInstance().getAllOrders(getActivity(),
            response -> {
                updateRVs(response.getOrders());
                setupBoardUpdates();
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.tlTakeAway.getTabAt(1).select();
        binding.tlDelivery.getTabAt(1).select();

        initRVs();

        initListeners();

        return binding.getRoot();
    }

    private void initListeners() {
        binding.llAddTakeAway.setOnClickListener(v -> {
        });
        binding.llAddDelivery.setOnClickListener(v -> {
        });

        binding.tlTakeAway.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTakeAwayAdapter.updateList(tab.getPosition() == 0 ? takeAwayOrdersClosed : takeAwayOrdersOpen);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.tlDelivery.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mDeliveryAdapter.updateList(tab.getPosition() == 0 ? deliveryOrdersClosed : deliveryOrdersOpen);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initRVs() {
        binding.rvTakeAway.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTakeAway.setAdapter(mTakeAwayAdapter);

        binding.rvDelivery.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDelivery.setAdapter(mDeliveryAdapter);
    }

    private void updateRVs(List<OrderModel> allOrders) {

        takeAwayOrdersOpen.clear();
        deliveryOrdersOpen.clear();
        takeAwayOrdersClosed.clear();
        deliveryOrdersClosed.clear();

        for (OrderModel order : allOrders) {
            if (order.getIsDelivery().equals("0")) {
                if (order.getOrderIsActive().equals("1")) //todo: understand is_active or is_opened to be used
                    takeAwayOrdersOpen.add(order);
                else takeAwayOrdersClosed.add(order);
            } else {
                if (order.getOrderIsActive().equals("1")) //todo: understand is_active or is_opened to be used
                    deliveryOrdersOpen.add(order);
                else deliveryOrdersClosed.add(order);
            }
        }

        mTakeAwayAdapter.updateList(
                binding.tlTakeAway.getSelectedTabPosition() == 0 ? takeAwayOrdersClosed : takeAwayOrdersOpen);
        mDeliveryAdapter.updateList(
                binding.tlDelivery.getSelectedTabPosition() == 0 ? deliveryOrdersClosed : deliveryOrdersOpen);

    }

    private void startBoardUpdates() {
        mRunnable.run();
    }

    private void setupBoardUpdates() {
        mHandler.postDelayed(mRunnable, REQUEST_REPEAT_INTERVAL);
    }

    private void removeBoardUpdates() {
        mHandler.removeCallbacks(mRunnable);
    }


    private void openOrderDetails(int orderId) {
//        todo: Go to OrderDetails Fragment
    }

    public void openPasswordDialog() {
        PasswordDialog passwordDialog = new PasswordDialog(mContext);
        passwordDialog.setCancelable(false);
        passwordDialog.show();

        passwordDialog.setOnDismissListener(dialog -> startBoardUpdates());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openPasswordDialog();
    }


}
