package hackerrank.algorithms.strings.separatethenumbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Result {

    /*
     * Complete the 'separateNumbers' function below.
     *
     * The function accepts STRING s as parameter.
     */

    public static void separateNumbers(String s) {
//        int longestNumber1 = s.length() / 2 + 1;
        int longestNumber1 = s.length() / 2;

//        System.out.println("longest number = " + longestNumber1);

        for (int number1Length = 1; number1Length <= longestNumber1; ++number1Length) {
            List<Long> valueList = convertToLongList(s, number1Length);

//            System.out.println("Value list = " + valueList);

            if (valueList.size() > 1 && isListBeautiful(valueList)) {
                System.out.println("YES " + valueList.get(0));
                return;
            }
        }

        System.out.println("NO");
    }

    public static List<Long> convertToLongList(String s, int numberLength) {
        List<Long> resultList = new ArrayList<>();

        int extraIncrement = 0;
//        long previousValue = -1l;

        for (int charIndex = 0; charIndex < s.length(); ) {
            int effectiveLength = numberLength + extraIncrement;

//            System.out.println("effectiveLength = " + effectiveLength);

            if (charIndex + effectiveLength > s.length()) {
                effectiveLength = s.length() - charIndex;
//                break;
            }

//            String subString = s.substring(charIndex, charIndex + effectiveLength + 1);
            String subString = s.substring(charIndex, charIndex + effectiveLength);

//            System.out.println("substring = " + subString + " leading character = " + subString.substring(0,0));

            if (subString.substring(0,1).equals("0")) {
                return List.of();
            }

            Long valueLong = Long.valueOf(subString);

            resultList.add(valueLong);

            if (extraIncrement == 0) {
//                System.out.println("valueLong = " + valueLong + " wrap value = " + (Math.pow(10, effectiveLength) - 1));

                if (valueLong == (Math.pow(10, effectiveLength) - 1l)) {
//                    System.out.println("Incrementing effectiveLength");

                    charIndex += numberLength + extraIncrement;

                    ++extraIncrement;
                } else {
                    charIndex += numberLength + extraIncrement;
                }
            } else {
                charIndex += numberLength + extraIncrement;
            }
        }

        return resultList;
    }

    private static boolean isListBeautiful(List<Long> longList) {
        for (int index = 1; index < longList.size(); ++index) {
            Long valueA = longList.get(index - 1);
            Long valueB = longList.get(index);

            if (valueA != valueB - 1) {
                return false;
            }
        }

        return true;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String s = bufferedReader.readLine();

                Result.separateNumbers(s);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
    }
}
