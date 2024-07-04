package leetcode.combinationsum2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidatesUnfiltered, int target) {
        int[] candidates = Arrays.stream(candidatesUnfiltered).filter(e -> e <= target).toArray();

        Map<Integer, Integer> valueCounts = new HashMap<>();

        for (int value : candidates) {
            valueCounts.compute(value, (k, v) -> v == null ? 1 : v + 1);
        }

        for (int candidateCount = 1; candidateCount < candidates.length; ++candidateCount) {

        }

        return null;
    }


}