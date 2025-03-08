package domain.events;

import domain.Dialog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class StartDialogEventTest {

    @Mock
    private Dialog dialog;

    @InjectMocks
    private StartDialogEvent startDialogEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processShouldCallSetDialogStartAndStartOnDialog() {
        startDialogEvent.process();
        verify(dialog).setDialogStart();
        verify(dialog).start();
    }
}
