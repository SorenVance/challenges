package org.kramer.lettervaluesum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kramer.lettervaluesum.LetterValueChallenge.WordPair;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LetterValueChallengeTests {
    private static final LetterValueChallenge challenge;

    static {
        try {
            Path challengeSumDataPath = Paths.get("src", "test", "resources", "characterSumChallengeData.txt");
            challenge = new LetterValueChallenge(challengeSumDataPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest(name = "Given Input of {0}, should return ''{1}''")
    @CsvSource({"317, microspectrophotometries", "319, reinstitutionalizations"})
    void challenge1_givenSum_ShouldReturnExpectedString(int sum, String expectedString) {
        assertEquals(expectedString, challenge.findFirstWordWithSum(sum));
    }

    @Test
    void challenge2_findAllWords_WithOddSum() {
        assertEquals(86339, challenge.findAllOddSumStrings().size());
    }

    @Test
    void challenge3_CountWordsWithSum() {
        assertEquals(1921L, challenge.countWordsWithSum(100));
    }

    @Test
    void challenge3_CountWordsWithSum_FindHighestCount() {
        Map.Entry<Integer, Long> entryWithHighestCount = challenge.countMostWordsBySum();
        assertEquals(93, entryWithHighestCount.getKey(), "Expected the sum of 93 to have the most words");
        assertEquals(1965, entryWithHighestCount.getValue(), "Expected 1965 words with sum of 93");
    }

    @Test
    void challenge4_FindEquivalentWordPairsWithSpecifiedLengthDifference() {
        Map<Integer, List<WordPair>> result = challenge.findEquivalentWordsWithSameSumAndDifferentLengths(11);
        assertEquals(2, result.size());
        assertTrue(result.get(151).contains(new WordPair("zyzzyva", "biodegradabilities")));
        assertTrue(result.get(219).contains(new WordPair("voluptuously", "electroencephalographic")));
    }

    // region CHALLENGE 5
    @Test
    void challenge5_FindExclusiveWordPairsWithSameSum() {
        Map<Integer, List<WordPair>> result = challenge.findExclusiveWordsWithSameSum(188);
        assertNotNull(result);
        assertTrue(result.containsKey(188));
        assertTrue(result.get(188).contains(new WordPair("cytotoxicity", "unreservedness")));
        assertTrue(result.containsKey(194));
        assertTrue(result.get(194).contains(new WordPair("defenselessnesses", "microphotographic")));
        assertTrue(result.get(194).contains(new WordPair("defenselessnesses", "photomicrographic")));
    }

    @Test
    void challenge5_findExclusiveWords_GivenList_ReturnsExpectedWords() {
        Map.Entry<Integer, List<String>> wordsBySumEntry = new AbstractMap.SimpleEntry<>(
                188, List.of("cytotoxicity", "unreservedness", "cryptocurrency"));

        var result = challenge.findExclusiveWords(wordsBySumEntry);
        assertEquals(1, result.getValue().size());
        assertEquals(188, result.getKey());
        assertTrue(result.getValue().contains(new WordPair("cytotoxicity", "unreservedness")));
        assertNotNull(result);
    }

    // end region
}