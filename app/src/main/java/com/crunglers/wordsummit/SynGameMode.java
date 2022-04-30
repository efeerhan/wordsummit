package com.example.myapplication;

import com.android.volley.RequestQueue;

public class SynGameMode extends GameMode {

    public SynGameMode(RequestQueue queue, QueryDelegate delegate) {
        super(queue, delegate);
    }

    @Override
    protected String getQueryPrefix() {
        return "rel_syn=";
    }
}
