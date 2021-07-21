package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogNewProductBinding;
import com.pos.bringit.models.NewProductModel;
import com.pos.bringit.utils.FieldBgHandlerTextWatcher;

import androidx.annotation.NonNull;

public class NewProductDialog extends Dialog {

    private DialogNewProductBinding binding;
    private Context mContext;
    private SaveDetailsListener mListener;

    public NewProductDialog(@NonNull final Context context, SaveDetailsListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        binding = DialogNewProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initTextChangeListenersByType();

        binding.tvSave.setOnClickListener(v -> fillModel());
        binding.ivClose.setOnClickListener(v -> dismiss());

    }

    private void initTextChangeListenersByType() {
        binding.edtName.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtName, binding.tvReqField));
        binding.edtPrice.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtPrice, binding.tvReqField));
        binding.edtDetails.addTextChangedListener(new FieldBgHandlerTextWatcher(binding.edtDetails, binding.tvReqField));
    }

    private void fillModel() {
        NewProductModel model = new NewProductModel();

        model.setName(binding.edtName.getText().toString());
        model.setPrice(binding.edtPrice.getText().toString());
        model.setNotes(binding.edtDetails.getText().toString());

        mListener.onSaved(model);
        dismiss();
    }

    public interface SaveDetailsListener {
        void onSaved(NewProductModel model);
    }

}

