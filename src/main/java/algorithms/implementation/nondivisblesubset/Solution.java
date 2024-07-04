package algorithms.implementation.nondivisblesubset;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    public static int nonDivisibleSubset(int k, List<Integer> s) {
        Map<Integer, Integer> remainderCounts = createRemainderCounts(s, k);
        int totalSize = 0;

        int halfK;
        if (k % 2 == 0) {
            halfK = k / 2;
        } else {
            halfK = (k / 2) + 1;
        }

        for (int counter = 1; counter < halfK; ++counter) {
            int otherPair = k - counter;

            Integer firstCount = remainderCounts.get(counter);
            Integer otherCount = remainderCounts.get(otherPair);

            if (firstCount != null) {
                if (otherCount == null) {
                    totalSize += firstCount;
                } else {
                    totalSize += Math.max(firstCount, otherCount);
                }
            } else {
                if (otherCount != null) {
                    totalSize += otherCount;
                }
            }
        }

        if (remainderCounts.containsKey(0)) {
            totalSize += 1;
        }

        if (k % 2 == 0 && remainderCounts.containsKey(halfK)) {
            totalSize += 1;
        }

        return totalSize;
    }

    public static Map<Integer, Integer> createRemainderCounts(List<Integer> s, int k) {
        Map<Integer, Integer> remainderCounts = new HashMap<>();

        for (Integer value : s) {
            Integer remainder = value % k;

            remainderCounts.compute(remainder, (kk, v) -> v == null ? 1 : v + 1);
        }

        return remainderCounts;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> s = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt).limit(n)
                .collect(toList());

        long nanosStart = System.nanoTime();
        int result = Result.nonDivisibleSubset(k, s);
        long nanoEnd = System.nanoTime();

        double durationMilliseconds = (nanoEnd - nanosStart) / 1e6;

        System.out.println("For n = " + n + " Duration millis = " + durationMilliseconds);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
