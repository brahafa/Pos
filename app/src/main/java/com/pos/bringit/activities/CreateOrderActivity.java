package com.pos.bringit.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;
import com.pos.bringit.R;
import com.pos.bringit.adapters.CartAdapter;
import com.pos.bringit.adapters.CartKitchenAdapter;
import com.pos.bringit.adapters.FolderAdapter;
import com.pos.bringit.adapters.MenuAdapter;
import com.pos.bringit.databinding.ActivityCreateOrderBinding;
import com.pos.bringit.fragments.ClearFragmentDirections;
import com.pos.bringit.fragments.DealAssembleFragment;
import com.pos.bringit.fragments.DealAssembleFragmentDirections;
import com.pos.bringit.fragments.PizzaAssembleFragment;
import com.pos.bringit.fragments.PizzaAssembleFragmentDirections;
import com.pos.bringit.models.BreadcrumbModel;
import com.pos.bringit.models.CartFillingModel;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.models.FillingModel;
import com.pos.bringit.models.FolderItemModel;
import com.pos.bringit.models.OrderItemsModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.pos.bringit.utils.Constants.ITEM_TYPE_DEAL;
import static com.pos.bringit.utils.Constants.ITEM_TYPE_FOOD;
import static com.pos.bringit.utils.SharedPrefs.getData;

public class CreateOrderActivity extends AppCompatActivity implements
        FolderAdapter.AdapterCallback, PizzaAssembleFragment.ToppingAddListener, DealAssembleFragment.DealItemsAddListener {

    private ActivityCreateOrderBinding binding;

    private List<CartModel> deletedItems = new ArrayList<>();
    private List<CartModel> addedItems = new ArrayList<>();

    private MenuAdapter mMenuAdapter = new MenuAdapter(this::openFolder);
    private CartKitchenAdapter mCartKitchenAdapter = new CartKitchenAdapter(this,
            new CartKitchenAdapter.AdapterCallback() {
                @Override
                public void onItemClick(CartModel fatherItem) {
                    openItem(fatherItem, true);
                }

                @Override
                public void onItemDuplicated(CartModel item) {
                    item.setPosition(mCartPosition);
                    mCartAdapter.duplicateItem(item);
                }

                @Override
                public void onItemRemoved(CartModel item, boolean isRemoved) {
                    if (isRemoved) deletedItems.add(item);
                    else deletedItems.remove(item);
                }

                @Override
                public void onItemAdded(CartModel item, boolean isAdded) {
                    if (isAdded) addedItems.add(item);
                    else addedItems.remove(item);
                }
            });
    private CartAdapter mCartAdapter = new CartAdapter(this, new CartAdapter.AdapterCallback() {
        @Override
        public void onItemClick(CartModel fatherItem) {
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
            if (isActive) closeInnerFragment();
            countPrices();
            binding.tvEmptyCart.setVisibility((mCartAdapter.getItemCount() == 0) ? View.VISIBLE : View.GONE);
        }
    });
    private FolderAdapter mFolderAdapter;

    private int mCartPosition = 0;
    private String type;
    private String itemId;
    private String previousFolderId = "";

    private double mTotalPriceSum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = CreateOrderActivityArgs.fromBundle(getIntent().getExtras()).getType();
        itemId = CreateOrderActivityArgs.fromBundle(getIntent().getExtras()).getItemId();
        Log.d("bundleType", type);
        Log.d("bundleItemId", itemId);

        initListeners();

        initRV();

        setInfo();

        openMainFolder();

        if (type.equals(Constants.NEW_ORDER_TYPE_ITEM))
            Request.getInstance().getOrderDetailsByID(this, itemId, orderDetailsResponse -> {
                fillKitchenCart(orderDetailsResponse.getOrder().getOrderItems());
            });
        else
            Request.getInstance().setDeliveryOption(this, type, isDataSuccess -> {
            });
    }

    private void initListeners() {

        binding.holderBack.setOnClickListener(v -> finish());
        binding.titleCashier.setOnClickListener(v -> {
        });
        binding.ivSearch.setOnClickListener(v -> {
        });

        binding.tvKitchenItemsTitle.setOnClickListener(v -> binding.rvCartKitchen.setVisibility(
                binding.rvCartKitchen.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));

        binding.tvHome.setOnClickListener(v -> openMainFolder());
        binding.tvSend.setOnClickListener(v -> completeCart());
    }

    private void initRV() {
        binding.rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binding.rvMenu.setAdapter(mMenuAdapter);

        binding.rvCartKitchen.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCartKitchen.setAdapter(mCartKitchenAdapter);

        binding.rvCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCart.setAdapter(mCartAdapter);

        mFolderAdapter = new FolderAdapter(type, this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this, FlexDirection.ROW_REVERSE);
        binding.rvFolders.setLayoutManager(layoutManager);
        binding.rvFolders.setAdapter(mFolderAdapter);
    }

    private void setInfo() {

        binding.gDetails.setVisibility(type.equals(Constants.NEW_ORDER_TYPE_ITEM) ? View.VISIBLE : View.GONE);

        binding.tvWaiterName.setText(getData(Constants.NAME_PREF));

        binding.tvTotalPrice.setText(String.valueOf(mTotalPriceSum));

        switch (type) {
            case Constants.NEW_ORDER_TYPE_TAKEAWAY:
                binding.ivLogoType.setImageResource(R.drawable.ic_take_away);
                binding.ivLogoType.setBackgroundColor(Color.parseColor("#503E9D")); //purple
                break;
            case Constants.NEW_ORDER_TYPE_DELIVERY:
                binding.ivLogoType.setImageResource(R.drawable.ic_delivery);
                binding.ivLogoType.setBackgroundColor(Color.parseColor("#FB6D3A")); //orange
                break;
        }
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

            if (itemKitchen.getItemFilling() != null)
                itemCart.setItem_filling(itemKitchen.getItemFilling());

            if (itemKitchen.getFatherId() != null)
                itemCart.setFatherId(itemKitchen.getFatherId());

            switch (itemKitchen.getItemType()) {
                case "Topping":
                    itemCart.setToppingLocation(itemKitchen.getLocation());
                    break;
                case "Deal":
                    itemCart.setValueJson(itemKitchen.getValueJson());
                    break;
            }

            tempList.add(itemCart);
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
    }


    private void openMainFolder() {
        openFolder("");
    }

    private void openFolder(String folderId) {
        Request.getInstance().getItemsInSelectedFolder(this, folderId, response -> {
            if (response.getBreadcrumbs().size() > 1)
                previousFolderId = response.getBreadcrumbs().get(response.getBreadcrumbs().size() - 2).getId();
            else
                previousFolderId = "";
            mMenuAdapter.updateList(response.getBreadcrumbs());
            Collections.reverse(response.getItems()); // remove if comes from server in right order
            mFolderAdapter.updateList(response.getItems());

            closeInnerFragment();
        });
    }

    private void openItem(CartModel item, boolean isFromKitchen) {
        closeInnerFragment();
        switch (item.getObject_type()) {
            case "Food":
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToPizzaAssemble(item, isFromKitchen));
                break;
            case "Deal":
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToDealAssemble(item, isFromKitchen));
                break;
        }

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
            case R.id.dealAssembleFragment:
                Navigation.findNavController(binding.navHostFragment).navigate(DealAssembleFragmentDirections.clearView());
        }
    }

    @Override
    public void onItemClick(FolderItemModel item) {
        int itemType;

        CartModel cartItem = new CartModel(
                item.getId(),
                mCartPosition,
                item.getObjectType(),
                item.getName(),
                type.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
                        ? item.getDeliveryPrice()
                        : item.getPickupPrice(),
                item.getObjectId());

        if (item.getFilling() != null) {
            List<CartFillingModel> fillingList = new ArrayList<>();
            for (FillingModel itemFilling : item.getFilling()) {
                fillingList.add(new CartFillingModel(
                        itemFilling.getName(),
                        type.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
                                ? itemFilling.getDeliveryPrice()
                                : itemFilling.getPickupPrice()));
            }
            cartItem.setItem_filling(fillingList);
        }


        mCartAdapter.addItem(cartItem);
        countPrices();
        binding.tvEmptyCart.setVisibility(View.GONE);

        binding.rvCart.scrollToPosition(mCartAdapter.getItemCount() - 1);
        mCartPosition++;

        switch (item.getObjectType()) {
            case "Food":
                itemType = ITEM_TYPE_FOOD;
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToPizzaAssemble(cartItem, false));
                break;
            case "Deal":
                cartItem.setValueJson(item.getValueJson());

                itemType = ITEM_TYPE_DEAL;
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToDealAssemble(cartItem, false));
                break;
            case "Drink":
            case "AdditionalOffer":
            default:
                return;
        }
        mMenuAdapter.addItem(new BreadcrumbModel(item.getId(), item.getName(), itemType));
        previousFolderId = item.getFatherId();
    }

    private void countPrices() {
        mTotalPriceSum = 0;

        for (CartModel item : mCartAdapter.getItems()) {
            mTotalPriceSum += item.getPrice();
            if (item.getItem_filling() != null) {
                for (CartFillingModel itemFilling : item.getItem_filling()) {
                    mTotalPriceSum += itemFilling.getPrice();
                }
            }
            if (!item.getToppings().isEmpty()) {
                for (CartModel itemTopping : item.getToppings()) {
                    mTotalPriceSum += itemTopping.getPrice();
                }
            }
        }
        binding.tvTotalPrice.setText(String.valueOf(mTotalPriceSum));
    }

    private void completeCart() {
        JSONObject data = new JSONObject();
        JSONObject cart = new JSONObject();
        Gson gson = new Gson();

        try {

            for (CartModel item : mCartAdapter.getItems()) {
                cart.put(item.getCartId(), new JSONObject(gson.toJson(item)));

                for (CartModel itemTopping : item.getToppings()) {
                    cart.put(itemTopping.getCartId(), new JSONObject(gson.toJson(itemTopping)));
                }
                for (CartModel itemDeal : item.getDealItems()) {
                    cart.put(itemDeal.getCartId(), new JSONObject(gson.toJson(itemDeal)));
                    for (CartModel itemToppingDeal : itemDeal.getToppings()) {
                        cart.put(itemToppingDeal.getCartId(), new JSONObject(gson.toJson(itemToppingDeal)));
                    }
                }
            }

            data.put("cart", cart);
            data.put("payment", "cash"); //todo edit when payment is ready
            data.put("total", mTotalPriceSum);
            data.put("followOrder", 2); //todo edit when sms is ready
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request.getInstance().completeCart(this, data, isDataSuccess -> finish());
    }

    @Override
    public void onFolderClick(String folderId) {
        openFolder(folderId);
    }

    @Override
    public void onToppingAdded(CartModel item, boolean fromKitchen) {
        if (fromKitchen) mCartKitchenAdapter.editItem(item);
        else mCartAdapter.editItem(item);
        countPrices();
    }

    @Override
    public void onDealItemsAdded(CartModel item, boolean fromKitchen) {
        if (fromKitchen) mCartKitchenAdapter.editItem(item);
        else mCartAdapter.editItem(item);
        countPrices();
    }
}
