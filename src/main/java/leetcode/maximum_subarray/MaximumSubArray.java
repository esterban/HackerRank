package leetcode.maximum_subarray;

import java.util.*;
import java.util.stream.Collectors;

class MaximumSubArray {
    public int maxSubArray(int[] nums) {
        List<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toList());

//        List<List<Integer>> result = new ArrayList<>();
//        result.add(numsList);

//        for (int counter = 1; counter <= 3; ++counter) {
//            List<List<Integer>> nextResult = new ArrayList<>();
//
//            for (List<Integer> subArray : result) {
//                if (subArray.size() <= 2) {
//                    nextResult.add(subArray);
//
//                    continue;
//                }
//
//                Map.Entry<List<Integer>, List<Integer>> nextSubArray = splitList(subArray);
//                nextResult.add(nextSubArray.getKey());
//                nextResult.add(nextSubArray.getValue());
//            }
//
//            System.out.println("nextResult = " + nextResult);
//
//            result = nextResult;
//        }



//        List<Integer> sumList = sumSubLists(result);
        List<Integer> sumListA = generateSumList(numsList);
        List<Integer> sumListB = generateSumList(sumListA);

        System.out.println("sumList = " + sumListA);
//        System.out.println("shiftedSumList = " + shiftedSumList);

        List<Integer> sumListReducedA = generateSumList(sumListA);
        List<Integer> sumListReducedB = generateSumList(sumListA.subList(1, sumListA.size()));

        System.out.println("sumListReducedA = " + sumListReducedA + " ->  sumListReducedB = " + sumListReducedB);

        return 0;
    }

    private static Map.Entry<List<Integer>, List<Integer>> splitList(List<Integer> numsList) {
        int centreIndex = numsList.size() / 2;
        if (numsList.size() > 1 && numsList.size() % 2 == 1) {
            ++centreIndex;
        }

        return new AbstractMap.SimpleEntry<>(numsList.subList(0, centreIndex), numsList.subList(centreIndex, numsList.size()));
    }

    private static int sumList(List<Integer> list) {
        return list.stream().reduce(0, (a, v) -> a + v);
    }

    private List<Integer> generateSumList(List<Integer> initialList) {
        List<List<Integer>> subListList = generateSubLists(initialList);
        List<Integer> sumList = sumSubLists(subListList);

        return sumList;
    }

    private List<List<Integer>> generateSubLists(List<Integer> initialList) {
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

//            System.out.println("nextResult = " + nextResult);

            result = nextResult;
        }

        return result;
    }


    private List<Integer> sumSubLists(List<List<Integer>> subListList) {
        List<Integer> result = new ArrayList<>();

        for (List<Integer> subArray : subListList) {
            int sum = sumList(subArray);
            result.add(sum);
        }

        return result;
    }
}