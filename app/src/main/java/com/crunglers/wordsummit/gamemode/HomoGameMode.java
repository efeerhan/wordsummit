package com.crunglers.wordsummit.gamemode;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.crunglers.wordsummit.R;
import com.crunglers.wordsummit.query.QueryDelegate;
import com.crunglers.wordsummit.query.ResultPool;

import java.io.InputStream;

public class HomoGameMode extends GameMode {

    public HomoGameMode(Context context, RequestQueue queue, QueryDelegate delegate) {
        super(context, queue, delegate);
    }

    @Override
    public void modifyPool(ResultPool pool) {
        return;
    }

    @Override
    protected String getQueryPrefix() {
        return "rel_hom=";
    }

    @Override
    protected String getExtraQueryFlags() {
        return "";
    }

    @Override
    protected InputStream getFileStream() {
        return context.getResources().openRawResource(R.raw.homophone_sources);
    }

    @Override
    public String getModeTip() {
        return "Find words that sound the same as \"%s\", but are spelled differently";
    }

    @Override
    public String getHighScoreTag() {
        return "homo_high_score";
    }


}
