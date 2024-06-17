package org.kramer.lettervaluesum;

public class LetterValueSummer {
  private static final int ASCII_OFFSET = 96;
  public int sum(String input) {
    if(isNullOrEmpty(input))
      return 0;

    return input.chars().map(LetterValueSummer::determineCharValue).sum();
  }

  private static int determineCharValue(int input) {
    return input - ASCII_OFFSET;
  }

  private static boolean isNullOrEmpty(String input) {
    return null == input || input.isEmpty();
  }
}
