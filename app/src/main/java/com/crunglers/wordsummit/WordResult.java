package com.crunglers.wordsummit;

import java.util.Arrays;

public class WordResult {
    public String word;
    public long score;
    public String[] tags;


    @Override
    public String toString() {
        return "WordResult{" +
                "word='" + word + '\'' +
                ", score=" + score +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
