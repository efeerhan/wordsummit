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
        for ( WordResult result : results ) {
            if (result.word.equals(trimmedGuess)) {
                results.remove(result);
                return true;
            }
        }
        return false;
    }
}
