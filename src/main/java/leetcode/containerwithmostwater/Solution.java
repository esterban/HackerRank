package leetcode.containerwithmostwater;

class Solution {
    public int maxArea(int[] height) {
        int maxArea = 0;

        int currentHeightA = 0;

        final int heightLength = height.length;

        for (int indexA = 0; indexA < heightLength; ++indexA) {
            int heightA = height[indexA];

            if (heightA <= currentHeightA) {
                continue;
            }

            currentHeightA = heightA;

            int maxPossibleArea = currentHeightA * (heightLength - indexA - 1);

            if (maxPossibleArea <= maxArea) {
                continue;
            }

            int currentHeightB = 0;

            for (int indexB = heightLength - 1; indexB > indexA; --indexB) {
                int heightB = height[indexB];

                if (heightB <= currentHeightB) {
                    continue;
                }

                currentHeightB = heightB;

                int distance = indexB - indexA;
                int thisHeight = Math.min(currentHeightA, currentHeightB);
                int thisArea = distance * thisHeight;
                maxArea = Math.max(maxArea, thisArea);

                if (currentHeightB >= currentHeightA) {
                    break;
                }
            }
        }

        return maxArea;
    }
}