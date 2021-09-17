package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogChooseColorBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;

import static com.pos.bringit.utils.Constants.COLOR_GREEN;
import static com.pos.bringit.utils.Constants.COLOR_ORANGE;
import static com.pos.bringit.utils.Constants.COLOR_PURPLE;
import static com.pos.bringit.utils.Constants.COLOR_RED;
import static com.pos.bringit.utils.Constants.COLOR_YELLOW;

public class ChooseColorDialog extends Dialog {

    private DialogChooseColorBinding binding;
    private ChooseColorListener listener;
    private ArrayList<String> colors;


    public ChooseColorDialog(@NonNull final Context context, ArrayList<String> colors, ChooseColorListener listener) {
        super(context);
        this.listener = listener;
        this.colors = colors;

        binding = DialogChooseColorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fillColors();

        binding.ivClose.setOnClickListener(v -> {
            chooseColors();
            dismiss();
        });

    }

    private void fillColors() {
        for (String color : colors) {
            switch (color) {
                case COLOR_GREEN: //green
                    binding.chbGreen.setChecked(true);
                    break;
                case COLOR_PURPLE: //purple
                    binding.chbPurple.setChecked(true);
                    break;
                case COLOR_RED: //red
                    binding.chbRed.setChecked(true);
                    break;
                case COLOR_ORANGE: //orange
                    binding.chbOrange.setChecked(true);
                    break;
                case COLOR_YELLOW: //yellow
                    binding.chbYellow.setChecked(true);
                    break;
            }
        }
    }

    private void chooseColors() {
        colors.clear();
        if (binding.chbGreen.isChecked()) colors.add(COLOR_GREEN);
        if (binding.chbPurple.isChecked()) colors.add(COLOR_PURPLE);
        if (binding.chbRed.isChecked()) colors.add(COLOR_RED);
        if (binding.chbOrange.isChecked()) colors.add(COLOR_ORANGE);
        if (binding.chbYellow.isChecked()) colors.add(COLOR_YELLOW);

        listener.onChose(colors);
    }

    public interface ChooseColorListener {
        void onChose(ArrayList<String> colors);
    }

}

