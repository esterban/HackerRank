package n254;

public class SolutionUtil {

    public static Byte[] createStartingSolution(final long value, final int numberOfDigits) {
        Byte[] solution = new Byte[numberOfDigits];

        if (!calcIsSolutionPossible(value, numberOfDigits).tThis) {
            return null;
        }

        int numberOfNinesNeeded = calcNumberOfNinesNeeded(value, numberOfDigits);

        int remainderAfterNines = calcRemainderAfterNines(value, numberOfNinesNeeded);

        int digitsLeft = numberOfDigits - numberOfNinesNeeded;

        for (int index = numberOfDigits - 1; index >= digitsLeft; --index) {
            solution[index] = 9;
        }

        int valueLeft = remainderAfterNines;

        for (int index = 0; index < digitsLeft - 1; ++index) {
            solution[index] = 1;
            --valueLeft;
        }

        if (valueLeft > 0) {
            solution[digitsLeft - 1] = (byte) valueLeft;
        }

        return solution;
    }

    public static int calcNumberOfNinesNeeded(final long value, final int numberOfDigits) {
        int ninesNeeded = (int) ((value) / 9);
        final int digitsLeft = numberOfDigits - ninesNeeded;
        final int remainder = calcRemainderAfterNines(value, ninesNeeded);

        int adjustedDigitsLeft = digitsLeft;

        if (remainder == 0 && digitsLeft == 1) {
            --ninesNeeded;
            ++adjustedDigitsLeft;
        }

        if (digitsLeft > 1) {
            final int ninesNotNeeded = digitsLeft / 9 + 1;

            ninesNeeded = Math.max(0, ninesNeeded - ninesNotNeeded);
            adjustedDigitsLeft += ninesNotNeeded;
        }


        if (adjustedDigitsLeft < 0) {
            throw new RuntimeException("adjustedDigitsLeft < 0 , this should not happen, internal error");
        }

        if ((ninesNeeded > numberOfDigits) || (adjustedDigitsLeft < 1 && remainder > 0)) {
            throw new RuntimeException("Not enough digits available, target value = " + value
                    + " , number of digits input = " + numberOfDigits
                    + " , ninesNeeded = " + ninesNeeded
                    + " , adjustedDigitsLeft = " + adjustedDigitsLeft
                    + " , remainder = " + remainder);
        }

        return ninesNeeded;
    }

    public static int calcRemainderAfterNines(final long value, final int numberOfNinesNeeded) {
        int remainderAfterNines = (int) (value - (numberOfNinesNeeded * 9));

        if (remainderAfterNines < 0) {
            throw new RuntimeException("remainderAfterNines < 0 , calculated value = " + remainderAfterNines);
        }

        return remainderAfterNines;
    }

    public static Solution.Pair<Boolean, String> calcIsSolutionPossible(final long value, final int numberOfDigits) {
        try {
            calcNumberOfNinesNeeded(value, numberOfDigits);
        } catch (RuntimeException e) {
            return new Solution.Pair<>(false, e.getMessage());
        }

        return new Solution.Pair<>(true, null);
    }

//    public static List<Byte[]> createValidRepresentations(final int numberOfDigits) {
//        return recurseRepresentation(1, numberOfDigits, numberOfDigits);
//    }
//
//    private static List<Byte[]> recurseRepresentation(long valueLeft, int digitsLeft, int representationSize) {
//        if (digitsLeft == 1) {
//            List<Byte[]> representationList = new ArrayList<>();
//
//            for (byte counter = (byte) valueLeft; counter <= 9; ++counter) {
//                Byte[] representation = new Byte[representationSize];
//                representation[representationSize - 1] = counter;
//                representationList.add(representation);
//            }
//
//            return representationList;
//        } else {
//            List<Byte[]> representationList = new ArrayList<>();
//
//            for (byte counter = (byte) valueLeft; counter <= 9; ++counter) {
//                byte finalCounter = counter;
//
//                List<Byte[]> representationSubList = recurseRepresentation(counter, digitsLeft - 1, representationSize);
//                representationSubList.forEach(e -> e[representationSize - digitsLeft] = finalCounter);
//
//                representationList.addAll(representationSubList);
//            }
//
//            return representationList;
//        }
//    }
}
