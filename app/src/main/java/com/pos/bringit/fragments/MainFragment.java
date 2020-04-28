package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.pos.bringit.R;
import com.pos.bringit.adapters.DeliveryAdapter;
import com.pos.bringit.adapters.TakeAwayAdapter;
import com.pos.bringit.databinding.FragmentMainBinding;
import com.pos.bringit.databinding.ItemTableBigHorizontalBinding;
import com.pos.bringit.databinding.ItemTableBigVerticalBinding;
import com.pos.bringit.databinding.ItemTableSmallBinding;
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

    private final int TABLE_SIZE_SMALL = 0;
    private final int TABLE_SIZE_BIG = 1;

    private final int TABLE_TYPE_SQUARE = 0;
    private final int TABLE_TYPE_ROUND = 1;

    private final int TABLE_ORIENTATION_HORIZONTAL = 0;
    private final int TABLE_ORIENTATION_VERTICAL = 1;

    private final int TABLE_AVAILABILITY_FREE = 0;
    private final int TABLE_AVAILABILITY_OCCUPIED = 1;


    private FragmentMainBinding binding;

    private TakeAwayAdapter mTakeAwayAdapter = new TakeAwayAdapter(this::openOrderDetails);
    private DeliveryAdapter mDeliveryAdapter = new DeliveryAdapter(this::openOrderDetails);

    private List<OrderModel> takeAwayOrdersOpen = new ArrayList<>();
    private List<OrderModel> deliveryOrdersOpen = new ArrayList<>();
    private List<OrderModel> takeAwayOrdersClosed = new ArrayList<>();
    private List<OrderModel> deliveryOrdersClosed = new ArrayList<>();

    private Context mContext;

    private OnLoggedInManagerListener listener;

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

        drawTables();

        return binding.getRoot();
    }

    private void drawTables() {

        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 1, TABLE_AVAILABILITY_FREE, "free", false, 50, 20);
        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 1, TABLE_AVAILABILITY_OCCUPIED, "cooking", false, 50, 250);
        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_SQUARE, TABLE_ORIENTATION_VERTICAL, 2, TABLE_AVAILABILITY_OCCUPIED, "cooking", true, 250, 20);
        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_SQUARE, TABLE_ORIENTATION_VERTICAL, 2, TABLE_AVAILABILITY_FREE, "free", false, 470, 20);
        addNewTable(TABLE_SIZE_BIG, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 3, TABLE_AVAILABILITY_OCCUPIED, "preparing", true, 250, 250);
        addNewTable(TABLE_SIZE_BIG, TABLE_TYPE_SQUARE, TABLE_ORIENTATION_HORIZONTAL, 3, TABLE_AVAILABILITY_FREE, "free", false, 700, 400);
        addNewTable(TABLE_SIZE_BIG, TABLE_TYPE_SQUARE, TABLE_ORIENTATION_VERTICAL, 4, TABLE_AVAILABILITY_OCCUPIED, "cooking", true, 700, 20);

    }

    private void addNewTable(int size, int type, int orientation, int number, int availability, String status, boolean isNotPayed, int x, int y) {
        RelativeLayout.LayoutParams params;

        TextView tvStatus;
        TextView tvNumber;
        TextView tvNotPayed;
        ImageView ivFree;
        RelativeLayout tableHolder;
        View table;

//        size
        if (size == TABLE_SIZE_BIG) {
//            orientation
            if (orientation == TABLE_ORIENTATION_HORIZONTAL) {
                ItemTableBigHorizontalBinding tableBinding = ItemTableBigHorizontalBinding.inflate(getLayoutInflater());
                table = tableBinding.getRoot();
                tvStatus = tableBinding.tvStatus;
                tvNumber = tableBinding.tvNumber;
                tvNotPayed = tableBinding.tvNotPayed;
                ivFree = tableBinding.ivVacant;
                tableHolder = tableBinding.rlHolderTable;
            } else {
                ItemTableBigVerticalBinding tableBinding = ItemTableBigVerticalBinding.inflate(getLayoutInflater());
                table = tableBinding.getRoot();
                tvStatus = tableBinding.tvStatus;
                tvNumber = tableBinding.tvNumber;
                tvNotPayed = tableBinding.tvNotPayed;
                ivFree = tableBinding.ivVacant;
                tableHolder = tableBinding.rlHolderTable;
            }
        } else {
            ItemTableSmallBinding tableBinding = ItemTableSmallBinding.inflate(getLayoutInflater());
            table = tableBinding.getRoot();
            tvStatus = tableBinding.tvStatus;
            tvNumber = tableBinding.tvNumber;
            tvNotPayed = tableBinding.tvNotPayed;
            ivFree = tableBinding.ivVacant;
            tableHolder = tableBinding.rlHolderTable;
        }

//        type
        if (type == TABLE_TYPE_ROUND) {
            tableHolder.setBackgroundResource(R.drawable.selector_table_background_round);
        } else {
            tableHolder.setBackgroundResource(R.drawable.selector_table_background);
        }

//        availability
        tableHolder.setSelected(availability == TABLE_AVAILABILITY_OCCUPIED);
        tvNumber.setActivated(availability == TABLE_AVAILABILITY_OCCUPIED);
        tvStatus.setActivated(availability == TABLE_AVAILABILITY_OCCUPIED);
        ivFree.setVisibility(availability == TABLE_AVAILABILITY_OCCUPIED ? View.INVISIBLE : View.VISIBLE);

        //        not payed
        tvNotPayed.setVisibility(isNotPayed ? View.VISIBLE : View.GONE);


        tvStatus.setText(getStatusRes(status));
        tvNumber.setText(String.valueOf(number));

        table.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        params = new RelativeLayout.LayoutParams(table.getMeasuredWidth(), table.getMeasuredHeight());
        params.leftMargin = x;
        params.topMargin = y;
        binding.flHolderTables.addView(table, params);
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
            if (order.getIsDelivery().equals("1")) {
                if (order.getStatus().equals("sent")) deliveryOrdersClosed.add(order);
                else deliveryOrdersOpen.add(order);
            } else {
                if (order.getStatus().equals("sent")) takeAwayOrdersClosed.add(order);
                else takeAwayOrdersOpen.add(order);
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

        passwordDialog.setOnDismissListener(dialog -> {
            listener.onLoggedIn(true);
            startBoardUpdates();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            listener = (OnLoggedInManagerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLoggedInManagerListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openPasswordDialog();
    }

    public interface OnLoggedInManagerListener {
        public void onLoggedIn(boolean isLoggedIn);
    }

    private int getStatusRes(String status) {
        switch (status) {
            case "sent":
                return R.string.sent;
            case "packing":
                return R.string.packing;
            case "cooking":
                return R.string.cooking;
            case "preparing":
                return R.string.preparing;
            case "received":
                return R.string.received;
            default:
                return R.string.free;
        }
    }

}
