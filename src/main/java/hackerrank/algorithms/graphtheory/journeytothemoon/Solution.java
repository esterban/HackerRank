package hackerrank.algorithms.graphtheory.journeytothemoon;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'journeyToMoon' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. 2D_INTEGER_ARRAY astronaut
     */

    public static long journeyToMoon(int n, List<List<Integer>> astronaut) {
//        Map<Integer, Set<Integer>> countryIDMap = IntStream.range(0, n).boxed().collect(Collectors.toMap(i -> i, i -> new HashSet<>()));
        Map<Integer, Set<Integer>> countryIDMap = new HashMap<>();
        Map<Integer, Integer> idToCountryMap = new HashMap<>();
        Set<Integer> astronautIdsNotUsed = IntStream.range(0, n).boxed().collect(Collectors.toSet());

        Integer nextCountry = 0;

        for (List<Integer> idPair : astronaut) {
            countryIDMap.putIfAbsent(nextCountry, new HashSet<>());

            if (!idToCountryMap.containsKey(idPair.get(0)) && !idToCountryMap.containsKey(idPair.get(1))) {
                countryIDMap.get(nextCountry).add(idPair.get(0));
                idToCountryMap.put(idPair.get(0), nextCountry);

                countryIDMap.get(nextCountry).add(idPair.get(1));
                idToCountryMap.put(idPair.get(1), nextCountry);

                ++nextCountry;
            } else if (!idToCountryMap.containsKey(idPair.get(0))) {
                Integer countryId = idToCountryMap.get(idPair.get(1));
                idToCountryMap.put(idPair.get(0), countryId);
                countryIDMap.get(countryId).add(idPair.get(0));

//                ++nextCountry;
            } else if (!idToCountryMap.containsKey(idPair.get(1))) {
                Integer countryId = idToCountryMap.get(idPair.get(0));
                idToCountryMap.put(idPair.get(1), countryId);
                countryIDMap.get(countryId).add(idPair.get(1));

//                ++nextCountry;
            } else {
                Integer id0Country = idToCountryMap.get(idPair.get(0));
                Integer id1Country = idToCountryMap.get(idPair.get(1));

                if (!id0Country.equals(id1Country)) {
                    countryIDMap.get(id0Country).addAll(countryIDMap.get(id1Country));

                    updateAstronautToCountryMap(idToCountryMap, countryIDMap.get(id1Country), id0Country);

                    countryIDMap.put(id1Country, new HashSet<>());
                } else {
                    countryIDMap.get(id0Country).add(idPair.get(0));
                    countryIDMap.get(id0Country).add(idPair.get(1));
                }
            }

            astronautIdsNotUsed.remove(idPair.get(0));
            astronautIdsNotUsed.remove(idPair.get(1));

            checkNoDuplicateAstronautIds(countryIDMap);
        }

        countryIDMap.compute(nextCountry, (k, v) -> v == null ? new HashSet<>() : v);

        for (Integer astronautId : astronautIdsNotUsed) {
            countryIDMap.get(nextCountry).add(astronautId);
            idToCountryMap.put(astronautId, nextCountry);

            ++nextCountry;
            countryIDMap.compute(nextCountry, (k, v) -> v == null ? new HashSet<>() : v);
        }

        Set<AbstractMap.SimpleEntry<Integer, Integer>> idPairs = new HashSet<>();

//        for (int index = 0; index < n; ++index) {
//            Set<Integer> firstAstronautIdsSet = countryIDMap.get(index);
//
//            for (int secondCountryId = 1; secondCountryId < n; ++secondCountryId) {
//                if (index == secondCountryId) {
//                    continue;
//                }
//
//                Set<Integer> secondAstronautIdsSet = countryIDMap.get(secondCountryId);
//
//                if (firstAstronautIdsSet != null && secondAstronautIdsSet != null) {
//                    for (Integer firstId : firstAstronautIdsSet) {
//                        for (Integer secondId : secondAstronautIdsSet) {
//                            int key = firstId < secondId ? firstId : secondId;
//                            int value = firstId < secondId ? secondId : firstId;
//
//                            idPairs.add(new AbstractMap.SimpleEntry<>(key, value));
//                        }
//                    }
//                }
//            }
//        }

        List<Integer> sizeList = convertSetsToSizes(countryIDMap.values());

        long totalCombinations = 0;

        for (int firstIndex = 0; firstIndex < sizeList.size(); ++firstIndex) {
            for (int secondIndex = firstIndex + 1; secondIndex < sizeList.size(); ++secondIndex) {
                int combinations = sizeList.get(firstIndex) * sizeList.get(secondIndex);
                totalCombinations += combinations;
            }
        }

        return totalCombinations;
    }

    private static void checkNoDuplicateAstronautIds(Map<Integer, Set<Integer>> countryToAstronautMap) {
        Set<Integer> astronautIdsFound = new HashSet<>();

        for (Map.Entry<Integer, Set<Integer>> countryAstronauts : countryToAstronautMap.entrySet()) {
            int size = countryAstronauts.getValue().size();
            int sizeBefore = astronautIdsFound.size();
            astronautIdsFound.addAll(countryAstronauts.getValue());


            if (sizeBefore + size != astronautIdsFound.size()) {
                throw new RuntimeException("Duplicates found");
            }
        }
    }

    private static void updateAstronautToCountryMap(Map<Integer, Integer> astronautToCountryMap, Set<Integer> astronautsToUpdate, Integer countryId) {
        for (Integer astronaut : astronautsToUpdate) {
            if (!astronautToCountryMap.containsKey(astronaut)) {
                throw new RuntimeException("Trying to update a non-existent astronaut in astronautToCountryMap astronaut id = " + astronaut);
            }

            astronautToCountryMap.put(astronaut, countryId);
        }
    }

    private static List<Integer> convertSetsToSizes(Collection<Set<Integer>> listOfAstronauts) {
        List<Integer> sizeList = new ArrayList<>();

        for (Set<Integer> astronauts : listOfAstronauts) {
            sizeList.add(astronauts.size());
        }

        return sizeList;
    }


}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int p = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> astronaut = new ArrayList<>();

        IntStream.range(0, p).forEach(i -> {
            try {
                astronaut.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        long result = Result.journeyToMoon(n, astronaut);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
