package leetcode.dividetwointegers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    public void testDivideA() {
        Solution solution = new Solution();
        int result = solution.divide(10, 3);

        System.out.println("result = " + result);
    }

    @Test
    public void testDivideB() {
        Solution solution = new Solution();
        int result = solution.divide(-2147483648, -3);

        System.out.println("result = " + result);
    }


}