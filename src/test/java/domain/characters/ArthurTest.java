package domain.characters;

import domain.Dialog;
import domain.events.EventManager;
import domain.events.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class ArthurTest {

    @InjectMocks
    Arthur arthur;

    @Mock
    Dialog dialog;

    @Mock
    EventManager eventManager;

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
        arthur.setEventManager(eventManager);
    }

    @Test
    void testDialog() throws InterruptedException {
        final String expectedOutput = "-- Не знаю, я ведь не слушал.\n" +
                "-- Артур думает, что это ужасно. -- Колонны Нельсона больше нет, Макдональдса нет; все, что осталось, это я и слова \"В основном, безвредная\". Через несколько секунд останется только \"В основном, безвредная\". А ведь только вчера на моей планете все было так хорошо!\n";
        arthur.run();

        assertEquals(expectedOutput, outputStream.toString());

        verify(dialog, times(2)).waitForTurn(false);
        verify(dialog, times(2)).switchTurn();
        verify(eventManager).notifyEvent(EventType.DIALOG_END);
    }
}
