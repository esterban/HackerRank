package leetcode.jumpgame2;

class Solution {
    public int jump(int[] nums) {
        double startTimeNanos = System.nanoTime();

//        int result = recurse(nums, nums.length - 1, 0);
        int result = iterate2(nums);

        double endTimeNanos = System.nanoTime();
        double durationSeconds = (endTimeNanos - startTimeNanos) / 1e9;

        System.out.printf("Duration = %10.3f", durationSeconds);

        return result;
    }

    public int iterate2(int[] nums) {
        int bestJumpCount = 0;
        int nextBestIndex = nums.length - 1;

        boolean loop = true;

        while (loop) {
            nextBestIndex = findNextIndex(nums, nextBestIndex);
            ++bestJumpCount;

            if (nextBestIndex == 0) {
                break;
            }
        }

        return bestJumpCount;
    }

    public int findNextIndex(int[] nums, int index) {
        int nextIndex = index;

        for (int numsIndex = 0; numsIndex < index; ++numsIndex) {
            int position = numsIndex + nums[numsIndex];

            if (position >= index && numsIndex < nextIndex) {
                nextIndex = numsIndex;
//                return numsIndex;
            }
        }

        return nextIndex;
    }


    public int recurse(int[] nums, int startIndex, int numberOfJumps) {
        if (startIndex == 0) {
            return numberOfJumps;
        } else if (startIndex < 0) {
            return -1;
        }

        int bestJumpCount = Integer.MAX_VALUE;

        for (int index = 0; index < startIndex; ++index) {
            int numValue = nums[index];

            int newPosition = numValue + index;

            if (newPosition >= startIndex) {
                int nextJumpCount = recurse(nums, index, numberOfJumps + 1);

                if (nextJumpCount >= 0 && nextJumpCount < bestJumpCount) {
                    bestJumpCount = nextJumpCount;
                }
            }
        }

        return bestJumpCount == Integer.MAX_VALUE ? -1 : bestJumpCount;
    }

    //    public int iterate(int[] nums) {
//        int result = 0;
//
//        int index = 0;
//
//        boolean loop = true;
//
//        List<List<Integer>> numsList = new ArrayList<>();
//        int[] numsIndices = Collections.nCopies(nums.length, 0).stream().mapToInt(e -> e).toArray();
//
//        final int targetPosition = nums.length - 1;
//
//        int position = 0;
//        int jumpCount = 0;
//
//        int bestJumpCount = Integer.MAX_VALUE;
//
//        while (loop) {
//            List<Integer> jumps = numsList.get(index);
//
//            if (jumps == null) {
//                 jumps = IntStream.range(1, nums[index] + 1).boxed().collect(Collectors.toList());
//                 numsList.set(index, jumps);
//            }
//
//            int jump = jumps.get(numsIndices[index]);
//
//            int newPosition = position + jump;
//            int newJumpCount = jumpCount + 1;
//
//            if (newPosition == targetPosition) {
//                if (newJumpCount < bestJumpCount) {
//                    bestJumpCount = newJumpCount;
//                }
//
//                ++numsIndices[index];
//            } else if (newPosition > targetPosition) {
//                ++numsIndices[index];
//
//                --index;
//            }
//        }
//
//        return result;
//    }


}

