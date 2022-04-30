package com.example.myapplication;

import com.android.volley.RequestQueue;

public class HomoGameMode extends GameMode {

    public HomoGameMode(RequestQueue queue, QueryDelegate delegate) {
        super(queue, delegate);
    }

    @Override
    protected String getQueryPrefix() {
        return "rel_hom=";
    }


}
