package leetcode.powxn;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    @Test
    public void testA() {
        Solution solution = new Solution();
        double result = solution.myPow(1.0000000000001, -2147483648);

        assertThat(result).isEqualTo(0.9997854693121002);
    }

}