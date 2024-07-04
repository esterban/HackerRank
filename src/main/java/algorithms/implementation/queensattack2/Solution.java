//package algorithms.implementation.queensattack2;
//
//import org.apache.commons.lang3.tuple.ImmutablePair;
//import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.IntStream;
//import java.util.stream.Stream;
//
//import static java.util.stream.Collectors.toList;
//
//class Vector2DInt {
//    private final Vector2D m_vector2D;
//
//    public Vector2DInt(int x, int y) {
//        m_vector2D = new Vector2D(x, y);
//    }
//
//    public int getX() {
//        return (int) Math.round(m_vector2D.getX());
//    }
//
//    public int getY() {
//        return (int) Math.round(m_vector2D.getY());
//    }
//}
//
//class Result {
//    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
//        int queenRowIndex = r_q - 1;
//        int queenColIndex = c_q - 1;
//
//        Vector2DInt queenPosition = new Vector2DInt(queenRowIndex, queenColIndex);
//
//        ImmutablePair<Vector2DInt, Vector2DInt> horizontalVectors = createHorizontalVectors(queenPosition, n);
//        ImmutablePair<Vector2DInt, Vector2DInt> verticalVectors = createVerticalVectors(queenPosition, n);
//
//        return 0;
//    }
//
//    public static ImmutablePair<Vector2DInt, Vector2DInt> createHorizontalVectors(Vector2DInt queenPosition, int boardSize) {
//        int leftLength = 0;
//
//        if (queenPosition.getX() > 0) {
//            leftLength = queenPosition.getX() - 1;
//        }
//
//        int rightLength = 0;
//
//        if (queenPosition.getX() < boardSize - 1) {
//            rightLength = boardSize - queenPosition.getX() - 1;
//        }
//
//        Vector2DInt leftVector2DInt = new Vector2DInt(- leftLength, 0);
//        Vector2DInt rightVector2DInt = new Vector2DInt(rightLength, 0);
//
//        return new ImmutablePair<>(leftVector2DInt, rightVector2DInt);
//    }
//
//    public static ImmutablePair<Vector2DInt, Vector2DInt> createVerticalVectors(Vector2DInt queenPosition, int boardSize) {
//        int bottomLength = 0;
//
//        if (queenPosition.getY() > 0) {
//            bottomLength = queenPosition.getY();
//        }
//
//        int topLength = 0;
//
//        if (queenPosition.getY() < boardSize - 1) {
//            topLength = boardSize - queenPosition.getY() - 1;
//        }
//
//        Vector2DInt bottomVector2DInt = new Vector2DInt(0, - bottomLength);
//        Vector2DInt topVector2DInt = new Vector2DInt(0, topLength);
//
//        return new ImmutablePair<>(bottomVector2DInt, topVector2DInt);
//    }
//
//    public static ImmutablePair<ImmutablePair<Vector2DInt, Vector2DInt>, ImmutablePair<Vector2DInt, Vector2DInt>> createDiagonalVectors(Vector2DInt queenPosition, int boardSize) {
////        int leftDistance =
//
//        return null;
//    }
//}
//
//class Result2 {
//    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
//        // Write your code here
//        int rowQueenIndex = r_q - 1;
//        int colQueenIndex = c_q - 1;
//
//        List<List<Integer>> boardWithMoves = createQueenMoves(n, rowQueenIndex, colQueenIndex);
//
//        removeObstaclesSquares(n, rowQueenIndex, colQueenIndex, boardWithMoves, obstacles);
//
//        return countMoves(boardWithMoves);
//    }
//
//    private static List<List<Integer>> createQueenMoves(int n, int rowQueen, int colQueen) {
//        List<List<Integer>> board = createBoard(n);
//
//        List<List<Integer>> unitVectors = createMoveDirections();
//
//        for (List<Integer> moveVector : unitVectors) {
//            createQueenMove(n, rowQueen, colQueen, moveVector, board, 1, false);
//        }
//
//        return board;
//    }
//
//    private static void removeObstaclesSquares(int n, int rowQueen, int colQueen, List<List<Integer>> board, List<List<Integer>> obstacles) {
//        for (List<Integer> obstacle : obstacles) {
//            Integer obstacleRow = obstacle.get(0) - 1;
//            Integer obstacleCol = obstacle.get(1) - 1;
//
//            if (board.get(obstacleRow).get(obstacleCol) == 1) {
//                List<Integer> directionVector = createMoveVector(rowQueen, colQueen, obstacleRow, obstacleCol);
//
//                createQueenMove(n, obstacleRow, obstacleCol, directionVector, board, 0, true);
//            }
//        }
//    }
//
//    private static List<Integer> createMoveVector(int rowQueen, int colQueen, int rowOther, int colOther) {
//        int rowDelta = (rowOther - rowQueen);
//        int colDelta = (colOther - colQueen);
//
//        int scaledRowDelta = rowDelta >= 1 ? 1 : rowDelta == 0 ? 0 : -1;
//        int scaledColDelta = colDelta >= 1 ? 1 : colDelta == 0 ? 0 : -1;
//
//        List<Integer> result = new ArrayList<>();
//
//        result.add(scaledRowDelta);
//        result.add(scaledColDelta);
//
//        return result;
//    }
//
//    private static void createQueenMove(int n, int rowQueen, int colQueen, List<Integer> moveUnitVector, List<List<Integer>> board, Integer assignValue, boolean keepFirstPosition) {
//        int currentRow = rowQueen;
//        int currentCol = colQueen;
//
//        if (!keepFirstPosition) {
//            currentRow += moveUnitVector.get(0);
//            currentCol += moveUnitVector.get(1);
//        }
//
//        while (currentRow >= 0 && currentCol >= 0 && currentRow < n && currentCol < n) {
//            board.get(currentRow).set(currentCol, assignValue);
//
//            currentRow += moveUnitVector.get(0);
//            currentCol += moveUnitVector.get(1);
//        }
//    }
//
//    private static List<List<Integer>> createBoard(int n) {
//        List<List<Integer>> result = new ArrayList<>();
//
//        for (int index = 0; index < n; ++index) {
//            List<Integer> row = new ArrayList<>();
//            IntStream.range(0, n).forEach((k) -> row.add(0));
//
//            result.add(row);
//        }
//
//        return result;
//    }
//
//    private static List<List<Integer>> createMoveDirections() {
//        List<List<Integer>> result = new ArrayList<>();
//
//        for (int row = -1; row <= 1; ++row) {
//            for (int col = -1; col <= 1; ++col) {
//                if (row != 0 || col != 0) {
//                    List<Integer> vector = new ArrayList<>();
//                    vector.add(row);
//                    vector.add(col);
//
//                    result.add(vector);
//                }
//            }
//        }
//
//        return result;
//    }
//
//    private static int countMoves(List<List<Integer>> board) {
//        int count = 0;
//
//        for (List<Integer> row : board) {
//            int sum = row.stream().reduce(0, Integer::sum);
//            count += sum;
//        }
//
//        return count;
//    }
//}
//
//public class Solution {
//    public static void main(String[] args) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
////        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
//
//        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
//
//        int n = Integer.parseInt(firstMultipleInput[0]);
//
//        int k = Integer.parseInt(firstMultipleInput[1]);
//
//        String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
//
//        int r_q = Integer.parseInt(secondMultipleInput[0]);
//
//        int c_q = Integer.parseInt(secondMultipleInput[1]);
//
//        List<List<Integer>> obstacles = new ArrayList<>();
//
//        IntStream.range(0, k).forEach(i -> {
//            try {
//                obstacles.add(
//                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
//                                .map(Integer::parseInt)
//                                .collect(toList())
//                );
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//
//        long startNanos = System.nanoTime();
//        int result = Result.queensAttack(n, k, r_q, c_q, obstacles);
//        long endNanos = System.nanoTime();
//
//        double durationMillis = (endNanos - startNanos) / 1e6;
//
//        System.out.println("Duration ms = " + durationMillis);
//
//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();
//
//        bufferedReader.close();
//        bufferedWriter.close();
//    }
//}
