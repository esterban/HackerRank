package hackerrank.algorithms.implementation.larrysarray;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'larrysArray' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts INTEGER_ARRAY A as parameter.
     */
    public static String larrysArray(List<Integer> A) {
        for (int currentValue = 1; currentValue < A.size() - 1; ++currentValue) {
            int currentValueIndex = findValuePosition(A, currentValue);
            int distance = currentValueIndex - currentValue + 1;

            while (distance > 0) {
                if (distance == 1) {
                    rotateIndex(currentValueIndex - 1, A, -1);
                    currentValueIndex -= 1;
                } else  {
                    rotateIndex(currentValueIndex - 2, A, -2);
                    currentValueIndex -= 2;
                }

                distance = currentValueIndex - currentValue + 1;
            }
        }

        int lastIndex = A.size() - 1;

        return A.get(lastIndex - 1) < A.get(lastIndex) ? "YES" : "NO";
    }

    public static int findValuePosition(List<Integer> array, Integer searchValue) {
        return IntStream.range(0, array.size()).filter(e -> array.get(e).equals(searchValue)).findFirst().getAsInt();
    }

    public static void rotateIndex(int index, List<Integer> list, int indexIncrement) {
        if (indexIncrement < 0) {
            indexIncrement = (3 - (Math.abs(indexIncrement) % 3)) % 3;
        }

        int valueA = list.get(index);
        int valueB = list.get(index + 1);
        int valueC = list.get(index + 2);

        int indexNewA = index + (indexIncrement % 3);
        int indexNewB = index + ((indexIncrement + 1) % 3);
        int indexNewC = index + ((indexIncrement + 2) % 3);

        list.set(indexNewA, valueA);
        list.set(indexNewB, valueB);
        list.set(indexNewC, valueC);
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
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> A = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList());

                String result = Result.larrysArray(A);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
