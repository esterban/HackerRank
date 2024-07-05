package hackerrank.algorithms.implementation.cavitymap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    public static List<String> cavityMap(List<String> grid) {
        List<List<Integer>> integerMap = convertToIntegerMap(grid);

        List<List<Integer>> integerMapOut = copyArray2D(integerMap);

        scanForCavities(integerMap, integerMapOut);

        List<String> gridResult = convertToGrid(integerMapOut);

        return gridResult;
    }

    public static void scanForCavities(List<List<Integer>> gridIn, List<List<Integer>> arrayOut) {

        for (int rowIndex = 1; rowIndex < gridIn.size() - 1; ++rowIndex) {
            for (int columnIndex = 1; columnIndex < gridIn.get(0).size() - 1; ++columnIndex) {
                if (isCavity(gridIn, rowIndex, columnIndex)) {
                    arrayOut.get(rowIndex).set(columnIndex, -1);
                }
            }
        }
    }

    public static boolean isCavity(List<List<Integer>> arrayIn, int row, int column) {
//        boolean result = false;

        Integer value = arrayIn.get(row).get(column);

        Integer leftValue = arrayIn.get(row).get(column - 1);
        Integer rightValue = arrayIn.get(row).get(column + 1);

        if (leftValue >= value || rightValue >= value) {
            return false;
        }

        Integer topValue = arrayIn.get(row - 1).get(column);
        Integer bottomValue = arrayIn.get(row + 1).get(column);

        if (topValue >= value || bottomValue >= value) {
            return false;
        }

//        Integer topLeftValue = arrayIn.get(row - 1).get(column - 1);
//        Integer topRightValue = arrayIn.get(row - 1).get(column + 1);
//
//        if (topLeftValue >= value || topRightValue >= value) {
//            return false;
//        }
//
//        Integer bottomLeftValue = arrayIn.get(row + 1).get(column - 1);
//        Integer bottomRightValue = arrayIn.get(row + 1).get(column + 1);
//
//        if (bottomLeftValue >= value || bottomRightValue >= value) {
//            return false;
//        }

        return true;
    }

    public static List<List<Integer>> copyArray2D(List<List<Integer>> arrayIn) {
        List<List<Integer>> copy = arrayIn.stream().map(e -> new ArrayList<Integer>(e)).collect(Collectors.toList());
        return copy;
    }

    public static List<List<Integer>> convertToIntegerMap(List<String> grid) {
        List<List<Integer>> integerMap = new ArrayList<>();

        for (String rowString : grid) {
            List<Integer> rowIntegers = new ArrayList<>();

            for (int columnIndex = 0; columnIndex < rowString.length(); ++columnIndex) {
                Integer cellValue = Integer.valueOf(String.valueOf(rowString.charAt(columnIndex)));
                rowIntegers.add(cellValue);
            }

            integerMap.add(rowIntegers);
        }

        return integerMap;
    }

    public static List<String> convertToGrid(List<List<Integer>> integerMap) {
        List<String> grid = new ArrayList<>();

        for (List<Integer> rowValues : integerMap) {
            String rowString = "";

            for (Integer rowValue : rowValues) {
                rowString += rowValue != -1 ? String.valueOf(rowValue) : "X";
            }

            grid.add(rowString);
        }

        return grid;
    }


}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> grid = IntStream.range(0, n).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        List<String> result = Result.cavityMap(grid);

        bufferedWriter.write(
                result.stream()
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
