package leetcode.combinationsum2;

import java.util.*;
import java.util.stream.Collectors;

public class SolutionV2 {
    Map<Integer, List<Integer>> subLists = new HashMap<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<Integer> sortedValues = Arrays.stream(candidates).boxed().sorted().collect(Collectors.toList());


        Map<Integer, Integer> valueCountMap = generateValueCountMap(candidates);
        valueCountMap = limitValueCountMapByTarget(valueCountMap, target);

        Set<List<Integer>> resultSet = recurse(valueCountMap, target, 0);
//        Set<List<Integer>> resultSet = new HashSet<>();

        return resultSet.stream().map(e -> e.stream().sorted().collect(Collectors.toList())).collect(Collectors.toList());
    }

    private Set<List<Integer>> recurse(Map<Integer, Integer> valueCountMap, int target, int startIndex) {
        if (startIndex >= valueCountMap.size()) {
            return null;
        }

        Set<List<Integer>> result = new HashSet<>();

        Integer value = getNthElement(valueCountMap.keySet(), startIndex);
        Integer count = getNthElement(valueCountMap.values(), startIndex);

        for (int counter = 0; counter <= count; ++counter) {
            int valueProduct = value * counter;

            if (valueProduct > target) {
                break;
            }

            int remaining = target - valueProduct;

            if (remaining == 0) {
                List<Integer> solution = Collections.nCopies(counter, value);
                result.add(solution);
            }

            Set<List<Integer>> nextSolutionSet = recurse(valueCountMap, remaining, startIndex + 1);

            if (nextSolutionSet != null) {
                for (List<Integer> solution : nextSolutionSet) {
                    List<Integer> solutionCopy = new ArrayList<>();
                    solutionCopy.addAll(solution);
                    solutionCopy.addAll(Collections.nCopies(counter, value));

                    result.add(solutionCopy);
                }
            }
        }

        return result;
    }

    Set<List<Integer>> recurse2(List<Integer> sortedValues, int target, int startIndex) {
        if (target < 0) {
            return null;
        }

        Set<List<Integer>> result = new HashSet<>();

        for (int index = 0; index < sortedValues.size(); ++index) {
            Integer value = getNthElement(sortedValues, index);

            // System.out.println("Value = " + value + " remaining = " + (target - value));

            if (sortedValues.size() == 1) {
                if (target == value) {
                    List<Integer> singleResult = Collections.singletonList(target);

                    result.add(singleResult);
                }

                continue;
            }

            List<Integer> valuesToUse;
            int offsetIndex = index + startIndex;

            if (subLists.containsKey(offsetIndex)) {
                valuesToUse = subLists.get(offsetIndex);
                // System.out.println("Reading from cache index = " + index + " , offset index = " + offsetIndex + " list = " + valuesToUse);
                // System.out.println("Start index = " + startIndex + " Check array = " + removeFirstNElements(sortedValues, index + 1));
            } else {
                valuesToUse = new ArrayList<>();
                valuesToUse.addAll(removeFirstNElements(sortedValues, index + 1));
                subLists.put(offsetIndex, valuesToUse);

                // System.out.println("index = " + (offsetIndex + 1) + " Add to cache = " + valuesToUse);
            }

            if (target == value) {
                // System.out.println("Solution found value = " + value + " values left = " + valuesToUse);

                // List<Integer> singleResult = new ArrayList<>();
                // singleResult.add(target);

                List<Integer> singleResult = Collections.singletonList(target);

                result.add(singleResult);
            } else {
                List<Integer> nextValues = new ArrayList<>();
                nextValues.addAll(valuesToUse);

                Set<List<Integer>> nextResults = recurse2(nextValues, target - value, startIndex + index + 1);

                if (nextResults != null) {
                    for (List<Integer> solution : nextResults) {
                        List<Integer> thisSolutionList = new ArrayList<>();
                        thisSolutionList.addAll(solution);
                        thisSolutionList.add(value);

                        result.add(thisSolutionList);
                    }
                }
            }
        }

        // System.out.println("result = " + result);

        return result;
    }

    List<Integer> removeFirstNElements(List<Integer> values, int nToRemove) {
        return values.subList(nToRemove, values.size());
    }

    Integer getNthElement(List<Integer> values, int n) {
        return values.get(n);
    }

//    Integer getNthElement(Set<Integer> values, int n) {
//        Iterator<Integer> setIterator = values.iterator();
//
//        for (int index = 0; index < n; ++index) {
//            setIterator.next();
//        }
//
//        return setIterator.next();
//    }

    Integer getNthElement(Collection<Integer> values, int n) {
        Iterator<Integer> setIterator = values.iterator();

        for (int index = 0; index < n; ++index) {
            setIterator.next();
        }

        return setIterator.next();
    }

    public Map<Integer, Integer> generateValueCountMap(int[] candidates) {
        Map<Integer, Integer> valueCountMap = new HashMap<>();

        for (int value : candidates) {
            valueCountMap.compute(value, (k, v) -> v == null ? 1 : v + 1);
        }

        return valueCountMap;
    }

    public Map<Integer, Integer> limitValueCountMapByTarget(Map<Integer, Integer> valueCountMap, int target) {
        Map<Integer, Integer> newValueCountMap = new HashMap<>();

        for (Map.Entry<Integer, Integer> valueCount : valueCountMap.entrySet()) {
            int count = target / valueCount.getKey();

            newValueCountMap.put(valueCount.getKey(), Math.min(valueCount.getValue(), count));
        }

        return newValueCountMap;
    }
}