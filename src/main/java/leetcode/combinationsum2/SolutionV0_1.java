package leetcode.combinationsum2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SolutionV0_1 {
    public List<List<Integer>> combinationSum2(int[] candidatesUnfiltered, int target) {
        int[] candidates = Arrays.stream(candidatesUnfiltered).filter(e -> e <= target).sorted().toArray();
        Set<List<Integer>> result = new HashSet<>();

        boolean loop = true;

        int[] indices = clearIndices(candidates.length);

        for (int candiatePickCount = 1; candiatePickCount <= candidates.length; ++candiatePickCount) {
            boolean isFirstLoop = true;

            while (loop) {
                int thisSum = calculateSum(candidates, indices, candiatePickCount);

                if (isFirstLoop && thisSum > target) {
                    break;
                }

                isFirstLoop = false;

                if (thisSum == target) {
                    int[] finalIndices = indices;
                    List<Integer> solution = IntStream.range(0, candiatePickCount).boxed().map(e -> candidates[finalIndices[e]]).sorted().collect(Collectors.toList());
                    result.add(solution);
                }

                loop = incrementIndices(indices, candiatePickCount, candidates.length);
            }

            indices = clearIndices(candidates.length);
            loop = true;
        }

        return result.stream().collect(Collectors.toList());
    }

    private int[] clearIndices(int indexCount) {
        return IntStream.range(0, indexCount).toArray();
    }

    private int calculateSum(int[] candidates, int[] indices, int indexCount) {
        int sum = 0;

        for (int indexNumber = 0; indexNumber < indexCount; ++indexNumber) {
            sum += candidates[indices[indexNumber]];
        }

        return sum;
    }

    public boolean incrementIndices(int[] indices, int indexCount, int maxIndex) {
        boolean loop = true;

        int currentIndex = indexCount - 1;

        while (loop) {
            if (indices[currentIndex] < maxIndex + (currentIndex - indexCount)) {
                break;
            }

            --currentIndex;

            if (currentIndex < 0) {
                return false;
            }
        }

        ++indices[currentIndex];
        int firstValue = indices[currentIndex] + 1;

        for (int index = currentIndex + 1; index < indexCount; ++index) {
            indices[index] = firstValue;
            ++firstValue;
        }

        if (indices[indexCount - 1] >= maxIndex) {
            return false;
        }

        return true;
    }
}