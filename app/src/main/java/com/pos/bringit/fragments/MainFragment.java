package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.pos.bringit.models.TableItem;
import com.pos.bringit.models.response.WorkingAreaResponse;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainFragment extends Fragment {

    private final int REQUEST_REPEAT_INTERVAL = 10 * 1000;

    private final String TABLE_TYPE_CIRCLE = "circle";
    private final String TABLE_TYPE_SQUARE = "square";
    private final String TABLE_TYPE_RECTANGLE_H = "rectangleH";
    private final String TABLE_TYPE_RECTANGLE_V = "rectangleV";

    private final int TABLE_AVAILABILITY_FREE = 0;
    private final int TABLE_AVAILABILITY_OCCUPIED = 1;

    private double cellSize;

    private FragmentMainBinding binding;

    private TakeAwayAdapter mTakeAwayAdapter = new TakeAwayAdapter(this::openOrderDetails);
    private DeliveryAdapter mDeliveryAdapter = new DeliveryAdapter(this::openOrderDetails);

    private List<OrderModel> takeAwayOrdersOpen = new ArrayList<>();
    private List<OrderModel> deliveryOrdersOpen = new ArrayList<>();
    private List<OrderModel> takeAwayOrdersClosed = new ArrayList<>();
    private List<OrderModel> deliveryOrdersClosed = new ArrayList<>();

    private Context mContext;
    private boolean startUpdates;

    private OnLoggedInManagerListener listener;

    private final Handler mHandler = new Handler();

    private Runnable mRunnable = () -> Request.getInstance().getAllOrders(getActivity(),
            response -> {
                updateRVs(response.getOrders());
//                setupBoardUpdates(); //todo bring back
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.tlTakeAway.getTabAt(1).select();
        binding.tlDelivery.getTabAt(1).select();

        initRVs();

        initListeners();

        getTables();
//        drawTables();

        return binding.getRoot();
    }

    private void drawTables() {

//        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 1, TABLE_AVAILABILITY_FREE, "free", false, 50, 20);
//        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 1, TABLE_AVAILABILITY_FREE, "free", false, 150, 20);
//        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 1, TABLE_AVAILABILITY_FREE, "free", false, 250, 20);
//        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 1, TABLE_AVAILABILITY_FREE, "free", false, 350, 20);
//        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 1, TABLE_AVAILABILITY_FREE, "free", false, 450, 20);
//        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 1, TABLE_AVAILABILITY_OCCUPIED, "cooking", false, 50, 250);
//        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_SQUARE, TABLE_ORIENTATION_VERTICAL, 2, TABLE_AVAILABILITY_OCCUPIED, "cooking", true, 250, 20);
//        addNewTable(TABLE_SIZE_SMALL, TABLE_TYPE_SQUARE, TABLE_ORIENTATION_VERTICAL, 2, TABLE_AVAILABILITY_FREE, "free", false, 470, 20);
//        addNewTable(TABLE_SIZE_BIG, TABLE_TYPE_ROUND, TABLE_ORIENTATION_HORIZONTAL, 3, TABLE_AVAILABILITY_OCCUPIED, "preparing", true, 250, 250);
//        addNewTable(TABLE_SIZE_BIG, TABLE_TYPE_SQUARE, TABLE_ORIENTATION_HORIZONTAL, 3, TABLE_AVAILABILITY_FREE, "free", false, 700, 20);
//        addNewTable(TABLE_SIZE_BIG, TABLE_TYPE_SQUARE, TABLE_ORIENTATION_VERTICAL, 4, TABLE_AVAILABILITY_OCCUPIED, "cooking", true, 700, 20);

    }

    private void addNewTable(TableItem tableItem) {
        RelativeLayout.LayoutParams params;

        TextView tvStatus;
        TextView tvNumber;
        TextView tvNotPayed;
        ImageView ivFree;
        RelativeLayout tableHolder;
        LinearLayout numberHolder;
        View table;

//        type
        switch (tableItem.getType()) {
            case TABLE_TYPE_RECTANGLE_H:
                ItemTableBigHorizontalBinding rectHTableBinding = ItemTableBigHorizontalBinding.inflate(getLayoutInflater());
                table = rectHTableBinding.getRoot();
                tvStatus = rectHTableBinding.tvStatus;
                tvNumber = rectHTableBinding.tvNumber;
                tvNotPayed = rectHTableBinding.tvNotPayed;
                ivFree = rectHTableBinding.ivVacant;
                numberHolder = rectHTableBinding.llHolderNumber;
                tableHolder = rectHTableBinding.rlHolderTable;

                table.measure(View.MeasureSpec.makeMeasureSpec((int) cellSize * 2, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY));
                break;
            case TABLE_TYPE_RECTANGLE_V:
                ItemTableBigVerticalBinding rectVTableBinding = ItemTableBigVerticalBinding.inflate(getLayoutInflater());
                table = rectVTableBinding.getRoot();
                tvStatus = rectVTableBinding.tvStatus;
                tvNumber = rectVTableBinding.tvNumber;
                tvNotPayed = rectVTableBinding.tvNotPayed;
                ivFree = rectVTableBinding.ivVacant;
                numberHolder = rectVTableBinding.llHolderNumber;
                tableHolder = rectVTableBinding.rlHolderTable;

                table.measure(View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec((int) cellSize * 2, View.MeasureSpec.EXACTLY));
                break;
            case TABLE_TYPE_CIRCLE:
                ItemTableSmallBinding circleTableBinding = ItemTableSmallBinding.inflate(getLayoutInflater());
                table = circleTableBinding.getRoot();
                tvStatus = circleTableBinding.tvStatus;
                tvNumber = circleTableBinding.tvNumber;
                tvNotPayed = circleTableBinding.tvNotPayed;
                ivFree = circleTableBinding.ivVacant;
                numberHolder = circleTableBinding.llHolderNumber;
                tableHolder = circleTableBinding.rlHolderTable;
                tableHolder.setBackgroundResource(R.drawable.selector_table_background_round);

                table.measure(View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY));
                break;
            default:
            case TABLE_TYPE_SQUARE:
                ItemTableSmallBinding tableBinding = ItemTableSmallBinding.inflate(getLayoutInflater());
                table = tableBinding.getRoot();
                tvStatus = tableBinding.tvStatus;
                tvNumber = tableBinding.tvNumber;
                tvNotPayed = tableBinding.tvNotPayed;
                ivFree = tableBinding.ivVacant;
                numberHolder = tableBinding.llHolderNumber;
                tableHolder = tableBinding.rlHolderTable;

                table.measure(View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY));
                break;
        }

//        availability
        int availability = TABLE_AVAILABILITY_FREE; //fixme get table availability
        tableHolder.setSelected(availability == TABLE_AVAILABILITY_OCCUPIED);
        tvNumber.setActivated(availability == TABLE_AVAILABILITY_OCCUPIED);
        tvStatus.setActivated(availability == TABLE_AVAILABILITY_OCCUPIED);
        ivFree.setVisibility(availability == TABLE_AVAILABILITY_OCCUPIED ? View.INVISIBLE : View.VISIBLE);

//        not payed
        tvNotPayed.setVisibility(View.GONE); //fixme handle order not payed


        tvStatus.setText(getStatusRes("status")); //fixme send order status here
        tvNumber.setText(tableItem.getNumber());

        numberHolder.setVisibility(cellSize < 100 ? View.GONE : View.VISIBLE);

        params = new RelativeLayout.LayoutParams(table.getMeasuredWidth(), table.getMeasuredHeight());

        params.leftMargin = (int) ((tableItem.getProperColumn() - 1) * cellSize);
        params.topMargin = (int) ((tableItem.getProperRow() - 1) * cellSize);

        Log.d("measures of table " + tableItem.getNumber(), "h: " + table.getMeasuredHeight() + " w: " + table.getMeasuredWidth());
//        Log.d("position of table " + tableItem.getNumber(), "h: " + params.topMargin + " w: " + params.leftMargin);

        binding.flHolderTables.addView(table, params);
    }

    private void initListeners() {
        binding.llAddTakeAway.setOnClickListener(
                v -> NavHostFragment.findNavController(this).navigate(
                        MainFragmentDirections.actionMainFragmentToCreateOrderActivity(Constants.NEW_ORDER_TYPE_TAKEAWAY, "")));
        binding.llAddDelivery.setOnClickListener(
                v -> NavHostFragment.findNavController(this).navigate(
                        MainFragmentDirections.actionMainFragmentToCreateOrderActivity(Constants.NEW_ORDER_TYPE_DELIVERY, "")));

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
        if (startUpdates) mRunnable.run();
    }

    private void setupBoardUpdates() {
        mHandler.postDelayed(mRunnable, REQUEST_REPEAT_INTERVAL);
    }

    private void removeBoardUpdates() {
        mHandler.removeCallbacks(mRunnable);
    }

    private void getTables() {
        Request.getInstance().getWorkingArea(getActivity(), response -> prepareWorkingArea(response.getWorkingArea()));
    }

    private void prepareWorkingArea(WorkingAreaResponse.WorkingAreaItem workingArea) {

        int serverHeight = workingArea.getProperHeight();
        int serverWidth = workingArea.getProperWidth();

        int cellCountVertical = serverHeight / 50;
        int cellCountHorizontal = serverWidth / 50;

        int parentHeight = binding.flHolderTables.getMeasuredHeight();
        int parentWidth = binding.flHolderTables.getMeasuredWidth();
        Log.d("parent measures", "h: " + parentHeight + " w: " + parentWidth);

        double cellHeight = parentHeight / cellCountVertical;
        double cellWidth = parentWidth / cellCountHorizontal;

        Log.d("cell measures", "h: " + cellHeight + " w: " + cellWidth);

        cellSize = Math.min(cellHeight, cellWidth);

        int paddingTop = (int) ((parentHeight - cellSize * cellCountVertical) / 2);
        int paddingLeft = (int) ((parentWidth - cellSize * cellCountHorizontal) / 2);

        Log.d("paddings", "top: " + paddingTop + " left: " + paddingLeft);

        binding.flHolderTables.setPadding(paddingLeft, paddingTop, 0, 0);

        fillTables(workingArea.getTables());

    }

    private void fillTables(List<TableItem> tables) {
        for (TableItem tableItem : tables) addNewTable(tableItem);
    }

    private void openOrderDetails(String orderId) {
        NavHostFragment.findNavController(this).navigate(
                MainFragmentDirections.actionMainFragmentToCreateOrderActivity(Constants.NEW_ORDER_TYPE_ITEM, orderId));
    }

    private void openPasswordDialog() {
        PasswordDialog passwordDialog = new PasswordDialog(mContext);
        passwordDialog.setCancelable(false);
        passwordDialog.show();

        passwordDialog.setOnDismissListener(dialog -> {
            listener.onLoggedIn(true);
            startUpdates = true;
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
        openPasswordDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        startBoardUpdates();
    }

    @Override
    public void onPause() {
        Toast.makeText(mContext, "on Pause", Toast.LENGTH_SHORT).show();
        removeBoardUpdates();
        super.onPause();
    }

    public interface OnLoggedInManagerListener {
        void onLoggedIn(boolean isLoggedIn);
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
