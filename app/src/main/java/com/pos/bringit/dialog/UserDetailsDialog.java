package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

import com.pos.bringit.adapters.AutocompleteAdapter;
import com.pos.bringit.databinding.DialogUserDetailsBinding;
import com.pos.bringit.models.UserDetailsModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.FieldBgHandlerTextWatcher;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_DELIVERY;
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_TABLE;
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_TAKEAWAY;

public class UserDetailsDialog extends Dialog {

    private DialogUserDetailsBinding binding;
    private Context mContext;
    private SaveDetailsListener mListener;
    private String mCityId = "";
    private ArrayAdapter<String> adapter;

    public UserDetailsDialog(@NonNull final Context context, UserDetailsModel model, String orderType, SaveDetailsListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        binding = DialogUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initTextChangeListenersByType(orderType);

        int isDelivery = orderType.equals(NEW_ORDER_TYPE_TAKEAWAY)
                || orderType.equals(NEW_ORDER_TYPE_TABLE) ? View.GONE : View.VISIBLE;
        binding.rvAutocomplete.setVisibility(isDelivery);
        binding.gTakeAway.setVisibility(isDelivery);
        binding.gTable.setVisibility(orderType.equals(NEW_ORDER_TYPE_TABLE) ? View.GONE : View.VISIBLE);


        fillInfo(model);

        binding.tvSave.setOnClickListener(v -> fillModel(model));
        binding.ivClose.setOnClickListener(v -> dismiss());

    }

    private void initTextChangeListenersByType(String orderType) {

        switch (orderType) {
            case NEW_ORDER_TYPE_DELIVERY:

                //  AutocompleteAdapter autocompleteCityAdapter = new AutocompleteAdapter(new ArrayList<>(), autocompleteModel -> {
                //   mCityId = autocompleteModel.getId();
                // binding.edtCity.setText(autocompleteModel.getName());
                //binding.edtCity.setSelection(binding.edtCity.getText().length());
                //   ((AutocompleteAdapter) binding.rvAutocomplete.getAdapter()).clearList();
                //  });
                AutocompleteAdapter autocompleteStreetAdapter = new AutocompleteAdapter(new ArrayList<>(), autocompleteModel -> {
                    binding.edtStreet.setText(autocompleteModel.getName());
                    binding.edtStreet.setSelection(binding.edtStreet.getText().length());
                    ((AutocompleteAdapter) binding.rvAutocomplete.getAdapter()).clearList();
                });
                binding.rvAutocomplete.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, true));

//                binding.edtLastName.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtLastName, binding.tvTitleLastName));
//                binding.edtCity.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                        binding.rvAutocomplete.setAdapter(autocompleteCityAdapter);
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        binding.edtCity.setActivated(s.toString().isEmpty());
//                        binding.tvTitleCity.setActivated(s.toString().isEmpty());
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        if (s.length() > 1)
//                            Request.getInstance().searchCities(mContext, s.toString(), response ->
//                                    autocompleteCityAdapter.updateList(response.getCitiesList()));
//                    }
//                });
                binding.edtStreet.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        binding.rvAutocomplete.setAdapter(autocompleteStreetAdapter);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        binding.edtStreet.setActivated(s.toString().isEmpty());
                        binding.tvTitleStreet.setActivated(s.toString().isEmpty());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > 1)
                            Request.getInstance().searchStreets(mContext, s.toString(), "124", response ->
                                    autocompleteStreetAdapter.updateList(response.getStreetsList()));
                    }
                });
                binding.edtHouse.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtHouse, binding.tvTitleHouse));
                binding.edtName.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtName, binding.tvTitleName));
                binding.edtPhone.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        binding.edtPhone.setActivated(s.toString().isEmpty());
                        binding.tvTitlePhone.setActivated(s.toString().isEmpty());

                        binding.ivDonePhone.setVisibility(s.toString().length() == 10 ? View.VISIBLE : View.GONE);
                        binding.ivErrorPhone.setVisibility(!s.toString().startsWith("0") ? View.VISIBLE : View.GONE);
                        binding.tvSave.setEnabled(binding.ivDonePhone.getVisibility() == View.VISIBLE);
                        if (s.toString().length() == 10) {
                            Request.getInstance().getUserByPhone(mContext, s.toString(), response -> {
                                autoFillUserInfo(response.getUser());
                            });
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case NEW_ORDER_TYPE_TAKEAWAY:
                binding.edtPhone.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        binding.ivDonePhone.setVisibility(s.toString().length() == 10 ? View.VISIBLE : View.GONE);
                        if (s.toString().length() == 10) {
                            Request.getInstance().getUserByPhone(mContext, s.toString(), response -> {
                                autoFillUserInfo(response.getUser());
                            });
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                binding.edtName.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtName, binding.tvTitleName));

        }
    }


    private void fillInfo(UserDetailsModel model) {
        binding.edtName.setText(model.getName());
        binding.edtLastName.setText(model.getLastName());
        binding.edtPhone.setText(model.getPhone());

        // binding.edtCity.setText(model.getAddress().getCityName());
        binding.edtStreet.setText(model.getAddress().getStreet());
        binding.edtHouse.setText(model.getAddress().getHouseNum());
        binding.edtEntrance.setText(model.getAddress().getEntrance());
        binding.edtFloor.setText(model.getAddress().getFloor());
        binding.edtApt.setText(model.getAddress().getAptNum());

        binding.edtDetails.setText(model.getNotes().getDelivery());
    }

    private void fillModel(UserDetailsModel model) {
        model.setName(binding.edtName.getText().toString());
        model.setLastName(binding.edtLastName.getText().toString());
        model.setPhone(binding.edtPhone.getText().toString());


        model.getAddress().setCityId(mCityId);
        model.getAddress().setCity(binding.edtCity.getText().toString());
        model.getAddress().setStreet(binding.edtStreet.getText().toString());
        model.getAddress().setHouseNum(binding.edtHouse.getText().toString());
        model.getAddress().setEntrance(binding.edtEntrance.getText().toString());
        model.getAddress().setFloor(binding.edtFloor.getText().toString());
        model.getAddress().setAptNum(binding.edtApt.getText().toString());

        model.getNotes().setDelivery(binding.edtDetails.getText().toString());

        mListener.onSaved(model);
        dismiss();
    }

    private void autoFillUserInfo(UserDetailsModel model) {
        if (binding.edtName.getText().toString().isEmpty())
            binding.edtName.setText(model.getName());
        if (binding.edtLastName.getText().toString().isEmpty())
            binding.edtLastName.setText(model.getLastName());

        // binding.edtCity.setText(model.getAddress().getCityName());
        if (binding.edtStreet.getText().toString().isEmpty())
            binding.edtStreet.setText(model.getAddress().getStreet());
        if (binding.edtHouse.getText().toString().isEmpty())
            binding.edtHouse.setText(model.getAddress().getHouseNum());
        if (binding.edtEntrance.getText().toString().isEmpty())
            binding.edtEntrance.setText(model.getAddress().getEntrance());
        if (binding.edtFloor.getText().toString().isEmpty())
            binding.edtFloor.setText(model.getAddress().getFloor());
        if (binding.edtApt.getText().toString().isEmpty())
            binding.edtApt.setText(model.getAddress().getAptNum());

    }

    public interface SaveDetailsListener {
        void onSaved(UserDetailsModel model);
    }

}

