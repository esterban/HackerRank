package leetcode.maximum_subarray;

import java.util.*;
import java.util.stream.Collectors;

//import static leetcode.maximum_subarray.MaxSubArrayUtil.*;

public class MaximumSubArray {
    public int maxSubArray(int[] nums) {
        List<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toList());

        Map.Entry<List<Integer>, List<Integer>> level0Split = splitList(numsList);

        List<Integer> leftSplitL0 = level0Split.getKey();
        List<Integer> rightSplitL0 = level0Split.getValue();

        System.out.println("Level0 split left subList= " + leftSplitL0 + " -> right = " + rightSplitL0);

        int leftSplitL0Sum = sumList(leftSplitL0);
        int rightSplitL0Sum = sumList(rightSplitL0);

        System.out.println("Level0 split left sum = " + leftSplitL0Sum + " -> right sum = " + rightSplitL0Sum);

        List<Integer> sumListL1 = generateSumList(numsList);
        List<Integer> sumListL2 = generateSumList(sumListL1);

        System.out.println("Level 1 -> sumList = " + sumListL1);
        System.out.println("Level 2 -> sumList = " + sumListL2);

        List<Integer> sumListReducedA = generateSumList(sumListL1);
        List<Integer> sumListReducedB = generateSumList(sumListL1.subList(1, sumListL1.size()));

        System.out.println("sumListReducedA = " + sumListReducedA + " -> sumListReducedB = " + sumListReducedB);

        return 0;
    }

    public static Map.Entry<List<Integer>, List<Integer>> splitList(List<Integer> numsList) {
        int centreIndex = numsList.size() / 2;
        if (numsList.size() > 1 && numsList.size() % 2 == 1) {
            ++centreIndex;
        }

        return new AbstractMap.SimpleEntry<>(numsList.subList(0, centreIndex), numsList.subList(centreIndex, numsList.size()));
    }

    public static int sumList(List<Integer> list) {
        return list.stream().reduce(0, (a, v) -> a + v);
    }

    public static List<Integer> generateSumList(List<Integer> initialList) {
        List<List<Integer>> subListList = generateSubLists(initialList);
        List<Integer> sumList = sumSubLists(subListList);

        return sumList;
    }

    public static List<List<Integer>> generateSubLists(List<Integer> initialList) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(initialList);

        boolean loop = true;

        while (loop) {
            List<List<Integer>> nextResult = new ArrayList<>();
            loop = false;

            for (List<Integer> subArray : result) {
                if (subArray.size() <= 2) {
                    nextResult.add(subArray);

                    continue;
                }

                Map.Entry<List<Integer>, List<Integer>> nextSubArray = splitList(subArray);
                List<Integer> leftList = nextSubArray.getKey();
                List<Integer> rightList = nextSubArray.getValue();

                nextResult.add(nextSubArray.getKey());
                nextResult.add(nextSubArray.getValue());

                loop |= leftList.size() > 2 || rightList.size() > 2;
            }

            result = nextResult;
        }

        return result;
    }

    public static List<Integer> sumSubLists(List<List<Integer>> subListList) {
        List<Integer> result = new ArrayList<>();

        for (List<Integer> subArray : subListList) {
            int sum = sumList(subArray);
            result.add(sum);
        }

        return result;
    }

}