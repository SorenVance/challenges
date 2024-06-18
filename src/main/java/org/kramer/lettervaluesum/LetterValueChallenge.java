package org.kramer.lettervaluesum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static org.kramer.lettervaluesum.LetterValueSummer.sum;

public class LetterValueChallenge {
    private final List<String> data;

    LetterValueChallenge(Path challengeSumDataPath) throws IOException {
        data = Files.readAllLines(challengeSumDataPath);
    }

    public String findFirstWordWithSum(int sum) {
        return data.stream()
                .filter(input -> sum(input) == sum)
                .findFirst()
                .orElse("No word with a sum of " + sum + " found!");
    }

    public List<String> findAllOddSumStrings() {
        return data.stream()
                .filter(input -> sum(input) % 2 != 0)
                .toList();
    }

    public long countWordsWithSum(int sum) {
        return data.stream()
                .filter(input -> sum(input) == sum)
                .count();
    }

    public Map.Entry<Integer, Long> countMostWordsBySum() {
        Map<Integer, Long> countsOfSums = data.stream()
                .map(LetterValueSummer::sum)
                .collect(groupingBy(Function.identity(), counting()));

        return Collections.max(countsOfSums.entrySet(), Comparator.comparingLong(
                Map.Entry::getValue));
    }

    Map<Integer, List<EquivalentWordPair>> findEquivalentWordsWithSameSum(int differenceInLength) {
        Map<Integer, List<String>> result = data.stream()
                .collect(groupingBy(LetterValueSummer::sum, HashMap::new, toCollection(ArrayList::new)));

        return result.entrySet().stream()
                .map(e1 -> filterWordsByDifferenceInLength(e1, differenceInLength))
                .filter(e -> !e.getValue().isEmpty())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    static Map.Entry<Integer, List<EquivalentWordPair>> filterWordsByDifferenceInLength(Map.Entry<Integer, List<String>> e, int differenceInLength) {
        if (e.getValue().size() < 2)
            return new AbstractMap.SimpleEntry<>(e.getKey(), List.of());

        List<EquivalentWordPair> equivalentWords = new ArrayList<>();
        Map<Integer, String> wordsByLength = new HashMap<>();
        e.getValue().forEach(word -> {
            int wordLen = word.length();
            int wordLenPlusDiff = wordLen + differenceInLength;
            int wordLenMinusDiff = wordLen - differenceInLength;
            if (wordsByLength.containsKey(wordLenPlusDiff))
                equivalentWords.add(new EquivalentWordPair(word, wordsByLength.get(wordLenPlusDiff)));
            if (wordsByLength.containsKey(wordLenMinusDiff))
                equivalentWords.add(new EquivalentWordPair(word, wordsByLength.get(wordLenMinusDiff)));

            wordsByLength.put(wordLen, word);
        });
        return new AbstractMap.SimpleEntry<>(e.getKey(), equivalentWords);
    }

    record EquivalentWordPair(String word1, String word2) {
    }
}
