package com.crunglers.wordsummit;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class StartFragment extends Fragment {

    Fragment selectFragment = new SelectFragment();

    public StartFragment() {
        super(R.layout.fragment_start);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle args) {
        super.onCreateView(inflater, container, args);
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        TransitionInflater tInflater = TransitionInflater.from(requireContext());
        setExitTransition(tInflater.inflateTransition(R.transition.slide_left));

        view.findViewById(R.id.startButton).setOnClickListener( v -> {
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.slide_out
                    )
                    .replace(R.id.fragment_container, selectFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}