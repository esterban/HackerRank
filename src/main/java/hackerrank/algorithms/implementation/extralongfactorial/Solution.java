package hackerrank.algorithms.implementation.extralongfactorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

class Result {

    /*
     * Complete the 'extraLongFactorials' function below.
     *
     * The function accepts INTEGER n as parameter.
     */

    public static void extraLongFactorials(int n) {
        // Write your code here
        BigInteger nBigInteger = BigInteger.valueOf(n);

        for (int counter = 1; counter <= n; ++counter) {
            nBigInteger = nBigInteger.multiply(BigInteger.valueOf(counter));
        }

        System.out.println(nBigInteger);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        Result.extraLongFactorials(n);

        bufferedReader.close();
    }
}
