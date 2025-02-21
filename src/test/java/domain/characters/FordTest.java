package domain.characters;

import domain.Dialog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class FordTest {

    @InjectMocks
    Ford ford;

    @Mock
    Dialog dialog;

    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final PrintStream originalOut = System.out;

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @BeforeEach
    void init() {
        System.setOut(new PrintStream(outputStream));
        openMocks(this);
    }

    @Test
    void testDialog() throws InterruptedException {
        final String expectedOutput = "-- А что она тебе говорила? \n" +
                "-- А... \n" +
                "-- Форд замурлыкал дальше\n";

        ford.run();

        assertThat(expectedOutput).isEqualTo(outputStream.toString());

        verify(dialog, times(2)).waitForTurn(true);
        verify(dialog, times(2)).switchTurn();
    }
}
