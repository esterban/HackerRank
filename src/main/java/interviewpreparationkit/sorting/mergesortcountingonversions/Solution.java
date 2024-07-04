//package interviewpreparationkit.sorting.mergesortcountingonversions;
//
//import org.openjdk.jmh.annotations.Benchmark;
//import org.openjdk.jmh.runner.RunnerException;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.IntStream;
//import java.util.stream.Stream;
//
//import static java.util.stream.Collectors.toList;
//
//class Result {
//    public static long countInversions(int[] arr) {
//        long numberOfSwaps = 0l;
////        int startMoveIndex = -1;
//
//        int arrayLength = arr.length;
//
//        for (int index = 1; index < arrayLength; ++index) {
//            int offset = 0;
//            int currentValue = arr[index];
//            int previousValue = arr[index - 1];
//
//            if (previousValue > currentValue) {
//                while (previousValue > currentValue) {
//                    arr[index - offset - 1] = currentValue;
//                    arr[index - offset] = previousValue;
//
//                    ++numberOfSwaps;
//
//                    if (index - offset > 1) {
//                        ++offset;
//                    }
//
//                    previousValue = arr[index - offset - 1];
//                }
//            }
//        }
//
//        return numberOfSwaps;
//    }
//}
//
//public class Solution {
//    public static void main(String[] args) throws IOException, InterruptedException, RunnerException {
////        org.openjdk.jmh.Main.main(args);
//
//        Solution solution = new Solution();
//        solution.runner();
//    }
//
//    //    @Benchmark
//    public void runner() throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
////        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
////                new FileInputStream("C:\\Users\\steph\\IdeaProjects\\HackerRank\\src\\main\\resources\\interviewpreparationkit\\sorting\\mergesortcountinginversions\\input4.txt")));
////        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
////                new FileInputStream("C:\\Users\\steph\\IdeaProjects\\HackerRank\\src\\main\\resources\\interviewpreparationkit\\sorting\\mergesortcountinginversions\\input0.txt")));
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
//
//        int t = Integer.parseInt(bufferedReader.readLine().trim());
//                int numberOfPerformanceSamples = 6;
////        int numberOfPerformanceSamples = 1;
////        int numberOfPerformanceSamples = 2;
//
//        Map<Integer, List<Integer>> inputValues = new HashMap<>();
//
//        List<List<Double>> results = new ArrayList<>();
//
//        IntStream.range(0, numberOfPerformanceSamples).
//                forEach(outItr ->
//                {
//                    IntStream.range(0, t).forEach(tItr -> {
//                        try {
//                            List<Integer> arr;
//                            int[] arrArr;
//
//                            if (inputValues.containsKey(tItr)) {
//                                arr = new ArrayList<>(inputValues.get(tItr));
//                            } else {
//                                int n = Integer.parseInt(bufferedReader.readLine().trim());
//                                arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
//                                        .map(Integer::parseInt)
//                                        .collect(toList());
//
//
//                                inputValues.put(tItr, new ArrayList<>(arr));
//                            }
//
//                            arrArr = arr.stream().mapToInt(Integer::intValue).toArray();
//
//                            long startNanos = System.nanoTime();
//                            long result = Result.countInversions(arrArr);
//                            long endNanos = System.nanoTime();
//
//                            double millisDuration = (endNanos - startNanos) / 1e6;
//
//                            if (results.size() == tItr) {
//                                results.add(new ArrayList<>());
//                            }
//
//                            results.get(tItr).add(millisDuration);
//
////                    bufferedWriter.write("Duration = " + millisDuration + "\n");
////                    System.out.println(millisDuration);
//
//                            bufferedWriter.write(String.valueOf(result) + "\n");
////                    bufferedWriter.newLine();
////
//                            bufferedWriter.flush();
//                        } catch (IOException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    });
//
//                    System.out.println();
//                });
//
//        for (
//                List<Double> row : results) {
//            String rowString = String.join(",", row.stream().map(e -> String.valueOf(e)).collect(toList()));
//
//            System.out.println(rowString);
//        }
//
//        bufferedReader.close();
//        bufferedWriter.close();
//    }
//}
