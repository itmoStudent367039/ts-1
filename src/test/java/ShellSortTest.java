import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

class ShellSortTest {

    ShellSort shellSort;

    @BeforeEach
    public void beforeEach() {
        shellSort = new ShellSort();
    }

    @ParameterizedTest
    @MethodSource("provideArraysForSorting")
    void sort_shouldReturnSorted(
            int[] inputArray,
            int expectedOuterLoopIterations,
            int expectedMiddleLoopIterations,
            int expectedInnerLoopIterations,
            int expectedShiftsCount,
            int[] expectedSortedArray
    ) {
        shellSort.sort(inputArray);

        assertEquals(expectedOuterLoopIterations, shellSort.getOuterLoopIterations());
        assertEquals(expectedMiddleLoopIterations, shellSort.getMiddleLoopIterations());
        assertEquals(expectedInnerLoopIterations, shellSort.getInnerLoopIterations());
        assertEquals(expectedShiftsCount, shellSort.getShiftsCount());
        assertThat(inputArray).containsExactly(expectedSortedArray);
    }

    private static Stream<Arguments> provideArraysForSorting() {
        return Stream.of(
                of(new int[]{1, 2, 3, 4, 5}, 2, 7, 0, 0, new int[]{1, 2, 3, 4, 5}),
                of(new int[]{5, 4, 3, 2, 1}, 2, 7, 4, 4, new int[]{1, 2, 3, 4, 5}),
                of(new int[]{9, 1, 6, 3, 8, 2}, 2, 8, 7, 7, new int[]{1, 2, 3, 6, 8, 9}),
                of(new int[]{9, 1, 6, 3, 8, 1}, 2, 8, 7, 7, new int[]{1, 1, 3, 6, 8, 9}),
                of(new int[]{52}, 0, 0, 0, 0, new int[]{52}),
                of(new int[]{}, 0, 0, 0, 0, new int[]{})
        );
    }
}
