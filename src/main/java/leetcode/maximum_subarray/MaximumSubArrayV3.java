package leetcode.maximum_subarray;

import java.util.ArrayList;
import java.util.List;

public class MaximumSubArrayV3 {
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;

        List<Integer> sumFromFirst = new ArrayList<>();

        int sum = 0;

        for (int value : nums) {
            sum += value;
            sumFromFirst.add(sum);
        }

        // System.out.println("sumFromFirst = " + sumFromFirst);

        for (int leftIndex = 0; leftIndex < sumFromFirst.size(); ++leftIndex) {
            int startingSum = sumFromFirst.get(leftIndex);
            // System.out.println("startingSum = " + startingSum);
            maxSum = Math.max(startingSum, maxSum);

            for (int rightIndex = 0; rightIndex < leftIndex; ++rightIndex) {
                startingSum -= nums[rightIndex];

                // System.out.println("currentSum = " + startingSum);

                // if (startingSum > maxSum) {
                //     System.out.println("Updating maxSum = " + startingSum);
                // }

                maxSum = Math.max(startingSum, maxSum);
            }
        }

        return maxSum;
    }
}
