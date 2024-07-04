package interviewpreparationkit.stringmanipulation.commonchild;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class Result {

    /*
     * Complete the 'commonChild' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING s1
     *  2. STRING s2
     */

    public static int commonChild(String s1, String s2) {
        int longestString = 0;

        // Write your code here
        Map<Character, Integer> histogram1 = calcStringHistogram(s1);
        Map<Character, Integer> histogram2 = calcStringHistogram(s2);

        for (Map.Entry<Character, Integer> entry : histogram1.entrySet()) {
            if (histogram2.containsKey(entry.getKey())) {
                longestString += Math.min(entry.getValue(), histogram2.get(entry.getKey()));
            }
        }

        return longestString;
    }

    public static Map<Character, Integer> calcStringHistogram(String string) {
        Map<Character, Integer> histogram = new HashMap<>();

        for (char character : string.toCharArray()) {
            histogram.compute(character, (k , v) -> v == null ? 1 : v + 1);
        }

        return histogram;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String s1 = bufferedReader.readLine();

        String s2 = bufferedReader.readLine();

        int result = Result.commonChild(s1, s2);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
