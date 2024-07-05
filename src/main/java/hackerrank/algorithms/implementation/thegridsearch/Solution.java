package hackerrank.algorithms.implementation.thegridsearch;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'gridSearch' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING_ARRAY G
     *  2. STRING_ARRAY P
     */

    public static String gridSearch(List<String> G, List<String> P) {
        // Write your code here



        int arrayRowCount = G.size();
        int arrayColumnCount = G.get(0).length();
        int patternRowCount = P.size();
        int patternColumnCount = P.get(0).length();

//        String firstCharacter = String.valueOf(P.get(0).charAt(0));
//        boolean match = false;

        for (int rowScan = 0; rowScan < arrayRowCount - patternRowCount + 1; ++rowScan) {
            String rowString = G.get(rowScan);

            for (int columnScan = 0; columnScan < arrayColumnCount - patternColumnCount + 1; ++columnScan) {
                String rowSubString = rowString.substring(columnScan, columnScan + patternColumnCount);

                for (int patternRowScan = 0; patternRowScan < patternRowCount; ++patternRowScan) {
                    String patternRowString = P.get(patternRowScan);
                    rowString = G.get(rowScan + patternRowScan);
                    rowSubString = rowString.substring(columnScan, columnScan + patternColumnCount);

                    boolean rowMatch = false;

                    if (rowSubString.equals(patternRowString)) {
                        rowMatch = true;
                    }

                    if (!rowMatch) {
                        break;
                    }

                    if (patternRowScan == patternRowCount - 1) {
                        return "YES";
                    }
                }
            }
        }

        return "NO";
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

                int R = Integer.parseInt(firstMultipleInput[0]);

                int C = Integer.parseInt(firstMultipleInput[1]);

                List<String> G = IntStream.range(0, R).mapToObj(i -> {
                            try {
                                return bufferedReader.readLine();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        })
                        .collect(toList());

                String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int r = Integer.parseInt(secondMultipleInput[0]);

                int c = Integer.parseInt(secondMultipleInput[1]);

                List<String> P = IntStream.range(0, r).mapToObj(i -> {
                            try {
                                return bufferedReader.readLine();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        })
                        .collect(toList());

                String result = Result.gridSearch(G, P);

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
