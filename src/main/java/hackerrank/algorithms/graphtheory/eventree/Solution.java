package hackerrank.algorithms.graphtheory.eventree;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Solution {

    static public class Node {
        private final List<Node> childNodes = new ArrayList<>();
        private Node parentNode;
        public final int nodeNumber;

        Node(int nodeNumber) {
            this.nodeNumber = nodeNumber;
        }

        public void addChildNode(Node childNode) {
            childNode.parentNode = this;
            childNodes.add(childNode);
        }

        public List<Node> getChildNodes() {
            return childNodes;
        }
    }

    public static Map<Integer, Node> createForest(List<Integer> t_from, List<Integer> t_to) {
//        Node rootNode = null;

        Map<Integer, Node> foundNodes = new HashMap<>();

        for (int edgeIndex = 0; edgeIndex < t_from.size(); ++edgeIndex) {
            Integer fromNodeNumber = t_from.get(edgeIndex);
            Integer toNodeNumber = t_to.get(edgeIndex);

            if (fromNodeNumber < toNodeNumber) {
                throw new RuntimeException("Edge has from lower than to = " + fromNodeNumber + " < " + toNodeNumber);
            }

            Node fromNode;
            Node toNode;

            if (!foundNodes.containsKey(fromNodeNumber)) {
                fromNode = new Node(fromNodeNumber);
                foundNodes.put(fromNodeNumber, fromNode);
            } else {
                fromNode = foundNodes.get(fromNodeNumber);
            }

            if (!foundNodes.containsKey(toNodeNumber)) {
                toNode = new Node(toNodeNumber);
                foundNodes.put(toNodeNumber, toNode);
            } else {
                toNode = foundNodes.get(toNodeNumber);
            }

            toNode.addChildNode(fromNode);
//            fromNode.parentNode = toNode;
        }

        return foundNodes;
    }

    public static int countSubNodesInclusive(Node node, Set<Integer> excludedNodeNumbers) {
        if (excludedNodeNumbers.contains(node.nodeNumber)) {
            return 0;
        } else if (node.childNodes.isEmpty()) {
            return 1;
        } else {
            int nodeCount = 1;

            for (Node childNode : node.childNodes) {
                if (!excludedNodeNumbers.contains(childNode.nodeNumber)) {
                    nodeCount += countSubNodesInclusive(childNode, excludedNodeNumbers);
                }
            }

            return nodeCount;
        }
    }

    public static List<Node> findLeafNodes(Node node, Set<Integer> excludeNodeNumbers) {
        List<Node> leafNodes = new ArrayList<>();

        if (excludeNodeNumbers.contains(node.nodeNumber)) {
            return Collections.emptyList();
        }

        for (Node childNode : node.childNodes) {
            if (!excludeNodeNumbers.contains(childNode.nodeNumber)) {
                List<Node> lowerLeafNodes = findLeafNodes(childNode, excludeNodeNumbers);
                leafNodes.addAll(lowerLeafNodes);
            }
        }

        if (leafNodes.isEmpty()) {
            leafNodes.add(node);
        }

        return leafNodes;
    }

    // Complete the evenForest function below.
    static int evenForest(int t_nodes, int t_edges, List<Integer> t_from, List<Integer> t_to) {
        Map<Integer, Node> nodesMap = createForest(t_from, t_to);
        int edgesRemoved = 0;

        Set<Integer> nodesProcessedNumbers = new HashSet<>();
        Set<Integer> culledNodes = new HashSet<>();

        while (nodesProcessedNumbers.size() < nodesMap.size() - 1) {
            List<Node> leafNodesList = findLeafNodes(nodesMap.get(1), nodesProcessedNumbers);

            for (Node leafNode : leafNodesList) {
                int childNodesCount = countSubNodesInclusive(leafNode, culledNodes);

                boolean allChildNodesProcessed = checkAllCheckNodesProcessed(leafNode, nodesProcessedNumbers);

                if (childNodesCount > 0 && childNodesCount % 2 == 0 && allChildNodesProcessed) {
                    ++edgesRemoved;
                    culledNodes.add(leafNode.nodeNumber);
                }

                if (allChildNodesProcessed) {
                    nodesProcessedNumbers.add(leafNode.nodeNumber);
                }
            }
        }

        return edgesRemoved;
    }

    public static boolean checkAllCheckNodesProcessed(Node node, Set<Integer> processedNodesList) {
        for (Node childNode : node.getChildNodes()) {
            if (!processedNodesList.contains(childNode.nodeNumber)) {
                return false;
            }
        }

        return true;
    }

    public static int countLeavesWithExclusions(Node node, Set<Integer> exclusions) {
        int count = 0;

        for (Node childNode : node.childNodes) {
            if (!exclusions.contains(childNode.nodeNumber)) {
                ++count;
            }
        }

        return count;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] tNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int tNodes = Integer.parseInt(tNodesEdges[0]);
        int tEdges = Integer.parseInt(tNodesEdges[1]);

        List<Integer> tFrom = new ArrayList<>();
        List<Integer> tTo = new ArrayList<>();

        IntStream.range(0, tEdges).forEach(i -> {
            try {
                String[] tFromTo = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                tFrom.add(Integer.parseInt(tFromTo[0]));
                tTo.add(Integer.parseInt(tFromTo[1]));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int res = evenForest(tNodes, tEdges, tFrom, tTo);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
