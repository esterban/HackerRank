package leetcode.maximum_subarray;

import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public int maxSubArray(int[] nums) {
        List<Integer> numsList = convertToList(nums);

        List<List<Integer>> result = new ArrayList<>();
        result.add(numsList);

        for (int counter = 1; counter <= 3; ++counter) {
            List<List<Integer>> nextResult = new ArrayList<>();

            for (List<Integer> subArray : result) {
                Map.Entry<List<Integer>, List<Integer>> nextSubArray = splitList(subArray);
                nextResult.add(nextSubArray.getKey());
                nextResult.add(nextSubArray.getValue());
            }

            System.out.println("nextResult = " + nextResult);

            result = nextResult;
        }

        return 0;
    }

    private static Map.Entry<List<Integer>, List<Integer>> splitList(List<Integer> numsList) {
        int centreIndex = numsList.size() / 2;

        return new AbstractMap.SimpleEntry<>(numsList.subList(0, centreIndex), numsList.subList(centreIndex, numsList.size()));


    }

    private static List<Integer> convertToList(int[] nums) {
        List<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toList());

        return numsList;
    }
}