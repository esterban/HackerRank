package hackerrank.algorithms.strings.strongpassword;

import java.io.*;

class Result {

    /*
     * Complete the 'minimumNumber' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. STRING password
     */

    public static int minimumNumber(int n, String password) {
        // Return the minimum number of characters to make the password strong
        long lowerCaseCount = password.chars().filter(e -> Character.isLowerCase(e)).count();
        long upperCaseCount = password.chars().filter(e -> Character.isUpperCase(e)).count();
        long numericCount = password.chars().filter(e -> Character.isDigit(e)).count();
        long symbolCount = password.length() - lowerCaseCount - upperCaseCount - numericCount;

        int lowerCountNeeded = lowerCaseCount == 0 ? 1 : 0;
        int upperCountNeeded = upperCaseCount == 0 ? 1 : 0;
        int numericCountNeeded = numericCount == 0 ? 1 : 0;
        int symbolCountNeeded = symbolCount == 0 ? 1 : 0;

        int totalNeeded = lowerCountNeeded + upperCountNeeded + numericCountNeeded + symbolCountNeeded;
        int newLength = totalNeeded + password.length();
        newLength = Math.max(totalNeeded, 6 - password.length() );

        return newLength;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        String password = bufferedReader.readLine();

        int answer = Result.minimumNumber(n, password);

        bufferedWriter.write(String.valueOf(answer));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
