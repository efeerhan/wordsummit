package com.crunglers.wordsummit;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SelectFragment extends Fragment {

    public SelectFragment() {
        super(R.layout.fragment_select);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle args) {
        super.onCreateView(inflater, container, args);

        View view = inflater.inflate(R.layout.fragment_select, container, false);
        TransitionInflater tInflater = TransitionInflater.from(requireContext());
        setEnterTransition(tInflater.inflateTransition(R.transition.slide_right));

        view.findViewById(R.id.SynonymSelect).setOnClickListener( v -> {
            new SynonymDialogFragment().show(getParentFragmentManager(), "SynonymSelect Dialog");
        });

        return view;
    }
}