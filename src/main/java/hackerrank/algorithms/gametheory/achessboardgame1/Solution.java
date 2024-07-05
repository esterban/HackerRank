package hackerrank.algorithms.gametheory.achessboardgame1;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

class Result {
    /*
     * Complete the 'chessboardGame' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. INTEGER x
     *  2. INTEGER y
     */

    enum Move {
        downLeftLeft,
        upLeftLeft,
        upUpLeft,
        upUpRight
    }

    private static final List<List<Boolean>> positionMovesToWin = new ArrayList<>();

    public static String chessboardGame(int x, int y) {
//    public static String chessboardGame(int y, int x) {
        init();
        populateWinnerMap(15);
        populateWinnerMap(15);
        populateWinnerMap(15);

        return positionMovesToWin.get(x - 1).get(y - 1) ? "Second" : "First";
    }

    public static void populateWinnerMap(int n) {
        for (int row = 1; row <= n; ++row) {
            int rowIndex = row - 1;

            for (int column = 1; column <= n; ++column) {
                int columnIndex = column - 1;

                if (positionMovesToWin.get(rowIndex).get(columnIndex) == null) {
                    calculateCanFindLosingCell(row, column);
                }
            }
        }
    }

    public static boolean isInBounds(int row, int column) {
        if (column < 1 || column > 15) {
            return false;
        }

        if (row < 1 || row > 15) {
            return false;
        }

        return true;
    }

    public static void init() {
        for (int rowIndex = 0; rowIndex < 15; ++rowIndex) {
            positionMovesToWin.add(new ArrayList<>(Collections.nCopies(15, null)));
        }

        positionMovesToWin.get(0).set(0, true);
        positionMovesToWin.get(0).set(1, true);
        positionMovesToWin.get(0).set(2, false);

        positionMovesToWin.get(1).set(0, true);
        positionMovesToWin.get(1).set(1, true);
        positionMovesToWin.get(1).set(2, false);

        positionMovesToWin.get(2).set(0, false);
        positionMovesToWin.get(2).set(1, false);
        positionMovesToWin.get(2).set(2, false);
    }

    public static boolean calculateCanFindLosingCell(int row, int column) {
        int rowIndex = row - 1;
        int columnIndex = column - 1;

        // System.out.println("Adding row, column = " + row + " , " + column + " move = " + move);
        AbstractMap.SimpleEntry<Integer, Integer> position = new AbstractMap.SimpleEntry<>(row, column);

        if (positionMovesToWin.get(rowIndex).get(columnIndex) != null) {
            return true;
        }

        boolean invalidSolution = false;
        boolean solutionFound = false;

        int result = attemptWin(row, column, Move.downLeftLeft);
        invalidSolution = invalidSolution || result == 0;
        solutionFound = solutionFound || result == 2;

        result = attemptWin(row, column, Move.upLeftLeft);
        invalidSolution = invalidSolution || result == 0;
        solutionFound = solutionFound || result == 2;

        result = attemptWin(row, column, Move.upUpLeft);
        invalidSolution = invalidSolution || result == 0;
        solutionFound = solutionFound || result == 2;

        result = attemptWin(row, column, Move.upUpRight);
        invalidSolution = invalidSolution || result == 0;
        solutionFound = solutionFound || result == 2;

        if (!invalidSolution && !solutionFound) {
            positionMovesToWin.get(rowIndex).set(columnIndex, true);

//            System.out.println("Added true position (" + row + " , " + column + ")");
        }

        return true;
    }

    private static int attemptWin(int row, int column, Move move) {
        AbstractMap.SimpleEntry<Integer, Integer> newPosition = applyMove(row, column, move);

        if (!isInBounds(newPosition.getKey(), newPosition.getValue())) {
            return 1;
        }

        int rowIndex = row - 1;
        int columnIndex = column - 1;

        int newRowIndex = newPosition.getKey() - 1;
        int newColumnIndex = newPosition.getValue() - 1;

        if (newPosition != null && positionMovesToWin.get(newRowIndex).get(newColumnIndex) != null && positionMovesToWin.get(newRowIndex).get(newColumnIndex)) {
            positionMovesToWin.get(rowIndex).set(columnIndex, false);

//            System.out.println("Added false position (" + row + " , " + column + ")");
            return 2;
        }

//        return newPosition == null ? 0 : 1;
        return positionMovesToWin.get(newRowIndex).get(newColumnIndex) == null ? 0 : 1;
    }

    public static AbstractMap.SimpleEntry<Integer, Integer> applyMove(int row, int column, Move move) {
        int newRow = row;
        int newColumn = column;

        switch (move) {
            case downLeftLeft:
                newRow += 1;
                newColumn -= 2;
                break;

            case upLeftLeft:
                newRow -= 1;
                newColumn -= 2;
                break;

            case upUpLeft:
                newRow -= 2;
                newColumn -= 1;
                break;

            case upUpRight:
                newRow -= 2;
                newColumn += 1;
                break;
        }

        return new AbstractMap.SimpleEntry<>(newRow, newColumn);
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int x = Integer.parseInt(firstMultipleInput[0]);

                int y = Integer.parseInt(firstMultipleInput[1]);

                String result = Result.chessboardGame(x, y);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
