 package leetcode.dividetwointegers;

import java.util.AbstractMap;

public class Solution {
    public int divide(int dividend, int divisor) {
        long absDividend = Math.abs((long)dividend);
        long absDivisor = Math.abs((long)divisor);

        if (absDividend < absDivisor) {
            return 0;
        }

        long dividendMagnitude = magTwo(absDividend);
        long divisorMagnitude = magTwo(absDivisor);

        AbstractMap.SimpleEntry<Long, Long> resultPair = divide(absDividend, absDivisor, dividendMagnitude, divisorMagnitude);

        long result = 0;
        long remainder = absDividend;

        while (resultPair != null) {
            result += resultPair.getKey() << (dividendMagnitude - divisorMagnitude);
            remainder -= resultPair.getKey() > 0L ? absDivisor << (dividendMagnitude - divisorMagnitude) : 0L;

            --dividendMagnitude;

            resultPair = divide(remainder, absDivisor, dividendMagnitude, divisorMagnitude);
        }

        if (flipSign(dividend, divisor) && result > Integer.MAX_VALUE) {
            return Integer.MIN_VALUE;
        }

        if (!flipSign(dividend, divisor) && result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        return flipSign(dividend, divisor) ? -((int)result) : ((int)result);
    }

    private AbstractMap.SimpleEntry<Long, Long> divide(long dividend, long divisor, long dividendMagnitude, long divisorMagnitude) {
        long magnitudeDifference = dividendMagnitude - divisorMagnitude;

        if (magnitudeDifference < 0) {
            return null;
        }

        long dividendShifted = dividend >> magnitudeDifference;

        long remainder = dividendShifted - divisor;

        return new AbstractMap.SimpleEntry<>(remainder >= 0L ? 1L : 0L, remainder);
    }

    private long magTwo(long value) {
        long mag = 0;

        while (value > 1) {
            value = value >> 1;
            ++mag;
        }

        return mag;
    }

    private boolean flipSign(long valueA, long valueB) {
        return valueA * valueB < 0;
    }
}