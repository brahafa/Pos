package com.pos.bringit.activities;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;
import com.pos.bringit.R;
import com.pos.bringit.adapters.CartAdapter;
import com.pos.bringit.adapters.CartKitchenAdapter;
import com.pos.bringit.adapters.FolderAdapter;
import com.pos.bringit.adapters.MenuAdapter;
import com.pos.bringit.databinding.ActivityCreateOrderBinding;
import com.pos.bringit.dialog.CommentDialog;
import com.pos.bringit.dialog.UserDetailsDialog;
import com.pos.bringit.fragments.AdditionalOfferFragment;
import com.pos.bringit.fragments.AdditionalOfferFragmentDirections;
import com.pos.bringit.fragments.ClearFragmentDirections;
import com.pos.bringit.fragments.DealAssembleFragment;
import com.pos.bringit.fragments.DealAssembleFragmentDirections;
import com.pos.bringit.fragments.PaymentFragment;
import com.pos.bringit.fragments.PaymentFragmentDirections;
import com.pos.bringit.fragments.PizzaAssembleFragment;
import com.pos.bringit.fragments.PizzaAssembleFragmentDirections;
import com.pos.bringit.models.BreadcrumbModel;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.DealItemModel;
import com.pos.bringit.models.FolderItemModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.PaymentDetailsModel;
import com.pos.bringit.models.PaymentModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.models.UserDetailsModel;
import com.pos.bringit.models.response.InvoiceResponse;
import com.pos.bringit.network.Request;
import com.pos.bringit.network.RequestHelper;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.MyExceptionHandler;
import com.pos.bringit.utils.PrinterPresenter;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DEAL;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DRINK;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_PIZZA;
import static com.pos.bringit.utils.Constants.ITEM_TYPE_DEAL;
import static com.pos.bringit.utils.Constants.ITEM_TYPE_FOOD;
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_DELIVERY;
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_ITEM;
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_TABLE;
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_TAKEAWAY;
import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_NEW;
import static com.pos.bringit.utils.Constants.PAYMENT_METHOD_CARD;
import static com.pos.bringit.utils.Constants.PAYMENT_METHOD_CASH;
import static com.pos.bringit.utils.SharedPrefs.getData;
import static com.pos.bringit.utils.Utils.countProductPrice;

public class CreateOrderActivity extends AppCompatActivity implements
        FolderAdapter.AdapterCallback, PizzaAssembleFragment.ToppingAddListener,
        AdditionalOfferFragment.FillingSelectListener, DealAssembleFragment.DealItemsAddListener,
        PaymentFragment.OnPaymentMethodChosenListener {

    private ActivityCreateOrderBinding binding;

    private List<CartModel> kitchenItems = new ArrayList<>();

    private MenuAdapter mMenuAdapter = new MenuAdapter(this::openFolder);

    private UserDetailsModel mUserDetails;

    private SunmiPrinterService woyouService = null;
    private PrinterPresenter printerPresenter;


    private CartAdapter mCartAdapter;
    private FolderAdapter mFolderAdapter;
    private CartKitchenAdapter mCartKitchenAdapter;

    private int mCartPosition = 0;
    private String mPaymentMethod = "noPay";
    private String mColor = "";
    private String type;
    private String printType;
    private String itemId;
    private String tableId;
    private double deliveryPrice;
    private String previousFolderId = "0";

    private double mTotalPriceSum = 0;
    private double mTotalPriceToSend = 0;
    private double mKitchenPriceSum = 0;
    private List<PaymentModel> mPayments = new ArrayList<>();
    private List<PaymentModel> mPaymentsToPay = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));

        mUserDetails = new UserDetailsModel();

        type = CreateOrderActivityArgs.fromBundle(getIntent().getExtras()).getType();
        tableId = CreateOrderActivityArgs.fromBundle(getIntent().getExtras()).getTableId();
        itemId = CreateOrderActivityArgs.fromBundle(getIntent().getExtras()).getItemId();

        printType = type;
        Log.d("bundleType", type);
        Log.d("bundleItemId", itemId);
        Log.d("bundleTableId", tableId);

        initListeners();

        initRV();

        setInfo();

        openMainFolder();

        connectPrintService();

        switch (type) {
            case Constants.NEW_ORDER_TYPE_ITEM:
                binding.cvSend.setVisibility(View.GONE); //fixme hading edit btn till next release

                RequestHelper requestHelper = new RequestHelper();
                requestHelper.getOrderDetailsByIDFromDb(this, itemId, orderDetailsResponse -> {
                    mUserDetails = orderDetailsResponse.getClient();
                    mUserDetails.getNotes().setDelivery(orderDetailsResponse.getDeliveryNotes());
                    mUserDetails.getNotes().setOrder(orderDetailsResponse.getOrderNotes());
                    deliveryPrice = orderDetailsResponse.getDeliveryPrice();
                    mPayments = orderDetailsResponse.getPayments();
                    printType = orderDetailsResponse.getDeliveryOption();
                    mColor = orderDetailsResponse.getColor();
                    setColorToCursor();

                    setIcons(orderDetailsResponse.getDeliveryOption());

                    if (orderDetailsResponse.getOrderItems() != null)
                        fillKitchenCart(orderDetailsResponse.getOrderItems());
                });
                if (!tableId.isEmpty()) {
                    binding.cvOpenTable.setVisibility(View.VISIBLE);
                    binding.tvOpenTable.setActivated(true);
                    binding.tvOpenTable.setText("Close");
                }
                break;
            case NEW_ORDER_TYPE_TABLE:
                binding.cvOpenTable.setVisibility(View.VISIBLE);
                if (itemId.equals("-1")) {
                    binding.tvOpenTable.setActivated(true);
                    binding.tvOpenTable.setText("Close");
                }
                break;
            case NEW_ORDER_TYPE_DELIVERY:
                countPrices();
                break;
        }

    }

    private void initListeners() {

        binding.holderBack.setOnClickListener(v -> finish());
        binding.titleCashier.setOnClickListener(v -> {
        });

        binding.tvKitchenItemsTitle.setOnClickListener(v -> {
            setCartOpenDrawable(binding.rvCartKitchen.getVisibility() == View.VISIBLE);
            binding.rvCartKitchen.setVisibility(
                    binding.rvCartKitchen.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        binding.ivSearch.setOnClickListener(v -> {
            if (binding.edtSearch.getVisibility() == View.VISIBLE) {
                binding.edtSearch.setVisibility(View.GONE);
                binding.edtSearch.getText().clear();
                openFolder(previousFolderId);
            } else {
                binding.edtSearch.setVisibility(View.VISIBLE);
                binding.edtSearch.requestFocus();
            }
        });
        binding.tvHome.setOnClickListener(v -> openMainFolder());

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 1)
                    Request.getInstance().searchProducts(CreateOrderActivity.this, s.toString(),
                            response -> mFolderAdapter.updateList(response.getItems()));
            }
        });

        addColorChooseListeners();

        binding.tvSend.setOnClickListener(v -> {
            if (checkRequiredUserInfo())
                if (type.equals(Constants.NEW_ORDER_TYPE_ITEM)) editCart();
                else completeCart();
            else
                openUserDetailsDialog();
        });
        binding.tvPay.setOnClickListener(v -> {
            if (Navigation.findNavController(binding.navHostFragment).getCurrentDestination().getId() == R.id.paymentFragment) {
                closeInnerFragment();
            } else {
                closeInnerFragment();
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToPayment(new PaymentDetailsModel(String.valueOf(mTotalPriceSum), mPayments, itemId)));
            }
        });

        binding.tvPrint.setOnClickListener(v -> {
            if (printerPresenter != null) {
                printerPresenter.print(mCartAdapter.getClearItems(), mCartKitchenAdapter.getClearItems(), mTotalPriceSum, itemId, mUserDetails, 1, printType, null);

            }
        });

        binding.tvInvoice.setOnClickListener(v ->
                Request.getInstance().getInvoiceByOrderId(this, itemId, response -> printDoc(response.getInvoice())));

        binding.tvComment.setOnClickListener(v -> openCommentDialog());
        binding.tvDetails.setOnClickListener(v -> openUserDetailsDialog());
        binding.tvOpenTable.setOnClickListener(v -> openWarningDialog(itemId.isEmpty())); //fixme change when get table_is_closed argument
        binding.tvClearCart.setOnClickListener(v -> openCancelOrderDialog());
    }

    private void addColorChooseListeners() {
        View.OnClickListener cursorClickListener = v -> binding.layoutChooseColor.getRoot().setVisibility(
                binding.layoutChooseColor.getRoot().getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

        View.OnClickListener redClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_red);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#E93746"; //red
            if (type.equals(Constants.NEW_ORDER_TYPE_ITEM)) changeColor();
        };
        View.OnClickListener greenClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_green);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#419D3E"; //green
            if (type.equals(Constants.NEW_ORDER_TYPE_ITEM)) changeColor();
        };
        View.OnClickListener blueClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_blue);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#2251f3"; //blue
            if (type.equals(Constants.NEW_ORDER_TYPE_ITEM)) changeColor();
        };
        View.OnClickListener yellowClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_yellow);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#FACD5D"; //yellow
            if (type.equals(Constants.NEW_ORDER_TYPE_ITEM)) changeColor();
        };
        View.OnClickListener orangeClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_orange);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#FB6D3A"; //orange
            if (type.equals(Constants.NEW_ORDER_TYPE_ITEM)) changeColor();
        };
        View.OnClickListener whiteClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_create);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = ""; //white
            if (type.equals(Constants.NEW_ORDER_TYPE_ITEM)) changeColor();
        };
        binding.ivCursor.setOnClickListener(cursorClickListener);
        binding.tvCursor.setOnClickListener(cursorClickListener);
        binding.layoutChooseColor.ivColorRed.setOnClickListener(redClickListener);
        binding.layoutChooseColor.tvColorRed.setOnClickListener(redClickListener);
        binding.layoutChooseColor.ivColorGreen.setOnClickListener(greenClickListener);
        binding.layoutChooseColor.tvColorGreen.setOnClickListener(greenClickListener);
        binding.layoutChooseColor.ivColorBlue.setOnClickListener(blueClickListener);
        binding.layoutChooseColor.tvColorBlue.setOnClickListener(blueClickListener);
        binding.layoutChooseColor.ivColorYellow.setOnClickListener(yellowClickListener);
        binding.layoutChooseColor.tvColorYellow.setOnClickListener(yellowClickListener);
        binding.layoutChooseColor.ivColorOrange.setOnClickListener(orangeClickListener);
        binding.layoutChooseColor.tvColorOrange.setOnClickListener(orangeClickListener);
        binding.layoutChooseColor.ivColorWhite.setOnClickListener(whiteClickListener);
        binding.layoutChooseColor.tvColorWhite.setOnClickListener(whiteClickListener);

    }

    private void changeColor() {
        Request.getInstance().editColor(this, mColor, itemId, isDataSuccess -> {
        });
    }

    private void setColorToCursor() {
        if (mColor != null)
            switch (mColor) {
                case "#E93746": //red
                    binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_red);
                    break;
                case "#419D3E": //green
                    binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_green);
                    break;
                case "#2251f3": //blue
                    binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_blue);
                    break;
                case "#FACD5D": //yellow
                    binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_yellow);
                    break;
                case "#FB6D3A": //orange
                    binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_orange);
                    break;
                case "": //white
                default:
                    binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_create);
            }
    }


    private void initRV() {
        binding.rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binding.rvMenu.setAdapter(mMenuAdapter);

        binding.rvCartKitchen.setLayoutManager(new LinearLayoutManager(this));

        mCartKitchenAdapter = new CartKitchenAdapter(this, type, new CartKitchenAdapter.AdapterCallback() {
            @Override
            public void onItemClick(ProductItemModel fatherItem) {
                getSourceItem(fatherItem);
            }

            @Override
            public void onItemStatusChange(CartKitchenAdapter.addOrRemove addOrRemove) {
                countPrices();
            }
        });
        binding.rvCartKitchen.setAdapter(mCartKitchenAdapter);

        mCartAdapter = new CartAdapter(this, type, new CartAdapter.AdapterCallback() {
            @Override
            public void onItemClick(ProductItemModel fatherItem) {
                openItem(fatherItem, false);
            }

            @Override
            public void onItemDuplicated() {
                mCartPosition++;
                binding.rvCart.scrollToPosition(mCartAdapter.getItemCount() - 1);
                countPrices();
            }

            @Override
            public void onActiveItemRemoved(boolean isActive) {
                if (isActive) {
                    closeInnerFragment();
                    openFolder(previousFolderId);
                }
                countPrices();
                binding.tvEmptyCart.setVisibility((mCartAdapter.getItemCount() == 0) ? View.VISIBLE : View.GONE);
            }
        });
        binding.rvCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCart.setAdapter(mCartAdapter);

        mFolderAdapter = new FolderAdapter(type, this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this, FlexDirection.ROW_REVERSE);
        binding.rvFolders.setLayoutManager(layoutManager);
        binding.rvFolders.setAdapter(mFolderAdapter);
    }

    private void setInfo() {
//        binding.tvPay.setEnabled(false);

        binding.gDetails.setVisibility(type.equals(Constants.NEW_ORDER_TYPE_ITEM) ? View.VISIBLE : View.GONE);
        binding.tvKitchenItemsTitle.setVisibility(type.equals(Constants.NEW_ORDER_TYPE_ITEM) ? View.VISIBLE : View.GONE);
        binding.ivKitchenCartOpen.setVisibility(type.equals(Constants.NEW_ORDER_TYPE_ITEM) ? View.VISIBLE : View.GONE);
        setCartOpenDrawable(type.equals(Constants.NEW_ORDER_TYPE_ITEM));

        binding.tvOrderNumber.setText(type.equals(Constants.NEW_ORDER_TYPE_ITEM) ? "#" + itemId : "New order");
        binding.tvWaiterName.setText(getData(Constants.NAME_PREF));

        binding.tvTotalPrice.setText(String.format(Locale.US, "%.2f", mTotalPriceSum));

// todo remove if not used any more
//        binding.cvInvoice.setVisibility(type.equals(Constants.NEW_ORDER_TYPE_ITEM) ? View.VISIBLE : View.GONE);

        setIcons(type);
    }

    private void setIcons(String iconType) {
        switch (iconType) {
            case NEW_ORDER_TYPE_TAKEAWAY:
                binding.ivLogoType.setImageResource(R.drawable.ic_take_away);
                binding.ivLogoType.setBackgroundColor(Color.parseColor("#503E9D")); //purple
                break;
            case NEW_ORDER_TYPE_DELIVERY:
                binding.ivLogoType.setImageResource(R.drawable.ic_delivery);
                binding.ivLogoType.setBackgroundColor(Color.parseColor("#FB6D3A")); //orange
                break;
            case NEW_ORDER_TYPE_TABLE:
                binding.ivLogoType.setImageResource(R.drawable.ic_eat_in);
                binding.ivLogoType.setBackgroundColor(Color.parseColor("#419D3E")); //green
                break;
        }
    }

    private void setCartOpenDrawable(boolean isOpen) {
        binding.ivKitchenCartOpen.setRotation(isOpen ? 270 : 0);
    }

    private void fillKitchenCart(List<ProductItemModel> orderItems) {
        for (int i = orderItems.size() - 1; i >= 0; i--) {
            if (orderItems.get(i).isCanceled() || orderItems.get(i).isDeleted()) {
                orderItems.remove(i);
            } else if (orderItems.get(i).getTypeName().equals(BUSINESS_ITEMS_TYPE_DEAL)) {
                List<DealItemModel> dealItemModelList = new ArrayList<>();
                for (int j = 0; j < orderItems.get(i).getProducts().size(); j++) {
                    DealItemModel dealItemModel = new DealItemModel(orderItems.get(i).getProducts().get(j), orderItems.get(i).getId());
                    dealItemModelList.add(dealItemModel);
                }
                orderItems.get(i).setDealItems(dealItemModelList);
            } else {
                for (CategoryModel category : orderItems.get(i).getCategories()) {
                    List<InnerProductsModel> products = category.getProducts();
                    for (int j = products.size() - 1; j >= 0; j--) {
                        if (products.get(j).isCanceled() || products.get(j).isDeleted()) {
                            products.remove(j);
                        }
                    }
                }
            }
        }
        mCartKitchenAdapter.updateList(orderItems);
        countPrices();
    }


    private void openMainFolder() {
        openFolder("0");
    }

    private void openFolder(String folderId) {
        Request.getInstance().getItemsInSelectedFolder(this, folderId, response -> {
            if (response.getBreadcrumbs().size() > 1)
                previousFolderId = response.getBreadcrumbs().get(response.getBreadcrumbs().size() - 2).getId();
            else
                previousFolderId = "0";
            mMenuAdapter.updateList(response.getBreadcrumbs());
            Collections.reverse(response.getItems()); // remove if comes from server in right order
            mFolderAdapter.updateList(response.getItems());

            closeInnerFragment();
        });
    }

    private void openItem(ProductItemModel item, boolean isFromKitchen) {
        closeInnerFragment();
        switch (item.getTypeName()) {
            case BUSINESS_ITEMS_TYPE_PIZZA:
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToPizzaAssemble(item, isFromKitchen));
                break;
            case BUSINESS_ITEMS_TYPE_DEAL:
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToDealAssemble(item, isFromKitchen));
                break;
            case BUSINESS_ITEMS_TYPE_DRINK:
            case BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER:
            case BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE:
//                if (!item.getCategories().isEmpty()) {
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToAdditionalOffer(item, isFromKitchen));
//                }
                break;
        }
        if (!isFromKitchen)
            Request.getInstance().getItemsInSelectedFolder(this, item.getFolderId(), response -> {

                switch (item.getTypeName()) {
                    case BUSINESS_ITEMS_TYPE_PIZZA:
                        response.getBreadcrumbs().add(new BreadcrumbModel(item.getId(), item.getName(), ITEM_TYPE_FOOD));
                        break;
                    case BUSINESS_ITEMS_TYPE_DEAL:
                        response.getBreadcrumbs().add(new BreadcrumbModel(item.getId(), item.getName(), ITEM_TYPE_DEAL));
                        break;
                    case BUSINESS_ITEMS_TYPE_DRINK:
                    case BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER:
                    case BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE:
                        if (!item.getCategories().isEmpty())
                            response.getBreadcrumbs().add(new BreadcrumbModel(item.getId(), item.getName(), ITEM_TYPE_FOOD));
                        break;
                }

                if (response.getBreadcrumbs().size() > 1)
                    previousFolderId = response.getBreadcrumbs().get(response.getBreadcrumbs().size() - 2).getId();
                else
                    previousFolderId = "0";
                mMenuAdapter.updateList(response.getBreadcrumbs());
                Collections.reverse(response.getItems()); // remove if comes from server in right order
                mFolderAdapter.updateList(response.getItems());

            });
    }

    private void getSourceItem(ProductItemModel fatherItem) {
        Request.getInstance().getOneProduct(this, fatherItem.getTypeName(), fatherItem.getSourceProductId(), response -> {
            ProductItemModel newItem = response.getProduct();

            newItem.setId(fatherItem.getId());
            newItem.setSourceProductId(fatherItem.getSourceProductId());

            for (CategoryModel category : newItem.getCategories())
                newItem.getSourceCategories().add(category.clone());

            newItem.getCategories().clear();
            newItem.getCategories().addAll(fatherItem.getCategories());

//            deals
            for (DealItemModel dealItem : newItem.getDealItems())
                newItem.getSourceDealItems().add(dealItem.clone());

            List<DealItemModel> dealItems = newItem.getDealItems();
            List<DealItemModel> existingDealItems = fatherItem.getDealItems();
            for (int i = 0; i < dealItems.size(); i++) {
                DealItemModel deal = dealItems.get(i);
                deal.setProducts(new ArrayList<>(existingDealItems.get(i).getProducts()));
            }

            for (DealItemModel dealSource : newItem.getSourceDealItems()) {
                dealSource.setSourceProducts(dealSource.getProducts());
            }

//            todo remember about deals
            openItem(newItem, true);
        });
    }

    @Override
    public void onBackPressed() {
        openFolder(previousFolderId);
    }

    private void closeInnerFragment() {
        NavController navController = Navigation.findNavController(binding.navHostFragment);
        switch (navController.getCurrentDestination().getId()) {
            case R.id.pizzaAssembleFragment:
                navController.navigate(PizzaAssembleFragmentDirections.clearView());
                return;
            case R.id.additionalOfferFragment:
                navController.navigate(AdditionalOfferFragmentDirections.clearView());
                return;
            case R.id.dealAssembleFragment:
                navController.navigate(DealAssembleFragmentDirections.clearView());
                return;
            case R.id.paymentFragment:
                navController.navigate(PaymentFragmentDirections.clearView());
                return;
        }
    }

    private void countPrices() {
        mTotalPriceSum = 0;

        for (ProductItemModel item : mCartAdapter.getItems()) {
            mTotalPriceSum += countProductPrice(item, type, false);
        }

        for (ProductItemModel item : mCartKitchenAdapter.getItems()) {
            mTotalPriceSum += countProductPrice(item, type, true);
        }

        mTotalPriceToSend = mTotalPriceSum;

        if (type.equals(NEW_ORDER_TYPE_ITEM) && deliveryPrice != 0) {
            mTotalPriceSum += deliveryPrice;
            binding.tvDeliveryPrice.setText(String.valueOf(deliveryPrice));
            binding.gDelivery.setVisibility(View.VISIBLE);
        } else if (type.equals(NEW_ORDER_TYPE_DELIVERY)) {
            mTotalPriceSum += BusinessModel.getInstance().getBusiness_delivery_cost();
            binding.tvDeliveryPrice.setText(String.valueOf(BusinessModel.getInstance().getBusiness_delivery_cost()));
            binding.gDelivery.setVisibility(View.VISIBLE);
        }
        double priceFinal = mTotalPriceSum;

        binding.tvTotalPrice.setText(String.format(Locale.US, "%.2f", priceFinal));

        mTotalPriceSum -= countPayments();

        binding.tvPay.setText(String.format(Locale.US, "שלם ₪%.2f", mTotalPriceSum));
//        binding.tvPay.setEnabled(mTotalPriceSum != 0); //todo open if needed
    }

    private double countPayments() {
        double sum = 0;
        for (PaymentModel payment : mPayments) {
            sum += Double.parseDouble(payment.getPrice());
        }
        return sum;
    }

    private boolean checkRequiredUserInfo() {
        switch (type) {
            case NEW_ORDER_TYPE_DELIVERY:
                if (
//                        mUserDetails.getLastName().isEmpty() ||
                        mUserDetails.getAddress().getCity().isEmpty() ||
                                mUserDetails.getAddress().getStreet().isEmpty() ||
                                mUserDetails.getAddress().getHouseNum().isEmpty())
                    return false;
                else {
                    if (mUserDetails.getAddress().getFloor().isEmpty())
                        mUserDetails.getAddress().setFloor("0");
                    if (mUserDetails.getAddress().getAptNum().isEmpty())
                        mUserDetails.getAddress().setAptNum("0");
                }
                break;
            case NEW_ORDER_TYPE_TAKEAWAY:
                if (mUserDetails.getName().isEmpty())
//                        || mUserDetails.getPhone().isEmpty())
                    return false;
        }
        return true;
    }

    private void completeCart() {
        JSONObject data = new JSONObject();
        Gson gson = new Gson();

        try {

            JSONArray cart = new JSONArray(gson.toJson(mCartAdapter.getClearItems()));
            //TODO GET THE CITY FROM DATA
            mUserDetails.getAddress().setCity("אשדוד");
            mUserDetails.getAddress().setCityId("124");
            JSONObject userInfo = new JSONObject(gson.toJson(mUserDetails));

            data.put("cart", cart);
            data.put("payment", mPaymentMethod);
            data.put("total", mTotalPriceToSend);
//            data.put("followOrder", 1); //todo edit when sms is ready
            data.put("addBy", "pos");
            data.put("deliveryOption", type);
            data.put("table_id", tableId);
            if (mColor != null && !mColor.isEmpty())
                data.put("color", mColor); //todo fix when color added
            data.put("userInfo", userInfo);
            data.put("business_id", BusinessModel.getInstance().getBusiness_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.gPb.setVisibility(View.VISIBLE);
        RequestHelper requestHelper = new RequestHelper();
        requestHelper.completeCartFromDb(this, data, response -> {
            binding.gPb.setVisibility(View.GONE);
            if (response.getOrder_id() != null) {
                if (!mPaymentsToPay.isEmpty())
                    createNewPayment(response.getOrder_id(), mPaymentsToPay);
//                if (mSumByCash != 0)
//                    createNewPayment(response.getOrder_id(), mSumByCash, PAYMENT_METHOD_CASH);
//                if (mSumByCard != 0)
//                    createNewPayment(response.getOrder_id(), mSumByCard, PAYMENT_METHOD_CARD);
                finish();
            }

        });
    }

    private void editCart() {
        JSONObject data = new JSONObject();
        JSONArray cartItems = new JSONArray();
        Gson gson = new Gson();


        try {
//            todo make edit cart
            for (ProductItemModel item : mCartAdapter.getClearItems()) {
                item.setChangeType(ORDER_CHANGE_TYPE_NEW);
                item.setOrderId(itemId);
                cartItems.put(new JSONObject(gson.toJson(item)));
            }

            for (ProductItemModel kitchenItem : mCartKitchenAdapter.getClearItems()) {
                fillSendItems(kitchenItem, cartItems);

                for (DealItemModel dealItem : kitchenItem.getDealItems()) {
                    for (ProductItemModel dealProduct : dealItem.getProducts()) {
                        fillSendItems(dealProduct, cartItems);
                    }
                }
            }

            JSONObject userInfo = new JSONObject();
            JSONObject notes = new JSONObject();

            if (!mUserDetails.getNotes().getOrder().equals(""))
                notes.put("order", mUserDetails.getNotes().getOrder());
            if (!mUserDetails.getNotes().getDelivery().equals(""))
                notes.put("delivery", mUserDetails.getNotes().getDelivery());

            userInfo.put("notes", notes);

            data.put("business_id", BusinessModel.getInstance().getBusiness_id());
            data.put("order_id", itemId);
            data.put("userInfo", userInfo);
            data.put("products", cartItems);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request.getInstance().editCart(this, data, isDataSuccess -> finish());
    }

    private void fillSendItems(ProductItemModel kitchenItem, JSONArray cartItems) {
        Gson gson = new Gson();
        try {
            switch (kitchenItem.getChangeType()) {
                case Constants.ORDER_CHANGE_TYPE_DELETED:
                    cartItems.put(
                            new JSONObject()
                                    .put("changeType", Constants.ORDER_CHANGE_TYPE_DELETED)
                                    .put("id", kitchenItem.getId())
                                    .put("order_id", kitchenItem.getOrderId()));
                    break;
                case ORDER_CHANGE_TYPE_NEW:
                    kitchenItem.setOrderId(itemId);
                    cartItems.put(new JSONObject(gson.toJson(kitchenItem)));
                    break;
            }

            for (CategoryModel category : kitchenItem.getCategories()) {
                for (InnerProductsModel topping : category.getProducts()) {
                    switch (topping.getChangeType()) {
                        case Constants.ORDER_CHANGE_TYPE_DELETED:
                            cartItems.put(
                                    new JSONObject()
                                            .put("changeType", Constants.ORDER_CHANGE_TYPE_DELETED)
                                            .put("id", topping.getId())
                                            .put("order_id", topping.getOrderId()));
                            break;
                        case ORDER_CHANGE_TYPE_NEW:
                            topping.setProductId(kitchenItem.getId());
                            topping.setSourceProductId(kitchenItem.getSourceProductId());
                            topping.setOrderId(itemId);
                            cartItems.put(new JSONObject(gson.toJson(topping)));
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openUserDetailsDialog() {
        UserDetailsDialog d = new UserDetailsDialog(this, mUserDetails, type, model -> {
            mUserDetails = model;
            if (checkRequiredUserInfo()) {
//                Request.getInstance().saveUserInfoWithNotes(this, model, isDataSuccess -> mUserDetails = model);
                completeCart();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        d.getWindow().setAttributes(lp);
        d.show();
    }

    public void openCommentDialog() {
        CommentDialog d = new CommentDialog(this, mUserDetails.getNotes().getOrder(),
                comment -> mUserDetails.getNotes().setOrder(comment));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        d.getWindow().setAttributes(lp);
        d.show();
    }

    public void openWarningDialog(boolean isClosed) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Warning")
                .setMessage("Are you sure you want ot perform that action?")
                .setPositiveButton(android.R.string.yes, (dialog, which) ->
                        Request.getInstance().openCloseTable(this, tableId, isClosed, isDataSuccess -> {
                            itemId = isClosed ? "-1" : "";
                            binding.tvOpenTable.setActivated(isClosed);
                            binding.tvOpenTable.setText(isClosed ? "Close" : "Open");
                            dialog.dismiss();
                        }))
                .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void openCancelOrderDialog() {
        if (type.equals(Constants.NEW_ORDER_TYPE_ITEM)) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Warning")
                    .setMessage("Are you sure you want ot cancel the order?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) ->
                            Request.getInstance().cancelOrder(this, itemId, isDataSuccess -> {
                                mCartAdapter.emptyCart();
                                CreateOrderActivity.this.finish();
                            }))
                    .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    //    item in folder click
    @Override
    public void onItemClick(FolderItemModel item) {
        int itemType = -1;

        ProductItemModel cartItem = new ProductItemModel(item);

        switch (item.getTypeName()) {
            case BUSINESS_ITEMS_TYPE_PIZZA:
                itemType = ITEM_TYPE_FOOD;

                for (CategoryModel category : cartItem.getCategories())
                    category.getProducts().clear();

                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToPizzaAssemble(cartItem, false));
                break;
            case BUSINESS_ITEMS_TYPE_DEAL:
                itemType = ITEM_TYPE_DEAL;

                for (DealItemModel deal : cartItem.getDealItems()) {
//                    if (!deal.getTypeName().equals(BUSINESS_ITEMS_TYPE_PIZZA))
                    deal.getProducts().clear();
                }

                for (DealItemModel dealSource : cartItem.getSourceDealItems()) {
                    dealSource.setSourceProducts(dealSource.getProducts());
                }

                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToDealAssemble(cartItem, false));
                break;
            case BUSINESS_ITEMS_TYPE_DRINK:
            case BUSINESS_ITEMS_TYPE_ADDITIONAL_OFFER:
            case BUSINESS_ITEMS_TYPE_ADDITIONAL_CHARGE:
                if (!item.getCategories().isEmpty()) {
                    itemType = ITEM_TYPE_FOOD;

                    for (CategoryModel category : cartItem.getCategories())
                        category.getProducts().clear();

                    Navigation.findNavController(binding.navHostFragment)
                            .navigate(ClearFragmentDirections.goToAdditionalOffer(cartItem, false));
                }
                break;
            default:
                return;
        }

        mCartAdapter.addItem(cartItem);
        countPrices();
        binding.tvEmptyCart.setVisibility(View.GONE);

        binding.rvCart.scrollToPosition(mCartAdapter.getItemCount() - 1);
        mCartPosition++;

        if (itemType != -1) {
            mMenuAdapter.addItem(new BreadcrumbModel(item.getId(), item.getName(), itemType));
            previousFolderId = item.getFolderId();
        }
    }


    @Override
    public void onFolderClick(String folderId) {
        openFolder(folderId);
    }

    @Override
    public void onToppingAdded(ProductItemModel item, boolean fromKitchen) {
        if (fromKitchen) mCartKitchenAdapter.editItem(item); //todo open when edit exists
        else
            mCartAdapter.editItem(item);
        countPrices();
    }

    @Override
    public void onFillingSelected(ProductItemModel item, boolean fromKitchen) {
        if (fromKitchen) mCartKitchenAdapter.editItem(item); //todo open when edit exists
        else
            mCartAdapter.editItem(item);
        countPrices();
    }

    @Override
    public void onDealItemsAdded(ProductItemModel item, boolean fromKitchen) {
        if (fromKitchen) mCartKitchenAdapter.editItem(item); //todo open when edit exists
        else
            mCartAdapter.editItem(item);
        countPrices();
    }

    @Override
    public void onPaid(String paymentMethod, double priceRemaining) {
        double paidPrice = mTotalPriceSum - priceRemaining;

        if (!type.equals(NEW_ORDER_TYPE_ITEM)) {
            double fullPrice = mTotalPriceToSend;
            if (type.equals(NEW_ORDER_TYPE_DELIVERY)) {
                fullPrice += BusinessModel.getInstance().getBusiness_delivery_cost();
            }
            if (fullPrice == paidPrice) {
                mPaymentMethod = paymentMethod;
            } else {
                switch (paymentMethod) {
                    case PAYMENT_METHOD_CASH:
                        mPaymentsToPay.add(new PaymentModel(String.valueOf(paidPrice), PAYMENT_METHOD_CASH));
//                        mSumByCash += paidPrice;
                        break;
                    case PAYMENT_METHOD_CARD:
                        mPaymentsToPay.add(new PaymentModel(String.valueOf(paidPrice), PAYMENT_METHOD_CARD));
//                        mSumByCard += paidPrice;
                        break;
                }
            }
        }

        mTotalPriceSum = priceRemaining;
        binding.tvPay.setText(String.format(Locale.US, "שלם ₪%.2f", priceRemaining));
//        binding.tvPay.setEnabled(priceRemaining != 0);
        if (printerPresenter != null) printerPresenter.openDrawer();
    }

    @Override
    public void onPrint(InvoiceResponse.InvoiceBean invoice) {
        printDoc(invoice);
    }

    private void createNewPayment(String orderId, List<PaymentModel> paymentModels) {
        Request.getInstance().createNewPayment(this, orderId, paymentModels, isDataSuccess -> {
        });
    }

    private void printDoc(InvoiceResponse.InvoiceBean invoice) {
        List<ProductItemModel> allOrderProducts = new ArrayList<>();
        allOrderProducts.addAll(mCartKitchenAdapter.getClearItems());
        allOrderProducts.addAll(mCartAdapter.getClearItems());
        if (printerPresenter != null) printerPresenter.print(invoice, allOrderProducts);
    }

    //    printer
    private InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {
            woyouService = service;
            printerPresenter = new PrinterPresenter(CreateOrderActivity.this, woyouService);
            binding.titleCashier.setOnClickListener(v -> printerPresenter.openDrawer());
        }

        @Override
        protected void onDisconnected() {
            woyouService = null;
        }
    };

    private void connectPrintService() {
        try {
            InnerPrinterManager.getInstance().bindService(this, innerPrinterCallback);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
    }

}
