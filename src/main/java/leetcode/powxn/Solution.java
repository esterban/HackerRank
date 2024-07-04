package leetcode.powxn;

public class Solution {
    public double myPow(double x, int n) {
        double result = 1.0;

        if (x == 1.0) {
            return 1.0;
        }

        if (x == -1.0) {
            return n % 2 == 0 ? 1.0 : -1.0;
        }

        if (n > 0) {
            for (int counter = 1; counter <= n; ++counter) {
                result *= x;

                if (result == 0.0 || result == 1.0) {
                    return result;
                }
            }
        } else if (n < 0) {
            double denominator = 1.0;

            for (int counter = n; counter < 0; ++counter) {
                denominator *= x;
            }

            return 1.0 / denominator;
        }

        return result;
    }
}