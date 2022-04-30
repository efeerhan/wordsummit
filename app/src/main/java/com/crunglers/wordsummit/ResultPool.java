package com.crunglers.wordsummit;

import java.util.List;

public class ResultPool {
    private List<WordResult> results;

    public ResultPool(List<WordResult> results) {
        this.results = results;
    }

    public int wordsLeft() {
        return results.size();
    }

    public boolean checkGuess(String guess) {
        String trimmedGuess = guess.trim();
        if (results.contains(trimmedGuess)) {
            results.remove(trimmedGuess);
            return true;
        }
        return false;
    }
}
