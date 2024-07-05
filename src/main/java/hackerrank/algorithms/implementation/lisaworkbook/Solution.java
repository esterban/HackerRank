package hackerrank.algorithms.implementation.lisaworkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Result {

    /*
     * Complete the 'workbook' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     *  3. INTEGER_ARRAY arr
     */

    public static int workbook(int n, int k, List<Integer> arr) {
        // Write your code here
        int pageCounter = 1;
        int smartPages = 0;

        for (int chapterCounter = 1; chapterCounter <= arr.size(); ++chapterCounter) {
            int problemCount = arr.get(chapterCounter - 1);
            int chapterLength = (problemCount / k) + (problemCount % k == 0 ? 0 : 1);

            for (int chapterPageCounter = 1; chapterPageCounter <= chapterLength; ++chapterPageCounter) {
                int firstProblem = (chapterPageCounter - 1) * k + 1;
                int lastProblem = Math.min(chapterPageCounter * k, problemCount);

                Set<Integer> problemsOnPage = IntStream.rangeClosed(firstProblem, lastProblem).boxed().collect(Collectors.toSet());

                if (problemsOnPage.contains(pageCounter + chapterPageCounter - 1)) {
                    ++smartPages;
                }
            }

            pageCounter += chapterLength;
        }

        return smartPages;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String[] arrTemp = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        List<Integer> arr = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrTemp[i]);
            arr.add(arrItem);
        }

        int result = Result.workbook(n, k, arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
