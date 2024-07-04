package algorithms.graphtheory.breadthfirstsearchshortestreach;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'bfs' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER m
     *  3. 2D_INTEGER_ARRAY edges
     *  4. INTEGER s
     */

    public static List<Integer> bfs(int n, int m, List<List<Integer>> edges, int s) {
        List<Integer> result = new ArrayList<>();

        Map<Integer, Set<Integer>> nodeToOtherNodes = new HashMap<>();

        for (List<Integer> edge : edges) {
            Integer firstNode = edge.get(0);
            Integer secondNode = edge.get(1);

            nodeToOtherNodes.putIfAbsent(firstNode, new HashSet<>());
            nodeToOtherNodes.get(firstNode).add(secondNode);

            nodeToOtherNodes.putIfAbsent(secondNode, new HashSet<>());
            nodeToOtherNodes.get(secondNode).add(firstNode);
        }

        for (int counter = 1; counter <= n; ++counter) {
            if (counter == s) {
                continue;
            }

            Integer numberOfHops = numberOfHops(nodeToOtherNodes, s, counter);
            result.add(numberOfHops);
        }

        return result;
    }

    public static int numberOfHops(Map<Integer, Set<Integer>> graph, int startNode, int targetNode) {
        if (!graph.containsKey(targetNode)) {
            return -1;
        }

        int currentNode = startNode;

        Set<Integer> nodesVisited = new HashSet<>();
        nodesVisited.add(startNode);

        int shortestPath = recurse(graph, startNode, targetNode, nodesVisited);
        if (shortestPath != -1) {
            shortestPath -= 6;
        }

        return shortestPath;
    }

    private static int recurse(Map<Integer, Set<Integer>> graph, int currentNode, int targetNode, Set<Integer> visitedNodes) {
        if (currentNode == targetNode) {
            return 6;
        }

//        boolean nodeFound = true;

        for (Integer childNode : graph.get(currentNode)) {
            if (!visitedNodes.contains(childNode)) {
                visitedNodes.add(childNode);

                int value = recurse(graph, childNode, targetNode, visitedNodes);

                if (value != -1) {
                    return value + 6;
                }
            }
        }

        return -1;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                List<List<Integer>> edges = new ArrayList<>();

                IntStream.range(0, m).forEach(i -> {
                    try {
                        edges.add(
                                Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                        .map(Integer::parseInt)
                                        .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                int s = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> result = Result.bfs(n, m, edges, s);

                bufferedWriter.write(
                        result.stream()
                                .map(Object::toString)
                                .collect(joining(" "))
                                + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
