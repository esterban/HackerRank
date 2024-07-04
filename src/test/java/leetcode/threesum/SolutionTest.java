package leetcode.threesum;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class SolutionTest {
    @Test
    public void testA() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("leetcode/threesumclosest/input01.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        int nums[] = getInputFromStream(bufferedReader);

        System.out.println(nums[0]);

    }

    private int[] getInputFromStream(BufferedReader bufferedReader) {
        List<Integer> inputList = new ArrayList<>();

        bufferedReader.lines().forEach(
                l -> {
                    Integer lineInt = Integer.valueOf(l);
                    inputList.add(lineInt);
                }
        );

        return inputList.stream().mapToInt(i -> i).toArray();
    }

}