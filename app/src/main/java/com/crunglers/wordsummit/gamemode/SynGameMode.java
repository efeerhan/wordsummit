package com.crunglers.wordsummit.gamemode;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.crunglers.wordsummit.R;
import com.crunglers.wordsummit.query.QueryDelegate;
import com.crunglers.wordsummit.query.ResultPool;

import java.io.InputStream;

public class SynGameMode extends GameMode {

    public SynGameMode(Context context, RequestQueue queue, QueryDelegate delegate) {
        super(context, queue, delegate);
    }

    @Override
    public void modifyPool(ResultPool pool) {
        pool.filterDiffTypes();
    }

    @Override
    protected String getQueryPrefix() {
        return "rel_syn=";
    }

    @Override
    protected String getExtraQueryFlags() {
        return "";
    }

    @Override
    protected InputStream getFileStream() {
        return context.getResources().openRawResource(R.raw.synonym_sources);
    }

    @Override
    public String getModeTip() {
        return "Type in synonyms for \"%s\"";
    }
}
