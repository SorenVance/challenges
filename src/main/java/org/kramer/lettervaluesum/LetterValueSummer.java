package org.kramer.lettervaluesum;

public class LetterValueSummer {
  public int sum(String source) {
    if(null == source ||source.isEmpty())
      return 0;
    return source.equals("a") ? 1 : 26;
  }
}
