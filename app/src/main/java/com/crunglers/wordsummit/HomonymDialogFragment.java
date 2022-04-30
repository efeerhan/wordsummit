package com.crunglers.wordsummit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class HomonymDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage("balls")
                .setPositiveButton("Next", (dialog, which) -> {
                    Intent i = new Intent(getContext(), SynonymGameActivity.class);
                    requireContext().startActivity(i);} )
                .create();
    }
}
