package leetcode.maximum_subarray;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MaximumSubArrayTest {

    @Test
    public void testA() {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};

        MaximumSubArray maximumSubArray = new MaximumSubArray();
        int result = maximumSubArray.maxSubArray(nums);

        assertThat(result).isEqualTo(6);
    }

}