package com.crunglers.wordsummit;

import com.android.volley.RequestQueue;

public abstract class GameMode {
    private final RequestQueue queue;
    private final QuerySender sender;

    public GameMode(RequestQueue queue, QueryDelegate delegate) {
        this.queue = queue;
        sender = new QuerySender(delegate);
    }

    private static final String baseUrl = "https://api.datamuse.com/words?";
    private static final int MAX_RESULTS = 10;

    public void doQuery(String word) {
         queue.add(sender.performQuery(baseUrl + getQueryPrefix() + word + "&max=" + MAX_RESULTS));
    }

    protected abstract String getQueryPrefix();


}
