package com.crunglers.wordsummit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

public class HomonymDialogFragment extends DialogFragment {

    private AlertDialog AD;
    String instructions = "Find words that sound like the given word within the time limit to earn points " +
            "and progress your character up the mountain!";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AD = new AlertDialog.Builder(requireContext())
                .setMessage(instructions)
                .setPositiveButton("Play", (dialog, which) -> {
                    Intent i = new Intent(getContext(), GameActivity.class);
                    i.putExtra("mode", "hom");
                    requireContext().startActivity(i);
                })
                .setNegativeButton("Go back", (dialog, which) -> {
                    dismiss();
                })
                .create();

        return AD;
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView textView = (TextView) AD.getWindow().findViewById(android.R.id.message);
        textView.setTypeface(ResourcesCompat.getFont(requireActivity(), R.font.visbyroundcf_demibold));

        Button negButton = AD.getButton(DialogInterface.BUTTON_NEGATIVE);
        negButton.setTypeface(ResourcesCompat.getFont(requireActivity(), R.font.visbyroundcf_demibold));
        negButton.setTextColor(getResources().getColor(R.color.text));
        negButton.setAllCaps(false);

        Button posButton = AD.getButton(DialogInterface.BUTTON_POSITIVE);
        posButton.setTypeface(ResourcesCompat.getFont(requireActivity(), R.font.visbyroundcf_demibold));
        posButton.setTextColor(getResources().getColor(R.color.text));
        posButton.setAllCaps(false);
    }
}
