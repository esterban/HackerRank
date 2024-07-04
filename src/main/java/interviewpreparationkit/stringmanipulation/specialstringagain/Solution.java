//package interviewpreparationkit.stringmanipulation.specialstringagain;
//
//import org.apache.commons.lang3.tuple.ImmutablePair;
//import org.apache.commons.lang3.tuple.MutablePair;
//import org.apache.commons.lang3.tuple.Pair;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.TreeMap;
//
//public class Solution {
//
//    // Complete the substrCount function below.
//    static long substrCount(int n, String s) {
//        long result = 0l;
//        String stringCopy = s;
//        char[] stringArray = s.toCharArray();
//
//        long numberOfStringResize = 0l;
//
//        TreeMap<Integer, Integer> stringPartsRemoved = new TreeMap<>();
//
//        long numberOfIterations = 0l;
//
//        int longestSubString = longestContinuousSubString(stringArray, n);
//
//        for (int subStringLength = longestSubString * 2 + 1 > n ? n : longestSubString * 2 + 1; subStringLength > 0; --subStringLength) {
//            for (int lowerIndex = 0; lowerIndex <= stringCopy.length() - subStringLength; ++lowerIndex) {
//                ++numberOfIterations;
//
//                int upperIndex = lowerIndex + subStringLength;
//
//                Map.Entry<Integer, Integer> removedEntry = stringPartsRemoved.lowerEntry(lowerIndex + 1);
//
////                if (removedEntry != null && lowerIndex < removedEntry.getValue()) {
////                    lowerIndex = removedEntry.getValue();
////
////                    continue;
////                }
//
//                Pair<Boolean, Boolean> isOnlyOneCharacter = isOnlyOneCharacter(stringArray, lowerIndex, upperIndex);
//
//                if (!isOnlyOneCharacter.getKey() && !isOnlyOneCharacter.getValue()) {
//                    continue;
//                }
//
//                ++numberOfStringResize;
//
//                if (isOnlyOneCharacter.getKey()) {
////                    result += subStringLength * (subStringLength + 1) / 2;
//                    ++result;
//                } else {
//                    ++result;
//                }
//
//                stringPartsRemoved.putIfAbsent(lowerIndex, lowerIndex + subStringLength);
//            }
//        }
//
////        System.out.println("Number of iterations = " + numberOfIterations);
////        System.out.println("Number of resizes = " + numberOfStringResize);
//
//        return result;
//    }
//
//    static int longestContinuousSubString(char[] stringArray, int n) {
//        int longestSubString = 1;
//
//        char currentChar = stringArray[0];
//
//        int lowerIndex = 0;
//
//        boolean isSameCharacter = true;
//
//        for (int index = 1; index < n; ++index) {
//            char thisChar = stringArray[index];
//
//            if (thisChar != currentChar) {
//                int subStringSize = index - lowerIndex;
//
//                if (subStringSize > longestSubString) {
//                    longestSubString = subStringSize;
//                }
//
//                currentChar = thisChar;
//                lowerIndex = index;
//
//                isSameCharacter = false;
//
//                continue;
//            }
//        }
//
//        if (isSameCharacter) {
//            return n;
//        }
//
//        return longestSubString;
//    }
//
//        static Pair<Boolean, Boolean> isOnlyOneCharacter(char[] stringArray, int lowerIndex, int upperIndex) {
////    static boolean isOnlyOneCharacter(char[] stringArray, int lowerIndex, int upperIndex) {
//        char firstChar = stringArray[lowerIndex];
//        Character secondChar = null;
//
//        for (int index = lowerIndex; index < upperIndex; ++index) {
//            char character = stringArray[index];
//
//            if (character != firstChar) {
//                if (secondChar != null) {
//                    return new ImmutablePair<>(false, false);
//                }
//
//                secondChar = character;
//            }
//        }
//
//        if (secondChar == null) {
//            return new ImmutablePair<>(true,false);
//        }
//
//        if ((upperIndex - lowerIndex) % 2 == 0) {
//            return new ImmutablePair<>(false, false);
//        }
//
//        int halfSize = (upperIndex - lowerIndex) / 2;
//
//        return new MutablePair<>(false, stringArray[lowerIndex + halfSize] == secondChar);
//    }
//
//    static Map<Character, Integer> numberOfUniqueCharacter(String string) {
//        Map<Character, Integer> uniqueCharacters = new HashMap<>();
//
//        for (char character : string.toCharArray()) {
//            uniqueCharacters.compute(character, (k, v) -> v == null ? 1 : v + 1);
//
//            if (uniqueCharacters.size() > 2) {
//                return null;
//            }
//        }
//
//        return uniqueCharacters;
//    }
//
//    private static Object m_mutex = new Object();
//
//    private static final Scanner scanner = new Scanner(System.in);
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
//
//        int n = scanner.nextInt();
//        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
//
//        String s = scanner.nextLine();
//
//        int truncatedStringLength = 100000;
////        int truncatedStringLength = 10 * 1000;
////        String truncatedString = s.substring(0, truncatedStringLength);
//
//        long startNanos = System.nanoTime();
//        long result = substrCount(n, s);
////        long result = substrCount(truncatedString.length(), truncatedString);
//        long endNanos = System.nanoTime();
//
//        double millisSpent = (endNanos - startNanos) / 1e6;
//
////        System.out.println("Time spent = " + millisSpent);
//
//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();
//
//        bufferedWriter.close();
//
//
//        scanner.close();
////        synchronized (m_mutex) {
////            while (true) {
////                m_mutex.wait();
////            }
////        }
//    }
//}
//
