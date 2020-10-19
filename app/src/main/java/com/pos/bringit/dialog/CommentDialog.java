package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.pos.bringit.adapters.AutocompleteAdapter;
import com.pos.bringit.databinding.DialogCommentBinding;
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


public class CommentDialog extends Dialog {

    private DialogCommentBinding binding;
    private CommentListener mListener;

    public CommentDialog(@NonNull final Context context, String comment, CommentListener listener) {
        super(context);
        mListener = listener;
        binding = DialogCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edtComment.setText(comment);

        binding.tvSave.setOnClickListener(v -> {
            String text = binding.edtComment.getText().toString();
            mListener.onSaved(text);
            dismiss();
        });
        binding.ivClose.setOnClickListener(v -> dismiss());

    }

    public interface CommentListener {
        void onSaved(String comment);
    }

}

