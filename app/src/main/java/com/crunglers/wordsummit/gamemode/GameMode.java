package com.crunglers.wordsummit.gamemode;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.crunglers.wordsummit.query.QueryDelegate;
import com.crunglers.wordsummit.query.QuerySender;
import com.crunglers.wordsummit.query.ResultPool;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GameMode {
    private static final String BASE_URL = "https://api.datamuse.com/words?";
    private static final int MAX_RESULTS = 10;

    private final List<String> baseWords = new ArrayList<>();
    protected Context context;
    private final RequestQueue queue;
    private final QuerySender sender;

    public GameMode(Context context, RequestQueue queue, QueryDelegate delegate) {
        this.context = context;
        this.queue = queue;
        sender = new QuerySender(delegate);
        baseWords.addAll(retrieveBaseWords());
    }

    /**
     * Starts query process using random base word
     * @return returns the baseword
     */
    public final String getRoundWord() {
        String word = baseWords.remove((int) (Math.random() * baseWords.size()));
        //System.out.println(BASE_URL + getQueryPrefix() + word + "&qe=" + getQueryPrefix().replace("=","") + "&max=" + MAX_RESULTS + "&md=fp" + getExtraQueryFlags());
        queue.add(sender.performQuery(BASE_URL + getQueryPrefix() + word + "&qe=" + getQueryPrefix().replace("=","") + "&max=" + MAX_RESULTS + "&md=fp" + getExtraQueryFlags()));
        return word;
    }

    private List<String> retrieveBaseWords() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileStream(), Charset.forName("UTF-8")))){
            return Arrays.asList(br.readLine().replaceAll("\"","").split(","));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected abstract void modifyPool(ResultPool pool);
    protected abstract String getQueryPrefix();
    protected abstract String getExtraQueryFlags();
    protected abstract InputStream getFileStream();
    protected abstract String getModeTip();
}
