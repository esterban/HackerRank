package hackerrank.algorithms.implementation.bomberman;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'bomberMan' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. STRING_ARRAY grid
     */

    public static Comparator<int[][]> intArrayComparator = new Comparator<int[][]>() {
        @Override
        public int compare(int[][] o1, int[][] o2) {
            for (int rowIndex = 0; rowIndex < o1.length; ++rowIndex) {
                int rowComparison = Arrays.compare(o1[rowIndex], o2[rowIndex]);

                if (rowComparison != 0) {
                    return rowComparison;
                }
            }

            return 0;
        }
    };

    public static List<String> bomberManPrimitive(int n, List<String> grid) {
        // Write your code here
        int[][] bombsArrayA = createBombsArrayPrimitive(grid);
        int[][] bombsArrayB = createBombsArrayPrimitive(grid);

//        Arrays.copyOf()

        int secondsPassed = 1;
        decrementCellsPrimitive(bombsArrayA);



//        Set<int[][]> historyOfBombArraysSet = new HashSet<>();
        Set<int[][]> historyOfBombArraysSet = new TreeSet<>(intArrayComparator);
        List<int[][]> historyOfBombArrays = new ArrayList<>();

        int loopingSeconds = -1;

        while (secondsPassed < n) {
            if (loopingSeconds > -1) {
//                int historyIndex = (n - loopingSeconds + 2) % historyOfBombArraysSet.size();
                int historyIndex = (n - loopingSeconds) % (historyOfBombArraysSet.size() - 1);
//                ++historyIndex;

                int[][] bombsArrayResult = historyOfBombArrays.get(historyIndex);
                List<String> result = convertArrayToStringsListPrimitive(bombsArrayResult);
                return result;
            }

            int previousHistorySize = historyOfBombArrays.size();

            if (secondsPassed % 2 == 1) {
                bombsArrayA = Arrays.stream(bombsArrayA).map(int[]::clone).toArray(int[][]::new);

                decrementCellsPrimitive(bombsArrayA);

                fillEmptyCellsPrimitive(bombsArrayA);
            } else if (secondsPassed % 2 == 0) {
                bombsArrayB = Arrays.stream(bombsArrayA).map(int[]::clone).toArray(int[][]::new);

                explodeBombsPrimitive(bombsArrayA, bombsArrayB);

                bombsArrayA = bombsArrayB;

                decrementCellsPrimitive(bombsArrayA);
            }

            historyOfBombArraysSet.add(bombsArrayA);

            if (historyOfBombArraysSet.size() == previousHistorySize) {
                loopingSeconds = secondsPassed;
            } else {
                historyOfBombArrays.add(bombsArrayA);
            }

            ++secondsPassed;
        }

        List<String> rowStrings = convertArrayToStringsListPrimitive(bombsArrayA);

        return rowStrings;
    }


    public static List<String> bomberMan(int n, List<String> grid) {
        // Write your code here
        List<List<Integer>> bombsArrayA = createBombsArray(grid);
        List<List<Integer>> bombsArrayB = createBombsArray(grid);

        int secondsPassed = 1;
        decrementCells(bombsArrayA);

        while (secondsPassed < n) {
            if (secondsPassed % 2 == 1) {
                decrementCells(bombsArrayA);

                fillEmptyCells(bombsArrayA);
            } else if (secondsPassed % 2 == 0) {
                copyBombsArray(bombsArrayA, bombsArrayB);

                explodeBombs(bombsArrayA, bombsArrayB);

                List<List<Integer>> bombsArraySwap = bombsArrayA;
                bombsArrayA = bombsArrayB;
                bombsArrayB = bombsArraySwap;

                decrementCells(bombsArrayA);
            }

            ++secondsPassed;
        }

        List<String> rowStrings = convertArrayToStringsList(bombsArrayA);

        return rowStrings;
    }

    public static void fillEmptyCells(List<List<Integer>> bombsArray) {
        for (List<Integer> bombsRow : bombsArray) {
            for (int columnIndex = 0; columnIndex < bombsRow.size(); ++columnIndex) {
                Integer cellValue = bombsRow.get(columnIndex);
                cellValue = cellValue == 0 ? 3 : cellValue;

                bombsRow.set(columnIndex, cellValue);
            }
        }
    }

    public static void fillEmptyCellsPrimitive(int[][] bombsArray) {
        for (int[] bombsRow : bombsArray) {
            for (int columnIndex = 0; columnIndex < bombsRow.length; ++columnIndex) {
                Integer cellValue = bombsRow[columnIndex];
                cellValue = cellValue == 0 ? 3 : cellValue;

                bombsRow[columnIndex] = cellValue;
            }
        }
    }


    public static void explodeBombs(List<List<Integer>> bombsArrayIn, List<List<Integer>> bombsArrayOut) {
        for (int rowIndex = 0; rowIndex < bombsArrayIn.size(); ++rowIndex) {
            for (int columnIndex = 0; columnIndex < bombsArrayIn.get(0).size(); ++columnIndex) {
                Integer cellValue = bombsArrayIn.get(rowIndex).get(columnIndex);

                if (cellValue == 1) {
                    explodeBomb(bombsArrayOut, rowIndex, columnIndex);
                }
            }
        }
    }


    public static void explodeBombsPrimitive(int[][] bombsArrayIn, int[][] bombsArrayOut) {
        for (int rowIndex = 0; rowIndex < bombsArrayIn.length; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < bombsArrayIn[0].length; ++columnIndex) {
                Integer cellValue = bombsArrayIn[rowIndex][columnIndex];

                if (cellValue == 1) {
                    explodeBombPrimitive(bombsArrayOut, rowIndex, columnIndex);
                }
            }
        }
    }

    public static void copyBombsArray(List<List<Integer>> bombsArrayIn, List<List<Integer>> bombsArrayOut) {
        for (int rowIndex = 0; rowIndex < bombsArrayIn.size(); ++rowIndex) {
            for (int columnIndex = 0; columnIndex < bombsArrayIn.get(0).size(); ++columnIndex) {
                Integer cellValue = bombsArrayIn.get(rowIndex).get(columnIndex);
                bombsArrayOut.get(rowIndex).set(columnIndex, cellValue);
            }
        }
    }

    public static void copyBombsArrayPrimitive(int[][] bombsArrayIn, int[][] bombsArrayOut) {
        for (int rowIndex = 0; rowIndex < bombsArrayIn.length; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < bombsArrayIn[0].length; ++columnIndex) {
                Integer cellValue = bombsArrayIn[rowIndex][columnIndex];
                bombsArrayOut[rowIndex][columnIndex] = cellValue;
            }
        }
    }

    public static void explodeBomb(List<List<Integer>> bombsArray, int bombRow, int bombColumn) {
        bombsArray.get(bombRow).set(bombColumn, 0);

        if (bombRow > 0) {
            bombsArray.get(bombRow - 1).set(bombColumn, 0);
        }

        if (bombRow < bombsArray.size() - 1) {
            bombsArray.get(bombRow + 1).set(bombColumn, 0);
        }

        if (bombColumn > 0) {
            bombsArray.get(bombRow).set(bombColumn - 1, 0);
        }

        if (bombColumn < bombsArray.get(0).size() - 1) {
            bombsArray.get(bombRow).set(bombColumn + 1, 0);
        }
    }

    public static void explodeBombPrimitive(int[][] bombsArray, int bombRow, int bombColumn) {
        bombsArray[bombRow][bombColumn] = 0;

        if (bombRow > 0) {
            bombsArray[bombRow - 1][bombColumn] = 0;
        }

        if (bombRow < bombsArray.length - 1) {
            bombsArray[bombRow + 1][bombColumn] = 0;
        }

        if (bombColumn > 0) {
            bombsArray[bombRow][bombColumn - 1] = 0;
        }

        if (bombColumn < bombsArray[0].length - 1) {
            bombsArray[bombRow][bombColumn + 1] = 0;
        }
    }


    public static void decrementCells(List<List<Integer>> bombsArray) {
        for (List<Integer> bombsRow : bombsArray) {
            for (int columnIndex = 0; columnIndex < bombsRow.size(); ++columnIndex) {
                Integer cellValue = bombsRow.get(columnIndex);
                cellValue = Math.max(cellValue - 1, 0);

                bombsRow.set(columnIndex, cellValue);
            }
        }
    }

    public static void decrementCellsPrimitive(int[][] bombsArray) {
        for (int[] bombsRow : bombsArray) {
            for (int columnIndex = 0; columnIndex < bombsRow.length; ++columnIndex) {
                Integer cellValue = bombsRow[columnIndex];
                cellValue = Math.max(cellValue - 1, 0);

                bombsRow[columnIndex] = cellValue;
            }
        }
    }


    public static List<List<Integer>> createBombsArray(List<String> grid) {
        List<List<Integer>> bombs = new ArrayList<>();

        for (String rowString : grid) {
            List<Integer> bombsRow = convertStringToRow(rowString);
            bombs.add(bombsRow);
        }

        return bombs;
    }

    public static int[][] createBombsArrayPrimitive(List<String> grid) {
        int[][] bombs = new int[grid.size()][grid.get(0).length()];

        int rowIndex = 0;

        for (String rowString : grid) {
            int[] bombsRow = convertStringToRowPrimitive(rowString);
            bombs[rowIndex] = bombsRow;
            ++rowIndex;
        }

        return bombs;
    }


    public static List<Integer> convertStringToRow(String rowString) {
        List<Integer> bombsRow = rowString.chars().boxed().map(e -> e == 'O' ? 3 : 0).collect(Collectors.toList());

        return bombsRow;
    }

    public static int[] convertStringToRowPrimitive(String rowString) {
        int[] bombRowArray = new int[rowString.length()];

        for (int charIndex = 0; charIndex < rowString.length(); ++charIndex) {
            char charValue = rowString.charAt(charIndex);

            if (charValue == 'O') {
                bombRowArray[charIndex] = 3;
            } else {
                bombRowArray[charIndex] = 0;
            }
        }

        return bombRowArray;
    }


    public static List<String> convertArrayToStringsList(List<List<Integer>> rowsList) {
        List<String> rowStrings = new ArrayList<>();

        for (List<Integer> rowBombs : rowsList) {
            String rowString = "";

            for (Integer bomb : rowBombs) {
                rowString += bomb > 0 ? 'O' : '.';
            }

            rowStrings.add(rowString);
        }

        return rowStrings;
    }

    public static List<String> convertArrayToStringsListPrimitive(int[][] rowsList) {
        List<String> rowStrings = new ArrayList<>();

        for (int[] rowBombs : rowsList) {
            String rowString = "";

            for (int bomb : rowBombs) {
                rowString += bomb > 0 ? 'O' : '.';
            }

            rowStrings.add(rowString);
        }

        return rowStrings;
    }


}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r = Integer.parseInt(firstMultipleInput[0]);

        int c = Integer.parseInt(firstMultipleInput[1]);

        int n = Integer.parseInt(firstMultipleInput[2]);

        List<String> grid = IntStream.range(0, r).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

//        List<String> result = Result.bomberMan(n, grid);
        List<String> result = Result.bomberManPrimitive(n, grid);

        bufferedWriter.write(
                result.stream()
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
