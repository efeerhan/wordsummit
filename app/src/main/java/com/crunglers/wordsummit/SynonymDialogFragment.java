package com.crunglers.wordsummit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SynonymDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog AD = new AlertDialog.Builder(requireContext())
                .setMessage("balls")
                .setPositiveButton("Next", (dialog, which) -> {
                    Intent i = new Intent(getContext(), GameActivity.class);
                    requireContext().startActivity(i);} )
                .setNegativeButton("Go back", (dialog, which) -> {
                    dismiss();
                })
                .create();

        /*TextView textView = (TextView)AD.findViewById(android.R.id.message);
        textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"font/visbyround.otf"));*/
        return AD;
    }
}
