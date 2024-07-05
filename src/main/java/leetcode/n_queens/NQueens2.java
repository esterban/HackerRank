package leetcode.n_queens;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NQueens2 {
    private int _n;


    public List<List<String>> solveNQueens(int n) {
        List<List<String>> solution = new ArrayList<>();

        this._n = n;

        LinkedHashSet valuesLeftSet = IntStream.range(0, n).boxed().collect(Collectors.toCollection(LinkedHashSet::new));

        solution = recurse(valuesLeftSet);

        return solution;
    }

    private List<List<String>> recurse(LinkedHashSet<Integer> valuesLeft) {
        List<List<String>> solution = new ArrayList<>();

        if (valuesLeft.isEmpty()) {
            solution.add(new ArrayList<>());
//            throw new RuntimeException("Should not be reached");

            return solution;
        }

        for (Integer currentValue : valuesLeft) {
            LinkedHashSet<Integer> nextValuesList = new LinkedHashSet<>();
            nextValuesList.addAll(valuesLeft);
            nextValuesList.remove(currentValue);

            String currentRow = toRowString(currentValue, _n);

            List<List<String>> nextSolution = recurse(nextValuesList);

            for (List<String> nextSolutionList : nextSolution) {
                nextSolutionList.add(currentRow);

                if (!checkDiagonals(nextSolutionList)) {
                    continue;
                }

                solution.add(nextSolutionList);
            }
        }

        return solution;
    }

    private static String toRowString(int queenColumnIndex, int rowLength) {
        int beforeCount = Math.max(0, queenColumnIndex);
        int afterCount = Math.max(0, rowLength - queenColumnIndex - 1);

        return ".".repeat(beforeCount) + "Q" + ".".repeat(afterCount);
    }

    private static boolean checkDiagonals(List<String> solution) {
        for (int rowA = 0; rowA < solution.size(); ++rowA) {
            String rowAString = solution.get(rowA);
            int rowAQueenPosition = queenPosition(rowAString);

            for (int rowB = rowA + 1; rowB < solution.size(); ++rowB) {
                String rowBString = solution.get(rowB);
                int rowBQueenPosition = queenPosition(rowBString);

                int rowDelta = Math.abs(rowA - rowB);
                int columnDelta = Math.abs(rowAQueenPosition - rowBQueenPosition);

                if (rowDelta == columnDelta) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int queenPosition(String rowString) {
        return rowString.lastIndexOf("Q");
    }
}
