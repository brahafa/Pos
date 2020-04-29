package com.pos.bringit.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.pos.bringit.R;
import com.pos.bringit.adapters.FolderAdapter;
import com.pos.bringit.adapters.MenuAdapter;
import com.pos.bringit.databinding.ActivityCreateOrderBinding;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.pos.bringit.utils.SharedPrefs.getData;

public class CreateOrderActivity extends AppCompatActivity {

    private ActivityCreateOrderBinding binding;

    private MenuAdapter mMenuAdapter = new MenuAdapter(this::openFolder);
    private FolderAdapter mFolderAdapter = new FolderAdapter(this::openFolder);

    private String type;


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

        binding.holderBack.setOnClickListener(v -> onBackPressed());
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

        binding.rvFolders.setLayoutManager(new GridLayoutManager(this, 5, GridLayoutManager.HORIZONTAL, true));
        binding.rvFolders.setAdapter(mMenuAdapter);
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
            mMenuAdapter.updateList(response.getBreadcrumbs());
            mFolderAdapter.updateList(response.getItems());
        });
    }

}
