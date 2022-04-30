package com.crunglers.wordsummit;

import android.content.Context;
import com.android.volley.RequestQueue;

import java.io.InputStream;

public class SynGameMode extends GameMode {

    public SynGameMode(Context context, RequestQueue queue, QueryDelegate delegate) {
        super(context, queue, delegate);
    }

    @Override
    protected void modifyPool(ResultPool pool) {
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
    protected String getModeTip() {
        return "Type in synonyms for \"%s\"";
    }
}
