import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static java.lang.Double.*;
import static org.assertj.core.api.Assertions.assertThat;

class ArcCosFunctionTest {

    private static final double EPS = 0.000000000001;
    private static final double DIFF = 0.05;
    private static final double SECTION_STEP_SIZE = 0.1;
    private static final double SECTION_STEP_COUNT = 10;
    private static final double SECTION_COUNT = 10;
    private static final double START = -100;
    private static final double END = 100;

    @ParameterizedTest
    @ValueSource(doubles = {
            MIN_VALUE,
            1,
            0,
            -1,
            MAX_VALUE,
            NaN,
            -MAX_VALUE
    })
    void testCritical(double value) {
        final var t = new ArcCosFunction();
        final double expected = Math.acos(value);
        final double actual = t.count(EPS, value);

        if (Double.valueOf(expected).isNaN()) {
            assertThat(Double.valueOf(actual).isNaN()).isTrue();
        } else {
            double res = Math.abs(expected - actual);
            assertThat(res).isLessThan(DIFF);
        }
    }

    @Test
    void testWithSections() {
        double pointer = START;
        int stepCount = 0;
        double sectionSize = (END - START) / SECTION_COUNT;
        while (Double.compare(pointer, END) < 0) {
            testCritical(pointer);
            stepCount++;
            if (stepCount == SECTION_STEP_COUNT) {
                pointer += sectionSize;
                stepCount = 0;
                continue;
            }
            pointer += SECTION_STEP_SIZE;
        }
    }

}