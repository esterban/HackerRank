package hackerrank.algorithms.implementation.modifiedkaprekarnumbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Result {

    /*
     * Complete the 'kaprekarNumbers' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER p
     *  2. INTEGER q
     */

    public static void kaprekarNumbers(int p, int q) {
        boolean invalidRange = true;

        // Write your code here
//        for (int testValue = p; testValue <= q; ++testValue) {
             for (int testValue = 99999; testValue <= 99999; ++testValue) {
            int numberOfDigits = (int) Math.log10(testValue) + 1;

            // if (Math.floor(Math.log10(testValue)) < Math.ceil(Math.log10(testValue))) {
            // ++numberOfDigits;
            // }

            long testValueSquared = (long)testValue * (long)testValue;

            // System.out.println("Test value = " + testValue + " , numberOfDigits = " + numberOfDigits);
            // System.out.println("Test value squared = " + testValueSquared);

            long rightHalf = testValueSquared % (long) Math.pow(10.0, numberOfDigits);
            long leftHalf = testValueSquared / (long) Math.pow(10.0, numberOfDigits);

            long leftRightTotal = leftHalf + rightHalf;

            // System.out.println("Right half = " + rightHalf + " , leftHalf = " + leftHalf);

            if (leftRightTotal == testValue) {
                // System.out.println("testValue floor = " + Math.floor(Math.log10(testValue)) + " ceil = " + Math.ceil(Math.log10(testValue)));

                System.out.print(leftRightTotal + " ");
                invalidRange = false;
            }
        }

        if (invalidRange) {
            System.out.println("INVALID RANGE");
        }
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int p = Integer.parseInt(bufferedReader.readLine().trim());

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        Result.kaprekarNumbers(p, q);

        bufferedReader.close();
    }
}
