package euler.twofivefour;

import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static long factorialDigitSum(long value) {
        long[] values = {1,1,2,6,6,3,9,9,9,27};

        return values[(int)value];
    }


    public static long factorial(long value) {
        long[] factorialValues = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};

        return factorialValues[(int) value];
    }

    public static long f(long value) {
        long sum = 0;

        while (value > 0) {
            long remainder = value % 10;
            long remainderFact = factorial(remainder);
            sum += remainderFact;
            value = value / 10;
        }

        return sum;
    }

    public static long s(long value) {
        long sumOfDigits = 0;

        while (value > 0) {
            long remainder = value % 10;
            sumOfDigits += remainder;
            value = value / 10;
        }

        return sumOfDigits;
    }

    public static long g(long value) {
        return gSkip(value, 0);

//        for (long counter = 1; counter < Long.MAX_VALUE; ++counter) {
//            long fCounter = f(counter);
//            long sfCounter = s(fCounter);
//
//            if (sfCounter == value) {
//                return counter;
//            }
//        }
//
//        throw new RuntimeException("Could not find g for = " + value);
    }

    public static long gSkip(long value, int numberToSkip) {
        int numberFound = 0;

        for (long counter = 1; counter < Long.MAX_VALUE; ++counter) {
            long fCounter = f(counter);
            long sfCounter = s(fCounter);

            if (sfCounter == value) {
                ++numberFound;

                if (numberFound > numberToSkip) {
                    return counter;
                }
            }
        }

        throw new RuntimeException("Could not find g for = " + value);

    }

    public static boolean isAllOnes(long value) {
        boolean nonOneFound = false;

        while (!nonOneFound) {
            nonOneFound = (value % 10) != 1;
            value = value / 10;

            if (value == 0) {
                break;
            }
        }

        return !nonOneFound;
    }

//    public static void main_old(String[] args) {
        public static void main(String[] args) {
        System.out.println("111 -> " + isAllOnes(111));

        for (long counter = 1; counter < 1000; ++counter) {
            boolean allFound = false;

            long lastG = 0;
            int numberToSkip = 0;

            long gCounter = gSkip(counter, numberToSkip);

//            while (!isAllOnes(gCounter)) {
//                gCounter = gSkip(counter, numberToSkip);

//                if (gCounter == lastG) {
//                    allFound = true;
//                }

                System.out.println("g(" + counter + ", " + numberToSkip + ") = " + gCounter);

//                ++numberToSkip;
//            }

//            System.out.println(counter + "," + gCounter);
        }
    }

    public static void main_old(String[] args) {
//    public static void main(String[] args) {
//        long i = 1;

        for (long i = 12; i < 15; ++i) {
            long g = UtilMk1.gIterate(i);

            System.out.println("g(" + i + ") = " + g);
        }
    }
}
