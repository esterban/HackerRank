package hackerrank.algorithms.implementation.matrixrotationalgo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    private static List<Integer> primeNumbers = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47);

    /*
     * Complete the 'matrixRotation' function below.
     *
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY matrix
     *  2. INTEGER r
     */

    public static void matrixRotation(List<List<Integer>> matrix, int r) {
        // Write your code here
        int columnSize = matrix.get(0).size();
        int rowSize = matrix.size();

        int rowCount = matrix.size() / 2;
        int columnCount = matrix.get(0).size() / 2;

        int ringCount = Math.min(rowCount, columnCount);

        long nullRotationCount = calculateNullRotationLength(matrix.get(0).size(), matrix.size());

        long realRotationCount = r % nullRotationCount;

        if (nullRotationCount > r) {
            realRotationCount = r;
        }

//        for (int rotationNumber = 1; rotationNumber <= r; ++rotationNumber) {
        for (int ringIndex = 0; ringIndex < ringCount; ++ringIndex) {
            int ringColumnLength = columnSize - 2 * ringIndex;
            int ringRowLength = rowSize - 2 * ringIndex;
            long ringLength = calculateRingLength(ringColumnLength, ringRowLength);

            long loopCount = realRotationCount % ringLength;

            for (int rotationNumber = 1; rotationNumber <= loopCount; ++rotationNumber) {
                rotateMatrixRing(ringIndex, matrix);
//                break;
            }
        }

        printMatrix(matrix);
    }

    public static boolean rotateMatrixRing(int ringIndex, List<List<Integer>> matrix) {
        int rowCount = matrix.size() - 2 * ringIndex;
        int columnCount = matrix.get(0).size() - 2 * ringIndex;

        if (rowCount < 2 || columnCount < 2) {
            return false;
        }

        Integer columnOverFlowA = rotateColumn(ringIndex, ringIndex, rowCount, true, matrix, null);
//        System.out.println("columnOverFlowA = " + columnOverFlowA);

        Integer rowOverFlowA = rotateRow(ringIndex, ringIndex + rowCount - 1, columnCount, true, matrix, columnOverFlowA);
//        System.out.println("rowOverFlowA = " + rowOverFlowA);

        Integer columnOverFlowB = rotateColumn(ringIndex + columnCount - 1, ringIndex, rowCount, false, matrix, rowOverFlowA);
//        System.out.println("columnOverFlowB = " + columnOverFlowB);

        Integer rowOverFlowB = rotateRow(ringIndex, ringIndex, columnCount, false, matrix, columnOverFlowB);
//        System.out.println("rowOverFlowB = " + rowOverFlowB);

        matrix.get(ringIndex + 1).set(ringIndex, rowOverFlowB);


        return true;
    }

    private static long calculateNullRotationLength(int columnSize, int rowSize) {
        int columnCount = rowSize / 2;
        int rowCount = columnSize / 2;

        int ringCount = Math.min(rowCount, columnCount);

//        long lengthProduct = 1;

        SortedMap<Integer, Integer> completeFactorisation = new TreeMap<>();

        for (int ringIndex = 0; ringIndex < ringCount; ++ringIndex) {
            int ringColumnLength = columnSize - 2 * ringIndex;
            int ringRowLength = rowSize - 2 * ringIndex;

            if (ringColumnLength <= 0 || ringRowLength <= 0) {
                continue;
            }

//            System.out.println("ringColumnLength = " + ringColumnLength + " , ringRowLength = " + ringRowLength);

            long ringLength = calculateRingLength(ringColumnLength, ringRowLength);

            SortedMap<Integer, Integer> primeFactorisation = factoriseNumber(ringLength);
            mergeFactorisations(completeFactorisation, primeFactorisation);

//            System.out.println("ringLength = " + ringLength);

//            lengthProduct *= ringLength;

//            System.out.println("lengthProduct = " + lengthProduct);
        }

        long lengthProduct = factorisationToLong(completeFactorisation);

        return lengthProduct;
    }

    public static long calculateRingLength(int columnLength, int rowLength) {
        return ((columnLength - 1) * 2 + (rowLength - 1) * 2);
    }

    public static void debugPrintMatrix(List<List<Integer>> matrix) {
        matrix.stream().map(e -> String.valueOf(e)).forEach(e -> System.out.println(String.join(" ", e)));
        System.out.println();
    }

    public static void printMatrix(List<List<Integer>> matrix) {
        matrix.stream().map(e -> e.stream()
                        .map(e1 -> String.valueOf(e1))
                        .collect(toList()))
                .forEach(e -> System.out.println(String.join(" ", e)));
    }

    public static Integer rotateColumn(int columnIndex,
                                       int rowIndex,
                                       int length,
                                       boolean downward,
                                       List<List<Integer>> matrix,
                                       Integer carryInteger) {
        Integer overflow;

        if (downward) {
            int lastRow = rowIndex + length - 1;

            overflow = matrix.get(lastRow).get(columnIndex);

            for (int row = lastRow; row > rowIndex + 1; --row) {
                Integer value = matrix.get(row - 1).get(columnIndex);
                matrix.get(row).set(columnIndex, value);
            }

            matrix.get(rowIndex + 1).set(columnIndex, carryInteger);
        } else {
            overflow = matrix.get(rowIndex).get(columnIndex);

            for (int row = rowIndex; row < rowIndex + length - 1; ++row) {
                Integer value = matrix.get(row + 1).get(columnIndex);
                matrix.get(row).set(columnIndex, value);
            }

            matrix.get(rowIndex + length - 2).set(columnIndex, carryInteger);
        }

        return overflow;
    }

    public static Integer rotateRow(int columnIndex,
                                    int rowIndex,
                                    int length,
                                    boolean forwards,
                                    List<List<Integer>> matrix,
                                    Integer carryInteger) {
        Integer overflow;


        if (forwards) {
            int lastColumn = columnIndex + length - 1;

            overflow = matrix.get(rowIndex).get(lastColumn);

            for (int column = lastColumn; column > columnIndex + 1; --column) {
                Integer value = matrix.get(rowIndex).get(column - 1);
                matrix.get(rowIndex).set(column, value);
            }

            matrix.get(rowIndex).set(columnIndex + 1, carryInteger);
        } else {
            overflow = matrix.get(rowIndex).get(columnIndex);

            for (int column = columnIndex; column < columnIndex + length - 1; ++column) {
                Integer value = matrix.get(rowIndex).get(column + 1);
                matrix.get(rowIndex).set(column, value);
            }

            matrix.get(rowIndex).set(columnIndex + length - 2, carryInteger);
        }

        return overflow;
    }

    public static SortedMap<Integer, Integer> factoriseNumber(long number) {
        SortedMap<Integer, Integer> primeFactorisation = new TreeMap<>();

        long remainder = number;

        for (Integer primeNumber : primeNumbers) {
            while (true) {
                if (remainder % primeNumber == 0) {
                    primeFactorisation.putIfAbsent(primeNumber, 0);
                    primeFactorisation.put(primeNumber, primeFactorisation.get(primeNumber) + 1);

                    remainder = remainder / primeNumber;
                } else {
                    break;
                }
            }
        }

        return primeFactorisation;
    }

    public static SortedMap<Integer, Integer> mergeFactorisations(SortedMap<Integer, Integer> factorisationA, SortedMap<Integer, Integer> factorisationB) {
        for (Map.Entry<Integer, Integer> factorisationPair : factorisationB.entrySet()) {
            factorisationA.compute(factorisationPair.getKey(), (k, v) -> v == null ? factorisationPair.getValue() : Math.max(v, factorisationPair.getValue()));
        }

        return factorisationA;
    }

    public static long factorisationToLong(SortedMap<Integer, Integer> factorisation) {
        long product = 1l;

        for (Map.Entry<Integer, Integer> entry : factorisation.entrySet()) {
            product *= Math.pow(entry.getKey(), entry.getValue());
        }

        return product;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(firstMultipleInput[0]);

        int n = Integer.parseInt(firstMultipleInput[1]);

        int r = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> matrix = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                matrix.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Result.matrixRotation(matrix, r);

        bufferedReader.close();
    }
}
