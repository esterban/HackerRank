package leetcode.substringwithconcatenationofallwords;

import java.util.*;
import java.util.stream.Collectors;


public class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        SortedMap<Integer, String> indexToStringMap = indexToString(words, s);

        final String firstWord = words[0];

        boolean allTheSame = true;

        List<SortedSet<Integer>> contiguousMatchSet = null;

        for (String word : words) {
            if (!word.equals(firstWord)) {
                allTheSame = false;
                break;
            }
        }

        List<String> wordList = Arrays.asList(words);

        if (allTheSame) {
            contiguousMatchSet = calcForAllWordsTheSame(words[0].length(), s, words[0], words.length);
        } else {
            contiguousMatchSet = findContiguousMatches(indexToStringMap, words[0].length(), words.length, wordList);
        }

        @SuppressWarnings(value = "UnnecessaryLocalVariable")
        List<Integer> firstIndicesList = contiguousMatchSet.stream().map(SortedSet::first).collect(Collectors.toList());

        return firstIndicesList;
    }

    private List<SortedSet<Integer>> findContiguousMatches(SortedMap<Integer, String> matchIndicesSet, int wordLength, int wordsNeededCount, List<String> wordList) {
        List<SortedSet<Integer>> contiguousMatchesSet = new ArrayList<>();
        SortedSet<Integer> contiguousMatches = new TreeSet<>();

        int[] matchIndicesArray = matchIndicesSet.keySet().stream().mapToInt(e -> e.intValue()).toArray();
        int matchIndicesCount = matchIndicesArray.length;

        boolean allWordsTheSae = false;


        List<String> wordListCopy = new ArrayList<>(wordList);

        int previousIndex = -1;
        Set<Integer> skipIndexSet = new HashSet<>();
        Set<Integer> currentSkipIndexSet = new HashSet<>();

        boolean restart = true;

        int startIndex = 0;

        long outerCounter = 0;
        long innerCounter = 0;

        int stringLengthNeeded = wordsNeededCount * wordLength;

        while (restart) {
            ++outerCounter;

            int stringLengthAvailable = (matchIndicesCount - startIndex) * wordLength;

            if (stringLengthAvailable < stringLengthNeeded) {
                break;
            }

            for (int indexIndex = startIndex; indexIndex < matchIndicesCount; ++indexIndex) {
                ++innerCounter;

                int index = matchIndicesArray[indexIndex];

                if (previousIndex != -1 && (index - previousIndex) % wordLength != 0) {
                    continue;
                }

                if (skipIndexSet.contains(index) || currentSkipIndexSet.contains(index)) {
                    continue;
                }

                if (previousIndex == -1 || index == previousIndex + wordLength) {
                    contiguousMatches.add(index);
                    previousIndex = index;

                    String word = matchIndicesSet.get(index);

                    if (!wordListCopy.contains(word)) {
                        skipIndexSet.add(contiguousMatches.first());
                        contiguousMatches.clear();

                        break;
                    }

                    wordListCopy.remove(word);

                    if (wordListCopy.isEmpty()) {
                        skipIndexSet.add(contiguousMatches.first());
                        contiguousMatchesSet.add(contiguousMatches);

                        contiguousMatches = new TreeSet<>();

                        break;
                    }
                } else {
                    skipIndexSet.add(contiguousMatches.first());
                    contiguousMatches.clear();

                    break;
                }
            }

            ++startIndex;
            previousIndex = -1;
            contiguousMatches.clear();
            wordListCopy = new ArrayList<>(wordList);

            if (skipIndexSet.size() == matchIndicesSet.size() || startIndex == matchIndicesSet.size()) {
                restart = false;
            }
        }

        System.out.println("Outer counter = " + outerCounter + " , inner counter = " + innerCounter);

        return contiguousMatchesSet;
    }

    private SortedMap<Integer, String> indexToString(String[] wordArray, String s) {
        SortedMap<Integer, String> indexToStringMap = new TreeMap<>();

        Set<String> processedWords = new HashSet<>();

        for (String word : wordArray) {
            int wordIndex = s.indexOf(word);

            if (processedWords.contains(word)) {
                continue;
            }

            while (wordIndex != -1) {
                indexToStringMap.put(wordIndex, word);
                wordIndex = s.indexOf(word, wordIndex + 1);
            }

            processedWords.add(word);
        }

        return indexToStringMap;
    }

    private List<SortedSet<Integer>> calcForAllWordsTheSame(int wordLength, String s, String firstWord, int wordCount) {
        List<SortedSet<Integer>> result = new ArrayList<>();

        String searchWord = firstWord.repeat(wordCount);

        int previousIndex = s.indexOf(searchWord);

        while(previousIndex != -1) {
            SortedSet<Integer> matchesSet = new TreeSet<>();
            matchesSet.add(previousIndex);
            result.add(matchesSet);

            previousIndex = s.indexOf(searchWord, previousIndex + 1);
        }

        return result;
    }
}