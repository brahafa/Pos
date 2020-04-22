package com.pos.bringit.activities;

import android.os.Bundle;

import com.pos.bringit.databinding.ActivityMainBinding;
import com.pos.bringit.dialog.PasswordDialog;
import com.pos.bringit.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;

import static com.pos.bringit.utils.SharedPrefs.getData;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        initListeners();
        setContentView(binding.getRoot());
    }

    private void initListeners() {
        binding.holderSwitch.setOnClickListener(v -> {
            binding.swWebsite.setChecked(!binding.swWebsite.isChecked());
        });
        binding.titleTime.setOnClickListener(v -> {
        });
        binding.titleLock.setOnClickListener(v -> {
        });
        binding.titleSettings.setOnClickListener(v -> {
        });
        binding.titleSearch.setOnClickListener(v -> {
        });

        binding.ivOpenPassword.setOnClickListener(v -> openPasswordDialog());
    }

    public void openPasswordDialog() {
        PasswordDialog passwordDialog = new PasswordDialog(this);
        passwordDialog.setCancelable(false);
        passwordDialog.show();

        passwordDialog.setOnDismissListener(dialog -> {
            setNameAndRole();
        });
    }

    private void setNameAndRole() {
        binding.tvUserName.setText(getData(Constants.NAME_PREF));
        binding.tvUserRole.setText(getData(Constants.ROLE_PREF));
    }

    @Override
    public void onBackPressed() {
        openPasswordDialog();
    }
}
