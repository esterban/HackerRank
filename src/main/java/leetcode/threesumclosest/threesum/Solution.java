package leetcode.threesumclosest.threesum;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Solution {


    public List<List<Integer>> threeSum(int[] nums) {
//        nums.as

        List<List<Integer>> solutionList = new ArrayList<>();

        Map<Integer, Set<List<Integer>>> partialSolutionMap = new HashMap<>();
        Set<Integer> availableValues = new HashSet<>(Arrays.stream(nums).boxed().collect(toList()));
//        availableValues.addAll(Collections. .asList(nums.toList));

        for (int i = 0; i < nums.length; ++i) {
            for (int j = i + 1; j < nums.length; ++j) {
                int partialSolution = i + j;

                partialSolutionMap.putIfAbsent(partialSolution, new HashSet<>());
                List<Integer> partialSolutionList = new ArrayList<>();

                if (i < j) {
                    partialSolutionList.add(i);
                    partialSolutionList.add(j);
                } else {
                    partialSolutionList.add(j);
                    partialSolutionList.add(i);
                }

                partialSolutionMap.get(partialSolution).add(partialSolutionList);
            }

        }

        for (Map.Entry<Integer, Set<List<Integer>>> partialSolution : partialSolutionMap.entrySet()) {
            Integer partialSolutionValue = partialSolution.getKey();

            if (availableValues.contains(- partialSolutionValue)) {
                for (List<Integer> partialSolutionList : partialSolution.getValue()) {
                    partialSolutionList.add(partialSolutionValue);
                    solutionList.add(partialSolutionList);
                }
            }

        }

        return solutionList;

//        for (int k = 0; k < nums.length; ++k) {
//        }
    }
}