package n254.attempt2;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    private static final long[] factorialsReversed = {362880L, 40320L, 5040L, 720L, 120L, 24L, 6L, 2L, 1L, 1L};

    private static int LONG_MAX_NUMBER_OF_DIGITS = 20;
    private static int LONG_FIRST_DIGIT_INDEX = LONG_MAX_NUMBER_OF_DIGITS - 1;

    private static long factorial(final long value) {
        assert (value >= 0L && value < 10L);

        return factorialsReversed[9 - (int) value];
    }

    public static long f(final long value, final int m) {
        long number = value;

        long sum = 0;

        while (number > 0) {
            long digit = number % 10;

            sum += factorial(digit);

            number = number / 10;
        }

//        return digitSum(sum);
        return sum;
    }

    private static long sf(final long value, final int m) {
        long fValue = f(value, m);
        long sfValue = digitSum(fValue);

        return sfValue;
    }

    private static long g(final long value, final int m) {
        long attempt = 1;

        long loopingLimit = Long.MAX_VALUE;

        for (; attempt < loopingLimit; ++attempt) {
            long sfValue = sf(attempt, m);

            if (sfValue == value) {
                return attempt;
            }
        }

        throw new RuntimeException("Could not find value limit of [" + loopingLimit + "]");
    }

    private static long sg(final long value, final int m) {
        long gValue = g(value, m);
        long sgValue = digitSum(gValue);

        return sgValue;
    }

    private static long sgSum(final long value, final int m) {
        long sum = 0;

        for (int counter = 1; counter <= value; ++counter) {
            long sgValue = sg(counter, m);
            long sgValueModded = sgValue % m;
            sum += sgValueModded;
        }

        return sum;
    }

    private static long digitSum(final long value) {
        long number = value;

        long sum = 0;

        while (number > 0) {
            long digit = number % 10;

            sum += digit;

            number = number / 10;
        }

        return sum;
    }

    public static boolean areDigitsAscending(Byte[] digits) {
        byte[] digitsPrimitiveArray = new byte[digits.length];

        for (int index = 0; index < digits.length; ++index) {
            digitsPrimitiveArray[index] = digits[index].byteValue();
        }

        return areDigitsAscending(digitsPrimitiveArray);
    }


    public static boolean areDigitsAscending(byte[] digits) {
        for (int index = 0; index < digits.length - 1; ++index) {
            if (digits[index] > digits[index + 1]) {
                return false;
            }
        }

        return true;
    }

    public static byte[] createLongDigitsForOne() {
        byte[] digits = new byte[LONG_MAX_NUMBER_OF_DIGITS];
        Arrays.fill(digits, (byte) 0);
        digits[LONG_FIRST_DIGIT_INDEX] = 1;

        return digits;
    }

    public static void incrementDigitsAscendingInPlace(byte[] digits) {
        int index = LONG_FIRST_DIGIT_INDEX;

        boolean incremented = false;

        while (!incremented) {
            byte lastDigit = digits[index];

            if (lastDigit == 0) {
                Arrays.fill(digits, index, LONG_MAX_NUMBER_OF_DIGITS , (byte) 1);

                incremented = true;
            } else if (lastDigit < 9) {
                Arrays.fill(digits, index, LONG_MAX_NUMBER_OF_DIGITS, (byte) (lastDigit + 1));

                incremented = true;
            }

            --index;
        }
    }

    public static long digitsToLong(byte[] digits) {
        long sum = 0L;
        int index = digits.length - 1;
        long digitMultiplier = 1L;

        while (index > 0 && digits[index] != 0) {
            sum += digits[index] * digitMultiplier;
            digitMultiplier *= 10L;
            --index;
        }

        return sum;
    }

    public static byte[] toDigits(final long value) {
        long number = value;

        int numberOfDigits = (int) Math.log10(value) + 1;

        byte[] result = new byte[numberOfDigits];

        int index = numberOfDigits - 1;

        while (number > 0) {
            long digit = number % 10;

            byte digitByte = (byte) digit;
            result[index] = digitByte;

            number = number / 10;
            --index;
        }

        assert (index == -1);

        return result;
    }

    public static void main(String[] args) {
        Scanner scannerIn = new Scanner(System.in);
        int q = Integer.parseInt(scannerIn.nextLine());

        long startNano = System.nanoTime();

//        debugOutput();

        for (int lineCounter = 1; lineCounter <= q; ++lineCounter) {
            String nmLine = scannerIn.nextLine();

            String[] parts = nmLine.split("\\s+");

            long n = Long.parseLong(parts[0]);
            int m = Integer.parseInt(parts[1]);

            long totalSum = sgSum(n, m);

            System.out.println(totalSum);
        }

        long endNano = System.nanoTime();
        long millisDuration = (endNano - startNano) / (1000 * 1000);
        System.out.println("Millis duration = " + millisDuration);
    }

    private static void debugOutput() {
        final int testM = 100000;
        final int counterLimit = 46;

        for (int counter = 1; counter <= counterLimit; ++counter) {
            long fValue = f(counter, testM);
            long sfValue = sf(counter, testM);
            long gValue = g(counter, testM);

            System.out.println("input = " + counter + " , f = " + fValue + " , sf = " + sfValue + " , g = " + gValue);
        }

        assert (sgSum(3L, 10000) == 8L);
        assert (sgSum(LONG_MAX_NUMBER_OF_DIGITS, 10000) == 156L);
    }

    private static void debugOutputValidInputNValues() {
        final long loopLimit = 10000L;

        for (long counter = 1L; counter <= loopLimit; ++counter) {


        }
    }
}

