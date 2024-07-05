package hackerrank.algorithms.graphtheory.torqueanddevelopment;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'roadsAndLibraries' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER c_lib
     *  3. INTEGER c_road
     *  4. 2D_INTEGER_ARRAY cities
     */

    public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {
        // Write your code here
        AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Integer, Integer>, Map<Integer, Set<Integer>>> cityGroupsByCity = createCityGroupsByCity(cities);

        int connectedCityGroupCount = cityGroupsByCity.getKey().getKey();
        int cityCount = cityGroupsByCity.getKey().getValue();
        Map<Integer, Set<Integer>> cityGroupsByCityMap = cityGroupsByCity.getValue();

        long unconnectedCities = n - cityCount;

//        int roadsToRemove = countRoadsToRemove(cities);
//        int roadsNeededCount = cities.size() - roadsToRemove;

        long roadsNeededCount = numberOfRoadsNeeded(cityGroupsByCityMap);

        long roadCost = roadsNeededCount * c_road;
        long unconnectedCitiesCost = unconnectedCities * c_lib;
        long cityGroupsLibraryCost = connectedCityGroupCount * c_lib;

        long cityOnlyCost = (long)n * (long)c_lib;
        long withRoadsCost = unconnectedCitiesCost + roadCost + cityGroupsLibraryCost;
        long minimumCost = Math.min(cityOnlyCost, withRoadsCost);

        System.out.println("---------------------------------");
        System.out.println("cost per library = " + c_lib);
        System.out.println("Road cost = " + c_road);
        System.out.println("---------------------------------");


        System.out.println("With roads cost = " + withRoadsCost);
        System.out.println("roads original count = " + cities.size());
        System.out.println("roads needed count = " + roadsNeededCount);
        System.out.println("Counted cities groups count = " + connectedCityGroupCount);
        System.out.println("Counted unconnected cities count = " + unconnectedCities);
//        System.out.println("Counted cities groups count = " + connectedCityGroupCount);

        System.out.println("City only cost = " + cityOnlyCost);

//        return unconnectedCitiesCost + roadCost + cityGroupsLibraryCost;
        return minimumCost;
    }

    //    public static AbstractMap.SimpleEntry<Integer, Map<Integer, Set<Integer>>> createCityGroupsByCity(List<List<Integer>> cities) {
    public static AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Integer, Integer>, Map<Integer, Set<Integer>>> createCityGroupsByCity(List<List<Integer>> cities) {
//        List<Set<Integer>> cityGroups = new ArrayList<>();
        Integer cityCount = 0;
        Integer cityGroupCount = 0;
        Map<Integer, Set<Integer>> cityGroupsByCity = new HashMap<>();

        for (List<Integer> city : cities) {
            Integer cityA = city.get(0);
            Integer cityB = city.get(1);

            Set<Integer> cityGroupA = cityGroupsByCity.get(cityA);
            Set<Integer> cityGroupB = cityGroupsByCity.get(cityB);

            if (cityGroupA != null && cityGroupB == null) {
                Set<Integer> cityGroup = cityGroupsByCity.get(cityA);
                cityGroup.add(cityB);
                cityGroupsByCity.put(cityB, cityGroup);

                ++cityCount;
            }
            if (cityGroupB != null && cityGroupA == null) {
                Set<Integer> cityGroup = cityGroupsByCity.get(cityB);
                cityGroup.add(cityA);
                cityGroupsByCity.put(cityA, cityGroup);

                ++cityCount;
            } else if (cityGroupA != null && cityGroupB != null && !cityGroupA.equals(cityGroupB)) {
//                Set<Integer> cityGroupA = cityGroupsByCity.get(cityA);
//                Set<Integer> cityGroupB = cityGroupsByCity.get(cityB);

                cityGroupA.addAll(cityGroupB);

                for (Integer citySet : cityGroupA) {
                    cityGroupsByCity.put(citySet, cityGroupA);
                }

//                cityGroupCount -= cityGroupB.size();
                --cityGroupCount;
            } else if (cityGroupA == null && cityGroupB == null) {
                Set<Integer> newCityGroup = new HashSet<>();
                newCityGroup.add(cityA);
                newCityGroup.add(cityB);
                cityGroupsByCity.put(cityA, newCityGroup);
                cityGroupsByCity.put(cityB, newCityGroup);

                ++cityGroupCount;

                cityCount += 2;
            }
        }

//        return new AbstractMap.SimpleEntry<>(cityCount, cityGroupsByCity);
        return new AbstractMap.SimpleEntry<>(new AbstractMap.SimpleEntry<>(cityGroupCount, cityCount), cityGroupsByCity);
    }

    public static int countRoadsToRemove(List<List<Integer>> roads) {
        List<AbstractMap.SimpleEntry<Integer, Integer>> sortedRoads = sortRoads(roads);

        Set<Integer> leftRoads = new HashSet<>();
        Set<Integer> rightRoads = new HashSet<>();

        for (AbstractMap.SimpleEntry<Integer, Integer> road : sortedRoads) {
            leftRoads.add(road.getValue());
            rightRoads.add(road.getKey());
        }

        leftRoads.retainAll(rightRoads);

        return leftRoads.size();
    }

    public static List<AbstractMap.SimpleEntry<Integer, Integer>> sortRoads(List<List<Integer>> roads) {
        List<AbstractMap.SimpleEntry<Integer, Integer>> sortedRoads = new ArrayList<>();

//        sortedRoads = roads.stream().map(e -> e.get(0) < e.get(1) ?
//                new AbstractMap<>.SimpleEntry<Integer, Integer>(e.get(0), e.get(1)) :
//                new AbstractMap<>.SimpleEntry<Integer, Integer>(e.get(1), e.get(0)))
//                    .collect(Collectors.toList());
        sortedRoads = roads.stream().map(e -> e.get(0) < e.get(1) ?
                        (new AbstractMap.SimpleEntry<Integer, Integer>(e.get(0), e.get(1))) :
                        (new AbstractMap.SimpleEntry<Integer, Integer>(e.get(1), e.get(0))))
                .collect(Collectors.toList());

        return sortedRoads;
    }

    public static int numberOfRoadsNeeded(Map<Integer, Set<Integer>> cityGroupsByCity) {
        int numberOfRoadsNeeded = 0;
        Set<Set<Integer>> cityGroups = new HashSet<>();
        cityGroups.addAll(cityGroupsByCity.values());

        for (Set<Integer> cityGroupCity : cityGroups) {
            numberOfRoadsNeeded += cityGroupCity.size() - 1;
        }

        return numberOfRoadsNeeded;
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

                int c_lib = Integer.parseInt(firstMultipleInput[2]);

                int c_road = Integer.parseInt(firstMultipleInput[3]);

                List<List<Integer>> cities = new ArrayList<>();

                IntStream.range(0, m).forEach(i -> {
                    try {
                        cities.add(
                                Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                        .map(Integer::parseInt)
                                        .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                long result = Result.roadsAndLibraries(n, c_lib, c_road, cities);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
