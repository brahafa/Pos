package com.pos.bringit.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.R;
import com.pos.bringit.adapters.CartAdapter;
import com.pos.bringit.adapters.FolderAdapter;
import com.pos.bringit.adapters.MenuAdapter;
import com.pos.bringit.databinding.ActivityCreateOrderBinding;
import com.pos.bringit.fragments.ClearFragmentDirections;
import com.pos.bringit.fragments.DealAssembleFragmentDirections;
import com.pos.bringit.fragments.PizzaAssembleFragment;
import com.pos.bringit.fragments.PizzaAssembleFragmentDirections;
import com.pos.bringit.models.BreadcrumbModel;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.models.FolderItemModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.Constants;

import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.pos.bringit.utils.Constants.ITEM_TYPE_DEAL;
import static com.pos.bringit.utils.Constants.ITEM_TYPE_FOOD;
import static com.pos.bringit.utils.SharedPrefs.getData;

public class CreateOrderActivity extends AppCompatActivity implements FolderAdapter.AdapterCallback, PizzaAssembleFragment.ToppingAddListener {

    private ActivityCreateOrderBinding binding;

    private MenuAdapter mMenuAdapter = new MenuAdapter(this::openFolder);
    private CartAdapter mCartAdapter = new CartAdapter(this, this::openItem);
    private FolderAdapter mFolderAdapter;

    private int mCartPosition = 0;
    private String type;
    private String previousFolderId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = CreateOrderActivityArgs.fromBundle(getIntent().getExtras()).getType();
        Log.d("bundleType", type);

        initListeners();

        initRV();

        setInfo();

        openMainFolder();
    }

    private void initListeners() {

        binding.holderBack.setOnClickListener(v -> finish());
        binding.titleCashier.setOnClickListener(v -> {
        });
        binding.ivSearch.setOnClickListener(v -> {
        });
        binding.tvHome.setOnClickListener(v -> openMainFolder());
    }

    private void initRV() {
        binding.rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binding.rvMenu.setAdapter(mMenuAdapter);

        binding.rvCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCart.setAdapter(mCartAdapter);

        mFolderAdapter = new FolderAdapter(type, this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this, FlexDirection.ROW_REVERSE);
        binding.rvFolders.setLayoutManager(layoutManager);
        binding.rvFolders.setAdapter(mFolderAdapter);
    }

    private void addToCart(String type, String itemId) {
//        Request.getInstance().addToCart(this, new CartModel(type, itemId), isDataSuccess -> {
//        });
    }


    private void setInfo() {
        binding.tvWaiterName.setText(getData(Constants.NAME_PREF));

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

    private void openItem(CartModel item) {
        closeInnerFragment();
        switch (item.getType()) {
            case "Food":
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToPizzaAssemble(item));
                break;
            case "Deal":
//                Navigation.findNavController(binding.navHostFragment)
//                        .navigate(ClearFragmentDirections.goToDealAssemble(item.getObjectId(), item.getValueJson()));
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
                        ? item.getDeliveryPrice() + " ₪"
                        : item.getPickupPrice() + " ₪",
                item.getObjectId());
        mCartAdapter.addItem(cartItem);
        binding.rvCart.scrollToPosition(mCartAdapter.getItemCount() - 1);
        mCartPosition++;

        switch (item.getObjectType()) {
            case "Food":
                itemType = ITEM_TYPE_FOOD;
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToPizzaAssemble(cartItem));
                break;
            case "Deal":
                itemType = ITEM_TYPE_DEAL;
                Navigation.findNavController(binding.navHostFragment)
                        .navigate(ClearFragmentDirections.goToDealAssemble(item.getObjectId(), item.getValueJson()));
                break;
            case "Drink":
            case "AdditionalOffer":
            default:
                return;
        }
        mMenuAdapter.addItem(new BreadcrumbModel(item.getId(), item.getName(), itemType));
        previousFolderId = item.getFatherId();
    }

    @Override
    public void onFolderClick(String folderId) {
        openFolder(folderId);
    }

    @Override
    public void onToppingAdded(CartModel item) {
        mCartAdapter.editItem(item);
    }
}
