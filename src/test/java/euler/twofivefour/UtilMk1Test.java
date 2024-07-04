package euler.twofivefour;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UtilMk1Test {
    @Test
    void testDecompose1() {
        List<Long> digits = UtilMk1.decomposeToDigits(1);

        assertThat(digits).hasSize(1);
        assertThat(digits).contains(1L);
    }

    @Test
    void testDecompose2() {
        List<Long> digits = UtilMk1.decomposeToDigits(2);

        assertThat(digits).hasSize(1);
        assertThat(digits).contains(2L);
    }

    @Test
    void testDecompose10() {
        List<Long> digits = UtilMk1.decomposeToDigits(10);

        assertThat(digits).hasSize(2);
        assertThat(digits).contains(0L, 1L);
    }

    @Test
    void testDecompose101() {
        List<Long> digits = UtilMk1.decomposeToDigits(101);

        assertThat(digits).hasSize(3);
        assertThat(digits).contains(1L, 0L, 1L);
    }

    @Test
    void testDecompose222322() {
        List<Long> digits = UtilMk1.decomposeToDigits(222322);

        assertThat(digits).hasSize(6);
        assertThat(digits).contains(2L, 2L, 3L, 2L, 2L, 2L);
    }

    @Test
    void recompose1() {
        List<Long> digits = new ArrayList<>(List.of(1L));

        long value = UtilMk1.recomposeReverseDigits(digits);

        assertThat(value).isEqualTo(1L);
    }

    @Test
    void recompose21() {
        List<Long> digits = new ArrayList<>(Arrays.asList(1L, 2L));

        long value = UtilMk1.recomposeReverseDigits(digits);

        assertThat(value).isEqualTo(21L);
    }

    @Test
    void recompose921() {
        List<Long> digits = new ArrayList<>(Arrays.asList(1L, 2L, 9L));

        long value = UtilMk1.recomposeReverseDigits(digits);

        assertThat(value).isEqualTo(921L);
    }

}