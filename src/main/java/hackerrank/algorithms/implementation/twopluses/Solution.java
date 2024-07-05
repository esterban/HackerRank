package hackerrank.algorithms.implementation.twopluses;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'twoPluses' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING_ARRAY grid as parameter.
     */

    static class Cell {
        public final int row;
        public final int column;
        public final boolean isGood;

        public Cell(int row, int column, boolean isGood) {
            this.row = row;
            this.column = column;
            this.isGood = isGood;
        }
    }

    public static int twoPluses(List<String> gridStrings) {
        // Write your code here
        int result = 1;

        List<List<Boolean>> grid = createGrid(gridStrings);

        int shortestAxisLength = Math.min(Integer.MAX_VALUE, grid.size());
        shortestAxisLength = Math.min(shortestAxisLength, grid.get(0).size());

        int maximumArmLength = shortestAxisLength / 2;
        maximumArmLength += shortestAxisLength % 2 == 1 ? 0 : -1;

        int rowCount = grid.size();
        int columnCount = grid.get(0).size();

        for (int armLengthA = 0; armLengthA <= maximumArmLength; ++armLengthA) {
            for (int rowA = armLengthA ; rowA < rowCount - armLengthA; ++rowA) {
                for (int columnA = armLengthA; columnA < columnCount - armLengthA; ++columnA) {

                    if (!isValidCross(rowA, columnA, armLengthA, grid)) {
                        continue;
                    }

                    for (int armLengthB = 0; armLengthB <= maximumArmLength; ++armLengthB) {
                        for (int rowB = armLengthB; rowB < rowCount - armLengthB; ++rowB) {
                            for (int columnB = armLengthB; columnB < columnCount - armLengthB; ++columnB) {

                                if (rowA == rowB && columnA == columnB) {
                                    continue;
                                }

                                if (!areTwoValidCrosses(rowA, columnA, armLengthA, rowB, columnB, armLengthB, grid)) {
                                    continue;
                                }

                                if (isValidCross(rowB, columnB, armLengthB, grid)) {
                                    int areaA = 1 + armLengthA * 4;
                                    int areaB = 1 + armLengthB * 4;

                                    int product = areaA * areaB;

                                    if (result < product) {
                                        result = product;
                                    }
                                }

                            }
                        }
                    }

                }
            }

        }

        return result;
    }

    public static List<List<Boolean>> createGrid(List<String> gridStrings) {
        List<List<Boolean>> grid = new ArrayList<>();

        for (String rowString : gridStrings) {
            List<Boolean> rowBooleans = Arrays.stream(rowString.split("")).map(e -> e.equals("G")).collect(toList());
            grid.add(rowBooleans);
        }

        return grid;
    }

    public static boolean isValidCross(int centreRow, int centreColumn, int armLength, List<List<Boolean>> grid) {
        // Check row
        for (int column = centreColumn - armLength; column <= centreColumn + armLength; ++column) {
            if (!grid.get(centreRow).get(column)) {
                return false;
            }
        }

        // Check column
        for (int row = centreRow - armLength; row <= centreRow + armLength; ++row) {
            if (!grid.get(row).get(centreColumn)) {
                return false;
            }
        }

        return true;
    }

    public static boolean areTwoValidCrosses(int centreRowA, int centreColumnA, int armLengthA, int centreRowB, int centreColumnB, int armLengthB, List<List<Boolean>> grid) {
        // A first
        if (centreRowB == centreRowA) {
            int distance = Math.abs(centreColumnA - centreColumnB);

            return distance > (armLengthA + armLengthB);
        } else if (centreColumnA == centreColumnB) {
            int distance = Math.abs(centreRowA - centreRowB);

            return distance > (armLengthA + armLengthB);
        }

        int columnDistance = Math.abs(centreColumnA - centreColumnB);
        int rowDistance = Math.abs(centreRowA - centreRowB);

        if (columnDistance > Math.max(armLengthA, armLengthB)) {
            return true;
        }

        if (rowDistance > Math.max(armLengthA, armLengthB)) {
            return true;
        }

        int minimumArmLength = Math.min(armLengthA, armLengthB);

        return !(columnDistance <= minimumArmLength || rowDistance <= minimumArmLength);
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<String> grid = IntStream.range(0, n).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        int result = Result.twoPluses(grid);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

