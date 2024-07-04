package interviewpreparationkit.dictionariesandmaps;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    // Complete the freqQuery function below.
    static List<Integer> freqQuery(List<List<Integer>> queries) {
        List<Integer> results = new ArrayList<>();

        Map<Integer, Integer> valueFrequencies = new HashMap<>();
        Map<Integer, Integer> frequencyCounts = new HashMap<>();

        for (List<Integer> query : queries) {
            Integer command = query.get(0);
            Integer value = query.get(1);

            switch (command) {
                case (1) :
                {
                    if (value == 24) {
//                        System.out.println("Adding 24");
                    }

                    valueFrequencies.putIfAbsent(value, 0);

                    Integer oldFrequencyCount = valueFrequencies.get(value);
                    Integer newFrequencyCount = oldFrequencyCount + 1;

                    valueFrequencies.put(value, newFrequencyCount);

                    frequencyCounts.compute(newFrequencyCount, (k, v) -> v == null ? 1 : v + 1);
                    frequencyCounts.computeIfPresent(oldFrequencyCount, (k, v) -> k == 0 || v - 1 == 0 ? null : v - 1);
                } break;

                case (2) :
                {
                    if (value == 24 || value == 7) {
//                        System.out.println("Removing 24");
                    }

                    Integer frequency = valueFrequencies.get(value);

                    if (frequency != null) {
                        valueFrequencies.compute(value, (k, v) -> v - 1 == 0 ? null : v - 1);
                        frequencyCounts.compute(frequency, (k, v) -> v - 1 == 0 ? null : v - 1);

                        Integer newFrequency = frequency - 1;

                        if (newFrequency != 0) {
                            frequencyCounts.compute(newFrequency, (k, v) -> v == null ? 1 : v + 1);
                        }
                    }

//                    Integer frequency = valueFrequencies.get(value);
//
//                    if (frequency != null) {
//                        if (frequency == 1) {
//                            valueFrequencies.remove(value);
//                        } else if (frequency > 1) {
//                            valueFrequencies.put(value, valueFrequencies.get(value) - 1);
//                        }
//                    }
                } break;

                case (3) :
                {
                    if (frequencyCounts.containsKey(value)) {
                        results.add(1);
                    } else {
                        results.add(0);
                    }
                } break;
            }

            int sumOfFrequencies = frequencyCounts.values().stream().reduce(0, Integer::sum);

            if (sumOfFrequencies != valueFrequencies.size()) {
                throw new RuntimeException("Eeeeeeek, sumOfFrequencies = " + sumOfFrequencies + " number of values = " + valueFrequencies.size());
            }
        }


        return results;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, q).forEach(i -> {
            try {
                queries.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> ans = freqQuery(queries);

        bufferedWriter.write(
                ans.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
