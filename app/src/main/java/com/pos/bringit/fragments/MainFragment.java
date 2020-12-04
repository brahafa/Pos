package com.pos.bringit.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.pos.bringit.R;
import com.pos.bringit.adapters.DeliveryAdapter;
import com.pos.bringit.adapters.TakeAwayAdapter;
import com.pos.bringit.databinding.FragmentMainBinding;
import com.pos.bringit.databinding.ItemTableBigHorizontalBinding;
import com.pos.bringit.databinding.ItemTableBigVerticalBinding;
import com.pos.bringit.databinding.ItemTableSmallBinding;
import com.pos.bringit.dialog.PasswordDialog;
import com.pos.bringit.models.CloseTableModel;
import com.pos.bringit.models.OrderModel;
import com.pos.bringit.models.TableItem;
import com.pos.bringit.models.response.AllOrdersResponse;
import com.pos.bringit.models.response.WorkingAreaResponse;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    private List<OrderModel> tableOrders = new ArrayList<>();
    private List<CloseTableModel> closedTables = new ArrayList<>();

    private List<TableItem> mCurrentTables = new ArrayList<>();

    private int lastNewOrdersCount = 0;

    private Context mContext;
    private boolean startUpdates;

    private OnLoggedInManagerListener listener;

    private final Handler mHandler = new Handler();

    private Runnable mRunnable = () -> Request.getInstance().getAllOrders(mContext,
            response -> {
                updateRVs(response);
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

        getTables();
//        Handler handler = new Handler();
//        handler.postDelayed(this::drawTables, 1000);

        return binding.getRoot();
    }

    private void drawTables() {

        InputStream is = getResources().openRawResource(R.raw.tables);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Gson gson = new Gson();
        WorkingAreaResponse response = gson.fromJson(writer.toString(), WorkingAreaResponse.class);
        prepareWorkingArea(response.getWorkingArea());

    }

    private void initListeners() {
        binding.llAddTakeAway.setOnClickListener(
                v -> NavHostFragment.findNavController(this).navigate(
                        MainFragmentDirections.actionMainFragmentToCreateOrderActivity(Constants.NEW_ORDER_TYPE_TAKEAWAY, "", "")));
        binding.llAddDelivery.setOnClickListener(
                v -> NavHostFragment.findNavController(this).navigate(
                        MainFragmentDirections.actionMainFragmentToCreateOrderActivity(Constants.NEW_ORDER_TYPE_DELIVERY, "", "")));

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

    private void updateRVs(AllOrdersResponse response) {

        List<OrderModel> allOrders = response.getOrders();
        removeDeleteOrders(allOrders);
        List<CloseTableModel> allClosedTables = response.getClosedTables();

        closedTables.clear();
        closedTables.addAll(allClosedTables);

        takeAwayOrdersOpen.clear();
        deliveryOrdersOpen.clear();
        takeAwayOrdersClosed.clear();
        deliveryOrdersClosed.clear();
        tableOrders.clear();


        int newOrdersCount = 0;

        for (OrderModel order : allOrders) {
            if (order.getAddedBySystem().equals("website")) newOrdersCount++;
            if (!order.getIsCanceled())
                switch (order.getDeliveryOption()) {
                    case Constants.NEW_ORDER_TYPE_DELIVERY:
                        if (isHistory(order)) deliveryOrdersClosed.add(order);
                        else deliveryOrdersOpen.add(order);
                        break;
                    case Constants.NEW_ORDER_TYPE_TAKEAWAY:
                        if (isHistory(order)) takeAwayOrdersClosed.add(order);
                        else takeAwayOrdersOpen.add(order);
                        break;
                    case Constants.NEW_ORDER_TYPE_TABLE:
                        if (!order.getStatus().equals("sent")) tableOrders.add(order);
                        break;
                }
        }

        if (newOrdersCount > lastNewOrdersCount) playSound();
        lastNewOrdersCount = newOrdersCount;

        fillTables();
        mTakeAwayAdapter.updateList(
                binding.tlTakeAway.getSelectedTabPosition() == 0 ? takeAwayOrdersClosed : takeAwayOrdersOpen);
        mDeliveryAdapter.updateList(
                binding.tlDelivery.getSelectedTabPosition() == 0 ? deliveryOrdersClosed : deliveryOrdersOpen);

    }

    private boolean isHistory(OrderModel order) {
        return order.getStatus().equals("sent") || order.getStartTimeStr().contains("day");
    }

    private void removeDeleteOrders(List<OrderModel> allOrders) {
        for (int i = allOrders.size() - 1; i >= 0; i--) {
            if (allOrders.get(i).getChangeType().equals(Constants.ORDER_CHANGE_TYPE_DELETED)) {
                allOrders.remove(i);
            }
        }
    }

    private void playSound() {
        MediaPlayer mp = MediaPlayer.create(mContext, R.raw.new_order);
        mp.setOnPreparedListener(MediaPlayer::start);
        mp.setOnCompletionListener(MediaPlayer::release);
    }

    private void startBoardUpdates() {
        if (startUpdates) mRunnable.run();
    }

    private void setupBoardUpdates() {
        removeBoardUpdates();
        mHandler.postDelayed(mRunnable, REQUEST_REPEAT_INTERVAL);
    }

    private void removeBoardUpdates() {
        mHandler.removeCallbacks(mRunnable);
    }

    private void getTables() {
        Request.getInstance().getWorkingArea(mContext, response -> prepareWorkingArea(response.getWorkingArea()));
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

        mCurrentTables = workingArea.getTables();
        fillTables();

    }

    private void fillTables() {
        binding.flHolderTables.removeAllViewsInLayout();
        for (TableItem tableItem : mCurrentTables) addNewTable(tableItem);
    }

    private void addNewTable(TableItem tableItem) {
        RelativeLayout.LayoutParams params;

        TextView tvStatus;
        TextView tvNumber;
        TextView tvNotPayed;
        ImageView ivFree;
        RelativeLayout tableHolder;
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
                tableHolder = circleTableBinding.rlHolderTable;
                tableHolder.setBackgroundResource(R.drawable.selector_table_background_round);

                table.measure(View.MeasureSpec.makeMeasureSpec((int) (cellSize - cellSize / 12), View.MeasureSpec.EXACTLY),
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
                tableHolder = tableBinding.rlHolderTable;

                table.measure(View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY));
                break;
        }

        OrderModel currentOrder = null;

        for (OrderModel order : tableOrders) {
            if (order.getTableId().equals(tableItem.getId())) {
                currentOrder = order;
                break;
            }
        }

        boolean isClosed = false;
        for (CloseTableModel closedTable : closedTables) {
            if (closedTable.getTableId().equals(tableItem.getId())) {
                isClosed = true;
                break;
            }
        }

//        availability
        int availability = (currentOrder == null && !isClosed) ? TABLE_AVAILABILITY_FREE : TABLE_AVAILABILITY_OCCUPIED;

        tableHolder.setSelected(availability == TABLE_AVAILABILITY_OCCUPIED);
        tvNumber.setActivated(availability == TABLE_AVAILABILITY_OCCUPIED);
        tvStatus.setActivated(availability == TABLE_AVAILABILITY_OCCUPIED);
        ivFree.setVisibility(availability == TABLE_AVAILABILITY_OCCUPIED ? View.GONE : View.VISIBLE);

//        not payed
        tvNotPayed.setVisibility(currentOrder != null && !currentOrder.isPaid() ? View.VISIBLE : View.GONE);

//        status
        String status = isClosed ? "opened" : "free";
        tvStatus.setText(getStatusRes(currentOrder != null ? currentOrder.getStatus() : status));

//        number
        tvNumber.setText(tableItem.getNumber());


        ivFree.getLayoutParams().height = (int) cellSize / 4;
        ivFree.getLayoutParams().width = (int) cellSize / 4;
        tvStatus.setTextSize((float) (cellSize / 12));
        tvNumber.setTextSize((float) (cellSize / 10));
        tvNotPayed.setTextSize((float) (cellSize / 12));

        params = new RelativeLayout.LayoutParams(table.getMeasuredWidth(), table.getMeasuredHeight());

        params.leftMargin = (int) ((tableItem.getProperColumn()) * cellSize);
        params.topMargin = (int) ((tableItem.getProperRow()) * cellSize);

        Log.d("measures of table " + tableItem.getNumber(), "h: " + table.getMeasuredHeight() + " w: " + table.getMeasuredWidth());
//        Log.d("position of table " + tableItem.getNumber(), "h: " + params.topMargin + " w: " + params.leftMargin);

        if (currentOrder == null)
            table.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(
                    MainFragmentDirections.actionMainFragmentToCreateOrderActivity(
                            Constants.NEW_ORDER_TYPE_TABLE, status.equals("opened") ? "-1" : "", tableItem.getId()))
            );
        else {
            String orderId = currentOrder.getId();
            table.setOnClickListener(v -> openOrderDetails(orderId, tableItem.getId()));
        }

        binding.flHolderTables.addView(table, params);
    }

    private void openOrderDetails(String orderId) {
        openOrderDetails(orderId, "");
    }

    private void openOrderDetails(String orderId, String tableId) {
        NavHostFragment.findNavController(this).navigate(
                MainFragmentDirections.actionMainFragmentToCreateOrderActivity(Constants.NEW_ORDER_TYPE_ITEM, orderId, tableId));
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
        Log.d("main fragment", "on Pause");
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
            case "opened":
                return R.string.opened;
            case "free":
            default:
                return R.string.free;
        }
    }

}
