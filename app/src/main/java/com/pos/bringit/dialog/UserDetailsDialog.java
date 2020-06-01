package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.pos.bringit.databinding.DialogUserDetailsBinding;
import com.pos.bringit.models.UserDetailsModel;
import com.pos.bringit.utils.FieldBgHandlerTextWatcher;

import androidx.annotation.NonNull;

import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_DELIVERY;
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_TABLE;
import static com.pos.bringit.utils.Constants.NEW_ORDER_TYPE_TAKEAWAY;


public class UserDetailsDialog extends Dialog {

    private DialogUserDetailsBinding binding;
    private SaveDetailsListener mListener;

    public UserDetailsDialog(@NonNull final Context context, UserDetailsModel model, String orderType, SaveDetailsListener listener) {
        super(context);
        mListener = listener;
        binding = DialogUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initTextChangeListenersByType(orderType);

        binding.gTakeAway.setVisibility(
                orderType.equals(NEW_ORDER_TYPE_TAKEAWAY)
                        || orderType.equals(NEW_ORDER_TYPE_TABLE) ? View.GONE : View.VISIBLE);
        binding.gTable.setVisibility(orderType.equals(NEW_ORDER_TYPE_TABLE) ? View.GONE : View.VISIBLE);


        fillInfo(model);

        binding.tvSave.setOnClickListener(v -> {
            fillModel(model);
            dismiss();
        });
        binding.ivClose.setOnClickListener(v -> dismiss());

    }

    private void initTextChangeListenersByType(String orderType) {

        switch (orderType) {
            case NEW_ORDER_TYPE_DELIVERY:
                binding.edtLastName.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtLastName, binding.tvTitleLastName));
                binding.edtCity.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtCity, binding.tvTitleCity));
                binding.edtStreet.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtStreet, binding.tvTitleStreet));
                binding.edtHouse.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtHouse, binding.tvTitleHouse));
            case NEW_ORDER_TYPE_TAKEAWAY:
                binding.edtName.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtName, binding.tvTitleName));
                binding.edtPhone.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        binding.edtPhone.setActivated(s.toString().isEmpty());
                        binding.tvTitlePhone.setActivated(s.toString().isEmpty());

                        binding.ivDonePhone.setVisibility(s.toString().length() > 9 ? View.VISIBLE : View.GONE);
                        binding.ivErrorPhone.setVisibility(!s.toString().startsWith("0") ? View.VISIBLE : View.GONE);
                        binding.tvSave.setEnabled(binding.ivDonePhone.getVisibility() == View.VISIBLE);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
        }
    }


    private void fillInfo(UserDetailsModel model) {
        binding.edtName.setText(model.getName());
        binding.edtLastName.setText(model.getLastName());
        binding.edtPhone.setText(model.getPhone());

        binding.edtCity.setText(model.getAddress().getCityName());
        binding.edtStreet.setText(model.getAddress().getStreet());
        binding.edtHouse.setText(model.getAddress().getHouseNum());
        binding.edtEntrance.setText(model.getAddress().getEntrance());
        binding.edtFloor.setText(model.getAddress().getFloor());
        binding.edtApt.setText(model.getAddress().getAptNum());

        binding.edtDetails.setText(model.getNotes().getOrder());
    }

    private void fillModel(UserDetailsModel model) {

        model.setName(binding.edtName.getText().toString());
        model.setLastName(binding.edtLastName.getText().toString());
        model.setPhone(binding.edtPhone.getText().toString());


        model.getAddress().setCityName(binding.edtCity.getText().toString());
        model.getAddress().setStreet(binding.edtStreet.getText().toString());
        model.getAddress().setHouseNum(binding.edtHouse.getText().toString());
        model.getAddress().setEntrance(binding.edtEntrance.getText().toString());
        model.getAddress().setFloor(binding.edtFloor.getText().toString());
        model.getAddress().setAptNum(binding.edtApt.getText().toString());

        model.getNotes().setOrder(binding.edtDetails.getText().toString());

        mListener.onSaved(model);
    }

    public interface SaveDetailsListener {
        void onSaved(UserDetailsModel model);
    }

}

