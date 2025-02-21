package domain;

import domain.events.EndDialogEvent;
import domain.events.StartDialogEvent;
import extension.TestLoggerExtension;
import extension.Watch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(TestLoggerExtension.class)
class DialogTest {

    @InjectMocks
    @Watch
    Dialog dialog;

    @Mock
    Lock mockLock;

    @Mock
    Condition mockFordCondition;

    @Mock
    Condition mockArthurCondition;

    @Mock
    Location location;

    @Mock
    SpaceShip ship;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockLock.newCondition()).thenReturn(mockFordCondition, mockArthurCondition);
    }

    @Test
    void testWaitForTurn_fordTurn() throws Exception {
        dialog.waitForTurn(true);

        var field = Dialog.class
                .getDeclaredField("isFordsTurn");
        field.setAccessible(true);

        assertThat(field.getBoolean(dialog)).isTrue();
        verify(mockLock, times(1)).lock();
        verify(mockLock, times(1)).unlock();
        verifyNoInteractions(mockFordCondition, mockArthurCondition);
    }

    @Test
    void testWaitForTurn_notFordTurn() throws Exception {
        var artur = new Thread(() -> {
            try {
                dialog.waitForTurn(false);
                dialog.switchTurn();
                new EndDialogEvent(ship, location, dialog).process();
            } catch (InterruptedException ignored) {

            }
        });
        var ford = new Thread(() -> {
            try {
                dialog.waitForTurn(true);
                dialog.switchTurn();
            } catch (InterruptedException ignored) {

            }
        });
        dialog.setFirst(artur);
        dialog.setSecond(ford);
        new StartDialogEvent(dialog).process();

        artur.join();
        ford.join();

        var field = Dialog.class
                .getDeclaredField("isFordsTurn");
        field.setAccessible(true);
        assertThat(field.getBoolean(dialog)).isTrue();
    }

    @Test
    void testWaitForTurn_notFordTurnWait() throws Exception {
        var artur = new Thread(() -> {
            try {
                dialog.waitForTurn(false);
                dialog.switchTurn();
                dialog.waitForTurn(false);
                dialog.switchTurn();
                new EndDialogEvent(ship, location, dialog).process();
            } catch (InterruptedException ignored) {

            }
        });
        var ford = new Thread(() -> {
            try {
                dialog.waitForTurn(true);
                dialog.switchTurn();
                dialog.waitForTurn(true);
                dialog.switchTurn();
            } catch (InterruptedException ignored) {

            }
        });
        dialog.setFirst(artur);
        dialog.setSecond(ford);
        new StartDialogEvent(dialog).process();
        artur.join();
        ford.join();

        var field = Dialog.class
                .getDeclaredField("isFordsTurn");
        field.setAccessible(true);
        assertThat(field.getBoolean(dialog)).isTrue();
    }

    @Test
    void testSwitchTurn() {
        dialog.switchTurn();

        verify(mockLock, times(1)).lock();
        verify(mockLock, times(1)).unlock();
    }
}
