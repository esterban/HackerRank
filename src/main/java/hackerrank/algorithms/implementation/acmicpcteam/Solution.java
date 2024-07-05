package hackerrank.algorithms.implementation.acmicpcteam;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {
    private static Map<Byte, Integer> s_byteCounts = new HashMap<>();

    static {
        for (Byte counter = Byte.MIN_VALUE; counter <= Byte.MAX_VALUE; ++counter) {
            int count = countDigitSet(counter);

            s_byteCounts.put(counter, count);

            if (counter == Byte.MAX_VALUE) {
                break;
            }
        }
    }

    private static Integer countDigitSet(Byte n) {
        int count = 0;

        int nInt = n;

        if (nInt < 0) {
            nInt = 256 + n;
        }

        while (nInt > 0) {
//            byte nMinus1 = (byte)(n.byteValue() - 1);
//
////            BigInteger nMinus1 = n.subtract(BigInteger.ONE);
//
//            byte comparison = (byte)(n & nMinus1);
//
//            ++count;
//
//            n = comparison;

            int nMinus1 = nInt - 1;
            int comparison = nInt & nMinus1;

            ++count;

            nInt = comparison;
        }

        return count;

//        Integer count = s_byteCounts.get(n);
//
//        return count;
    }

    public static List<Integer> acmTeam(List<String> topic) {
        List<BigInteger> topicsAsBigInts = new ArrayList<>();

        for (String topicString : topic) {
            BigInteger topicBigInteger = new BigInteger(topicString, 2);

            topicsAsBigInts.add(topicBigInteger);
        }

        int largestIntersection = 0;
        int countOfLargest = 0;

        for (int outerIndex = 0; outerIndex < topicsAsBigInts.size(); ++outerIndex) {
            BigInteger outerValue = topicsAsBigInts.get(outerIndex);

            for (int innerIndex = outerIndex + 1; innerIndex < topicsAsBigInts.size(); ++innerIndex) {
                BigInteger innerValue = topicsAsBigInts.get(innerIndex);
                BigInteger allTopicsBigInt = outerValue.or(innerValue);

//                int numberOfTopicsCheck = countSetDigits(allTopicsBigInt);

                int numberOfTopics = countSetDigits3(allTopicsBigInt);

//                if (numberOfTopics != numberOfTopicsCheck) {
//                    System.out.println("ERROR " + numberOfTopics + " should equal " + numberOfTopicsCheck);
//                    System.out.println("LHS = " + outerValue + " RHS = " + innerValue);
//
//                    throw new RuntimeException();
//                }

                if (numberOfTopics == largestIntersection) {
                    ++countOfLargest;
                }
                else if (numberOfTopics > largestIntersection) {
                    countOfLargest = 1;
                    largestIntersection = numberOfTopics;
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        result.add(largestIntersection);
        result.add(countOfLargest);

        return result;
    }

    public static int countSetDigits(BigInteger n) {
        int count = 0;

        while (n.compareTo(BigInteger.ZERO) > 0) {
            BigInteger nMinus1 = n.subtract(BigInteger.ONE);
            BigInteger comparison = n.and(nMinus1);

            ++count;

            n = comparison;
        }

        return count;
    }

//    public static int countSetDigits2(BigInteger n) {
//        int count = 0;
//
//        while (n.compareTo(BigInteger.ZERO) > 0) {
//            if (n.mod(BigInteger.valueOf(2l)).equals(BigInteger.ONE)) {
//
//            }
//        }
//
//
//    }

    public static int countSetDigits3(BigInteger n) {
        int count = 0;

        byte[] byteArray = n.toByteArray();

        for (byte value : byteArray) {
            int valueCount = s_byteCounts.get(value);

//            count += countDigitSet(value);
            count += valueCount;
        }

        return count;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

//        Thread.sleep(30000);

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<String> topic = IntStream.range(0, n).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        BigInteger testValue = new BigInteger("3055604868842075029617669083728952162242380650305664978604108947726298597184519450076612944895773212083928903746418468161128957054807930867869678555063");

        int countCheck = Result.countSetDigits(testValue);
        int count = Result.countSetDigits3(testValue);

        long startNanos = System.nanoTime();
        List<Integer> result = Result.acmTeam(topic);
        long endNanos = System.nanoTime();

        double durationMillis = (endNanos - startNanos) / 1e6;

        System.out.println("Duration ms = " + durationMillis);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
