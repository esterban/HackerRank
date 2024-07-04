package leetcode.maximum_subarray;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    public void testA() {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};

        Solution solution = new Solution();
        int result = solution.maxSubArray(nums);

        assertThat(result).isEqualTo(6);
    }

}