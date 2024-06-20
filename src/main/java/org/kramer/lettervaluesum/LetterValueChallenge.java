package org.kramer.lettervaluesum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static org.kramer.lettervaluesum.LetterValueSummer.sum;

public class LetterValueChallenge {
    private final List<String> data;
    private final Map<Integer, List<String>> wordsBySum;

    LetterValueChallenge(Path challengeSumDataPath) throws IOException {
        data = Files.readAllLines(challengeSumDataPath);
        wordsBySum = data.stream().collect(
                groupingBy(LetterValueSummer::sum, HashMap::new, toCollection(ArrayList::new)));
    }

    public String findFirstWordWithSum(int sum) {
        return data.stream()
                .filter(input -> sum(input) == sum)
                .findFirst()
                .orElse("No word with a sum of " + sum + " found!");
    }

    public List<String> findAllOddSumStrings() {
        return data.stream().filter(input -> sum(input) % 2 != 0).toList();
    }

    public long countWordsWithSum(int sum) {
        return data.stream().filter(input -> sum(input) == sum).count();
    }

    public Map.Entry<Integer, Long> countMostWordsBySum() {
        Map<Integer, Long> countsOfSums = data.stream()
                .map(LetterValueSummer::sum)
                .collect(groupingBy(Function.identity(), counting()));

        return Collections.max(countsOfSums.entrySet(), Comparator.comparingLong(Map.Entry::getValue));
    }

    public Map<Integer, List<WordPair>> findEquivalentWordsWithSameSumAndDifferentLengths(int differenceInLength) {
        return wordsBySum.entrySet().stream()
                .map(e -> filterWordsByDifferenceInLength(e, differenceInLength))
                .filter(e -> !e.getValue().isEmpty())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    static Map.Entry<Integer, List<WordPair>> filterWordsByDifferenceInLength(Map.Entry<Integer, List<String>> e, int differenceInLength) {
        if (e.getValue().size() < 2) return new AbstractMap.SimpleEntry<>(e.getKey(), List.of());

        List<WordPair> equivalentWords = new ArrayList<>();
        Map<Integer, String> wordsByLength = new HashMap<>();
        e.getValue().forEach(word -> {
            int wordLen = word.length();
            int wordLenPlusDiff = wordLen + differenceInLength;
            int wordLenMinusDiff = wordLen - differenceInLength;
            if (wordsByLength.containsKey(wordLenPlusDiff))
                equivalentWords.add(new WordPair(word, wordsByLength.get(wordLenPlusDiff)));
            if (wordsByLength.containsKey(wordLenMinusDiff))
                equivalentWords.add(new WordPair(word, wordsByLength.get(wordLenMinusDiff)));

            wordsByLength.put(wordLen, word);
        });
        return new AbstractMap.SimpleEntry<>(e.getKey(), equivalentWords);
    }

    // region CHALLENGE 5
    public Map<Integer, List<WordPair>> findExclusiveWordsWithSameSum(int sum) {
        return wordsBySum.entrySet().stream()
                .map(this::findExclusiveWords)
                .filter(e -> !e.getValue().isEmpty())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map.Entry<Integer, List<WordPair>> findExclusiveWords(Map.Entry<Integer, List<String>> wordsEntry) {
        if (wordsEntry.getValue().size() < 2)
            return new AbstractMap.SimpleEntry<>(wordsEntry.getKey(), List.of());

        List<String> words = wordsEntry.getValue();
        int[] charMasks = extractCharMasksFromWords(words);
        List<WordPair> wordPairs = determineExclusiveWordPairs(words, charMasks);

        return new AbstractMap.SimpleEntry<>(wordsEntry.getKey(), wordPairs);
    }

    private static List<WordPair> determineExclusiveWordPairs(List<String> words, int[] charMasks) {
        List<WordPair> wordPairs = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            for (int j = i + 1; j < words.size(); j++) {
                if ((charMasks[i] & charMasks[j]) == 0)
                    wordPairs.add(new WordPair(words.get(i), words.get(j)));
            }
        }

        return wordPairs;
    }

    private int[] extractCharMasksFromWords(List<String> words) {
        int[] charMasks = new int[words.size()];
        IntStream.range(0, words.size())
                .parallel()
                .forEach(i -> charMasks[i] = extractMaskForWord(words.get(i)));
        return charMasks;
    }

// end region CHALLENGE 5

    private int extractMaskForWord(String word) {
        int mask = 0;
        for (char c : word.toCharArray()) {
            mask |= 1 << (c - 'a');
        }
        return mask;
    }

    public record WordPair(String word1, String word2) {
    }
}
