package leetcode.nextpermutation;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    public void nextPermutation(int[] nums) {
        if (nums.length < 2) {
            return;
        }

        int[] leftPart = null;
        int[] rightPart = null;

        int lastIndex = nums.length - 1;
        int highestNumber = nums[lastIndex];

        --lastIndex;
        int currentValue = nums[lastIndex];

        for ( ; lastIndex >= 0; --lastIndex) {
            currentValue = nums[lastIndex];

            if (currentValue < highestNumber) {
                break;
            }

            highestNumber = currentValue;
        }

        if (lastIndex < 0) {
            Arrays.sort(nums);
            return;
        }

        leftPart = Arrays.copyOf(nums, lastIndex);

        List<Integer> rightList = Arrays.stream(nums).boxed().collect(Collectors.toList()).subList(lastIndex, nums.length);

        AbstractMap.SimpleEntry<List<Integer>, Integer> lowestValueAndList = getLowestValueAndRemove(rightList, currentValue);

        List<Integer> result = Arrays.stream(leftPart).boxed().collect(Collectors.toList());
        result.add(lowestValueAndList.getValue());
        result.addAll(lowestValueAndList.getKey().stream().sorted().collect(Collectors.toList()));

        for (int index = 0; index < nums.length; ++index) {
            nums[index] = result.get(index);
        }
    }

    private AbstractMap.SimpleEntry<List<Integer>, Integer> getLowestValueAndRemove(List<Integer> integerList, int minimumValue) {
        int lowestValue = Integer.MAX_VALUE;

        int indexOfLowestValue = -1;

        for (int index = 0; index < integerList.size(); ++index) {
            int value = integerList.get(index);

            if (integerList.get(index) < lowestValue && value > minimumValue) {
                indexOfLowestValue = index;
                lowestValue = integerList.get(index);
            }
        }

        List<Integer> inputCopy = new ArrayList<>(integerList);
        inputCopy.remove(indexOfLowestValue);

        return new AbstractMap.SimpleEntry<>(inputCopy, lowestValue);
    }


//    public void nextPermutation(int[] nums) {
//        if (nums.length < 2) {
//            return;
//        }
//
//        int[] leftPart = null;
//        int[] rightPart = null;
//
//        int lastIndex = nums.length - 1;
//        int highestNumber = nums[lastIndex];
//
//        --lastIndex;
//        int currentValue = nums[lastIndex];
//
//        for ( ; lastIndex >= 0; --lastIndex) {
//            currentValue = nums[lastIndex];
//
//            if (currentValue < highestNumber) {
//                break;
//            }
//
//            highestNumber = currentValue;
//        }
//
//        if (lastIndex < 0) {
//            Arrays.sort(nums);
//            return;
//        }
//
//        leftPart = Arrays.copyOf(nums, lastIndex);
//        rightPart = Arrays.copyOfRange(nums, lastIndex + 2, nums.length);
//
//        List<Integer> result = new ArrayList<>();
//        result.addAll(Arrays.stream(leftPart).boxed().collect(Collectors.toList()));
//        result.add(highestNumber);
//        result.add(currentValue);
//        result.addAll(Arrays.stream(rightPart).boxed().sorted().collect(Collectors.toList()));
//
//        for (int index = 0; index < nums.length; ++index) {
//            nums[index] = result.get(index);
//        }
//    }
}