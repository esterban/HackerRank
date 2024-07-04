package leetcode.multiplystrings;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void testA() {
        Solution solution = new Solution();

        String result = solution.multiply("11", "11");

        assertThat(result).isEqualTo("121");
    }

}