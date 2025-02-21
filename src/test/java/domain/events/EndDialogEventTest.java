package domain.events;

import domain.Dialog;
import domain.Location;
import domain.SpaceShip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EndDialogEventTest {

    @InjectMocks
    EndDialogEvent endDialogEvent;

    @Mock
    SpaceShip spaceShip;

    @Mock
    Location destination;

    @Mock
    Dialog dialog;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void endNotStartedDialogThrowsRuntimeException() {
        when(dialog.isDialogStart()).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> endDialogEvent.process());
    }

    @Test
    void endStartedDialogShipDepartToSpace() {
        when(dialog.isDialogStart()).thenReturn(true);
        endDialogEvent.process();
        verify(spaceShip).startEngine();
        verify(spaceShip).depart(destination);
    }
}
