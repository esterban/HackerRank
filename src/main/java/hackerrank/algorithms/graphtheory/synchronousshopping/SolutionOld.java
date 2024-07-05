package hackerrank.algorithms.graphtheory.synchronousshopping;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class ResultOld {
    /*
     * Complete the 'shop' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     *  3. STRING_ARRAY centers
     *  4. 2D_INTEGER_ARRAY roads
     */

    private final Map<Integer, Set<Integer>> centreFishesMap = new HashMap<>();
    private final Map<Integer, Set<AbstractMap.SimpleEntry<Integer, Integer>>> centreToRoadsMap = new HashMap<>();
    private int centreCount;
    private int fishCount;


    public static int shop(int n, int k, List<String> centres, List<List<Integer>> roads) {
        // Write your code here
        int totalTimeMinimum;

        ResultOld result = new ResultOld();
        result.centreCount = n;
        result.fishCount = k;

        Integer centreCounter = 1;

        for (String centre : centres) {
            String[] centreFishList = centre.split(" ");

            String[] fishArray = Arrays.copyOfRange(centreFishList, 1, centreFishList.length);

            Set<Integer> fishIds = Arrays.stream(fishArray).map(Integer::valueOf).collect(Collectors.toSet());

            result.centreFishesMap.put(centreCounter, fishIds);
            ++centreCounter;
        }

        for (List<Integer> roadTime : roads) {
            Integer fromCentre = roadTime.get(0);
            Integer toCentre = roadTime.get(1);
            Integer journeyTime = roadTime.get(2);

            result.centreToRoadsMap.putIfAbsent(fromCentre, new HashSet<>());
            result.centreToRoadsMap.putIfAbsent(toCentre, new HashSet<>());

            AbstractMap.SimpleEntry<Integer, Integer> fromCentreRoad = new AbstractMap.SimpleEntry<>(toCentre, journeyTime);
            AbstractMap.SimpleEntry<Integer, Integer> toCentreRoad = new AbstractMap.SimpleEntry<>(fromCentre, journeyTime);

            result.centreToRoadsMap.get(fromCentre).add(fromCentreRoad);
            result.centreToRoadsMap.get(toCentre).add(toCentreRoad);
        }

        Map<Integer, Map<List<Integer>, Integer>> routesToExit = result.findAllRoutesToExit();

        List<AbstractMap.SimpleEntry<List<Integer>, Integer>> routesToExitList = routesToExit.get(1).entrySet().stream().map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue())).collect(Collectors.toList());

        Map<List<Integer>, Set<Integer>> routesToFishSet = result.routesToFishSet(routesToExit, 1);

        List<Set<Integer>> fishSetList = new ArrayList<>(routesToFishSet.values());
        totalTimeMinimum = result.getMinimumJourneyTime(fishSetList, routesToExitList);

        return totalTimeMinimum;
    }

    private int getMinimumJourneyTime(List<Set<Integer>> fishSetList, List<AbstractMap.SimpleEntry<List<Integer>, Integer>> routesToExitList) {
        int minimumJourneyTime = Integer.MAX_VALUE;

        for (int littleCatPath = 0; littleCatPath < routesToExitList.size(); ++littleCatPath) {
            Set<Integer> littleCatFishSet = fishSetList.get(littleCatPath);
            int littleCatJourneyTime = routesToExitList.get(littleCatPath).getValue();

            for (int bigCatPath = 0; bigCatPath < routesToExitList.size(); ++bigCatPath) {
                Set<Integer> bigCatFishSet = new HashSet<>();
                bigCatFishSet.addAll(centreFishesMap.get(1));
                bigCatFishSet.addAll(fishSetList.get(bigCatPath));
                bigCatFishSet.addAll(littleCatFishSet);

                int bigCatJourneyTime = routesToExitList.get(bigCatPath).getValue();

                if (bigCatFishSet.size() == fishCount) {
                    int pathsJourneyTime = Math.max(littleCatJourneyTime, bigCatJourneyTime);

                    if (pathsJourneyTime < minimumJourneyTime) {
                        minimumJourneyTime = pathsJourneyTime;
                    }
                }
            }
        }

        return minimumJourneyTime;
    }

    private Set<Integer> getMissingFishSet(List<Set<Integer>> fishSetList, List<AbstractMap.SimpleEntry<List<Integer>, Integer>> routesToExitList) {
        Set<Integer> missingFishSet = IntStream.range(1, fishCount).boxed().collect(Collectors.toSet());

        for (List<Integer> path : routesToExitList.stream().map(e -> e.getKey()).collect(Collectors.toSet())) {
            Set<Integer> fishSet = path.stream().map(fishSetList::get).flatMap(Set::stream).collect(Collectors.toSet());

            missingFishSet.removeAll(fishSet);
        }

        return missingFishSet;
    }

    private Map<List<Integer>, Set<Integer>> routesToFishSet(Map<Integer, Map<List<Integer>, Integer>> routesToExit, int startCentre) {
        Map<List<Integer>, Set<Integer>> routesToFishSet = new HashMap<>();

        Map<List<Integer>, Integer> routes = routesToExit.get(startCentre);

        for (Map.Entry<List<Integer>, Integer> path : routes.entrySet()) {
            Set<Integer> fishSet = path.getKey().stream().map(e -> centreFishesMap.get(e)).flatMap(Set::stream).collect(Collectors.toSet());

            routesToFishSet.put(path.getKey(), fishSet);
        }

        return routesToFishSet;
    }

    private Map<Integer, Map<List<Integer>, Integer>> findAllRoutesToExit() {
        Map<Integer, Map<List<Integer>, Integer>> routesToExit = new HashMap<>();

        for (int centreId = 1; centreId < centreCount; ++ centreId) {
            Map<List<Integer>, Integer> pathsFound = new HashMap<>();

            Set<Integer> alreadyVisited = new HashSet<>();
            alreadyVisited.add(centreId);

            findCentre(centreId, centreCount, 0, alreadyVisited, new ArrayList<>(), pathsFound);

            routesToExit.put(centreId, pathsFound);
        }

        return routesToExit;
    }

    private boolean findCentre(int currentCentre, int targetCentre, int journeyCost, Set<Integer> alreadyVisited, List<Integer> currentPath, Map<List<Integer>, Integer> pathsFound) {
        if (currentCentre == targetCentre) {
            pathsFound.put(currentPath, journeyCost);
            return true;
        }

        Map<Integer, Integer> roads = centreToRoadsMap.get(currentCentre).stream().filter(c -> !alreadyVisited.contains(c.getKey())).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        for (Map.Entry<Integer, Integer> centreJourneyTime : roads.entrySet()) {
            Integer centre = centreJourneyTime.getKey();
            Integer journeyTime = centreJourneyTime.getValue();

            List<Integer> nextPath = new ArrayList<>(currentPath);
            Set<Integer> nextAlreadyVisited = new HashSet<>(alreadyVisited);
            nextPath.add(centre);
            nextAlreadyVisited.add(centre);

            findCentre(centre, targetCentre, journeyCost + journeyTime, nextAlreadyVisited, nextPath, pathsFound);
        }

        return false;
    }
}

public class SolutionOld {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        int k = Integer.parseInt(firstMultipleInput[2]);

        List<String> centers = IntStream.range(0, n).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        List<List<Integer>> roads = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                roads.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int res = ResultOld.shop(n, k, centers, roads);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
