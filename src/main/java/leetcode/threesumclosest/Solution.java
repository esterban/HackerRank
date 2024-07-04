package leetcode.threesumclosest;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Solution {

    public int threeSumClosest(int[] nums, int target) {
        int closestInt = Integer.MAX_VALUE;

        SortedMap<Integer, Integer> solutionSet = new TreeMap<>();

        for (int value : nums) {
            solutionSet.compute(value, (k , v) -> v == null ? 1 : v + 1);
        }

        for (Map.Entry<Integer, Integer> entry : solutionSet.entrySet()) {
            if (entry.getKey() > 0) {
                break;
            }

            if (entry.getKey() > 0) {
                break;
            }


        }

        return closestInt;
    }

    private List<Integer> createSolution(int i, int j, int k) {
        List<Integer> solution = List.of(i, j, k).stream().sorted().collect(Collectors.toList());

        return solution;
    }



}
