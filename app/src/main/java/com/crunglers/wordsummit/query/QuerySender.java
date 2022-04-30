package com.crunglers.wordsummit.query;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class QuerySender {
    private final static int MIN_SCORE_VAL = 400;
    private final QueryDelegate delegate;

    public QuerySender(QueryDelegate delegate) {
        this.delegate = delegate;
    }

    public  StringRequest performQuery(String url) {
        return new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<WordResult> queryWords = parseJSONString(response);
                        delegate.didReceiveQuery(new ResultPool(queryWords));
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("",error.toString());
                    }
                });
    }

    private  List<WordResult> parseJSONString(String string) {
        Gson gson = new Gson();
        Collection<WordResult> words;
        try {
            Type collectionType = new TypeToken<Collection<WordResult>>(){}.getType();
            words = gson.fromJson(string,collectionType);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return words.stream()
                .filter(w -> w.score > MIN_SCORE_VAL)
                .collect(Collectors.toList());
    }
}