package com.crunglers.wordsummit;

import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;

import java.io.InputStream;

public class HomoGameMode extends GameMode {

    public HomoGameMode(Context context, RequestQueue queue, QueryDelegate delegate) {
        super(context, queue, delegate);
    }

    @Override
    protected String getQueryPrefix() {
        return "rel_hom=";
    }

    @Override
    protected InputStream getFileStream() {
        return context.getResources().openRawResource(R.raw.homophone_sources);
    }

    @Override
    protected String getModeTip() {
        return "Find words that sound the same as \"%s\", but are spelled differently";
    }


}
