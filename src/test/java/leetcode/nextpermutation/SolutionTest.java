package leetcode.nextpermutation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    public void testNextPermutationA() {
        int[] nums = {1,2,3};

        Solution solution = new Solution();

        solution.nextPermutation(nums);

        assertThat(nums).isEqualTo(new int[]{1, 3, 2});
    }


    @Test
    public void testNextPermutationB() {
        int[] nums = {1,2,3,4,5};

        Solution solution = new Solution();

        solution.nextPermutation(nums);

        assertThat(nums).isEqualTo(new int[]{1,2,3,5,4});
    }

    @Test
    public void testNextPermutationC() {
        int[] nums = {3,2,1};

        Solution solution = new Solution();

        solution.nextPermutation(nums);

        assertThat(nums).isEqualTo(new int[]{1,2,3});
    }

    @Test
    public void testNextPermutationD() {
        int[] nums = {1,3,2};

        Solution solution = new Solution();

        solution.nextPermutation(nums);

        assertThat(nums).isEqualTo(new int[]{2,1,3});
    }

}