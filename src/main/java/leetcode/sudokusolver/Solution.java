 package leetcode.sudokusolver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static class CellPossibleValues {
        public final LinkedHashSet<Character> possibleValues = new LinkedHashSet<>();
        public final int rowIndex;
        public final int colIndex;

        public CellPossibleValues(int rowIndex, int colIndex) {
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
        }

        @Override
        public String toString() {
            return "CellPossibleValues{" +
                    "(" + rowIndex +
                    ", " + colIndex +
                    ") , possibleValues=" + possibleValues +
                    '}';
        }
    }


    public char[][] boardGrid;
    public char[][] initialBoardGrid;
    public List<Set<Character>> rowMissingValuesList = new ArrayList<>();
    public List<Set<Character>> columnMissingValuesList = new ArrayList<>();
    public List<Set<Character>> squareMissingValuesList = new ArrayList<>();
//    private int loopCounter = 0;


    public Solution() {
    }

    //    @SuppressWarnings("CopyConstructorMissesField")
    public Solution(Solution solution) {
        this.boardGrid = copyBoard(solution.boardGrid);
        this.initialBoardGrid = copyBoard(solution.boardGrid);
        this.rowMissingValuesList = solution.rowMissingValuesList.stream().map(HashSet::new).collect(Collectors.toList());
        this.columnMissingValuesList = solution.columnMissingValuesList.stream().map(HashSet::new).collect(Collectors.toList());
        this.squareMissingValuesList = solution.squareMissingValuesList.stream().map(HashSet::new).collect(Collectors.toList());
//        this.loopCounter = solution.loopCounter;
    }


    public void solveSudoku(char[][] board) {
        char[][] originalBoard = board;
        this.initialBoardGrid = copyBoard(board);
        boardGrid = board;

        outputBoard();

        populateRowMissingValues();
        populateColumnMissingValues();
        populateSquareMissingValues();

        System.out.println("Initialised");

        solve();

        copyBoardInPlace(boardGrid, originalBoard);

        outputBoard();
    }

    public void solve() {
        solveRecurse(boardGrid, 0);
    }


    public void solveV2() {
        int squareRowIndex = 0;
        int squareColumnIndex = 0;

        List<char[][]> possibleSquareSolutions = generateSquarePossibleSolutions(squareRowIndex, squareColumnIndex);

        for (char[][] squareSolutionA : possibleSquareSolutions) {
            char[][] trialBoard = copyBoard(initialBoardGrid);

            copySquareSolutionToBoard(squareSolutionA, squareRowIndex, squareColumnIndex, trialBoard);
            clearAndPopulateInternalMissingState();

            List<char[][]> possibleSquareSolutionsB = generateSquarePossibleSolutions(0, 1);

            if (!possibleSquareSolutionsB.isEmpty()) {
                char[][] squareSolution = possibleSquareSolutionsB.get(0);

                for (int localRowIndex = 0; localRowIndex < 3; ++localRowIndex) {
                    for (int localColumnIndex = 0; localColumnIndex < 3; ++localColumnIndex) {
                        int absoluteRowIndex = localRowIndex;
                        int absoluteColumnIndex = localColumnIndex + 3;

                        if (trialBoard[absoluteRowIndex][absoluteColumnIndex] != '.' && trialBoard[absoluteRowIndex][absoluteColumnIndex] != squareSolution[localRowIndex][localColumnIndex]) {
                            throw new RuntimeException("Disparity between solution and initial board");
                        }

                        trialBoard[absoluteRowIndex][absoluteColumnIndex] = squareSolution[localRowIndex][localColumnIndex];
                    }
                }

                boardGrid = trialBoard;

                clearAndPopulateInternalMissingState();
                checkGridIntegrity();
                checkMissingSets();

                System.out.println("Square (" + squareRowIndex + " , " + squareColumnIndex + ") has solution count = " + possibleSquareSolutions.size());
            }
        }
    }

    public char[][] solveRecurse(char[][] previousBoard, int squareIndex) {
        Map.Entry<Integer, Integer> position = squareIndexToSquarePosition(squareIndex);
        int squareRowIndex = position.getKey();
        int squareColumnIndex = position.getValue();

        Comparator<char[][]> comparator = new Comparator<char[][]>() {
            @Override
            public int compare(char[][] o1, char[][] o2) {
                for (int rowIndex = 0; rowIndex < o1.length; ++rowIndex) {
                    for (int columnIndex = 0; columnIndex < o1.length; ++columnIndex) {
                        if (o1[rowIndex][columnIndex] < o2[rowIndex][columnIndex]) {
                            return -1;
                        } else if (o1[rowIndex][columnIndex] > o2[rowIndex][columnIndex]) {
                            return 1;
                        }
                    }
                }

                return 0;
            }
        };

//        List<char[][]> possibleSquareSolutions = generateSquarePossibleSolutions(squareRowIndex, squareColumnIndex);
        SortedSet<char[][]> possibleSquareSolutions = new TreeSet<>(comparator);
        possibleSquareSolutions.addAll(generateSquarePossibleSolutions(squareRowIndex, squareColumnIndex));

        if (squareIndex == 8) {
            if (possibleSquareSolutions.isEmpty()) {
                return null;
            } else {
                char[][] squareSolution = possibleSquareSolutions.iterator().next();

                char[][] nextBoard = copyBoard(previousBoard);

                copySquareSolutionToBoard(squareSolution, squareRowIndex, squareColumnIndex, nextBoard);
                clearAndPopulateInternalMissingState();

                return possibleSquareSolutions.iterator().next();
            }
        }

        int looperCounter = 0;

        for (char[][] squareSolution : possibleSquareSolutions) {
            char[][] nextBoard = copyBoard(previousBoard);
            boardGrid = nextBoard;

            copySquareSolutionToBoard(squareSolution, squareRowIndex, squareColumnIndex, nextBoard);
            clearAndPopulateInternalMissingState();

            checkMissingSets();
            checkGridIntegrity();

            char[][] nextSolution = solveRecurse(nextBoard, squareIndex + 1);

            if (nextSolution != null) {
                return nextSolution;
            }

            ++looperCounter;
        }

        return null;
    }


    public List<char[][]> generateSquarePossibleSolutions(int squareRowIndex, int squareColumnIndex) {
        List<char[][]> possibleSquareSolutions = new ArrayList<>();

        List<CellPossibleValues> cellPossibleValuesList = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < 3; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < 3; ++columnIndex) {
                int translatedRowIndex = squareRowIndex * 3 + rowIndex;
                int translatedColIndex = squareColumnIndex * 3 + columnIndex;

                if (boardGrid[translatedRowIndex][translatedColIndex] == '.') {
                    Set<Character> cellPossibleValueChars = possibleValuesForCell(translatedRowIndex, translatedColIndex);

                    Solution.CellPossibleValues cellPossibleValues = new Solution.CellPossibleValues(translatedRowIndex, translatedColIndex);
                    cellPossibleValues.possibleValues.addAll(cellPossibleValueChars);

                    cellPossibleValuesList.add(cellPossibleValues);
                }
            }
        }

        int neededCount = cellPossibleValuesList.stream().map(e -> e.possibleValues.size()).reduce(1, (e, a) -> a * e);

        LinkedHashSet<Character> trialSquareSolution = new LinkedHashSet<>();

        int squareIndex = squarePositionToSquareIndex(squareRowIndex, squareColumnIndex);

        for (int globalIndex = 0; globalIndex < neededCount; ++globalIndex) {
            List<Integer> indexList = generateIndexList(cellPossibleValuesList, globalIndex);

            Set<Character> squareMissingValueCopy = new HashSet<>(this.squareMissingValuesList.get(squareIndex));

            for (int index = 0; index < indexList.size(); ++index) {
                int thisIndex = indexList.get(index);

                Character cellValue = getByIteration(cellPossibleValuesList.get(index).possibleValues, thisIndex);

                if (!squareMissingValueCopy.contains(cellValue)) {
                    trialSquareSolution.clear();
                    break;
                }

                trialSquareSolution.add(cellValue);
                squareMissingValueCopy.remove(cellValue);
            }

            if (!trialSquareSolution.isEmpty()) {
                char[][] squareSolution = new char[3][3];

                int newSolutionIndex = 0;

                for (int localRowIndex = 0; localRowIndex < 3; ++localRowIndex) {
                    for (int localColumnIndex = 0; localColumnIndex < 3; ++localColumnIndex) {
                        int absoluteRowIndex = localRowIndex + squareRowIndex * 3;
                        int absoluteColumnIndex = localColumnIndex + squareColumnIndex * 3;

                        if (initialBoardGrid[absoluteRowIndex][absoluteColumnIndex] != '.') {
                            squareSolution[localRowIndex][localColumnIndex] = initialBoardGrid[absoluteRowIndex][absoluteColumnIndex];
                        } else {
                            squareSolution[localRowIndex][localColumnIndex] = getByIteration(trialSquareSolution, newSolutionIndex);
                            ++newSolutionIndex;
                        }
                    }
                }

                possibleSquareSolutions.add(squareSolution);

//                System.out.println("Square solution = ");
//                outputBoardSquare(squareSolution);
            }

            trialSquareSolution.clear();
        }

        return possibleSquareSolutions;
    }

    public Set<Character> possibleValuesForCell(int rowIndex, int columnIndex) {
        int squareIndex = squareAbsolutePositionToSquareIndex(rowIndex, columnIndex);

        Set<Character> rowMissingSetCopy = new HashSet<>(rowMissingValuesList.get(rowIndex));
        Set<Character> columnMissingSet = columnMissingValuesList.get(columnIndex);
        Set<Character> squareMissingSet = squareMissingValuesList.get(squareIndex);

        rowMissingSetCopy.retainAll(columnMissingSet);
        rowMissingSetCopy.retainAll(squareMissingSet);

        return rowMissingSetCopy;
    }


    public List<Integer> generateIndexList(List<Solution.CellPossibleValues> cellPossibleValuesList, int index) {
        List<Integer> indexList = new ArrayList<>(Collections.nCopies(cellPossibleValuesList.size(), 0));

        int previousPow = 1;

        for (int indexIndex = cellPossibleValuesList.size() - 1; indexIndex >= 0; --indexIndex) {
            int indexSize = cellPossibleValuesList.get(indexIndex).possibleValues.size();

            int singleIndex = (index / previousPow) % cellPossibleValuesList.get(indexIndex).possibleValues.size();

            index -= singleIndex;

            previousPow *= indexSize;

            indexList.set(indexIndex, singleIndex);
        }

        return indexList;
    }


    public static Character getByIteration(Collection<Character> collection, int index) {

        Iterator<Character> iterator = collection.iterator();

        int i = index;
        while (iterator.hasNext()) {
            i--;
            if (i == -1) {
                return iterator.next();
            }
            iterator.next();
        }
        throw new IndexOutOfBoundsException("Entry does not exist: " + i);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialisation methods
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void clearAndPopulateInternalMissingState() {
        rowMissingValuesList.clear();
        columnMissingValuesList.clear();
        squareMissingValuesList.clear();

        populateRowMissingValues();
        populateColumnMissingValues();
        populateSquareMissingValues();
    }

    public void populateRowMissingValues() {
        for (int rowIndex = 0; rowIndex < 9; ++rowIndex) {
            rowMissingValuesList.add(rowMissingValues(rowIndex));
        }
    }

    public void populateColumnMissingValues() {
        for (int columnIndex = 0; columnIndex < 9; ++columnIndex) {
            columnMissingValuesList.add(columnMissingValues(columnIndex));
        }
    }

    public void populateSquareMissingValues() {
        for (int squareIndex = 0; squareIndex < 9; ++squareIndex) {
            Map.Entry<Integer, Integer> squarePosition = squareIndexToSquarePosition(squareIndex);

            int squareRowIndex = squarePosition.getKey();
            int squareColumnIndex = squarePosition.getValue();

            squareMissingValuesList.add(squareMissingValues(squareRowIndex, squareColumnIndex));
        }
    }

    public Set<Character> rowMissingValues(int rowIndex) {
        Set<Character> missingValues = generateMissingValueSet();

        for (int columnIndex = 0; columnIndex < 9; ++columnIndex) {
            Character value = boardGrid[rowIndex][columnIndex];
            missingValues.remove(value);
        }

        return missingValues;
    }

    public Set<Character> columnMissingValues(int columnIndex) {
        Set<Character> missingValues = generateMissingValueSet();

        for (int rowIndex = 0; rowIndex < 9; ++rowIndex) {
            Character value = boardGrid[rowIndex][columnIndex];
            missingValues.remove(value);
        }

        return missingValues;
    }

    public Set<Character> squareMissingValues(int squareRowIndex, int squareColumnIndex) {
        Set<Character> missingValues = generateMissingValueSet();

        for (int rowIndex = 0; rowIndex < 3; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < 3; ++columnIndex) {
                int absoluteRowIndex = squareRowIndex * 3 + rowIndex;
                int absoluteColumnIndex = squareColumnIndex * 3 + columnIndex;

                Character value = boardGrid[absoluteRowIndex][absoluteColumnIndex];
                missingValues.remove(value);
            }
        }

        return missingValues;
    }

    public Set<Character> generateMissingValueSet() {
        return IntStream.range(1, 10).boxed().map(e -> Character.forDigit(e, 10)).collect(Collectors.toSet());
    }

    private static char[][] copyBoard(char[][] other) {
        char[][] boardCopy = new char[9][9];

        for (int rowIndex = 0; rowIndex < 9; ++rowIndex) {
            boardCopy[rowIndex] = Arrays.copyOf(other[rowIndex], 9);
        }

        return boardCopy;
    }

    private static void copyBoardInPlace(char[][] other, char[][] target) {
        for (int rowIndex = 0; rowIndex < 9; ++rowIndex) {
            target[rowIndex] = Arrays.copyOf(other[rowIndex], 9);
        }
    }

    private void copySquareSolutionToBoard(char[][] squareSolution, int squareRowIndex, int squareColumnIndex, char[][] trialBoard) {
        for (int localRowIndex = 0; localRowIndex < 3; ++localRowIndex) {
            for (int localColumnIndex = 0; localColumnIndex < 3; ++localColumnIndex) {
                int absoluteRowIndex = localRowIndex + squareRowIndex * 3;
                int absoluteColumnIndex = localColumnIndex + squareColumnIndex * 3;

                if (trialBoard[absoluteRowIndex][absoluteColumnIndex] != '.' && trialBoard[absoluteRowIndex][absoluteColumnIndex] != squareSolution[localRowIndex][localColumnIndex]) {
                    throw new RuntimeException("Disparity between solution and initial board");
                }

                trialBoard[absoluteRowIndex][absoluteColumnIndex] = squareSolution[localRowIndex][localColumnIndex];
            }
        }

        boardGrid = trialBoard;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // Coordinate methods
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public Map.Entry<Integer, Integer> squareIndexToSquarePosition(int squareIndex) {
        int squareRowIndex = squareIndex / 3;
        int squareColumnRowIndex = squareIndex % 3;

        return new AbstractMap.SimpleEntry<>(squareRowIndex, squareColumnRowIndex);
    }

    public int squareAbsolutePositionToSquareIndex(int rowIndex, int columnIndex) {
        return (rowIndex / 3) * 3 + columnIndex / 3;
    }

    public int squarePositionToSquareIndex(int rowIndex, int columnIndex) {
        return rowIndex * 3 + columnIndex;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // Internal validity methods
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void checkMissingSets() {
        int rowsCount = 0;
        int columnsCount = 0;
        int squaresCount = 0;
        int gridCount = 0;

        for (Set<Character> rowSet : rowMissingValuesList) {
            rowsCount += rowSet.size();
        }

        for (Set<Character> columnSet : columnMissingValuesList) {
            columnsCount += columnSet.size();
        }

        for (Set<Character> squareSet : squareMissingValuesList) {
            squaresCount += squareSet.size();
        }

        for (int rowIndex = 0; rowIndex < 9; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < 9; ++columnIndex) {
                char value = boardGrid[rowIndex][columnIndex];

                gridCount += value == '.' ? 1 : 0;
            }
        }

        if (rowsCount != columnsCount || rowsCount != squaresCount || rowsCount != gridCount) {
            throw new RuntimeException("Counts do not match row count = " + rowsCount + " columns count = " + columnsCount + " squares Count = " + squaresCount + " grid count = " + gridCount);
        }
    }


    public void checkGridIntegrity() {
        for (int rowIndex = 0; rowIndex < 9; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < 9; ++columnIndex) {
                int squareIndex = squareAbsolutePositionToSquareIndex(rowIndex, columnIndex);

                char value = boardGrid[rowIndex][columnIndex];

                if (value != '.') {
                    Set<Character> rowMissingValues = rowMissingValuesList.get(rowIndex);
                    Set<Character> columnMissingValues = columnMissingValuesList.get(columnIndex);
                    Set<Character> squareMissingValues = squareMissingValuesList.get(squareIndex);

                    if (rowMissingValues.contains(value)) {
                        throw new RuntimeException("Duplicate row value");
                    }

                    if (columnMissingValues.contains(value)) {
                        throw new RuntimeException("Duplicate column value");
                    }

                    if (squareMissingValues.contains(value)) {
                        throw new RuntimeException("Duplicate square value");
                    }
                }

            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // Debugging
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void outputBoard() {
        for (char[] row : boardGrid) {
            List<Character> characterList = IntStream.range(0, row.length).mapToObj(i -> row[i]).collect(Collectors.toList());
            List<String> stringList = characterList.stream().map(String::valueOf).collect(Collectors.toList());

            String string = String.join(" ", stringList);
            System.out.println(string);
        }
    }

    public static void outputBoardSquare(char[][] square) {
        for (char[] row : square) {
            List<Character> characterList = IntStream.range(0, row.length).mapToObj(i -> row[i]).collect(Collectors.toList());
            List<String> stringList = characterList.stream().map(String::valueOf).collect(Collectors.toList());

            String string = String.join(" ", stringList);
            System.out.println(string);
        }
    }


}
