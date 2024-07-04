package interviewpreparationkit.stringmanipulation;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'isValid' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String isValid(String s) {
        // Write your code here

        Map<Character, Integer> histogram = calcStringLetterHistogram(s);

        boolean atMostToDelete = true;

        Map<Integer, Integer> characterCount = new HashMap<>();

        for (Map.Entry<Character, Integer> entry : histogram.entrySet()) {
            characterCount.compute(entry.getValue(), (k, v) -> v == null ? 1 : v + 1);
        }

        if (characterCount.size() > 2) {
            atMostToDelete = false;
        } else {
            if (characterCount.size() != 1) {
                Iterator<Map.Entry<Integer, Integer>> it = characterCount.entrySet().iterator();
                Map.Entry<Integer, Integer> firstEntry = it.next();
                Map.Entry<Integer, Integer> secondEntry = it.next();

                if (firstEntry.getValue() > 1 && secondEntry.getValue() > 1) {
                    atMostToDelete = false;
                }
                if (!((firstEntry.getKey() == 1 && firstEntry.getValue() == 1) || (secondEntry.getKey() == 1 && secondEntry.getValue() == 1))) {
                    if (Math.abs(firstEntry.getKey() - secondEntry.getKey()) > 1) {

                        atMostToDelete = false;
                    }
                }
//                else if (firstEntry.getValue() > 1 && secondEntry.getValue() > 1) {
//                    atMostToDelete = false;
//
//                }
//                else {
//                    if (Math.abs(firstEntry.getValue() - secondEntry.getValue()) > 1) {
//                        atMostToDelete = false;
//                    }
//                }
            }
        }

        if (atMostToDelete) {
            return "YES";
        }

        return "NO";
    }

    public static Map<Character, Integer> calcStringLetterHistogram(String string) {
        Map<Character, Integer> histogram = new HashMap<>();

        for (char character : string.toCharArray()) {
            histogram.compute(character, (k, v) -> v == null ? 1 : v + 1);
        }

        return histogram;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String s = bufferedReader.readLine();

        String result = Result.isValid(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
