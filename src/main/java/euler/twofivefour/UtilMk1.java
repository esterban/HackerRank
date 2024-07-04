package euler.twofivefour;

import java.util.*;

public class UtilMk1 {
    public static long factorialValueAvailable(long value) {
        long[] valuesAvailable = {1, 2, 5, -1, -1, 3, -1, -1, 6};

        if (value >= 1 && value <= 9) {
            return valuesAvailable[(int) value - 1];
        }

        if (value == 27) {
            return 9;
        }

        return -1;
    }

    public static SortedSet<Long> valueAvailableSet() {
        SortedSet<Long> valueAvailableSet = new TreeSet<>(Arrays.asList(new Long[]{1L, 2L, 3L, 6L, 9L}));

        return valueAvailableSet;
    }

//    public static long nextHighestValue

    public static long gIterate(long value) {
        Set<Long> availableValues = valueAvailableSet();

        long trailing = value % 10;

        if (availableValues.contains(value)) {
            return factorialValueAvailable(value);
        }



        throw new RuntimeException("gIterate(" + value + ") could not be computed");
    }

    public static List<Long> gValuesList = new ArrayList<>();

    public static List<Long> decomposeToDigits(long value) {
        List<Long> digits = new ArrayList<>();

        while (value > 0) {
            long digit = value % 10;

            digits.add(digit);

            value = value / 10;
        }

        return digits;
    }

    public static long recomposeReverseDigits(List<Long> digits) {
        long value = 0;
        long pow = 1L;

        for (int index = 0; index < digits.size(); ++index) {
            long digit = digits.get(index);

            value += digit * pow;
            pow *= 10L;
        }

        return value;
    }

    public static long gMk2(long value) {
        if (gValuesList.isEmpty()) {
            gValuesList.add(1L);
        }

        if (gValuesList.size() < (int)value) {
            long lastG = gValuesList.size() + 1;

            List<Long> digits = decomposeToDigits(gValuesList.get(gValuesList.size() - 1));
        }

        throw new RuntimeException("Could not compute g(" + value + ")");
    }
}
