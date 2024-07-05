package hackerrank.algorithms.sorting.closestnumbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'closestNumbers' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static List<Integer> closestNumbers(List<Integer> arr) {
        // Write your code here
        arr.sort(Integer::compare);

//        SortedSet<Integer> resultSet = new TreeSet<>();
        List<Integer> resultSet = new ArrayList<>();

        int minimumDifference = Integer.MAX_VALUE;

        for (int index = 1; index < arr.size(); ++index) {
            Integer valueA = arr.get(index - 1);
            Integer valueB = arr.get(index);
            int difference = valueB - valueA;

            System.out.println("valueA = " + valueA + " , valueB = " + valueB);
            System.out.println("difference = " + difference);

            if (difference < minimumDifference) {
                System.out.println("clearing resultSet");
                // System.out.println("difference = " + difference);

                resultSet.clear();

                resultSet.add(valueA);
                resultSet.add(valueB);

                minimumDifference = difference;
            } else if (difference == minimumDifference) {
                System.out.println("Adding difference to resultSet");
                // System.out.println("difference = " + difference);

                resultSet.add(valueA);
                resultSet.add(valueB);
            }
        }

//        result.addAll(resultSet);

//        return result;
        return resultSet;
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

        List<Integer> result = Result.closestNumbers(arr);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining(" "))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
