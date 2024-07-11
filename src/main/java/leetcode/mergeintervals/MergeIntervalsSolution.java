package leetcode.mergeintervals;

import java.util.*;
import java.util.stream.Collectors;

public class MergeIntervalsSolution {
    public int[][] merge(int[][] intervals) {
        int[][] result;

        List<List<Integer>> intervalSet = createIntervalSet(intervals);

        result = intervalSet.stream().map(e -> new int[]{e.get(0), e.get(1)}).collect(Collectors.toList()).toArray(int[][]::new);

        return result;
    }

    private List<List<Integer>> createIntervalSet(int[][] intervals) {
        Set<List<Integer>> intervalSet = new HashSet<>();

        for (int[] interval : intervals) {
            List<Integer> intervalList = toIntervalList(interval);

            Set<List<Integer>> nextIntervalSet = new HashSet<>();

            boolean intervalAdded = false;

            boolean loop = true;

            while (loop) {
                for (List<Integer> existingInterval : intervalSet) {
                    if (intervalOverlap(intervalList, existingInterval)) {
                        intervalList = mergeIntervalPair(intervalList, existingInterval);

                        intervalAdded = true;
                    } else {
                        nextIntervalSet.add(existingInterval);
                    }
                }

                if (!intervalAdded) {
                    nextIntervalSet.add(intervalList);
                    loop = false;
                }

                intervalAdded = false;
                intervalSet = nextIntervalSet;
            }
        }

        return intervalSet.stream().sorted(Comparator.comparingInt(e -> e.get(0))).collect(Collectors.toList());
    }

    private List<Integer> mergeIntervalPair(List<Integer> intervalA, List<Integer> intervalB) {
        if (!intervalOverlap(intervalA, intervalB)) {
            throw new RuntimeException("Intervals not overlapping");
        }

        int leftValue = Math.min(intervalA.get(0), intervalB.get(0));
        int rightValue = Math.max(intervalA.get(1), intervalB.get(1));

        return List.of(leftValue, rightValue);
    }

    public boolean intervalOverlap(List<Integer> intervalA, List<Integer> intervalB) {
        return (intervalA.get(0) <= intervalB.get(0) && intervalA.get(1) >= intervalB.get(0)) ||
                (intervalB.get(0) <= intervalA.get(0) && intervalB.get(1) >= intervalA.get(0));
    }

    private List<Integer> toIntervalList(int[] interval) {
        return List.of(interval[0], interval[1]);
    }
}