package hackerrank.algorithms.graphtheory.froginmaze.attempt2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Solution {
    public static class Tunnel {
        public final int rowA;
        public final int columnA;
        public final int rowB;
        public final int columnB;

        public Tunnel(int rowA, int columnA, int rowB, int columnB) {
            this.rowA = rowA;
            this.columnA = columnA;
            this.rowB = rowB;
            this.columnB = columnB;
        }
    }

    public static final class TunnelCell implements ICell {

        public final int destinationRow;
        public final int destinationColumn;
        public ICell destinationCell;

        public TunnelCell(int destinationRow, int destinationColumn, ICell destinationCell) {
            this.destinationRow = destinationRow;
            this.destinationColumn = destinationColumn;
            this.destinationCell = destinationCell;
        }

        @Override
        public Frog action(Frog frog) {
//            return new Frog(destinationRow, destinationColumn, frog.isAlive, frog.isFree);
            frog.row = destinationRow;
            frog.column = destinationColumn;
            frog.isAlive = destinationCell.isKillsFrog();
            frog.isFree = destinationCell.isAliveExitCell();

            return frog;
        }

        @Override
        public boolean canHoldFrog() {
            return true;
        }

        @Override
        public boolean isAliveExitCell() {
            return false;
        }

        @Override
        public boolean isKillsFrog() {
            return destinationCell.getClass() != TunnelCell.class && destinationCell.isKillsFrog();
        }
    }


    public static final class ExitCell implements ICell {
        @Override
        public Frog action(Frog frog) {
            return Frog.generateFreeFrog(frog);
        }

        @Override
        public boolean canHoldFrog() {
            return true;
        }

        @Override
        public boolean isAliveExitCell() {
            return true;
        }

        @Override
        public boolean isKillsFrog() {
            return false;
        }
    }

    public static class FreeCell implements ICell {
        @Override
        public Frog action(Frog frog) {
//        return new Frog(frog.row, frog.column, frog.isAlive, false);
            return frog;
        }

        @Override
        public boolean canHoldFrog() {
            return true;
        }

        @Override
        public boolean isAliveExitCell() {
            return false;
        }

        @Override
        public boolean isKillsFrog() {
            return false;
        }
    }

    public static final class MineCell implements ICell {
        @Override
        public Frog action(Frog frog) {
            return Frog.generateDeadFrog(frog);
        }

        @Override
        public boolean canHoldFrog() {
            return true;
        }

        @Override
        public boolean isAliveExitCell() {
            return false;
        }

        @Override
        public boolean isKillsFrog() {
            return true;
        }
    }

    public static final class ObstacleCell implements ICell {
        @Override
        public Frog action(Frog frog) {
            return frog;
        }

        @Override
        public boolean canHoldFrog() {
            return false;
        }

        @Override
        public boolean isAliveExitCell() {
            return false;
        }

        @Override
        public boolean isKillsFrog() {
            return false;
        }
    }

    public static final class StartingCell implements ICell {
        @Override
        public Frog action(Frog frog) {
            return frog;
        }

        @Override
        public boolean canHoldFrog() {
            return true;
        }

        @Override
        public boolean isAliveExitCell() {
            return false;
        }

        @Override
        public boolean isKillsFrog() {
            return false;
        }
    }


    public interface ICellGenerator {
        ICell generate(String cellString);
    }

    public static final class CellGeneratorImpl implements ICellGenerator {
        @Override
        public ICell generate(String cellString) {
            switch (cellString) {
                case "#":
                    return new ObstacleCell();

                case "A":
                    return new StartingCell();

                case "*":
                    return new MineCell();

                case "%":
                    return new ExitCell();

                case "O":
                    return new FreeCell();
            }
//        return null;

            throw new RuntimeException("Cell string unrecognised = " + cellString);
        }
    }


    public static final class Frog {
//    public final int row;
//    public final int column;
//    public final boolean isAlive;
//    public final boolean isFree;

        public int row;
        public int column;
        public boolean isAlive;
        public boolean isFree;


        public Frog(int row, int column, boolean isAlive, boolean isFree) {
            this.row = row;
            this.column = column;
            this.isAlive = isAlive;
            this.isFree = isFree;
        }

        public static Frog generateDeadFrog(Frog frog) {
//        return new Frog(frog.row, frog.column, false, frog.isFree);
            frog.isAlive = false;
            return frog;
        }

        public static Frog generateFreeFrog(Frog frog) {
//        return new Frog(frog.row, frog.column, frog.isAlive, true);
            frog.isFree = true;
            return frog;
        }

        public static Frog moveUp(Frog frog) {
            if (frog.row < 0) {
                throw new RuntimeException(frog + " cannot move up already at row " + frog.row);
            }

//        if (frog.row == 1) {
//            System.out.println("Top edge found");
//        }

//        return new Frog(frog.row - 1, frog.column, frog.isAlive, frog.isFree);
            --frog.row;
            return frog;
        }

        public static Frog moveDown(Frog frog) {
//        if (frog.row < 1) {
//            throw new RuntimeException(this + " cannot move up already at row " + frog.row);
//        }

//        return new Frog(frog.row + 1, frog.column, frog.isAlive, frog.isFree);
            ++frog.row;
            return frog;
        }

        public static Frog moveLeft(Frog frog) {
            if (frog.column < 0) {
                throw new RuntimeException(frog + " cannot move left already at row " + frog.column);
            }

//        return new Frog(frog.row, frog.column - 1, frog.isAlive, frog.isFree);

            --frog.column;
            return frog;
        }

        public static Frog moveRight(Frog frog) {
//        if (frog.column < 0) {
//            throw new RuntimeException(this + " cannot move left already at row " + frog.column);
//        }

//        return new Frog(frog.row, frog.column + 1, frog.isAlive, frog.isFree);
            ++frog.column;
            return frog;
        }


        @Override
        public String toString() {
            return "Frog{" +
                    "row=" + row +
                    ", column=" + column +
                    ", isAlive=" + isAlive +
                    ", isFree=" + isFree +
                    '}';
        }
    }


    public interface ICell {
        Frog action(Frog frog);

        boolean canHoldFrog();

        boolean isAliveExitCell();

        boolean isKillsFrog();
    }


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);
        int m = Integer.parseInt(firstMultipleInput[1]);
        int k = Integer.parseInt(firstMultipleInput[2]);

        ICellGenerator cellGenerator = new CellGeneratorImpl();

        List<List<ICell>> map = new ArrayList<>();
        int startFrogRow = 0;
        int startFrogColumn = 0;

//        IntStream.range(0, n).forEach(nItr -> {
        for (int nItr = 0; nItr < n; ++nItr) {
            try {
                String row = bufferedReader.readLine();
                List<ICell> rowList = new ArrayList<>();

                // Write your code here
                String[] rowElements = row.split("");

//                for (String cellString : rowElements) {
                for (int columnIndex = 0; columnIndex < rowElements.length; ++columnIndex) {
                    String cellString = rowElements[columnIndex];

                    ICell cell = cellGenerator.generate(cellString);

                    if (cellString.equals("A")) {
                        rowList.add(new FreeCell());
                        startFrogRow = nItr;
                        startFrogColumn = columnIndex;
                    } else {
                        rowList.add(cell);
                    }
                }

                // System.out.println(rowElements[0]);

                map.add(rowList);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        List<Tunnel> tunnelList = new ArrayList<>();

        IntStream.range(0, k).forEach(kItr -> {
            try {
                String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int i1 = Integer.parseInt(secondMultipleInput[0]);
                int j1 = Integer.parseInt(secondMultipleInput[1]);
                int i2 = Integer.parseInt(secondMultipleInput[2]);
                int j2 = Integer.parseInt(secondMultipleInput[3]);

                // Write your code here
                Tunnel tunnel = new Tunnel(i1 - 1, j1 - 1, i2 - 1, j2 - 1);
                tunnelList.add(tunnel);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Write your code here
        map = applyTunnelsToMap(map, tunnelList);


        List<ICell> availableCells = new ArrayList<>();
        Frog frog = new Frog(0, 0, true, false);
        Random random = new Random();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        for (int sampleCounter = 1; sampleCounter <= 1; ++sampleCounter) {
            long startNanos = System.nanoTime();

            double aliveExitProbability = calcAliveExitProbability(map, startFrogRow, startFrogColumn);

            long endNanos = System.nanoTime();

            double durationNanos = endNanos - startNanos;
            double durationSeconds = durationNanos / 1e9;

            // System.out.println("Time = " + simpleDateFormat.format(new Date()));
            // System.out.println("Time taken = " + durationSeconds + "s");
            System.out.println(aliveExitProbability);
        }

        bufferedReader.close();
    }

//    public static double calcAliveExitProbability(List<List<ICell>> map, int startRow, int startColumn) {
//        double aliveExitProbability;
//
//        final long numberOfPasses = 1000;
//
//        int numberOfRows = map.size();
//        int numberOfColumns = map.get(0).size();
//
//        List<ICell> availableCells = new ArrayList<>();
//
//        List<List<Double>> mapProbabilities = generateProbabilityMap(startRow, startColumn, numberOfRows, numberOfColumns);
//
//        for (int passCounter = 1; passCounter <= numberOfPasses; ++passCounter) {
//
//            for (int rowIndex = 0; rowIndex < map.size(); ++rowIndex) {
//                List<ICell> rowList = map.get(rowIndex);
//                List<Double> rowProbabilityList = mapProbabilities.get(rowIndex);
//
//                for (int columnIndex = 0; columnIndex < numberOfColumns; ++columnIndex) {
//                    ICell cell = rowList.get(columnIndex);
//
//                    Double cellProbability = rowProbabilityList.get(columnIndex);
//
//                    if (cellProbability > 0.0 && !(cell.isAliveExitCell() || cell.isKillsFrog())) {
//                        availableCells = generateAvailableCells(map, rowIndex, columnIndex, availableCells);
//
//                        long numberOfAvailableCells = availableCells.stream().filter(Objects::nonNull).count();
//
//                        Double adjacentProbability = cellProbability / (double) numberOfAvailableCells;
//
//                        AbstractMap.SimpleEntry<Double, Long> adjacentProbabilityPair = null;
//
//                        for (int availableCellIndex = 0; availableCellIndex < availableCells.size(); ++availableCellIndex) {
//                            ICell availableCell = availableCells.get(availableCellIndex);
//
//                            if (availableCell != null && availableCell.getClass() == TunnelCell.class) {
//                                TunnelCell tunnelCell = (TunnelCell) availableCell;
//
//                                adjacentProbabilityPair = applyTunnelsToProbabilitiesMap(map, mapProbabilities, ((TunnelCell) tunnelCell).destinationRow, tunnelCell.getDestinationColumn, cellProbability, numberOfAvailableCells);
//                                numberOfAvailableCells += adjacentProbabilityPair.getValue();
//                            }
//                        }
//
//                        for (int availableCellIndex = 0; availableCellIndex < availableCells.size(); ++availableCellIndex) {
//                            ICell availableCell = availableCells.get(availableCellIndex);
//
//                            if (availableCell != null && availableCell.getClass() == TunnelCell.class) {
//                                TunnelCell tunnelCell = (TunnelCell) availableCell;
//                                double probabilityPerCell = cellProbability / (double) adjacentProbabilityPair.getValue();
//
//                                adjacentProbabilityPair = applyTunnelsToProbabilitiesMap(map, mapProbabilities, ((TunnelCell) tunnelCell).destinationRow, tunnelCell.getDestinationColumn, probabilityPerCell, null);
//                            }
//                        }
//
//                        double probabilityToAdd = adjacentProbabilityPair == null ? cellProbability / numberOfAvailableCells : adjacentProbabilityPair.getKey();
//
//                        mapProbabilities = addProbabilityToCells(mapProbabilities, availableCells, rowIndex, columnIndex, probabilityToAdd);
//
//                        rowProbabilityList.set(columnIndex, 0.0);
//                    }
//                }
//            }
//
//            aliveExitProbability = countAliveProbabilities(mapProbabilities, map);
//
//            if (aliveExitProbability > 1.1) {
//                throw new RuntimeException("Alive probability is greater than 1.0, probability = " + aliveExitProbability);
//            }
//
////            System.out.println("Alive probability = " + aliveExitProbability);
//        }
//
//        aliveExitProbability = countAliveProbabilities(mapProbabilities, map);
//
//        return aliveExitProbability;
//    }

    public static double calcAliveExitProbability(List<List<ICell>> map, int startRow, int startColumn) {
        double aliveExitProbability;

//        final long numberOfPasses = 1000;
//        final long numberOfPasses = 10000;
        final long numberOfPasses = 50000;
//        final long numberOfPasses = 100000;
//        final long numberOfPasses = (long) 1e6;

        int numberOfRows = map.size();
        int numberOfColumns = map.get(0).size();

        List<ICell> availableCells = new ArrayList<>();

//        List<List<Double>> mapProbabilities = generateProbabilityMap(startRow, startColumn, numberOfRows, numberOfColumns);
//        List<List<Double>> mapProbabilitiesSwap = generateProbabilityMap(startRow, startColumn, numberOfRows, numberOfColumns);
//        mapProbabilitiesSwap = resetProbabilityMap(mapProbabilitiesSwap);

        double[][] mapProbabilities = generateProbabilityMapArray(startRow, startColumn, numberOfRows, numberOfColumns);
        double[][] mapProbabilitiesSwap = generateProbabilityMapArray(startRow, startColumn, numberOfRows, numberOfColumns);
        mapProbabilitiesSwap = resetProbabilityMapArray(mapProbabilitiesSwap);

        for (int passCounter = 1; passCounter <= numberOfPasses; ++passCounter) {
//            PassEvent passEvent = new PassEvent();
//            passEvent.begin();

//            try {
                for (int rowIndex = 0; rowIndex < map.size(); ++rowIndex) {
                    List<ICell> rowList = map.get(rowIndex);
//                List<Double> rowProbabilityList = mapProbabilities.get(rowIndex);

                    for (int columnIndex = 0; columnIndex < numberOfColumns; ++columnIndex) {
                        ICell cell = rowList.get(columnIndex);

//                    Double cellProbability = rowProbabilityList.get(columnIndex);
                        double cellProbability = mapProbabilities[rowIndex][columnIndex];

                        if (cellProbability > 0.0 && !(cell.isAliveExitCell() || cell.isKillsFrog())) {
//                        List<AbstractMap.SimpleEntry<Integer, Integer>> destinationCells = new ArrayList<>();

                            availableCells = generateAvailableCells(map, rowIndex, columnIndex, availableCells);

//                            long numberOfAvailableCells = availableCells.stream().filter(Objects::nonNull).count();
                            long numberOfAvailableCells = countNumberOfNonNullObjects(availableCells);

                            if (numberOfAvailableCells > 0) {
                                double newProbability = cellProbability / (double) numberOfAvailableCells;
//                            addProbabilityToCells(mapProbabilitiesSwap, availableCells, rowIndex, columnIndex, newProbability);
                                addProbabilityToCellsArray(mapProbabilitiesSwap, availableCells, rowIndex, columnIndex, newProbability);
//                            addProbabilityToTunnelCells(mapProbabilitiesSwap, availableCells, rowIndex, columnIndex, newProbability);
                                addProbabilityToTunnelCellsArray(mapProbabilitiesSwap, availableCells, rowIndex, columnIndex, newProbability);
                            } else {
                                mapProbabilitiesSwap[rowIndex][columnIndex] = cellProbability;
                            }
                        } else {
                            double newCellProbability = cellProbability + mapProbabilitiesSwap[rowIndex][columnIndex];

                            mapProbabilitiesSwap[rowIndex][columnIndex] = newCellProbability;
                        }
                    }
                }
//                passEvent.passNumber = passCounter;
//            } finally {
//                passEvent.end();
//                passEvent.commit();
//            }


            double[][] mapProbabilitiesTemp = mapProbabilities;
            resetProbabilityMapArray(mapProbabilitiesTemp);
            mapProbabilities = mapProbabilitiesSwap;
            mapProbabilitiesSwap = mapProbabilitiesTemp;

//            aliveExitProbability = countAliveProbabilitiesArray(mapProbabilities, map);
//
//            if (aliveExitProbability > 1.0) {
//                throw new RuntimeException("Alive probability is greater than 1.0, probability = " + aliveExitProbability);
//            }
//
//            double allProbabilities = countAllProbabilitiesArray(mapProbabilities, map);
//
//            if (Math.abs(allProbabilities - 1.0) > 1e-6) {
//                throw new RuntimeException("All probabilities sum != 1.0, probability = " + allProbabilities);
//            }

//            System.out.println("Alive probability = " + aliveExitProbability);
        }

        aliveExitProbability = countAliveProbabilitiesArray(mapProbabilities, map);

        return aliveExitProbability;
    }


//    public static AbstractMap.SimpleEntry<Double, Long> applyTunnelsToProbabilitiesMap(List<List<ICell>> map, List<List<Double>> mapProbabilities, int rowTunnelDestination, int columnTunnelDestination, final double probabilityToAdd, Long numberOfAvailableCells) {
//        List<ICell> availableCells = new ArrayList<>();
//        availableCells = generateAvailableCells(map, rowTunnelDestination, columnTunnelDestination, availableCells);
//        long numberOfAvailableCellsTunnel = availableCells.stream().filter(Objects::nonNull).count();
//
//        Double probabilityToAddInner = null;
//        if (numberOfAvailableCells == null) {
//            probabilityToAddInner = probabilityToAdd;
//            addProbabilityToCells(mapProbabilities, availableCells, rowTunnelDestination, columnTunnelDestination, probabilityToAddInner);
//        } else {
//            numberOfAvailableCells += numberOfAvailableCellsTunnel;
//            probabilityToAddInner = probabilityToAdd;
//            addProbabilityToCells(mapProbabilities, availableCells, rowTunnelDestination, columnTunnelDestination, probabilityToAddInner);
//        }
//
//
//        return new AbstractMap.SimpleEntry<>(probabilityToAdd, numberOfAvailableCells);
//    }

//    public static double countAliveProbabilities(List<List<Double>> mapProbabilities, List<List<ICell>> map) {
//        double aliveExitProbabilitySum = 0.0;
//
//        int numberOfRows = map.size();
//        int numberOfColumns = map.get(0).size();
//
//        for (int rowIndex = 0; rowIndex < numberOfRows; ++rowIndex) {
//            List<ICell> rowList = map.get(rowIndex);
//            List<Double> rowProbabilityList = mapProbabilities.get(rowIndex);
//
//            for (int columnIndex = 0; columnIndex < numberOfColumns; ++columnIndex) {
//                ICell cell = rowList.get(columnIndex);
//
//                if (cell.isAliveExitCell()) {
//                    Double cellProbability = rowProbabilityList.get(columnIndex);
//                    aliveExitProbabilitySum += cellProbability;
//                }
//            }
//        }
//
//        return aliveExitProbabilitySum;
//    }

    public static double countAliveProbabilitiesArray(double[][] mapProbabilities, List<List<ICell>> map) {
        double aliveExitProbabilitySum = 0.0;

        int numberOfRows = map.size();
        int numberOfColumns = map.get(0).size();

        for (int rowIndex = 0; rowIndex < numberOfRows; ++rowIndex) {
            List<ICell> rowList = map.get(rowIndex);
//            List<Double> rowProbabilityList = mapProbabilities.get(rowIndex);

            for (int columnIndex = 0; columnIndex < numberOfColumns; ++columnIndex) {
                ICell cell = rowList.get(columnIndex);

                if (cell.isAliveExitCell()) {
                    double cellProbability = mapProbabilities[rowIndex][columnIndex];
                    aliveExitProbabilitySum += cellProbability;
                }
            }
        }

        return aliveExitProbabilitySum;
    }


//    public static double countAllProbabilities(List<List<Double>> mapProbabilities, List<List<ICell>> map) {
//        double cellProbabilitySum = 0.0;
//
//        int numberOfRows = map.size();
//        int numberOfColumns = map.get(0).size();
//
//        for (int rowIndex = 0; rowIndex < numberOfRows; ++rowIndex) {
//            List<Double> rowProbabilityList = mapProbabilities.get(rowIndex);
//
//            for (int columnIndex = 0; columnIndex < numberOfColumns; ++columnIndex) {
//                Double cellProbability = rowProbabilityList.get(columnIndex);
//                cellProbabilitySum += cellProbability;
//            }
//        }
//
//        return cellProbabilitySum;
//    }

    public static long countNumberOfNonNullObjects(List<?> list) {
        long count = 0;

        for (Object object : list) {
            count += object != null ? 1 : 0;
        }

        return count;
    }

    public static double countAllProbabilitiesArray(double[][] mapProbabilities, List<List<ICell>> map) {
        double cellProbabilitySum = 0.0;

        int numberOfRows = map.size();
        int numberOfColumns = map.get(0).size();

        for (int rowIndex = 0; rowIndex < numberOfRows; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < numberOfColumns; ++columnIndex) {
                double cellProbability = mapProbabilities[rowIndex][columnIndex];
                cellProbabilitySum += cellProbability;
            }
        }

        return cellProbabilitySum;
    }

//    public static List<List<Double>> addProbabilityToCells(List<List<Double>> mapProbabilities, List<ICell> availableCells, int rowIndex, int columnIndex, Double probability) {
//        if (availableCells.get(0) != null && availableCells.get(0).getClass() != TunnelCell.class) {
//            Double cellProbability = mapProbabilities.get(rowIndex - 1).get(columnIndex) + probability;
//            mapProbabilities.get(rowIndex - 1).set(columnIndex, cellProbability);
//        }
//
//        if (availableCells.get(1) != null && availableCells.get(1).getClass() != TunnelCell.class) {
//            Double cellProbability = mapProbabilities.get(rowIndex + 1).get(columnIndex) + probability;
//            mapProbabilities.get(rowIndex + 1).set(columnIndex, cellProbability);
//        }
//
//        if (availableCells.get(2) != null && availableCells.get(2).getClass() != TunnelCell.class) {
//            Double cellProbability = mapProbabilities.get(rowIndex).get(columnIndex - 1) + probability;
//            mapProbabilities.get(rowIndex).set(columnIndex - 1, cellProbability);
//        }
//
//        if (availableCells.get(3) != null && availableCells.get(3).getClass() != TunnelCell.class) {
//            Double cellProbability = mapProbabilities.get(rowIndex).get(columnIndex + 1) + probability;
//            mapProbabilities.get(rowIndex).set(columnIndex + 1, cellProbability);
//        }
//
//        return mapProbabilities;
//    }

    //    public static double[][] addProbabilityToCellsArray(double[][] mapProbabilities, List<ICell> availableCells, int rowIndex, int columnIndex, Double probability) {
    public static double[][] addProbabilityToCellsArray(double[][] mapProbabilities, List<ICell> availableCells, int rowIndex, int columnIndex, double probability) {
        if (availableCells.get(0) != null && availableCells.get(0).getClass() != TunnelCell.class) {
            double cellProbability = mapProbabilities[rowIndex - 1][columnIndex] + probability;
            mapProbabilities[rowIndex - 1][columnIndex] = cellProbability;
        }

        if (availableCells.get(1) != null && availableCells.get(1).getClass() != TunnelCell.class) {
//            Double cellProbability = mapProbabilities.get(rowIndex + 1).get(columnIndex) + probability;
//            mapProbabilities.get(rowIndex + 1).set(columnIndex, cellProbability);

            double cellProbability = mapProbabilities[rowIndex + 1][columnIndex] + probability;
            mapProbabilities[rowIndex + 1][columnIndex] = cellProbability;

        }

        if (availableCells.get(2) != null && availableCells.get(2).getClass() != TunnelCell.class) {
//            Double cellProbability = mapProbabilities.get(rowIndex).get(columnIndex - 1) + probability;
//            mapProbabilities.get(rowIndex).set(columnIndex - 1, cellProbability);

            double cellProbability = mapProbabilities[rowIndex][columnIndex - 1] + probability;
            mapProbabilities[rowIndex][columnIndex - 1] = cellProbability;
        }

        if (availableCells.get(3) != null && availableCells.get(3).getClass() != TunnelCell.class) {
//            Double cellProbability = mapProbabilities.get(rowIndex).get(columnIndex + 1) + probability;
//            mapProbabilities.get(rowIndex).set(columnIndex + 1, cellProbability);

            double cellProbability = mapProbabilities[rowIndex][columnIndex + 1] + probability;
            mapProbabilities[rowIndex][columnIndex + 1] = cellProbability;
        }

        return mapProbabilities;
    }

//    public static List<List<Double>> addProbabilityToTunnelCells(List<List<Double>> mapProbabilities, List<ICell> availableCells, int rowIndex, int columnIndex, Double probability) {
//        if (availableCells.get(0) != null && availableCells.get(0).getClass() == TunnelCell.class) {
//            Integer destinationRow = ((TunnelCell) availableCells.get(0)).destinationRow;
//            Integer destinationColumn = ((TunnelCell) availableCells.get(0)).destinationColumn;
//
//            Double cellProbability = mapProbabilities.get(destinationRow).get(destinationColumn) + probability;
//            mapProbabilities.get(destinationRow).set(destinationColumn, cellProbability);
//        }
//
//        if (availableCells.get(1) != null && availableCells.get(1).getClass() == TunnelCell.class) {
//            Integer destinationRow = ((TunnelCell) availableCells.get(1)).destinationRow;
//            Integer destinationColumn = ((TunnelCell) availableCells.get(1)).destinationColumn;
//
//            Double cellProbability = mapProbabilities.get(destinationRow).get(destinationColumn) + probability;
//            mapProbabilities.get(destinationRow).set(destinationColumn, cellProbability);
//        }
//
//        if (availableCells.get(2) != null && availableCells.get(2).getClass() == TunnelCell.class) {
//            Integer destinationRow = ((TunnelCell) availableCells.get(2)).destinationRow;
//            Integer destinationColumn = ((TunnelCell) availableCells.get(2)).destinationColumn;
//
//            Double cellProbability = mapProbabilities.get(destinationRow).get(destinationColumn) + probability;
//            mapProbabilities.get(destinationRow).set(destinationColumn, cellProbability);
//        }
//
//        if (availableCells.get(3) != null && availableCells.get(3).getClass() == TunnelCell.class) {
//            Integer destinationRow = ((TunnelCell) availableCells.get(3)).destinationRow;
//            Integer destinationColumn = ((TunnelCell) availableCells.get(3)).destinationColumn;
//
//            Double cellProbability = mapProbabilities.get(destinationRow).get(destinationColumn) + probability;
//            mapProbabilities.get(destinationRow).set(destinationColumn, cellProbability);
//        }
//
//        return mapProbabilities;
//    }

    public static double[][] addProbabilityToTunnelCellsArray(double[][] mapProbabilities, List<ICell> availableCells, int rowIndex, int columnIndex, double probability) {
        if (availableCells.get(0) != null && availableCells.get(0).getClass() == TunnelCell.class) {
            int destinationRow = ((TunnelCell) availableCells.get(0)).destinationRow;
            int destinationColumn = ((TunnelCell) availableCells.get(0)).destinationColumn;

            double cellProbability = mapProbabilities[destinationRow][destinationColumn] + probability;
            mapProbabilities[destinationRow][destinationColumn] = cellProbability;
        }

        if (availableCells.get(1) != null && availableCells.get(1).getClass() == TunnelCell.class) {
            int destinationRow = ((TunnelCell) availableCells.get(1)).destinationRow;
            int destinationColumn = ((TunnelCell) availableCells.get(1)).destinationColumn;

            double cellProbability = mapProbabilities[destinationRow][destinationColumn] + probability;
            mapProbabilities[destinationRow][destinationColumn] = cellProbability;
        }

        if (availableCells.get(2) != null && availableCells.get(2).getClass() == TunnelCell.class) {
            int destinationRow = ((TunnelCell) availableCells.get(2)).destinationRow;
            int destinationColumn = ((TunnelCell) availableCells.get(2)).destinationColumn;

            double cellProbability = mapProbabilities[destinationRow][destinationColumn] + probability;
            mapProbabilities[destinationRow][destinationColumn] = cellProbability;
        }

        if (availableCells.get(3) != null && availableCells.get(3).getClass() == TunnelCell.class) {
            int destinationRow = ((TunnelCell) availableCells.get(3)).destinationRow;
            int destinationColumn = ((TunnelCell) availableCells.get(3)).destinationColumn;

            double cellProbability = mapProbabilities[destinationRow][destinationColumn] + probability;
            mapProbabilities[destinationRow][destinationColumn] = cellProbability;
        }

        return mapProbabilities;
    }


    public static List<List<Double>> generateProbabilityMap(int startRow, int startColumn, int numberOfRows, int numberOfColumns) {
        List<List<Double>> mapProbabilities = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < numberOfRows; ++rowIndex) {
            List<Double> rowProbabilities = new ArrayList<>(Collections.nCopies(numberOfColumns, 0.0));

            if (rowIndex == startRow) {
                rowProbabilities.set(startColumn, 1.0);
            }

            mapProbabilities.add(rowProbabilities);
        }

        return mapProbabilities;
    }

    public static double[][] generateProbabilityMapArray(int startRow, int startColumn, int numberOfRows, int numberOfColumns) {
        double[][] mapProbabilities = new double[numberOfRows][numberOfColumns];

        for (int rowIndex = 0; rowIndex < numberOfRows; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < numberOfColumns; ++columnIndex) {
                mapProbabilities[rowIndex][columnIndex] = 0.0;
            }
        }

        mapProbabilities[startRow][startColumn] = 1.0;

        return mapProbabilities;
    }


    public static List<List<Double>> resetProbabilityMap(List<List<Double>> mapProbability) {
        for (List<Double> row : mapProbability) {
            for (int columnIndex = 0; columnIndex < row.size(); ++columnIndex) {
                row.set(columnIndex, 0.0);
            }
        }

        return mapProbability;
    }

    public static double[][] resetProbabilityMapArray(double[][] mapProbability) {
        for (int rowIndex = 0; rowIndex < mapProbability.length; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < mapProbability[rowIndex].length; ++columnIndex) {
                mapProbability[rowIndex][columnIndex] = 0.0;
            }
        }

        return mapProbability;
    }

    public static List<List<ICell>> applyTunnelsToMap(List<List<ICell>> map, List<Tunnel> tunnelList) {
        for (Tunnel tunnel : tunnelList) {
            int rowA = tunnel.rowA;
            int columnA = tunnel.columnA;
            int rowB = tunnel.rowB;
            int columnB = tunnel.columnB;

            convertToTunnelOrExitCell(map, rowA, columnA, rowB, columnB);
//            convertToTunnelOrExitCell(map, rowB, columnB, rowA, columnA);

//            ICell newCellA = null;
//            ICell newCellB = null;
//
//            if (availableCellsA.stream().filter(e -> e != null).collect(Collectors.toList()).isEmpty()) {
//                newCellA = new ExitCell();
//            } else {
//                newCellA = new TunnelCell(rowB, columnB);
//            }
//
//            List<ICell> rowARow = map.get(rowA);
//            rowARow.set(columnA, newCellA);
//
//            if (availableCellsA.stream().filter(e -> e != null).collect(Collectors.toList()).isEmpty()) {
//                newCellA = new ExitCell();
//            } else {
//                newCellA = new TunnelCell(rowB, columnB);
//            }
//
//            TunnelCell tunnelCellB = new TunnelCell(rowA, columnA);
//            List<ICell> rowBRow = map.get(rowB);
//            rowBRow.set(columnB, tunnelCellB);
        }

        return map;
    }

    public static List<ICell> generateAvailableCells(List<List<ICell>> map, int startRow, int startColumn, List<ICell> availableCells) {
        int rowSize = map.get(0).size();
        int columnSize = map.size();

//        List<ICell> availableCells = new ArrayList<>();
        availableCells.clear();

        if (startRow > 0) {
            ICell aboveCell = map.get(startRow - 1).get(startColumn);
            availableCells.add(aboveCell.canHoldFrog() ? aboveCell : null);
        } else {
            availableCells.add(null);
        }

        if (startRow < columnSize - 1) {
            ICell belowCell = map.get(startRow + 1).get(startColumn);
            availableCells.add(belowCell.canHoldFrog() ? belowCell : null);
        } else {
            availableCells.add(null);
        }

        if (startColumn > 0) {
            ICell leftCell = map.get(startRow).get(startColumn - 1);
            availableCells.add(leftCell.canHoldFrog() ? leftCell : null);
        } else {
            availableCells.add(null);
        }

        if (startColumn < rowSize - 1) {
            ICell rightCell = map.get(startRow).get(startColumn + 1);
            availableCells.add(rightCell.canHoldFrog() ? rightCell : null);
        } else {
            availableCells.add(null);
        }

        return availableCells;
    }

//    public static void convertToTunnelOrExitCell(List<List<ICell>> map, int rowA, int columnA, int rowB, int columnB) {
//        List<ICell> availableCellsA = new ArrayList<>();
//        availableCellsA = generateAvailableCells(map, rowB, columnB, availableCellsA);
//
//        ICell newCellA = map.get(rowA).get(columnA);
//        ICell destinationCell = map.get(rowB).get(columnB);
//
//        if (availableCellsA.stream().allMatch(Objects::isNull) && !destinationCell.isAliveExitCell()) {
//            newCellA = new MineCell();
//
//            map.get(rowA).set(columnA, newCellA);
//        } else  {
//            newCellA = new TunnelCell(rowB, columnB, destinationCell);
//
//            if (!destinationCell.isAliveExitCell() && !destinationCell.isKillsFrog()) {
//                destinationCell = new TunnelCell(rowA, columnA, newCellA);
//                ((TunnelCell) newCellA).destinationCell = destinationCell;
//            }
//
//            if (newCellA == null || destinationCell == null) {
//                throw new RuntimeException("newCellA or destinationCell is null, newCellA = " + newCellA + " destinationCell = " + destinationCell);
//            }
//
//            List<ICell> rowARow = map.get(rowA);
//            rowARow.set(columnA, newCellA);
//            map.get(rowB).set(columnB, destinationCell);
//        }
//
//    }

    public static void convertToTunnelOrExitCell(List<List<ICell>> map, int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
//        List<ICell> availableCellsDestion = new ArrayList<>();
//        availableCellsDestion = generateAvailableCells(map, destinationRow, destinationColumn, availableCellsDestion);

        TunnelCell tunnelSource = new TunnelCell(destinationRow, destinationColumn, null);
        TunnelCell tunnelDestination = new TunnelCell(sourceRow, sourceColumn, tunnelSource);
        tunnelSource.destinationCell = tunnelDestination;

        map.get(sourceRow).set(sourceColumn, tunnelSource);
        map.get(destinationRow).set(destinationColumn, tunnelDestination);
    }
}
