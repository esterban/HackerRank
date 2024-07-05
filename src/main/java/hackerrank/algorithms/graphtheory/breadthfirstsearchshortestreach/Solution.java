package hackerrank.algorithms.graphtheory.breadthfirstsearchshortestreach;

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

//            if (!nodeToOtherNodes.containsKey(secondNode)) {

            nodeToOtherNodes.get(secondNode).add(firstNode);
//            }
        }

        for (int counter = 1; counter <= n; ++counter) {
            if (counter == s) {
                continue;
            }

//            Integer numberOfHops = numberOfHops(nodeToOtherNodes, s, counter);
            Integer numberOfHops = numberOfHopsMK2(nodeToOtherNodes, s, counter);
            result.add(numberOfHops);
        }

        return result;
    }

//    private static boolean detectCircularReference(Map<Integer, Set<Integer>> graph, int firstNode, int secondNode) {
//
//    }

//    public static int numberOfHops(Map<Integer, Set<Integer>> graph, int startNode, int targetNode) {
//        if (!graph.containsKey(targetNode) || !graph.containsKey(startNode)) {
//            return -1;
//        }
//
//        int currentNode = startNode;
//
//        Set<Integer> nodesVisited = new HashSet<>();
//        nodesVisited.add(startNode);
//
//        int shortestPath = recurse(graph, startNode, targetNode, nodesVisited);
////        if (shortestPath != -1) {
////            shortestPath -= 6;
////        }
//
//        return shortestPath;
//    }
//
//    private static int recurse(Map<Integer, Set<Integer>> graph, int currentNode, int targetNode, Set<Integer> visitedNodes) {
//        if (currentNode == targetNode) {
//            return 6;
//        }
//
////        boolean nodeFound = true;
//
//        for (Integer childNode : graph.get(currentNode)) {
//            if (!visitedNodes.contains(childNode)) {
//
//                if (childNode == targetNode) {
////                    visitedNodes.add(childNode);
//                    return 6;
//                }
//            }
//        }
//
//        int shortestPath = -1;
//
//        for (Integer childNode : graph.get(currentNode)) {
//
//            if (!visitedNodes.contains(childNode)) {
////                visitedNodes.add(childNode);
//
//                int value = recurse(graph, childNode, targetNode, visitedNodes);
//
//                if (value != -1 && (value < shortestPath || shortestPath == -1)) {
//                    shortestPath = value + 6;
//                }
//            }
//        }
//
//        return shortestPath;
//    }

    public static int numberOfHopsMK2(Map<Integer, Set<Integer>> graph, int startNode, int targetNode) {
        if (!graph.containsKey(targetNode) || !graph.containsKey(startNode)) {
            return -1;
        }

        int currentNode = startNode;

        for (int depth = 1; depth < 50; ++depth) {
            Set<Integer> visitedNodes = new HashSet<>();
            visitedNodes.add(startNode);

            int shortestPath = recurseMK2(graph, currentNode, targetNode, depth, visitedNodes);
            if (shortestPath != -1) {
                return shortestPath;
            }
        }

        return -1;
    }


    //    private static int recurseMK2(Map<Integer, Set<Integer>> graph, int currentNode, int targetNode, int depth, Set<Integer> visitedNodes) {
    private static int recurseMK2(Map<Integer, Set<Integer>> graph, int currentNode, int targetNode, int depth, Set<Integer> visitedNodes) {
        if (depth == 0) {
            return -1;
        }

        Set<Integer> visitedNodesCopy = new HashSet<>(visitedNodes);

        for (Integer childNode : graph.get(currentNode)) {
            if (childNode == targetNode) {
                return 6;
            }
        }

        for (Integer childNode : graph.get(currentNode)) {
            if (!visitedNodesCopy.contains(childNode)) {
                visitedNodesCopy.add(childNode);

                int value = recurseMK2(graph, childNode, targetNode, depth - 1, visitedNodesCopy);

                if (value != -1) {
                    return value + 6;
                }
            }
        }

//        visitedNodes.addAll(graph.get(currentNode));

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
