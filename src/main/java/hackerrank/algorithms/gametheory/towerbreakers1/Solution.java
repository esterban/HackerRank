package hackerrank.algorithms.gametheory.towerbreakers1;

import java.io.*;
import java.util.stream.IntStream;

class Result {

    /*
     * Complete the 'towerBreakers' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER m
     */


    public static int towerBreakers(int n, int m) {
        boolean isPrime = isPrime(m);
        boolean isTowersEven = n % 2 == 0;
        boolean isTowersOdd = n % 2 == 1;
        boolean playWonWins = isPrime && isTowersEven;

        if (m == 1) {
            return 2;
        }

        if (isPrime) {
            return isTowersOdd ? 1 : 2;
        }

        return isTowersOdd ? 1 : 2;
    }

    private static boolean isPrime(int n) {
        int sqrt = (int) Math.sqrt(n);

        for (int counter = 2; counter <= sqrt; ++counter) {
            if (n % counter == 0) {
                return false;
            }
        }

        return true;
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

                int m = Integer.parseInt(firstMultipleInput[1]);

                int result = Result.towerBreakers(n, m);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
