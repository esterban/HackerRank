package algorithms.implementation.cutthesticks;

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
    public static List<Integer> cutTheSticks(List<Integer> arr) {
        List<Integer> results = new ArrayList<>();


        List<Integer> resultStickList;

        do {
            int shortestLength = findShortestStickLength(arr);
            resultStickList = new ArrayList<>();

            results.add(arr.size());

            for (Integer length : arr) {
                if (length > shortestLength) {
                    Integer newLength = length - shortestLength;

                    resultStickList.add(newLength);
                }
            }

            arr = resultStickList;
        } while (!resultStickList.isEmpty());

        // Write your code here
        return results;
    }

    private static int findShortestStickLength(List<Integer> stickList) {
        Integer shortestLength = stickList.get(0);

        for (Integer length : stickList) {
            if (length < shortestLength) {
                shortestLength = length;
            }
        }

        return shortestLength;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        List<Integer> result = Result.cutTheSticks(arr);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
