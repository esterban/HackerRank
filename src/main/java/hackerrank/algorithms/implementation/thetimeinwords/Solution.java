package hackerrank.algorithms.implementation.thetimeinwords;

import java.io.*;

class Result {

    /*
     * Complete the 'timeInWords' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. INTEGER h
     *  2. INTEGER m
     */
    public static String[] SMALL_NUMBERS = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    public static String[] TENS = {"twenty", "thirty", "forty", "fifty"};

    public static String timeInWords(int h, int m) {
        String hourString = intToString(h);

        if (m == 0) {
            return intToString(h) + " o' clock";
        } else if (m < 31) {
            return generateMinutesString(m) + " past " + hourString;
        } else {
            hourString = intToString(h + 1);
            int minutesToHour = 60 - m;

            String minutesToString = generateMinutesString(minutesToHour);

            return minutesToString + " to " + hourString;
        }
    }

    public static String intToString(int intValue) {
        if (intValue <= 20) {
            return SMALL_NUMBERS[intValue - 1];
        }

        int tensDigit = intValue / 10;
        int tensIndex = tensDigit - 2;
        int singlesIndex = intValue % 10;

        return TENS[tensIndex] + (singlesIndex > 0 ? " " + SMALL_NUMBERS[singlesIndex - 1] : "");
    }

    public static String generateMinutesString(int minutes) {
        if (minutes == 15) {
            return "quarter";
        } else if (minutes == 30) {
            return "half";
        }

        return intToString(minutes) + (minutes == 1 ? " minute" : " minutes");
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int h = Integer.parseInt(bufferedReader.readLine().trim());

        int m = Integer.parseInt(bufferedReader.readLine().trim());

        String result = Result.timeInWords(h, m);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
