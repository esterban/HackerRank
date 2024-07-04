package interviewpreparationkit.miscellaneous.timecomplexityprimality;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'primality' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts INTEGER n as parameter.
     */

    public static String primality(int n) {
        // Write your code here
        int rootN = (int)Math.sqrt(n);

        if (n % 2 == 0) {
            return "Not prime";
        }

        for (int counter = 3; counter <= rootN; counter += 2) {
            if (n % counter == 0) {
                return "Not prime";
            }
        }

        return "Prime";
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

//        int p = Integer.parseInt(bufferedReader.readLine().trim());

//        IntStream.range(0, p).forEach(pItr -> {
//            try {
//                int n = Integer.parseInt(bufferedReader.readLine().trim());

                String result = Result.primality(5);

                System.out.println("Is prime? = " + result);

//                bufferedWriter.write(result);
//                bufferedWriter.newLine();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        });

        bufferedReader.close();
//        bufferedWriter.close();
    }
}
