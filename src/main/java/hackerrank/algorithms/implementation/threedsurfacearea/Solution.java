package hackerrank.algorithms.implementation.threedsurfacearea;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'surfaceArea' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY A as parameter.
     */

    public static int surfaceArea(List<List<Integer>> A) {
        int surfaceArea = 0;

        surfaceArea += A.size() * A.get(0).size() * 2;

        // Exterior surface area - width
        for (int widthIndex = 0; widthIndex < A.size(); ++widthIndex) {
            surfaceArea += A.get(widthIndex).get(0);
            surfaceArea += A.get(widthIndex).get(A.get(0).size() - 1);
        }

        for (int breadthIndex = 0; breadthIndex < A.get(0).size(); ++breadthIndex) {
            surfaceArea += A.get(0).get(breadthIndex);
            surfaceArea += A.get(A.size() - 1).get(breadthIndex);
        }

        for (int widthIndex = 0; widthIndex < A.size(); ++widthIndex) {
            for (int breadthIndex = 0; breadthIndex < A.get(0).size(); ++breadthIndex) {
                // right cuboid
                if (breadthIndex < A.get(0).size() - 1) {
                    int heightDifference = A.get(widthIndex).get(breadthIndex + 1) - A.get(widthIndex).get(breadthIndex);

                    if (heightDifference > 0) {
                        surfaceArea += heightDifference;
                    }
                }

                if (breadthIndex > 0) {
                    int heightDifference = A.get(widthIndex).get(breadthIndex - 1) - A.get(widthIndex).get(breadthIndex);

                    if (heightDifference > 0) {
                        surfaceArea += heightDifference;
                    }
                }

                if (widthIndex < A.size() - 1) {
                    int heightDifference = A.get(widthIndex + 1).get(breadthIndex) - A.get(widthIndex).get(breadthIndex);

                    if (heightDifference > 0) {
                        surfaceArea += heightDifference;
                    }
                }

                if (widthIndex > 0) {
                    int heightDifference = A.get(widthIndex - 1).get(breadthIndex) - A.get(widthIndex).get(breadthIndex);

                    if (heightDifference > 0) {
                        surfaceArea += heightDifference;
                    }
                }
            }
        }

        // Write your code here
        return surfaceArea;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int H = Integer.parseInt(firstMultipleInput[0]);

        int W = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> A = new ArrayList<>();

        IntStream.range(0, H).forEach(i -> {
            try {
                A.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.surfaceArea(A);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
