package leetcode.mergeintervals;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

class MergeIntervalsSolutionTest {
    @Test
    public void testA() {
        MergeIntervalsSolution mergeIntervalsSolution = new MergeIntervalsSolution();

        int[][] intervalList = new int[][]{{1, 2}, {3, 4}};

        int[][] result = mergeIntervalsSolution.merge(intervalList);

        assertThat(result.length).isEqualTo(2);
        assertThat(result).contains(new int[]{1, 2}, atIndex(0));
        assertThat(result).contains(new int[]{3, 4}, atIndex(1));
    }

    @Test
    public void testB() {
        MergeIntervalsSolution mergeIntervalsSolution = new MergeIntervalsSolution();

        int[][] intervalList = new int[][]{{1, 2}, {2, 4}};

        int[][] result = mergeIntervalsSolution.merge(intervalList);

        assertThat(result.length).isEqualTo(1);
        assertThat(result).contains(new int[]{1, 4}, atIndex(0));
    }

    @Test
    public void testC() {
        MergeIntervalsSolution mergeIntervalsSolution = new MergeIntervalsSolution();

        int[][] intervalList = new int[][]{{1, 2}, {2, 4}, {5,9}};

        int[][] result = mergeIntervalsSolution.merge(intervalList);

        assertThat(result.length).isEqualTo(2);
        assertThat(result).contains(new int[]{1, 4}, atIndex(0));
        assertThat(result).contains(new int[]{5, 9}, atIndex(1));
    }

    @Test
    public void testD() {
        MergeIntervalsSolution mergeIntervalsSolution = new MergeIntervalsSolution();

        int[][] intervalList = new int[][]{{2,3},{4,6},{5,7},{3,4}};

        int[][] result = mergeIntervalsSolution.merge(intervalList);

        assertThat(result.length).isEqualTo(1);
        assertThat(result).contains(new int[]{2, 7}, atIndex(0));
    }

    @Test
    public void testIntervalOverlapA() {
        List<Integer> intervalA = List.of(1,2);
        List<Integer> intervalB = List.of(3,4);

        MergeIntervalsSolution mergeIntervalsSolution = new MergeIntervalsSolution();

        assertThat(mergeIntervalsSolution.intervalOverlap(intervalA, intervalA)).isTrue();
        assertThat(mergeIntervalsSolution.intervalOverlap(intervalB, intervalB)).isTrue();
        assertThat(mergeIntervalsSolution.intervalOverlap(intervalA, intervalB)).isFalse();
        assertThat(mergeIntervalsSolution.intervalOverlap(intervalB, intervalA)).isFalse();
    }

    @Test
    public void testIntervalOverlapB() {
        List<Integer> intervalA = List.of(1,2);
        List<Integer> intervalB = List.of(2,4);

        MergeIntervalsSolution mergeIntervalsSolution = new MergeIntervalsSolution();

        assertThat(mergeIntervalsSolution.intervalOverlap(intervalA, intervalB)).isTrue();
        assertThat(mergeIntervalsSolution.intervalOverlap(intervalB, intervalA)).isTrue();
    }
}
