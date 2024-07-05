package hackerrank.algorithms.gametheory.gameofstones1;


import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

class Result {

    /*
     * Complete the 'gameOfStones' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts INTEGER n as parameter.
     */

    //    public static final Set<Integer> noPossibleMoves = new HashSet<Integer>(Arrays.asList(0, 1));
    public static final List<Integer> possibleMoves = Arrays.asList(2, 3, 5);
    public static final Map<Integer, Boolean> movesToIsFirstPlayer = new HashMap<>();

    static {
        movesToIsFirstPlayer.put(1, false);
    }

    public static String gameOfStones(int n) {
        // Write your code here
        Set<Boolean> winningPlayers = new HashSet<>();

        if (movesToIsFirstPlayer.containsKey(n)) {
            return movesToIsFirstPlayer.get(n) ? "First" : "Second";
        }

        calculateWiningPlayers(n);

        if (movesToIsFirstPlayer.containsKey(n)) {
            return movesToIsFirstPlayer.get(n) ? "First" : "Second";
        }

        throw new RuntimeException("No solution returned for " + n);
    }

    private static void calculateWiningPlayers(int stones) {
        if (movesToIsFirstPlayer.containsKey(stones)) {
            return;
        }

        for (int stoneCount = 2; stoneCount <= stones; ++stoneCount) {
            if (movesToIsFirstPlayer.containsKey(stoneCount)) {
                continue;
            }

            boolean winningMoveFound = false;

            for (Integer move : possibleMoves) {
                int stonesLeftAfterMove = stoneCount - move;

                if (stonesLeftAfterMove <= 1) {
                    movesToIsFirstPlayer.put(stoneCount, true);
                    winningMoveFound = true;
                    break;
                }
            }

            if (winningMoveFound) {
                continue;
            }

            int secondPlayerWins = 0;

            for (Integer move : possibleMoves) {
                int stonesLeftAfterMove = stoneCount - move;

                if (movesToIsFirstPlayer.containsKey(stonesLeftAfterMove)) {
                    boolean nextWinningPlayer = movesToIsFirstPlayer.get(stonesLeftAfterMove);

                    if (nextWinningPlayer) {
                        ++secondPlayerWins;
                    }
                }
            }

            movesToIsFirstPlayer.put(stoneCount, !(secondPlayerWins == 3));
        }

        movesToIsFirstPlayer.get(stones);
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
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                String result = Result.gameOfStones(n);

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
