package hackerrank.algorithms.implementation.absolutepermutation;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

class Result {

    /*
     * Complete the 'absolutePermutation' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     */

//    public static List<Integer> absolutePermutation(int n, int k) {
//        int permutationCount = 0;
//        List<Integer> smallestPermutation = new ArrayList<>();
//
//        if (k > n / 2) {
//            return Collections.singletonList(-1);
//        }
//
//        // Write your code here
//        for (int counter = 1; counter <= n; ++counter) {
//            Integer position = counter + k - 1;
//            position = (position % n) + 1;
//
//            smallestPermutation.add(position);
//        }
//
//        return smallestPermutation;
//    }

    public static List<Integer> absolutePermutation(int n, int k) {
        List<Integer> smallestPermutation = new ArrayList<>();

        SortedSet<Integer> usedValues = new TreeSet<>();

        for (int counter = 1; counter <= n; ++counter) {
//            int possibleLowerValue = Math.abs(counter - k);
            boolean valueAdded = false;

            int possibleLowerValue = counter - k;
            int possibleUpperValue = counter + k;

            if (possibleLowerValue >= 1 && !usedValues.contains(possibleLowerValue)) {
                usedValues.add(possibleLowerValue);
                smallestPermutation.add(possibleLowerValue);
                valueAdded = true;
            } else if (possibleUpperValue <= n && !usedValues.contains(possibleUpperValue)) {
                usedValues.add(possibleUpperValue);
                smallestPermutation.add(possibleUpperValue);
                valueAdded = true;
            }

            if (!valueAdded) {
                return Collections.singletonList(-1);
            }
        }

        return smallestPermutation;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int k = Integer.parseInt(firstMultipleInput[1]);

                List<Integer> result = Result.absolutePermutation(n, k);

                bufferedWriter.write(
                        result.stream()
                                .map(Object::toString)
                                .collect(joining(" "))
                                + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
