package leetcode.n_queens;

import java.util.*;
import java.util.stream.Collectors;

class NQueens {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        // Set<List<String>> result = new HashSet<>();

        if (n == 1) {
            return Collections.singletonList(Collections.singletonList("Q"));
        }

        if (n == 3) {
            return Collections.emptyList();
        }

        boolean nIsOdd = n % 2 == 1;

        int firstIndex = 0;
        int lastIndex = n;
        int maxMove = n / 2;

        if (!nIsOdd) {
            ++firstIndex;
            --lastIndex;
        }

        for (int startingIndex = firstIndex; startingIndex < lastIndex; ++startingIndex) {
            for (int move = 2; move < n; ++move) {
                List<String> solution = new ArrayList<>();

                int queenIndex = startingIndex;

                Set<Integer> columnsUsed = new HashSet<>();

                int previousIndex = -1;

                for (int queenCounter = 1; queenCounter <= n; ++queenCounter) {
                    String rowString = toRowString(queenIndex, n);

                    solution.add(rowString);

                    previousIndex = queenIndex;
                    queenIndex = addAndWrap(queenIndex, move, n, nIsOdd);

                    // System.out.println("Queen index = " + queenIndex);

                    if (columnsUsed.contains(queenIndex)) {
                        System.out.println("Breaking loop startingIndex = " + startingIndex + " -> queenIndex = " + queenIndex + " -> move = " + move);
                        System.out.println("Breaking columns used = " + columnsUsed);

                        break;
                    }

                    if (Math.abs(previousIndex - queenIndex) <= 1) {
                        System.out.println("Breaking previousIndex = " + previousIndex + " -> queenIndex = " + queenIndex);

                        break;
                    }

                    columnsUsed.add(queenIndex);
                }

                if (solution.size() == n) {
                    System.out.println("Adding startingIndex = " + startingIndex + " -> queenIndex = " + queenIndex + " -> move = " + move);

                    result.add(solution);
                }
            }
        }

        return result.stream().collect(Collectors.toList());
    }

    private static String toRowString(int queenColumnIndex, int rowLength) {
        int beforeCount = Math.max(0, queenColumnIndex);
        int afterCount = Math.max(0, rowLength - queenColumnIndex - 1);

        return ".".repeat(beforeCount) + "Q" + ".".repeat(afterCount);
    }

    private static int addAndWrap(int value, int delta, int limit, boolean isOdd) {
        if (delta >= 0) {
            if (value + delta >= limit) {
                if (isOdd) {
                    return (value + delta) % limit;
                }

                if (value + delta - 1 >= limit) {
                    return (value + delta - 1) % limit;
                }

                return (value + delta) % limit;
            }

            return value + delta;
        }

        return -100;
    }
}