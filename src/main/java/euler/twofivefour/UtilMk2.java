package euler.twofivefour;

import java.util.*;

import static java.lang.Math.pow;

public class UtilMk2 {
    public static Long first3Values[] = {1L, 2L, 5L};
    public static Set<Long> availableValues; // = new HashSet<>();

    public static List<Long> gValues = new ArrayList<>();
    public static List<Long> gValues2Digits = new ArrayList<>();

    static {
        availableValues = new HashSet<>(Arrays.asList(first3Values));
    }

//    public static long getNthDigit(long value, long nthDigit) {
//        long decLeftShift = value / (long)pow(10L, (nthDigit));
//
//        return decLeftShift % 10L;
//    }

    public static long g(long value) {
//        if ((int)value >= gValues.size()) {
        Set<Long> availableDigits = UtilMk1.valueAvailableSet();

        if (availableDigits.contains(value)) {
            return UtilMk1.factorialValueAvailable(value);
        }

        while (value >= gValues.size()) {
            long lastValue = gValues.get(gValues.size() - 1);

            List<Long> digits = UtilMk1.decomposeToDigits(lastValue);

            if (digits.get(digits.size() - 1) == 1) {
                digits.set(0, 2L);
            } else if (digits.get(digits.size() - 1) == 2) {

            }

//            long nextValue = UtilMk1.

//                    gValues.add(lastValue);

//            if ()
        }

        return gValues.get(gValues.size() - 1);
    }

}
