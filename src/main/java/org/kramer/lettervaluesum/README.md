# LETTER VALUE SUM CHALLENGE
[The Letter Value Sum Challenge](https://us.reddit.com/r/dailyprogrammer/comments/onfehl/20210719_challenge_399_easy_letter_value_sum/) is a repost of Challenge #52 [easy], originally posted by u/rya11111 in May 2012.)

Assign every lowercase letter a value, from 1 for a to 26 for z. Given a string of lowercase letters, find the sum of the values of the letters in the string.


```
lettersum("") => 0
lettersum("a") => 1
lettersum("z") => 26
lettersum("cab") => 6
lettersum("excellent") => 100
lettersum("microspectrophotometries") => 317
```
## Optional bonus challenges
Use the [enable1 word list](https://raw.githubusercontent.com/dolph/dictionary/master/enable1.txt) for the 
optional bonus challenges.
- [X] `microspectrophotometries` is the only word with a letter sum of 317. Find the only word with a letter sum of 319.
- [X] How many words have an odd letter sum?

- [X] There are 1921 words with a letter sum of 100, making it the second most common letter sum. What letter sum is 
  most 
   common, and how many words have it?
- [X] `zyzzyva` and `biodegradabilities` have the same letter sum as each other (151), and their lengths differ by 
  11 letters.
   Find the other pair of words with the same letter sum whose lengths differ by 11 letters.
- [ ] `cytotoxicity` and `unreservedness` have the same letter sum as each other (188), and they have no letters in 
  common. 
   Find a pair of words that have no letters in common, and that have the same letter sum, which is larger than 188. (There are two such pairs, and one word appears in both pairs.)
- [ ] The list of word `{ geographically, eavesdropper, woodworker, oxymorons }` contains 4 words. Each word in the 
  list 
   has both a different number of letters, and a different letter sum. The list is sorted both in descending order of word length, and ascending order of letter sum. What's the longest such list you can find?
