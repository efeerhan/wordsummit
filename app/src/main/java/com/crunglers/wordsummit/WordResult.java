package com.crunglers.wordsummit;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WordResult {
    public String word;
    public long score;
    public String[] tags;



    public double freq() {
        String freqString = Arrays.stream(tags).filter(s -> s.charAt(0) == 'f').findFirst().orElse(null);
        if (freqString == null) {
            Log.i("",word + " Has no frequency tag");
            return 0.0;
        }
        return Double.parseDouble(freqString.substring(2));
    }

    public List<String> types() {
        List<String> types = Arrays.stream(tags).filter(s -> s.charAt(0) != 'f').collect(Collectors.toList());
        if (types == null) {{
            Log.i("",word + "Has no word type tags");
            return Collections.emptyList();
        }}
        return types;
    }

    @Override
    public String toString() {
        return "WordResult{" +
                "word='" + word + '\'' +
                ", score=" + score +
                ", tags=" + Arrays.toString(tags) +
                ", freq=" + freq() +
                '}';
    }
}
