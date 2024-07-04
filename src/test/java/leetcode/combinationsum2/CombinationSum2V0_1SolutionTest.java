package leetcode.combinationsum2;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class CombinationSum2V0_1SolutionTest {

    @Test
    void testA() {
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;

        SolutionV0_1 solution = new SolutionV0_1();

        List<List<Integer>> result = solution.combinationSum2(candidates, target);

        assertThat(result).hasSize(4);
    }

    @Test
    void testB() {
        int[] candidates = {14, 6, 25, 9, 30, 20, 33, 34, 28, 30, 16, 12, 31, 9, 9, 12, 34, 16, 25, 32, 8, 7, 30, 12, 33, 20, 21, 29, 24, 17, 27, 34, 11, 17, 30, 6, 32, 21, 27, 17, 16, 8, 24, 12, 12, 28, 11, 33, 10, 32, 22, 13, 34, 18, 12};
        int target = 27;

        SolutionV0_1 solution = new SolutionV0_1();

        List<List<Integer>> result = solution.combinationSum2(candidates, target);

        assertThat(result).hasSize(2);
    }

    //[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
    @Test
    void testC() {
        int[] candidates = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        int target = 30;

        SolutionV0_1 solution = new SolutionV0_1();

        List<List<Integer>> result = solution.combinationSum2(candidates, target);

        assertThat(result).hasSize(2);
    }


    @Test
    public void testIncrementIndicesA() {
        SolutionV0_1 solution = new SolutionV0_1();

        int[] indices = Collections.nCopies(4, 0).stream().mapToInt(e -> e).toArray();

        boolean success = solution.incrementIndices(indices, 1, 4);
        assertThat(indices[0]).isEqualTo(1);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 1, 4);
        assertThat(indices[0]).isEqualTo(2);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 1, 4);
        assertThat(indices[0]).isEqualTo(3);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 1, 4);
        assertThat(success).isFalse();
    }

    @Test
    public void testIncrementIndicesB() {
        SolutionV0_1 solution = new SolutionV0_1();

        int[] indices = IntStream.range(0, 2).toArray();

        boolean success = solution.incrementIndices(indices, 2, 4);
        assertThat(indices[0]).isEqualTo(0);
        assertThat(indices[1]).isEqualTo(2);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 2, 4);
        assertThat(indices[0]).isEqualTo(0);
        assertThat(indices[1]).isEqualTo(3);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 2, 4);
        assertThat(indices[0]).isEqualTo(1);
        assertThat(indices[1]).isEqualTo(2);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 2, 4);
        assertThat(indices[0]).isEqualTo(1);
        assertThat(indices[1]).isEqualTo(3);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 2, 4);
        assertThat(indices[0]).isEqualTo(2);
        assertThat(indices[1]).isEqualTo(3);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 2, 4);
        assertThat(success).isFalse();
    }

    @Test
    public void testIncrementIndicesC() {
        SolutionV0_1 solution = new SolutionV0_1();

//        int[] indices = Collections.nCopies(4, 0).stream().mapToInt(e -> e).toArray();
        int[] indices = IntStream.range(0, 3).toArray();

        assertThat(indices[0]).isEqualTo(0);
        assertThat(indices[1]).isEqualTo(1);
        assertThat(indices[2]).isEqualTo(2);

        boolean success = solution.incrementIndices(indices, 3, 4);
        assertThat(indices[0]).isEqualTo(0);
        assertThat(indices[1]).isEqualTo(1);
        assertThat(indices[2]).isEqualTo(3);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 3, 4);
        assertThat(indices[0]).isEqualTo(0);
        assertThat(indices[1]).isEqualTo(2);
        assertThat(indices[2]).isEqualTo(3);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 3, 4);
        assertThat(indices[0]).isEqualTo(1);
        assertThat(indices[1]).isEqualTo(2);
        assertThat(indices[2]).isEqualTo(3);
        assertThat(success).isTrue();

        success = solution.incrementIndices(indices, 3, 4);
        assertThat(success).isFalse();
    }


    @Test
    public void testIncrementIndicesD() {
        SolutionV0_1 solution = new SolutionV0_1();

        int[] indices = IntStream.range(0, 4).toArray();

        boolean success = solution.incrementIndices(indices, 4, 4);
        assertThat(success).isFalse();
    }

//    []

}