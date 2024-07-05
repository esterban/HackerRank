package leetcode.maximum_subarray;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static leetcode.maximum_subarray.MaxSubArrayUtil.sumList;

public class MaximumSubArrayV2 {
    public int maxSubArray(int[] nums) {
        List<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toList());

        int maxSum = Integer.MIN_VALUE;

        for (int subArrayLength = 1; subArrayLength <= numsList.size(); ++subArrayLength) {
            for (int leftIndex = 0; leftIndex <= numsList.size() - subArrayLength; ++leftIndex) {
                List<Integer> subList = numsList.subList(leftIndex, leftIndex + subArrayLength);

                int sum = sumList(subList);

                maxSum = Math.max(sum , maxSum);
            }
        }

        return maxSum;
    }
}
