package interviewpreparationkit.greedyalgorithms.minimumabsolutedifferenceinanarray;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {
    public static int minimumAbsoluteDifference(List<Integer> arr) {
        TreeSet<Integer> differences = new TreeSet<>(arr);

        int maxDifference = Integer.MAX_VALUE;

        Iterator<Integer> diffIt = differences.iterator();

        int firstValue = diffIt.next();
        int secondValue = 0;

        if (arr.size() > differences.size()) {
            return 0;
        }

        do {
            secondValue = diffIt.next();
            int difference = Math.abs(secondValue - firstValue);

            if (difference < maxDifference) {
                maxDifference = difference;
            }

            firstValue = secondValue;
        } while(diffIt.hasNext());


        return maxDifference;
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

        long nanosStart = System.nanoTime();
        int result = Result.minimumAbsoluteDifference(arr);
        long nanosEnd = System.nanoTime();

        double millis = (nanosEnd - nanosStart) / 1e6;

        System.out.println("Duration = " + millis);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
