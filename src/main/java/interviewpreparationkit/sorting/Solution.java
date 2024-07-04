package interviewpreparationkit.sorting;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    public static Set<Integer> goodIndices = new HashSet<>();

    /*
     * Complete the 'activityNotifications' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY expenditure
     *  2. INTEGER d
     */

    public static int activityNotifications(List<Integer> expenditure, int d) {
        SortedMap<Integer, Integer> valueCounts = new TreeMap<>();

        List<Integer> expenditureValues = new ArrayList<>(expenditure.subList(0, d));

        expenditureValues.stream().forEach(v -> addValue(valueCounts, v));

        Integer tailValue = 0;

        int numberOfNotifications = 0;

        for (int activeDayIndex = d; activeDayIndex < expenditure.size(); ++activeDayIndex) {
            tailValue = expenditure.get(activeDayIndex - d);
            Integer todaysExpenditure = expenditure.get(activeDayIndex);

//            if (activeDayIndex == 24093) {
//                System.out.println("At index = " + activeDayIndex + " what is happening");
//            }

            double medianExpenditure = median(valueCounts, d);

            if ((double)todaysExpenditure >= medianExpenditure * 2.0) {
                ++numberOfNotifications;

//                if (!goodIndices.contains(activeDayIndex)) {
//                    throw new RuntimeException("Problem, index " + activeDayIndex + " not found in correct solution");
//                }
            }

//            if (medianExpenditure > 102.0) {
//                throw new RuntimeException("All is well");
//            }

            checkCount(valueCounts, d);

            removeValue(valueCounts, tailValue);
            addValue(valueCounts, todaysExpenditure);

            checkCount(valueCounts, d);
        }

        return numberOfNotifications;
    }

    public static double median(SortedMap<Integer, Integer> valueCounts, int size) {
        int countSum = 0;

        boolean isOddSize = size % 2 == 1;
        int lowerIndex = size / 2 - 1;
        int upperIndex = size / 2;

        for (Map.Entry<Integer, Integer> entry : valueCounts.entrySet()) {
            int count = entry.getValue();
            countSum += count;

            if (isOddSize) {
                if (countSum > upperIndex) {
                    return entry.getKey();
                }
            } else {
                if (countSum > upperIndex) {
                    return entry.getKey();
                } else if (countSum == upperIndex) {
                    Integer lowerValue = entry.getKey();

                    Integer upperValue = valueCounts.tailMap(lowerValue + 1).firstKey();

                    return ((double)(lowerValue + upperValue)) / 2.0;
                }
            }
        }

        throw new RuntimeException("Could not find median");
    }

    public static void addValue(Map<Integer, Integer> map, Integer value) {
        map.compute(value, (k, v) -> v == null ? 1 : v + 1);
    }

    public static void removeValue(Map<Integer, Integer> map, Integer value) {
        map.compute(value, (k, v) -> v == 1 ? null : v - 1);
    }

    public static void checkCount(Map<Integer, Integer> map, int expectedCount) {
        int sum = map.values().stream().reduce(0, Integer::sum);

        if (sum != expectedCount) {
            throw new RuntimeException("Count mismatch");
        }
    }

    public static int activityNotifications2(List<Integer> expenditure, int d) {
        // Write your code here

        int numberOfAlerts = 0;

//        List<Integer> expenditureValues = expenditure.subList(0 , d);

//        SortedSet<Integer> expenditureSet = new TreeSet<>(expenditure.subList(0, d));
        List<Integer> expenditureValues = new ArrayList<>(expenditure.subList(0, d));

        Integer tailValue = expenditure.get(0);

        for (int activeDayIndex = d; activeDayIndex < expenditure.size(); ++activeDayIndex) {

            double medianExpenditure = median(expenditureValues);
//            double medianExpenditure = median2(expenditureValues);
//            double medianExpenditure = median3(expenditureSet);

            Integer todaysExpenditure = expenditure.get(activeDayIndex);

//            if (activeDayIndex == 24093) {
//                System.out.println("At index " + activeDayIndex + " Good median = " + medianExpenditure + " todayExpenditure = " + todaysExpenditure);
//            }

            if ((double) todaysExpenditure >= (medianExpenditure * 2.0)) {
//                numberOfAlerts += numberOfAlerts + 1;
                numberOfAlerts += 1;
                goodIndices.add(activeDayIndex);
            }

            boolean didRemove = expenditureValues.remove(tailValue);

            if (!didRemove) {
                throw new RuntimeException("Eeek, nothing was removed");
            }

            expenditureValues.add(todaysExpenditure);

            tailValue = expenditure.get(activeDayIndex - d + 1);


//            expenditureSet.remove(tailValue);
//            expenditureSet.add(todaysExpenditure);

//            expenditureValues.remove(0);
//            expenditureValues.add(expenditure.get(activeDayIndex));
        }

        return numberOfAlerts;
    }

    public static double median(List<Integer> values) {
        values.sort(Integer::compareTo);

        if (values.size() % 2 == 1) {
            return values.get(values.size() / 2);
        } else {
//            int lowerIndex = values.size() / 2;
//            int upperIndex = values.size() / 2 + 1;

            int lowerIndex = values.size() / 2 - 1;
            int upperIndex = values.size() / 2;

//            int lowerIndex = values.size() / 2 - 2;
//            int upperIndex = values.size() / 2 - 1;


            double median = (values.get(lowerIndex) + values.get(upperIndex)) / 2.0;

            return median;
        }
    }

    public static double median2(List<Integer> values) {
//        values.sort(Integer::compareTo);

        List<Integer> sortedValues = values.stream().sorted().collect(toList());

        if (values.size() % 2 == 1) {
            return sortedValues.get(sortedValues.size() / 2);
        } else {
            double median = (sortedValues.get(sortedValues.size() / 2 - 1) + sortedValues.get(sortedValues.size() / 2)) / 2.0;

            return median;
        }
    }

    public static double median3(SortedSet<Integer> valuesSet) {
        Iterator<Integer> it = valuesSet.iterator();

        int lowerIndex = valuesSet.size() / 2 - 1;

//        double valueA = 0.0;

        for (int index = 0; index < valuesSet.size(); ++index) {
            double valueA = it.next();

            if (index == lowerIndex) {
                if (valuesSet.size() % 2 == 1) {
                    return valueA;
                } else {
                    double valueB = it.next();

                    return (valueA + valueB) / 2.0;
                }
            }
        }

        throw new RuntimeException("Could not find median");
    }


}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int d = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> expenditure = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

//        int resultGood = Result.activityNotifications2(expenditure, d);


        long startNanos = System.nanoTime();
        int result = Result.activityNotifications(expenditure, d);
        long endNanos = System.nanoTime();

        long nanosSpent = endNanos - startNanos;
        double millisSpent = nanosSpent / 1e6;

//        System.out.println("Time taken (ms) = " + millisSpent);

        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();
//        bufferedWriter.write(String.valueOf(resultGood));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
