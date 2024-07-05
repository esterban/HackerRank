package n254;

import java.util.*;

public class Solution {
    //    private static final List<List<Byte[]>> allRepresentationsForLength = new ArrayList<>();
    private static final Map<Long, Pair<Long, Long>> gValueCache = new HashMap<>();

    public static long f(final long value) {
        long number = value;

        long sum = 0;

        while (number > 0) {
            long digit = number % 10;

            sum += factorial(digit);

            number = number / 10;
        }

//        return digitSum(sum);
        return sum;
    }

    public static List<Byte[]> createValidRepresentations(final int numberOfDigits) {
        return recurseRepresentation(1, numberOfDigits, numberOfDigits);
    }

    private static List<Byte[]> recurseRepresentation(long valueLeft, int digitsLeft, int representationSize) {
        if (digitsLeft == 1) {
            List<Byte[]> representationList = new ArrayList<>();

            for (byte counter = (byte) valueLeft; counter <= 9; ++counter) {
                Byte[] representation = new Byte[representationSize];
                representation[representationSize - 1] = counter;
                representationList.add(representation);
            }

            return representationList;
        } else {
            List<Byte[]> representationList = new ArrayList<>();

            for (byte counter = (byte) valueLeft; counter <= 9; ++counter) {
                byte finalCounter = counter;

                List<Byte[]> representationSubList = recurseRepresentation(counter, digitsLeft - 1, representationSize);
                representationSubList.forEach(e -> e[representationSize - digitsLeft] = finalCounter);

                representationList.addAll(representationSubList);
            }

            return representationList;
        }
    }

    private static long factorial(final long value) {
        assert (value >= 0L && value < 10L);

        return factorialsReversed[9 - (int) value];
    }


    public final static class Pair<T, U> {
        public Pair(T t, U u) {
            tThis = t;
            uThis = u;
        }

        public final T tThis;
        public final U uThis;
    }

    public static List<Byte[]> recurse(long valueLeft, int level) {
        if (level == 1) {
            if (valueLeft > 9) {
                return Collections.emptyList();
            }

            List<Byte[]> resultList = new ArrayList<>();
            Byte[] result = new Byte[1];
            result[0] = (byte) valueLeft;

            resultList.add(result);

            return resultList;
        }

        List<Byte[]> newRepsList = new ArrayList<>();

        for (byte counter = 0; counter <= Math.min(valueLeft, 9); ++counter) {
            List<Byte[]> levelBelowRepresentations = recurse(valueLeft - counter, level - 1);

            for (Byte[] rep : levelBelowRepresentations) {

                Byte[] newRep;
                if (rep == null) {
                    newRep = new Byte[1];
                    newRep[0] = counter;
                } else {
                    newRep = Arrays.copyOf(rep, rep.length + 1);
                    newRep[level - 1] = counter;
                }

                newRepsList.add(newRep);
            }
        }

        return newRepsList;
    }

    public static List<Byte[]> recurse2(long valueLeft, int level) {
//        991
//        982
//        9811
//        973

        return null;
    }

    public static Byte[] startingRepresentation(long value, int length) {
        int size = (int) (value / 9);

        if (value % 9 != 0) {
            ++size;
        }


        Byte[] representation = new Byte[length];

        representation[0] = (byte) (value % 9);

        if (length > size) {
            representation[0] = (byte) 1;


        }

        for (int index = 1; index < size - 1; ++index) {
            representation[index] = 9;
        }

        return representation;
    }

    private static int[] factorialsReversed = {362880, 40320, 5040, 720, 120, 24, 6, 2, 1, 1};

    private static long fSolve(long value) {
        long remainder = value;
        long solution = 0;

        int currentIndex = 0;
        long pow = 1;

        while (remainder > 0) {
            int factorial = factorialsReversed[currentIndex];

            if (factorial > remainder) {
                ++currentIndex;
            } else if (factorial == remainder) {
                solution += (9 - currentIndex) * pow;

                if (solution < 0) {
                    return Long.MAX_VALUE;
                }

                remainder -= factorial;

                pow *= 10;
            } else {
                solution += (9 - currentIndex) * pow;

                if (solution < 0) {
                    return Long.MAX_VALUE;
                }

                remainder -= factorial;

                pow *= 10;
            }
        }

        return solution;
    }

    private static long valueOfRep(Byte[] rep) {
        long value = 0;
        long pow = 1;

        for (int index = 0; index < rep.length; ++index) {
            long digitValue = rep[index] * pow;
            value += digitValue;

            pow *= 10l;
        }

        return value;
    }

    private static long valueOfRepReversed(Byte[] rep) {
        long value = 0;
        long pow = 1;

        for (int index = rep.length - 1; index >= 0; --index) {
            long digitValue = rep[index] * pow;
            value += digitValue;

            pow *= 10l;
        }

        return value;
    }


    public static Pair<Long, Long> gFunction2(long valueToFind) {
        long lowestSolution = Long.MAX_VALUE;
        long fForSolution = 0;

        if (gValueCache.containsKey(valueToFind)) {
            return gValueCache.get(valueToFind);
        }

        for (int lengthCounter = 1; lengthCounter < 20; ++lengthCounter) {
//            List<Byte[]> representationsForLength = recurse(valueToFind, lengthCounter);
            List<Byte[]> representationsForLength = createValidRepresentations(lengthCounter);

            for (Byte[] rep : representationsForLength) {
//                long value = valueOfRep(rep);
//                long fSolve = fSolve(value);
//                if (fSolve < lowestSolution) {
//                    lowestSolution = fSolve;
//                    fForSolution = value;
//                }

                long value = valueOfRepReversed(rep);
                long fValue = f(value);
                long sfValue = digitSum(fValue);

                if (valueToFind == sfValue) {
                    lowestSolution = value;
                    fForSolution = fValue;

                    break;
                }
            }

            if (lowestSolution != Long.MAX_VALUE) {
                break;
            }
        }

        Pair<Long, Long> solution = new Pair<>(lowestSolution, fForSolution);
        gValueCache.put(valueToFind, solution);

        if (lowestSolution == Long.MAX_VALUE) {
            throw new RuntimeException("Could not compute GFunction n = " + valueToFind);
        }

        return solution;
    }

    public static long digitSum(long in) {
        long number = in;

        long sum = 0;

        while (number > 0) {
            long digit = number % 10;

            sum += digit;

            number = number / 10;
        }

        return sum;
    }

    public static void main(String[] args) {
        Scanner scannerIn = new Scanner(System.in);
        int q = Integer.valueOf(scannerIn.nextLine());

        long startNano = System.nanoTime();

        for (int lineCounter = 1; lineCounter <= q; ++lineCounter) {
            String nmLine = scannerIn.nextLine();

            String[] parts = nmLine.split("\\s+");

            long n = Long.valueOf(parts[0]);
            int m = Integer.valueOf(parts[1]);

            long totalSum = 0;

            for (long counter = 1; counter <= n; ++counter) {
                Pair<Long, Long> gf = gFunction2(counter);

                totalSum += digitSum(gf.tThis) % m;
                totalSum = totalSum % m;
            }

//            System.out.println(totalSum);

//            System.out.println("n = " + n + " , m = " + m + " value = " + totalSum);
//            System.out.println("n = " + n + " , g = " + gFunction2(n).tThis);
//            System.out.println("n = " + n + " , g = " + gFunction2(n).tThis + " , sg = " + digitSum(gFunction2(n).tThis));
            System.out.println(gFunction2(n).tThis);
//            System.out.println(digitSum(gFunction2(n).tThis));

//            long sumSGValue = sumSG(n, m);
//            long modSumSGValue = sumSGValue % m;

//            System.out.println(modSumSGValue);
        }

        // long endNano = System.nanoTime();
        // long millisDuration = (endNano - startNano) / (1000 * 1000);
        // System.out.println("Millis duration = " + millisDuration);
    }
}

