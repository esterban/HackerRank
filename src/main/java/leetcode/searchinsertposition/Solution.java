package leetcode.searchinsertposition;

import java.util.Arrays;

public class Solution {
    private int originalLength = -1;

    public int searchInsert(int[] nums, int target) {
        originalLength = nums.length;

        if (nums.length == 0) {
            return -100;
        } else if (nums.length == 1 && nums[0] != target) {
            if (nums[0] > target) {
                return 0;
            }

            return 1;
        }

        int outsideIndex = isFoundOutsideOriginalArray(nums, target, nums.length);

        if (outsideIndex >= 0) {
            return outsideIndex;
        }


        return lookAndSplit(nums, target);
    }

    private int lookAndSplit(int[] nums, int target) {
        int numsLength = nums.length;
        boolean isEvenLength = numsLength % 2 == 0;

        if (numsLength == 0) {
            return -10;
        } else if (numsLength == 1) {
            if (nums[0] != target) {
                return -200;
            }

            return 0;
        }

        int leftEndIndex = -1;
        int rightEndIndex = -1;

        if (isEvenLength) {
            leftEndIndex = numsLength / 2 - 1;
            rightEndIndex = numsLength / 2;
        } else {
            leftEndIndex = numsLength / 2;
            rightEndIndex = numsLength / 2 + 1;
        }

        if (rightEndIndex >= numsLength) {
            throw new RuntimeException(
                    "rightEndIndex >= numsLength , rightEndIndex = " + rightEndIndex + " , numsLength = " + numsLength);
        }

        int leftEndValue = nums[leftEndIndex];
        int rightEndValue = nums[rightEndIndex];

//        int leftEndValue = nums[leftEndIndex - 1];
//        int rightEndValue = nums[rightEndIndex - 1];

        if (leftEndValue == target) {
            return leftEndIndex;
        } else if (rightEndValue == target) {
            return rightEndIndex;
        } else if (leftEndValue < target && rightEndValue > target) {
            return rightEndIndex;
        }

        int[] leftArray = Arrays.copyOfRange(nums, 0, rightEndIndex);
        int[] rightArray = Arrays.copyOfRange(nums, rightEndIndex, numsLength);

        int foundIndexLeft = lookAndSplit(leftArray, target);
        int foundIndexRight = lookAndSplit(rightArray, target);

        if (foundIndexLeft >= 0) {
            return foundIndexLeft;
        }

        if (foundIndexRight >= 0) {
//            return foundIndexRight + numsLength / 2;
//            return foundIndexRight + numsLength / 2 + 1;
            return foundIndexRight + rightEndIndex;
        }


        return -40;
    }

    private int isFoundOutsideOriginalArray(int[] nums, int target, int originalLength) {
        if (target < nums[0]) {
            return 0;
        }

        if (target > nums[originalLength - 1]) {
            return originalLength;
        }

        if (target == nums[originalLength - 1]) {
            return originalLength - 1;
        }

        return -70;
    }
}