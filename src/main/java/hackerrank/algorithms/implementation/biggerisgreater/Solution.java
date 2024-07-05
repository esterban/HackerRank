package hackerrank.algorithms.implementation.biggerisgreater;

import java.io.*;
import java.text.Collator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Result {

    /*
     * Complete the 'biggerIsGreater' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING w as parameter.
     */

    public static String biggerIsGreater(String w) {
        char lastChar = w.charAt(w.length() - 1);
        int searchingIndex = w.length() - 2;

        // Write your code here
        for (; searchingIndex >= 0; --searchingIndex) {
            char checkChar = w.charAt(searchingIndex);

            if (checkChar < lastChar) {
                break;
            }
        }

        String tailString = w.substring(searchingIndex + 1, w.length() - 1);
//        char[] characters = tailString.chars().mapToObj(i -> (char)i).sorted(Collator.getInstance()).collect(Collectors.joining());
        String tailStringSorted = tailString.chars()
                .mapToObj(i -> (char) i)
                .sorted(Collator.getInstance())
                .map(String::valueOf)
                .collect(Collectors.joining());

        String result = w.substring(0, searchingIndex) + w.charAt(searchingIndex) + tailStringSorted;

        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int T = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, T).forEach(TItr -> {
            try {
                String w = bufferedReader.readLine();

                String result = Result.biggerIsGreater(w);

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
