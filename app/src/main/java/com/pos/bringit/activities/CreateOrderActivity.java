package com.pos.bringit.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.R;
import com.pos.bringit.adapters.FolderAdapter;
import com.pos.bringit.adapters.MenuAdapter;
import com.pos.bringit.databinding.ActivityCreateOrderBinding;
import com.pos.bringit.fragments.ClearFragmentDirections;
import com.pos.bringit.fragments.PizzaAssembleFragmentDirections;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.Constants;

import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.pos.bringit.utils.SharedPrefs.getData;

public class CreateOrderActivity extends AppCompatActivity {

    private ActivityCreateOrderBinding binding;

    private MenuAdapter mMenuAdapter = new MenuAdapter(this::openFolder);
    private FolderAdapter mFolderAdapter;

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
        binding.titleDetails.setOnClickListener(v -> {
        });
        binding.ivSearch.setOnClickListener(v -> {
        });
        binding.tvHome.setOnClickListener(v -> openMainFolder());
    }

    private void initRV() {
        binding.rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binding.rvMenu.setAdapter(mMenuAdapter);

        mFolderAdapter = new FolderAdapter(type, new FolderAdapter.AdapterCallback() {
            @Override
            public void onItemClick(String type, String itemId) {
//                addToCart(type, itemId);
                if (type.equals("Food")) {
                    Navigation.findNavController(binding.navHostFragment).navigate(ClearFragmentDirections.goToPizzaAssemble(itemId));
                }
            }

            @Override
            public void onFolderClick(String folderId) {
                closeInnerFragment();
                openFolder(folderId);
            }
        });
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this, FlexDirection.ROW_REVERSE);
        binding.rvFolders.setLayoutManager(layoutManager);
        binding.rvFolders.setAdapter(mFolderAdapter);
    }

    private void addToCart(String type, String itemId) {
        Request.getInstance().addToCart(this, new CartModel(type, itemId), isDataSuccess -> {
        });
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

    @Override
    public void onBackPressed() {
        if (!closeInnerFragment())
            openFolder(previousFolderId);
    }

    private boolean closeInnerFragment() {
        if (Navigation.findNavController(binding.navHostFragment).getCurrentDestination().getId() == R.id.pizzaAssembleFragment) {
            Navigation.findNavController(binding.navHostFragment).navigate(PizzaAssembleFragmentDirections.clearView());
            return true;
        }
        return false;
    }
}
