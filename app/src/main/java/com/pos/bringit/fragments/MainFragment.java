package com.pos.bringit.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.pos.bringit.R;
import com.pos.bringit.adapters.DeliveryAdapter;
import com.pos.bringit.adapters.TakeAwayAdapter;
import com.pos.bringit.databinding.FragmentMainBinding;
import com.pos.bringit.databinding.ItemTableRoundBinding;
import com.pos.bringit.databinding.ItemTableSmallBinding;
import com.pos.bringit.dialog.PasswordDialog;
import com.pos.bringit.local_db.DbHandler;
import com.pos.bringit.models.CloseTableModel;
import com.pos.bringit.models.OrderDetailsModel;
import com.pos.bringit.models.OrderModel;
import com.pos.bringit.models.TableItem;
import com.pos.bringit.models.response.AllOrdersResponse;
import com.pos.bringit.models.response.WorkingAreaResponse;
import com.pos.bringit.network.Request;
import com.pos.bringit.network.RequestHelper;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.PrinterPresenter;
import com.pos.bringit.utils.Utils;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;

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
    private final int TABLE_AVAILABILITY_RESERVED = 2;

    private double cellSize;

    private FragmentMainBinding binding;

    private TakeAwayAdapter mTakeAwayAdapter = new TakeAwayAdapter(this::openOrderDetails);
    private DeliveryAdapter mDeliveryAdapter = new DeliveryAdapter(this::openOrderDetails);

    private List<OrderModel> takeAwayOrdersOpen = new ArrayList<>();
    private List<OrderModel> deliveryOrdersOpen = new ArrayList<>();
    private List<OrderModel> takeAwayOrdersClosed = new ArrayList<>();
    private List<OrderModel> deliveryOrdersClosed = new ArrayList<>();
    private List<OrderModel> takeAwayOrdersFuture = new ArrayList<>();
    private List<OrderModel> deliveryOrdersFuture = new ArrayList<>();
    private List<OrderModel> deliveryOrdersAwaitingPayment = new ArrayList<>();
    private List<OrderModel> tableOrders = new ArrayList<>();
    private List<CloseTableModel> reservedTables = new ArrayList<>();

    private List<TableItem> mCurrentTables = new ArrayList<>();

    private int lastNewOrdersCount = 0;

    private Context mContext;
    private boolean startUpdates;

    private OnLoggedInManagerListener listener;

    private final Handler mHandler = new Handler();
    private SunmiPrinterService woyouService = null;
    private PrinterPresenter printerPresenter;

    private RequestHelper requestHelper = new RequestHelper();

    private Runnable mRunnable = () -> requestHelper.getAllOrdersFromDb(mContext,
            response -> {
                updateRVs(response);
                setupBoardUpdates();
                listener.onBusinessStatusCheck();
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

        connectPrintService();
        binding.tvTakeAway.setOnLongClickListener(v -> {
            throw new RuntimeException("Test crash");
        });
        binding.tvDelivery.setOnLongClickListener(v -> {
            Utils.openAlertDialog(getContext(), "הדפסת הזמנות", "האם אתה בטוח שאתה רוצה להדפיס את כל ההזמנות הקימות?", isRetry -> {
                if (!isRetry) return;
                DbHandler dbHandler = new DbHandler(getContext());
                List<OrderDetailsModel> orderModels = dbHandler.getAllOrdersToPrintFromDb();
                printAllOrders(orderModels, 0);
            });
            return false;
        });
        binding.llAddTakeAway.setOnClickListener(v -> gotoCreateOrder(Constants.NEW_ORDER_TYPE_TAKEAWAY));
        binding.tvAddTakeAway.setOnClickListener(v -> gotoCreateOrder(Constants.NEW_ORDER_TYPE_TAKEAWAY));
        binding.llAddDelivery.setOnClickListener(v -> gotoCreateOrder(Constants.NEW_ORDER_TYPE_DELIVERY));
        binding.tvAddDelivery.setOnClickListener(v -> gotoCreateOrder(Constants.NEW_ORDER_TYPE_DELIVERY));

        binding.tlTakeAway.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mTakeAwayAdapter.updateList(takeAwayOrdersClosed);
                        break;
                    case 1:
                        mTakeAwayAdapter.updateList(takeAwayOrdersOpen);
                        break;
                    case 2:
                        mTakeAwayAdapter.updateList(takeAwayOrdersFuture);
                        break;
                }
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
                switch (tab.getPosition()) {
                    case 0:
                        mDeliveryAdapter.updateList(deliveryOrdersClosed);
                        break;
                    case 1:
                        mDeliveryAdapter.updateList(deliveryOrdersOpen);
                        break;
                    case 2:
                        mDeliveryAdapter.updateList(deliveryOrdersFuture);
                        break;
                    case 3:
                        mDeliveryAdapter.updateList(deliveryOrdersAwaitingPayment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void printAllOrders(List<OrderDetailsModel> orderModels, int index) {
        if (index >= orderModels.size() || orderModels.size() == 0) return;
        if (printerPresenter != null) {
            OrderDetailsModel orderDetailsModel = orderModels.get(index);
            if (orderDetailsModel.getStatus().equals("sent")
                    || orderDetailsModel.isCanceled()
                    || orderDetailsModel.getChangeType().equals("CANCELED")
                    || (orderDetailsModel.getDeliveryOption().equals(Constants.NEW_ORDER_TYPE_TABLE) && !orderDetailsModel.isTableIsActive())) {
                printAllOrders(orderModels, index + 1);
                return;
            }
            printerPresenter.print(orderDetailsModel, () -> printAllOrders(orderModels, index + 1));
        }
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

        reservedTables.clear();
        reservedTables.addAll(allClosedTables);

        takeAwayOrdersOpen.clear();
        deliveryOrdersOpen.clear();
        takeAwayOrdersClosed.clear();
        deliveryOrdersClosed.clear();
        takeAwayOrdersFuture.clear();
        deliveryOrdersFuture.clear();
        deliveryOrdersAwaitingPayment.clear();
        tableOrders.clear();


        int newOrdersCount = 0;

        for (OrderModel order : allOrders) {
            if (order.getAddedBySystem().equals("website")) newOrdersCount++;
            if (!order.isCanceled())
                switch (order.getDeliveryOption()) {
                    case Constants.NEW_ORDER_TYPE_DELIVERY:
                        if (isFuture(order)) deliveryOrdersFuture.add(order);
                        else if (isHistory(order)) deliveryOrdersClosed.add(order);
                        else if (isAwaitingPayment(order)) deliveryOrdersAwaitingPayment.add(order);
                        else deliveryOrdersOpen.add(order);
                        break;
                    case Constants.NEW_ORDER_TYPE_TAKEAWAY:
                        if (isFuture(order)) takeAwayOrdersFuture.add(order);
                        else if (isHistory(order)) takeAwayOrdersClosed.add(order);
                        else takeAwayOrdersOpen.add(order);
                        break;
                    case Constants.NEW_ORDER_TYPE_TABLE:
                        if (isTableOrder(order)) tableOrders.add(order);
                        break;
                }
        }

        if (newOrdersCount > lastNewOrdersCount) playSound();
        lastNewOrdersCount = newOrdersCount;

        fillTables();

        switch (binding.tlTakeAway.getSelectedTabPosition()) {
            case 0:
                mTakeAwayAdapter.updateList(takeAwayOrdersClosed);
                break;
            case 1:
                mTakeAwayAdapter.updateList(takeAwayOrdersOpen);
                break;
            case 2:
                mTakeAwayAdapter.updateList(takeAwayOrdersFuture);
                break;
        }
        switch (binding.tlDelivery.getSelectedTabPosition()) {
            case 0:
                mDeliveryAdapter.updateList(deliveryOrdersClosed);
                break;
            case 1:
                mDeliveryAdapter.updateList(deliveryOrdersOpen);
                break;
            case 2:
                mDeliveryAdapter.updateList(deliveryOrdersFuture);
                break;
            case 3:
                mDeliveryAdapter.updateList(deliveryOrdersAwaitingPayment);
                break;
        }

    }

    private boolean isHistory(OrderModel order) {
        return order.getStatus().equals("sent")
                || order.getStatus().equals("finished");
//        || order.getStartTimeStr().contains("day");
    }

    private boolean isFuture(OrderModel order) {
        return order.getScheduledTime() != null;
    }

    private boolean isAwaitingPayment(OrderModel order) {
        return order.getPayToDeliveryMan() == 1;
    }

    private boolean isTableOrder(OrderModel order) {
        return !order.getStatus().equals("sent") && order.isTableIsActive();
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

        ItemTableSmallBinding tableBinding = ItemTableSmallBinding.inflate(getLayoutInflater());

        View table = tableBinding.getRoot();
        TextView tvStatus = tableBinding.tvStatus;
        TextView tvNumber = tableBinding.tvNumber;
        TextView tvNotPayed = tableBinding.tvNotPayed;
        ImageView ivFree = tableBinding.ivVacant;
        ImageView ivLevel = tableBinding.ivLevel;

        RelativeLayout.LayoutParams params;

//        type
        switch (tableItem.getType()) {
            case TABLE_TYPE_RECTANGLE_H:
                table.measure(View.MeasureSpec.makeMeasureSpec((int) cellSize * 2, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY));
                break;
            case TABLE_TYPE_RECTANGLE_V:
                table.measure(View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec((int) cellSize * 2, View.MeasureSpec.EXACTLY));
                break;
            case TABLE_TYPE_CIRCLE:
                ItemTableRoundBinding tableRoundBinding = ItemTableRoundBinding.inflate(getLayoutInflater());

                table = tableRoundBinding.getRoot();
                tvStatus = tableRoundBinding.tvStatus;
                tvNumber = tableRoundBinding.tvNumber;
                tvNotPayed = tableRoundBinding.tvNotPayed;
                ivFree = tableRoundBinding.ivVacant;
                ivLevel = tableRoundBinding.ivLevel;

                ivLevel.getLayoutParams().height = (int) cellSize / 2;
                ivLevel.getLayoutParams().width = (int) cellSize / 2;

                table.measure(View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec((int) cellSize, View.MeasureSpec.EXACTLY));
                break;
            default:
            case TABLE_TYPE_SQUARE:
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

        boolean isReserved = false;
        for (CloseTableModel reservedTable : reservedTables) {
            if (reservedTable.getTableId().equals(tableItem.getId())) {
                isReserved = true;
                break;
            }
        }

//        availability
        int availability = isReserved ? TABLE_AVAILABILITY_RESERVED : TABLE_AVAILABILITY_FREE;
        availability = (currentOrder != null) ? TABLE_AVAILABILITY_OCCUPIED : availability;

        table.setSelected(availability != TABLE_AVAILABILITY_FREE);
        table.setActivated(availability == TABLE_AVAILABILITY_RESERVED);

        tvNumber.setActivated(availability != TABLE_AVAILABILITY_FREE);
        tvNumber.setSelected(!(availability == TABLE_AVAILABILITY_RESERVED));

        tvStatus.setActivated(availability != TABLE_AVAILABILITY_FREE);
        tvStatus.setSelected(!(availability == TABLE_AVAILABILITY_RESERVED));
        tvStatus.setVisibility(availability != TABLE_AVAILABILITY_FREE ? View.VISIBLE : View.GONE);

        ivFree.setVisibility(availability == TABLE_AVAILABILITY_FREE ? View.VISIBLE : View.GONE);

//        not payed
        tvNotPayed.setVisibility(currentOrder != null && currentOrder.getIsPaid() != 1 ? View.VISIBLE : View.GONE);
        if (currentOrder != null && currentOrder.getIsPaid() == 2) tvNotPayed.setText("תשלום חלקי");

//        status
        String status = isReserved ? "reserved" : "free";
        tvStatus.setText(getStatusRes(currentOrder != null ? currentOrder.getStatus() : status));

//        number
        tvNumber.setText(String.format("TABLE %s", tableItem.getNumber()));

//        color
        if (currentOrder != null && currentOrder.getColor() != null && !currentOrder.getColor().isEmpty()) {
            ivLevel.setColorFilter(Color.parseColor(currentOrder.getColor()));
            ivLevel.setVisibility(View.VISIBLE);
        } else
            ivLevel.setVisibility(View.GONE);


        ivFree.getLayoutParams().height = (int) cellSize / 4;
        ivFree.getLayoutParams().width = (int) cellSize / 4;
        ivLevel.getLayoutParams().height = (int) cellSize / 8;
        ivLevel.getLayoutParams().width = (int) cellSize / 2;
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
                            Constants.NEW_ORDER_TYPE_TABLE, status.equals("reserved") ? "-1" : "", tableItem.getId()))
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
        passwordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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

    private void gotoCreateOrder(String type) {
        NavHostFragment.findNavController(this).navigate(
                MainFragmentDirections.actionMainFragmentToCreateOrderActivity(type, "", ""));
    }

    public interface OnLoggedInManagerListener {
        void onLoggedIn(boolean isLoggedIn);

        void onBusinessStatusCheck();
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
            case "reserved":
                return R.string.reserved;
            case "free":
            default:
                return R.string.free;
        }
    }

    //    printer
    private InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {
            woyouService = service;
            printerPresenter = new PrinterPresenter(getContext(), woyouService);
        }

        @Override
        protected void onDisconnected() {
            woyouService = null;
        }
    };

    private void connectPrintService() {
        try {
            InnerPrinterManager.getInstance().bindService(getContext(), innerPrinterCallback);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
    }

}
