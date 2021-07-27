package com.pos.bringit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ActivityMainBinding;
import com.pos.bringit.dialog.ExitDialog;
import com.pos.bringit.dialog.PasswordDialog;
import com.pos.bringit.fragments.MainFragment;
import com.pos.bringit.fragments.MainFragmentDirections;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.MyExceptionHandler;
import com.pos.bringit.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import static com.pos.bringit.utils.SharedPrefs.getData;
import static com.pos.bringit.utils.SharedPrefs.saveData;

public class MainActivity extends AppCompatActivity implements MainFragment.OnLoggedInManagerListener {

    private final int TYPE_LOGIN = 0;
    private final int TYPE_OTHER_WORKER = 1;
    private final int TYPE_SWITCH_BUSINESS = 2;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));

        initListeners();
        setContentView(binding.getRoot());
    }

    private void initListeners() {

        binding.holderSwitch.setOnClickListener(v -> openPasswordDialog(TYPE_SWITCH_BUSINESS));
        binding.titleTime.setOnClickListener(v -> openPasswordDialog(TYPE_OTHER_WORKER));
        binding.titleSearch.setOnClickListener(v -> goToSearchOrder());
        binding.titleCashbox.setOnClickListener(v -> goToCashBox());
//        binding.titleSettings.setOnClickListener(v -> {
//        });
        binding.titleExit.setOnClickListener(v -> openExitDialog());

//        binding.ivOpenPassword.setOnClickListener(v -> openPasswordDialog());
        binding.tvUserName.setOnClickListener(v -> openPasswordDialog());
    }


    public void openExitDialog() {
        ExitDialog exitDialog = new ExitDialog(this, new ExitDialog.ExitListener() {
            @Override
            public void onExit() {
                finish();
            }

            @Override
            public void onLogout() {
                saveData(Constants.TOKEN_PREF, "");

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        exitDialog.show();
    }

    public void openPasswordDialog() {
        openPasswordDialog(TYPE_LOGIN);
    }

    public void openPasswordDialog(int type) {
        PasswordDialog passwordDialog = new PasswordDialog(this);
        passwordDialog.setCancelable(type != TYPE_LOGIN);
        passwordDialog.setCancelButton(type != TYPE_LOGIN);
        passwordDialog.setOtherWorker(type == TYPE_OTHER_WORKER);
        passwordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        passwordDialog.show();

        passwordDialog.setOnDismissListener(dialog -> {
            switch (type) {
                case TYPE_OTHER_WORKER:
                    if (passwordDialog.getWorker() != null)
                        goToWorkersClock(passwordDialog.getWorker().getId());
                    break;
                case TYPE_SWITCH_BUSINESS:
                    if (passwordDialog.getWorker() != null)
                        if (passwordDialog.getWorker().getPermissions().getOpenCloseBusiness().equals("1"))
                            binding.swWebsite.setChecked(!binding.swWebsite.isChecked());
                        else Utils.openPermissionAlertDialog(this);
                default:
                    setNameAndRole();
                    break;
            }
        });
    }

    private void goToWorkersClock(String workerId) {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(
                MainFragmentDirections.actionMainFragmentToWorkersClockActivity(workerId));
    }

    private void goToSearchOrder() {
        Navigation.findNavController(this, R.id.nav_host_fragment)
                .navigate(MainFragmentDirections.actionMainFragmentToSearchOrderActivity());
    }

    private void goToCashBox() {
        Navigation.findNavController(this, R.id.nav_host_fragment)
                .navigate(MainFragmentDirections.actionMainFragmentToCashBoxActivity());
    }

    private void setNameAndRole() {
        binding.tvUserName.setText(getData(Constants.NAME_PREF));
//        binding.tvUserRole.setText(getData(Constants.ROLE_PREF));
    }

    private void checkBusinessStatus() {
        Request.getInstance().checkBusinessStatus(this, this::setBusinessStatus);
    }

    private void changeBusinessStatus(boolean isOpen) {
        Request.getInstance().changeBusinessStatus(this, isOpen, isDataSuccess -> setBusinessStatus(isOpen));
    }

    private void setBusinessStatus(boolean isBusinessOpen) {
        binding.swWebsite.setChecked(!isBusinessOpen);
        binding.titleSwitch.setText(!isBusinessOpen ? "אתר פעיל" : "אתר לא פעיל");

        binding.swWebsite.setOnCheckedChangeListener((buttonView, isChecked) -> changeBusinessStatus(!isChecked));
    }

    @Override
    public void onBackPressed() {
        openPasswordDialog();
    }

    @Override
    public void onLoggedIn(boolean isLoggedIn) {
        checkBusinessStatus();
        setNameAndRole();
    }

    @Override
    public void onBusinessStatusCheck() {
        checkBusinessStatus();
    }
}
