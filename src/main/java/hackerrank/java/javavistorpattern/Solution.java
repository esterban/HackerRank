package hackerrank.java.javavistorpattern;

import java.io.*;
import java.util.*;

import java.util.stream.Collectors;

enum Color {
    RED, GREEN
}

abstract class Tree {

    private int value;
    private Color color;
    private int depth;

    public Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

    private final ArrayList<Tree> children = new ArrayList<>();

    public TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {

    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis {
    public abstract int getResult();

    public abstract void visitNode(TreeNode node);

    public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
    private int sum = 0;

    public int getResult() {
        //implement this
        return sum;
    }

    public void visitNode(TreeNode node) {
        //implement this
    }

    public void visitLeaf(TreeLeaf leaf) {
        //implement this
        sum += leaf.getValue();
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    //    private double product = 1.0;
    private long product = 1;

    public int getResult() {
        //implement this
        return (int) product;
    }

    public void visitNode(TreeNode node) {
        //implement this
        if (node.getColor() == Color.RED) {
            product *= node.getValue();
            product = product % (1000 * 1000 * 1000 + 7);
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        //implement this
        if (leaf.getColor() == Color.RED) {
            product *= leaf.getValue();
            product = product % (1000 * 1000 * 1000 + 7);
        }
    }
}

class FancyVisitor extends TreeVis {
    private int nonLeafSum = 0;
    private int greenLeafSum = 0;

    public int getResult() {
        //implement this
        return Math.abs(nonLeafSum - greenLeafSum);
    }

    public void visitNode(TreeNode node) {
        //implement this
        if (node.getDepth() % 2 == 1) {
            nonLeafSum += node.getValue();
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        //implement this
        if (leaf.getColor() == Color.GREEN) {
            greenLeafSum += leaf.getValue();
        }
    }
}

public class Solution {
    public static Tree solve() {
        //read the tree from STDIN and return its root as a return value of this function
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String line;

        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int numberOfNodes = Integer.parseInt(line);


        List<Integer> valuesList = createValuesList(bufferedReader);
        List<Color> colorList = createColorList(bufferedReader);
        List<AbstractMap.SimpleEntry<Integer, Integer>> nodeLinksList = createNodeLinksList(bufferedReader);
        Set<Integer> parentNodeNumbersSet = createParentNodeSet(nodeLinksList);

        List<Tree> unconnectedNodeList = createUnconnectedNodes(parentNodeNumbersSet, numberOfNodes, valuesList, colorList);

        Map<Integer, Set<Integer>> normalMapping = createNormalMapping(nodeLinksList);
        Map<Integer, Set<Integer>> reverseMapping = createReverseMapping(nodeLinksList);

//        createChildToParentNodeMap(unconnectedNodeList, nodeLinksList, 1, 1, 1);

        createChildToParentNodeMapB(unconnectedNodeList, normalMapping, reverseMapping, 1, 0, 1);

        return unconnectedNodeList.get(0);
    }

    public static Map<Integer, Set<Integer>> createNormalMapping(List<AbstractMap.SimpleEntry<Integer, Integer>> edgesList) {
        Map<Integer, Set<Integer>> mapping = new HashMap<>();

        for (AbstractMap.SimpleEntry<Integer, Integer> edge : edgesList) {
            Integer fromNode = edge.getKey();
            Integer toNode = edge.getValue();

            mapping.putIfAbsent(fromNode, new HashSet<>());

            mapping.get(fromNode).add(toNode);
        }

        return mapping;
    }

    public static Map<Integer, Set<Integer>> createReverseMapping(List<AbstractMap.SimpleEntry<Integer, Integer>> edgesList) {
        Map<Integer, Set<Integer>> mapping = new HashMap<>();

        for (AbstractMap.SimpleEntry<Integer, Integer> edge : edgesList) {
            Integer fromNode = edge.getValue();
            Integer toNode = edge.getKey();

            mapping.putIfAbsent(fromNode, new HashSet<>());

            mapping.get(fromNode).add(toNode);
        }

        return mapping;
    }


    public static void createChildToParentNodeMap(List<Tree> nodeList, List<AbstractMap.SimpleEntry<Integer, Integer>> edgesList, int currentNodeNumber, int parentNodeNumber, int depth) {
        int currentNodeIndex = currentNodeNumber - 1;
        nodeList.get(currentNodeIndex).setDepth(depth);
        Tree currentNode = nodeList.get(currentNodeIndex);

        for (AbstractMap.SimpleEntry<Integer, Integer> edge : edgesList) {
            Integer leftNodeNumber = edge.getKey();
            Integer rightNodeNumber = edge.getValue();

            if (leftNodeNumber == currentNodeNumber && rightNodeNumber != parentNodeNumber) {
                int parentIndex = leftNodeNumber - 1;
                int childIndex = rightNodeNumber - 1;

                Tree parentNode = nodeList.get(parentIndex);

                if (parentNode.getClass() != TreeNode.class) {
                    parentNode = new TreeNode(currentNode.getValue(), currentNode.getColor(), depth);
                    nodeList.set(parentIndex, parentNode);
                }

                createChildToParentNodeMap(nodeList, edgesList, rightNodeNumber, leftNodeNumber, depth + 1);

                Tree childNode = nodeList.get(childIndex);
                ((TreeNode) parentNode).addChild(childNode);
            } else if (rightNodeNumber == currentNodeNumber && leftNodeNumber != parentNodeNumber) {
                int parentIndex = rightNodeNumber - 1;
                int childIndex = leftNodeNumber - 1;

                Tree parentNode = nodeList.get(parentIndex);

                if (parentNode.getClass() != TreeNode.class) {
                    parentNode = new TreeNode(currentNode.getValue(), currentNode.getColor(), depth);
                    nodeList.set(parentIndex, parentNode);
                }

                createChildToParentNodeMap(nodeList, edgesList, leftNodeNumber, rightNodeNumber, depth + 1);

                Tree childNode = nodeList.get(childIndex);
                ((TreeNode) parentNode).addChild(childNode);
            }
        }
    }

    public static void createChildToParentNodeMapB(List<Tree> nodeList, Map<Integer, Set<Integer>> normalMapping, Map<Integer, Set<Integer>> reversalMapping, int currentNodeNumber, int parentNodeNumber, int depth) {
        Set<Integer> mappingToSet = normalMapping.getOrDefault(currentNodeNumber, new HashSet<>());
        Set<Integer> reversalToSet = reversalMapping.getOrDefault(currentNodeNumber, new HashSet<>());
        mappingToSet.addAll(reversalToSet);

        int currentIndex = currentNodeNumber - 1;
        nodeList.get(currentIndex).setDepth(depth);

        for (Integer otherNodeNumber : mappingToSet) {
            if (otherNodeNumber != currentNodeNumber && otherNodeNumber != parentNodeNumber) {
                int childIndex = otherNodeNumber - 1;

                Tree parentNode = nodeList.get(currentIndex);

                if (parentNode.getClass() != TreeNode.class) {
                    parentNode = new TreeNode(parentNode.getValue(), parentNode.getColor(), depth);
                    nodeList.set(currentIndex, parentNode);
                }

                createChildToParentNodeMapB(nodeList, normalMapping, reversalMapping, otherNodeNumber, currentNodeNumber, depth + 1);

                Tree childNode = nodeList.get(childIndex);
                ((TreeNode) parentNode).addChild(childNode);
            }
        }
    }


    public static List<Tree> createUnconnectedNodes(Set<Integer> nonLeafNodeNumbers, int nodeCount, List<Integer> values, List<Color> colors) {
        List<Tree> nodeList = new ArrayList<>();

        for (int nodeCounter = 1; nodeCounter <= nodeCount; ++nodeCounter) {
            int index = nodeCounter - 1;
            int value = values.get(index);
            Color color = colors.get(index);

            Tree newNode = new TreeLeaf(value, color, -1);
            nodeList.add(newNode);
        }

        return nodeList;
    }

    public static List<Color> createColorList(BufferedReader bufferedReader) {
        String line;

        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Color> colourList = Arrays.asList(line.split(" ")).stream()
                .map(s -> Integer.valueOf(s) == 0 ? Color.RED : Color.GREEN)
                .collect(Collectors.toList());

        return colourList;
    }

    public static List<Integer> createValuesList(BufferedReader bufferedReader) {
        String line;

        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Integer> valuesList = Arrays.asList(line.split(" ")).stream()
                .map(s -> Integer.valueOf(s))
                .collect(Collectors.toList());

        return valuesList;
    }

    public static List<AbstractMap.SimpleEntry<Integer, Integer>> createNodeLinksList(BufferedReader bufferedReader) {
        List<AbstractMap.SimpleEntry<Integer, Integer>> nodeLinksList = new ArrayList<>();

        String line;

        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (line != null) {
            String[] valueString = line.split(" ");
            Integer leftNodeNumber = Integer.valueOf(valueString[0]);
            Integer rightNodeNumber = Integer.valueOf(valueString[1]);

            nodeLinksList.add(new AbstractMap.SimpleEntry<>(leftNodeNumber, rightNodeNumber));

            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return nodeLinksList;
    }

    public static Set<Integer> createParentNodeSet(List<AbstractMap.SimpleEntry<Integer, Integer>> nodeLinksList) {
        Set<Integer> nodeNumberSet = nodeLinksList.stream().map(e -> e.getKey()).collect(Collectors.toSet());

        return nodeNumberSet;
    }


    public static void main(String[] args) {
        Tree root = solve();
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();

        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);

        int res1 = vis1.getResult();
        int res2 = vis2.getResult();
        int res3 = vis3.getResult();

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}