package hackerrank.algorithms.sorting.bigsorting;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'bigSorting' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts STRING_ARRAY unsorted as parameter.
     */

    public static List<String> bigSorting(List<String> unsorted) {
        // Write your code here
        List<BigInteger> numberList = new ArrayList<>();
        List<Long> smallNumbersList = new ArrayList<>();

        SortedMap<Integer, List<String>> veryLongNumbersMap = new TreeMap<>();

        for (String numberString : unsorted) {
            BigInteger integerValue;

            if (numberString.length() > 100) {
                Integer stringLength = numberString.length();

                veryLongNumbersMap.putIfAbsent(stringLength, new ArrayList<>());

                veryLongNumbersMap.get(stringLength).add(numberString);
            } else if (numberString.length() < 19) {
                smallNumbersList.add(Long.valueOf(numberString));
            } else {
                integerValue = new BigInteger(numberString);
                numberList.add(integerValue);
            }
        }

        List<String> sortedStrings = numberList.stream().sorted().map(e -> e.toString()).collect(Collectors.toList());
        List<String> smallNumberString = smallNumbersList.stream().sorted().map(e -> String.valueOf(e)).collect(Collectors.toList());
        smallNumberString.addAll(sortedStrings);

        for (Map.Entry<Integer, List<String>> vLargeEntry : veryLongNumbersMap.entrySet()) {
            if (vLargeEntry.getValue().size() == 1) {
                smallNumberString.add(vLargeEntry.getValue().get(0));
            } else {
//                throw new RuntimeException("More than one number of length = " + vLargeEntry.getValue() + " count = " + vLargeEntry.getValue().size());

                List<String> largeSortedStrings = vLargeEntry.getValue().stream().map(e -> new BigInteger(e)).sorted().map(e -> e.toString()).collect(toList());
                smallNumberString.addAll(largeSortedStrings);
            }
        }

        return smallNumberString;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> unsorted = IntStream.range(0, n).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        List<String> result = Result.bigSorting(unsorted);

        bufferedWriter.write(
                result.stream()
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
