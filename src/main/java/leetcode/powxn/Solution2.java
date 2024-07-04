package leetcode.powxn;

public class Solution2 {
    public double myPow(double x, int n) {
        double[] xPowers = new double[32];

        double currentPower = x;

        xPowers[0] = 1.0;

        for (int index = 1; index < 32; ++index) {
            xPowers[index] = currentPower;
            currentPower *= currentPower;
        }

        double result = 1.0;

        if (n > 0) {
            int powerIndex = 1;
            int currentIndex = n;

            while (currentIndex > 0) {
                if (currentPower % 2 == 1) {
                    result *= xPowers[powerIndex];
                    ++powerIndex;
                }

                currentIndex = currentIndex >> 1;
            }
        }

        return result;
    }
}
