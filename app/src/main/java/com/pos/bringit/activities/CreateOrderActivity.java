package com.pos.bringit.activities;

import android.graphics.Color;
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
import com.pos.bringit.models.OrderItemsModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.models.UserDetailsModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.Constants;
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

import androidx.appcompat.app.AppCompatActivity;
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
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_TABLE;
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_TAKEAWAY;
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

    private CartKitchenAdapter mCartKitchenAdapter = new CartKitchenAdapter(this,
            new CartKitchenAdapter.AdapterCallback() {
                @Override
                public void onItemClick(CartModel fatherItem) {
//                    openItem(fatherItem, true); //todo fix when edit exists
                }

                @Override
                public void onItemDuplicated(CartModel item) {
                    item.setPosition(mCartPosition);
//                    mCartAdapter.duplicateItem(item); //todo fix when edit exists
                }

                @Override
                public void onItemRemoved(CartModel item) {
                    if (kitchenItems.contains(item)) kitchenItems.remove(item);
                    else kitchenItems.add(item);
                }

            });

    private CartAdapter mCartAdapter;
    private FolderAdapter mFolderAdapter;

    private int mCartPosition = 0;
    private String mPaymentMethod = "noPay";
    private String mColor = "";
    private String type;
    private String itemId;
    private String tableId;
    private String previousFolderId = "0";

    private double mTotalPriceSum = 0;
    private double mKitchenPriceSum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUserDetails = new UserDetailsModel();

        type = CreateOrderActivityArgs.fromBundle(getIntent().getExtras()).getType();
        tableId = CreateOrderActivityArgs.fromBundle(getIntent().getExtras()).getTableId();
        itemId = CreateOrderActivityArgs.fromBundle(getIntent().getExtras()).getItemId();
        Log.d("bundleType", type);
        Log.d("bundleItemId", itemId);

        initListeners();

        initRV();

        setInfo();

        openMainFolder();

        connectPrintService();

        if (type.equals(Constants.NEW_ORDER_TYPE_ITEM))
            Request.getInstance().getOrderDetailsByID(this, itemId, orderDetailsResponse -> {
                fillKitchenCart(orderDetailsResponse.getOrder().getOrderItems());
            });
        else if (type.equals(NEW_ORDER_TYPE_TABLE)) binding.cvOpenTable.setVisibility(View.VISIBLE);

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
            closeInnerFragment();
            Navigation.findNavController(binding.navHostFragment)
                    .navigate(ClearFragmentDirections.goToPayment(String.valueOf(mTotalPriceSum)));
        });

        binding.tvPrint.setOnClickListener(v -> {
            if (printerPresenter != null) {
                printerPresenter.print(mCartAdapter.getItems(), 1, type);
            }
        });

        binding.tvComment.setOnClickListener(v -> openCommentDialog());
        binding.tvDetails.setOnClickListener(v -> openUserDetailsDialog());
        binding.tvOpenTable.setOnClickListener(v -> {
        });
        binding.tvClearCart.setOnClickListener(v -> mCartAdapter.emptyCart());
    }

    private void addColorChooseListeners() {
        View.OnClickListener cursorClickListener = v -> binding.layoutChooseColor.getRoot().setVisibility(
                binding.layoutChooseColor.getRoot().getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

        View.OnClickListener redClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_red);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#E93746"; //red
        };
        View.OnClickListener greenClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_green);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#419D3E"; //green
        };
        View.OnClickListener blueClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_blue);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#2251f3"; //blue
        };
        View.OnClickListener yellowClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_yellow);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#FACD5D"; //yellow
        };
        View.OnClickListener orangeClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_orange);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = "#FB6D3A"; //orange
        };
        View.OnClickListener whiteClickListener = v -> {
            binding.ivCursor.setBackgroundResource(R.drawable.background_top_text_create);
            binding.layoutChooseColor.getRoot().setVisibility(View.GONE);
            mColor = ""; //white
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


    private void initRV() {
        binding.rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binding.rvMenu.setAdapter(mMenuAdapter);

        binding.rvCartKitchen.setLayoutManager(new LinearLayoutManager(this));
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
        binding.tvPay.setEnabled(false);

        binding.gDetails.setVisibility(type.equals(Constants.NEW_ORDER_TYPE_ITEM) ? View.VISIBLE : View.GONE);
        setCartOpenDrawable(type.equals(Constants.NEW_ORDER_TYPE_ITEM));

        binding.tvOrderNumber.setText(type.equals(Constants.NEW_ORDER_TYPE_ITEM) ? "#" + itemId : "New order");
        binding.tvWaiterName.setText(getData(Constants.NAME_PREF));

        binding.tvTotalPrice.setText(String.valueOf(mTotalPriceSum));

        switch (type) {
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

    private void fillKitchenCart(List<OrderItemsModel> orderItems) {
        List<CartModel> tempList = new ArrayList<>();
        for (OrderItemsModel itemKitchen : orderItems) {

            CartModel itemCart = new CartModel(
                    itemKitchen.getItemId(),
                    Integer.parseInt(itemKitchen.getCartId().substring(5)),
                    itemKitchen.getItemType(),
                    itemKitchen.getName(),
                    itemKitchen.getPrice(),
                    itemKitchen.getItemId());

            itemCart.setCategory(itemKitchen.getCategory());
            itemCart.setChangeType(itemKitchen.getChangeType());

            itemCart.setFolderId(itemKitchen.getFolderId());

            itemCart.setPizzaType(itemKitchen.getShape());

            if (itemKitchen.getItemFilling() != null)
                itemCart.setItem_filling(itemKitchen.getItemFilling());

            if (itemKitchen.getFatherId() != null)
                itemCart.setFatherId(itemKitchen.getFatherId());

            switch (itemKitchen.getItemType()) {
                case "Topping":
                    itemCart.setToppingLocation(itemKitchen.getLocation());
                    break;
                case "Deal":
//                    itemCart.setValueJson(itemKitchen.getValueJson()); // fixme: set deals from order
                    break;
            }

            tempList.add(itemCart);

            mKitchenPriceSum += itemKitchen.getPrice();
        }

        List<CartModel> kitchenList = new ArrayList<>();

        for (CartModel itemTemp : tempList)
            if (itemTemp.getFatherId() != null)
                for (CartModel itemParent : tempList)
                    if (itemParent.getCartId().equals(itemTemp.getFatherId()))
                        if (itemTemp.getObject_type().equals("Topping")) itemParent.getToppings().add(itemTemp);
                        else itemParent.getDealItems().add(itemTemp);

        for (CartModel itemParent : tempList)
            if (itemParent.getFatherId() == null)
                kitchenList.add(itemParent);

        mCartPosition = kitchenList.get(kitchenList.size() - 1).getPosition() + 1;
        mCartKitchenAdapter.updateList(kitchenList);
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
                if (!item.getCategories().isEmpty()) {
                    Navigation.findNavController(binding.navHostFragment)
                            .navigate(ClearFragmentDirections.goToAdditionalOffer(item, isFromKitchen));
                }
                break;
        }
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

    @Override
    public void onBackPressed() {
        openFolder(previousFolderId);
    }

    private void closeInnerFragment() {
        switch (Navigation.findNavController(binding.navHostFragment).getCurrentDestination().getId()) {
            case R.id.pizzaAssembleFragment:
                Navigation.findNavController(binding.navHostFragment).navigate(PizzaAssembleFragmentDirections.clearView());
                return;
            case R.id.additionalOfferFragment:
                Navigation.findNavController(binding.navHostFragment).navigate(AdditionalOfferFragmentDirections.clearView());
                return;
            case R.id.dealAssembleFragment:
                Navigation.findNavController(binding.navHostFragment).navigate(DealAssembleFragmentDirections.clearView());
                return;
            case R.id.paymentFragment:
                Navigation.findNavController(binding.navHostFragment).navigate(PaymentFragmentDirections.clearView());
                return;
        }
    }

    private void countPrices() {
        mTotalPriceSum = 0;

        mTotalPriceSum += mKitchenPriceSum;

        for (ProductItemModel item : mCartAdapter.getItems()) {
            mTotalPriceSum += countProductPrice(item, type);
        }

        binding.tvTotalPrice.setText(String.valueOf(mTotalPriceSum));
        binding.tvPay.setText(String.format("שלם ₪%s", mTotalPriceSum));
        binding.tvPay.setEnabled(mTotalPriceSum != 0);
    }

    private boolean checkRequiredUserInfo() {
        switch (type) {
            case NEW_ORDER_TYPE_DELIVERY:
                if (mUserDetails.getLastName().isEmpty()
                        || mUserDetails.getAddress().getCityName().isEmpty()
                        || mUserDetails.getAddress().getStreet().isEmpty()
                        || mUserDetails.getAddress().getHouseNum().isEmpty())
                    return false;
                else {
                    if (mUserDetails.getAddress().getFloor().isEmpty()) mUserDetails.getAddress().setFloor("0");
                    if (mUserDetails.getAddress().getAptNum().isEmpty()) mUserDetails.getAddress().setAptNum("0");
                }
                break;
            case NEW_ORDER_TYPE_TAKEAWAY:
                if (mUserDetails.getPhone().isEmpty() || mUserDetails.getName().isEmpty())
                    return false;
        }
        return true;
    }

    private void completeCart() {
        JSONObject data = new JSONObject();
        Gson gson = new Gson();

        try {

            JSONArray cart = new JSONArray(gson.toJson(mCartAdapter.getClearItems()));
            JSONObject userInfo = new JSONObject(gson.toJson(mUserDetails));

            data.put("cart", cart);
            data.put("payment", mPaymentMethod);
            data.put("total", mTotalPriceSum);
            data.put("followOrder", 2); //todo edit when sms is ready
            data.put("deliveryOption", type);
            data.put("table_id", tableId);
            data.put("color", mColor); //todo fix when color added
            data.put("userInfo", userInfo);
            data.put("business_id", BusinessModel.getInstance().getBusiness_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request.getInstance().completeCart(this, data, isDataSuccess -> finish());
    }

    private void editCart() {
        JSONObject data = new JSONObject();
        JSONArray cartItems = new JSONArray();
        Gson gson = new Gson();

        try {
//            todo make edit cart
//            for (CartModel item : mCartAdapter.getItems()) {
//                item.setChangeType(ORDER_CHANGE_TYPE_NEW);
//                cartItems.put(new JSONObject(gson.toJson(item)));
//
//                for (CartModel itemTopping : item.getToppings()) {
//                    itemTopping.setChangeType(ORDER_CHANGE_TYPE_NEW);
//                    cartItems.put(new JSONObject(gson.toJson(itemTopping)));
//                }
//                for (CartModel itemDeal : item.getDealItems()) {
//                    itemDeal.setChangeType(ORDER_CHANGE_TYPE_NEW);
//                    cartItems.put(new JSONObject(gson.toJson(itemDeal)));
//                    for (CartModel itemToppingDeal : itemDeal.getToppings()) {
//                        itemToppingDeal.setChangeType(ORDER_CHANGE_TYPE_NEW);
//                        cartItems.put(new JSONObject(gson.toJson(itemToppingDeal)));
//                    }
//                }
//            }
//
//            for (CartModel kitchenItem : kitchenItems) {
//                if (kitchenItem.getChangeType().equals(Constants.ORDER_CHANGE_TYPE_DELETED))
//                    cartItems.put(
//                            new JSONObject()
//                                    .put("changeType", kitchenItem.getChangeType())
//                                    .put("id", kitchenItem.getObjectId()));
//                else
//                    cartItems.put(new JSONObject(gson.toJson(kitchenItem)));
//
//            }

            data.put("order_id", itemId);
            data.put("items", cartItems);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request.getInstance().editCart(this, data, isDataSuccess -> finish());
    }

    public void openUserDetailsDialog() {
        UserDetailsDialog d = new UserDetailsDialog(this, mUserDetails, type, model -> mUserDetails = model);
//                Request.getInstance().saveUserInfoWithNotes(this, model, isDataSuccess -> mUserDetails = model));
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

    //    item in folder click
    @Override
    public void onItemClick(FolderItemModel item) {
        int itemType = -1;

        ProductItemModel cartItem = new ProductItemModel(item);

        switch (item.getTypeName()) {
            case BUSINESS_ITEMS_TYPE_PIZZA:
                itemType = ITEM_TYPE_FOOD;

                for (CategoryModel category : cartItem.getCategories()) category.getProducts().clear();

                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToPizzaAssemble(cartItem, false));
                break;
            case BUSINESS_ITEMS_TYPE_DEAL:
                itemType = ITEM_TYPE_DEAL;

                for (DealItemModel deal : cartItem.getDealItems()) deal.getProducts().clear();

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

                    for (CategoryModel category : cartItem.getCategories()) category.getProducts().clear();

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
//        if (fromKitchen) mCartKitchenAdapter.editItem(item); //todo open when edit exists
//        else
        mCartAdapter.editItem(item);
        countPrices();
    }

    @Override
    public void onFillingSelected(ProductItemModel item, boolean fromKitchen) {
//        if (fromKitchen) mCartKitchenAdapter.editItem(item); //todo open when edit exists
//        else
        mCartAdapter.editItem(item);
        countPrices();
    }

    @Override
    public void onDealItemsAdded(ProductItemModel item, boolean fromKitchen) {
//        if (fromKitchen) mCartKitchenAdapter.editItem(item); //todo open when edit exists
//        else
        mCartAdapter.editItem(item);
        countPrices();
    }

    @Override
    public void onPaid(String paymentMethod) {
        mPaymentMethod = paymentMethod;
    }

    private InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {
            woyouService = service;
            printerPresenter = new PrinterPresenter(CreateOrderActivity.this, woyouService);
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
