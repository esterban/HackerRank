package leetcode.combinationsum2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CombinationSum2SolutionTest {

    @Test
    void testA() {
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;

        SolutionV2 solution = new SolutionV2();

        List<List<Integer>> result = solution.combinationSum2(candidates, target);

        assertThat(result).hasSize(4);

        assertThat(result).contains(
                List.of(1, 1, 6),
                List.of(1, 2, 5),
                List.of(1, 7),
                List.of(2, 6) );
    }

    @Test
    void testB() {
        int[] candidates = {2, 5, 2, 1, 2};
        int target = 5;

        SolutionV2 solution = new SolutionV2();

        List<List<Integer>> result = solution.combinationSum2(candidates, target);

        assertThat(result).hasSize(2);

        assertThat(result).contains(
                List.of(1,2,2),
                List.of(5) );
    }

    // wrightsr 2024-07-04 Not sure what the test case should be - solution works on leetcode but I can't see what the inputs and result are
//    @Test
//    void testC() {
//        int[] candidates = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
//        int target = 30;
//
//        SolutionV2 solution = new SolutionV2();
//
//        List<List<Integer>> result = solution.combinationSum2(candidates, target);
//
//        assertThat(result).hasSize(2);
//    }
}