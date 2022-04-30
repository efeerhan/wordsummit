package com.crunglers.wordsummit;

import android.util.Log;

import java.util.Arrays;

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
