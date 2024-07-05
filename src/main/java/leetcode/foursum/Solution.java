package leetcode.foursum;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    private Comparator<AbstractMap.SimpleEntry<Integer, Integer>> pairComparator = new Comparator<AbstractMap.SimpleEntry<Integer, Integer>>() {
        @Override
        public int compare(AbstractMap.SimpleEntry<Integer, Integer> o1, AbstractMap.SimpleEntry<Integer, Integer> o2) {
            if (o1.getKey().compareTo(o2.getKey()) != 0) {
                return o1.getKey().compareTo(o2.getKey());
            }

            return o1.getValue().compareTo(o2.getValue());
        }
    };

//    public List<List<Integer>> fourSum(int[] nums, int target) {
//        Set<List<Integer>> result = new HashSet<>();
//
//        for (int indexA = 0; indexA < nums.length - 3; ++indexA) {
//            int a = nums[indexA];
//
//            for (int indexB = indexA + 1; indexB < nums.length - 2; ++indexB) {
//                int b = nums[indexB];
//
//                for (int indexC = indexB + 1; indexC < nums.length - 1; ++indexC) {
//                    int c = nums[indexC];
//
//                    for (int indexD = indexC + 1; indexD < nums.length; ++indexD) {
//                        int d = nums[indexD];
//
//                        if (a + b + c + d == target) {
//                            List<Integer> solution = sortedQuadruplet(a, b, c, d);
//                            result.add(solution);
//                        }
//                    }
//                }
//            }
//        }
//
//        return result.stream().collect(Collectors.toList());
//    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);

        Set<List<Integer>> result = new HashSet<>();

        Set<Integer> doubleCounts = findDoubleCounts(nums);
        Set<Integer> tripleCounts = findTripleCounts(nums);
        Set<Integer> quadrupleCounts = findQuadrupleCounts(nums);


        List<AbstractMap.SimpleEntry<Integer, Integer>> pairList = createPairs(nums);


        Set<AbstractMap.SimpleEntry<Integer, Integer>> doublePairSet = createDoublePairs(nums, doubleCounts);
        SortedMap<Long, SortedSet<AbstractMap.SimpleEntry<Integer, Integer>>> pairMap = createPairsMap(pairList);

        for (Map.Entry<Long, SortedSet<AbstractMap.SimpleEntry<Integer, Integer>>> entry : pairMap.entrySet()) {
            long pairValue = entry.getKey();
            long oppositeValue = target - pairValue;

//            if (oppositeValue.equals(pairValue) && !doublePairSet.contains(oppositeValue)) {
//                continue;
//            }


            if (pairMap.containsKey(oppositeValue)) {
                for (AbstractMap.SimpleEntry<Integer, Integer> pairA : entry.getValue()) {
                    for (AbstractMap.SimpleEntry<Integer, Integer> pairB : pairMap.get(oppositeValue)) {

                        if (checkValidTwinPair(pairA, pairB, doubleCounts, tripleCounts, quadrupleCounts)) {
                            List<Integer> solution = sortedQuadruplet(pairA.getKey(), pairA.getValue(), pairB.getKey(), pairB.getValue());
                            result.add(solution);
                        }
                    }
                }
            }
        }

        return result.stream().collect(Collectors.toList());
    }

    private boolean checkValidTwinPair(AbstractMap.SimpleEntry<Integer, Integer> pairA,
                                       AbstractMap.SimpleEntry<Integer, Integer> pairB,
                                       Set<Integer> doubleCounts,
                                       Set<Integer> tripleCounts,
                                       Set<Integer> quadrupleCounts) {
        int valueA1 = pairA.getKey();
        int valueA2 = pairA.getValue();
        int valueB1 = pairB.getKey();
        int valueB2 = pairB.getValue();

        if (valueA1 == valueA2 && valueA1 == valueB1 && valueA1 == valueB2 && !quadrupleCounts.contains(valueA1)) {
            return false;
        }

        if (valueA1 == valueB1 && valueA2 == valueB1 && !tripleCounts.contains(valueA1)) {
            return false;
        }

        if (valueA1 == valueB2 && valueA2 == valueB2 && !tripleCounts.contains(valueA1)) {
            return false;
        }

        if (valueA1 == valueB1 && valueA1 == valueB2 && !tripleCounts.contains(valueA1)) {
            return false;
        }

        if (valueA2 == valueB1 && valueA2 == valueB2 && !tripleCounts.contains(valueA2)) {
            return false;
        }


        if (valueA1 == valueB1 && !doubleCounts.contains(valueA1)) {
            return false;
        }

        if (valueA1 == valueB2 && !doubleCounts.contains(valueA1)) {
            return false;
        }

        if (valueA2 == valueB1 && !doubleCounts.contains(valueA2)) {
            return false;
        }

        if (valueA2 == valueB2 && !doubleCounts.contains(valueA2)) {
            return false;
        }

        return true;
    }


    private List<Integer> sortedQuadruplet(int a, int b, int c, int d) {
        List<Integer> result = List.of(a, b, c, d).stream().sorted().collect(Collectors.toList());
        return result;
    }

    private List<AbstractMap.SimpleEntry<Integer, Integer>> createPairs(int[] nums) {
        List<AbstractMap.SimpleEntry<Integer, Integer>> result = new ArrayList<>();

        for (int indexA = 0; indexA < nums.length - 1; ++indexA) {
            int valueA = nums[indexA];

            for (int indexB = indexA + 1; indexB < nums.length; ++indexB) {
                int valueB = nums[indexB];

                AbstractMap.SimpleEntry<Integer, Integer> pair = createSortedPair(valueA, valueB);
                result.add(pair);
            }
        }

        return result;
    }

    private Set<AbstractMap.SimpleEntry<Integer, Integer>> createDoublePairs(int[] nums, Set<Integer> doubleCounts) {
        Set<AbstractMap.SimpleEntry<Integer, Integer>> singlePairs = new HashSet<>();
        Set<AbstractMap.SimpleEntry<Integer, Integer>> doublePairs = new HashSet<>();

        for (int indexA = 0; indexA < nums.length - 1; ++indexA) {
            int valueA = nums[indexA];

            for (int indexB = indexA + 1; indexB < nums.length; ++indexB) {
                int valueB = nums[indexB];

                AbstractMap.SimpleEntry<Integer, Integer> pair = createSortedPair(valueA, valueB);

                if (singlePairs.contains(pair) && doubleCounts.contains(valueA) && doubleCounts.contains(valueB)) {
                    doublePairs.add(pair);
                }

                singlePairs.add(pair);
            }
        }

        return doublePairs;
    }

    private Set<Integer> findDoubleCounts(int[] nums) {
        Set<Integer> singleCounts = new HashSet<>();
        Set<Integer> doubleCounts = new HashSet<>();

        for (int value : nums) {
            if (singleCounts.contains(value)) {
                doubleCounts.add(value);
            }

            singleCounts.add(value);
        }

        return doubleCounts;
    }

    private Set<Integer> findTripleCounts(int[] nums) {
        Set<Integer> singleCounts = new HashSet<>();
        Set<Integer> doubleCounts = new HashSet<>();
        Set<Integer> tripleCounts = new HashSet<>();

        for (int value : nums) {
            if (singleCounts.contains(value)) {
                if (doubleCounts.contains(value)) {
                    tripleCounts.add(value);
                }

                doubleCounts.add(value);
            }

            singleCounts.add(value);
        }

        return tripleCounts;
    }

    private Set<Integer> findQuadrupleCounts(int[] nums) {
        Set<Integer> singleCounts = new HashSet<>();
        Set<Integer> doubleCounts = new HashSet<>();
        Set<Integer> tripleCounts = new HashSet<>();
        Set<Integer> quadrupleCounts = new HashSet<>();

        for (int value : nums) {
            if (singleCounts.contains(value)) {
                if (doubleCounts.contains(value)) {
                    if (tripleCounts.contains(value)) {
                        quadrupleCounts.add(value);
                    }

                    tripleCounts.add(value);
                }

                doubleCounts.add(value);
            }

            singleCounts.add(value);
        }

        return quadrupleCounts;
    }


    private AbstractMap.SimpleEntry<Integer, Integer> createSortedPair(Integer valueA, Integer valueB) {
        return valueA <= valueB ? new AbstractMap.SimpleEntry<>(valueA, valueB) : new AbstractMap.SimpleEntry<>(valueB, valueA);
    }

    private SortedMap<Long, SortedSet<AbstractMap.SimpleEntry<Integer, Integer>>> createPairsMap(List<AbstractMap.SimpleEntry<Integer, Integer>> pairList) {
        SortedMap<Long, SortedSet<AbstractMap.SimpleEntry<Integer, Integer>>> result = new TreeMap<>();

        for (AbstractMap.SimpleEntry<Integer, Integer> pair : pairList) {
            Long sum = (long) (pair.getKey() + pair.getValue());

            result.putIfAbsent(sum, new TreeSet<>(pairComparator));
            result.get(sum).add(pair);
        }

        return result;
    }
}

