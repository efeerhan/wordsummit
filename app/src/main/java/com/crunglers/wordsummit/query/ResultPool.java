package com.crunglers.wordsummit.query;

import java.util.List;

public class ResultPool {
    private List<WordResult> results;
    public final WordResult queryWord;


    public ResultPool(List<WordResult> results) {
        this.results = results;
        this.queryWord = results.remove(0);
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


    public void filterDiffTypes() {
        List<String> types = queryWord.types();
        results.removeIf(w -> !(types.stream().distinct().filter(w.types()::contains).count() > 0));
    }
}
