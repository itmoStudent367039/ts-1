package domain.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class EventManagerTest {

    @Mock
    Event startDialogEvent;

    @Mock
    Event endDialogEvent;

    @InjectMocks
    EventManager eventManager;

    Map<EventType, Event> eventMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        eventMap = new ConcurrentHashMap<>();
        eventMap.put(EventType.DIALOG_START, startDialogEvent);
        eventMap.put(EventType.DIALOG_END, endDialogEvent);

        eventManager = new EventManager(eventMap);
    }

    @Test
    void notifyEventShouldProcessStartDialogEvent() {
        eventManager.notifyEvent(EventType.DIALOG_START);

        verify(startDialogEvent).process();
        verifyNoInteractions(endDialogEvent);
    }

    @Test
    void notifyEventShouldProcessEndDialogEvent() {
        eventManager.notifyEvent(EventType.DIALOG_END);

        verify(endDialogEvent).process();
        verifyNoInteractions(startDialogEvent);
    }

    @Test
    void notifyEventShouldHandleNullEventType() {
        assertThrows(NullPointerException.class, () -> eventManager.notifyEvent(null));

        verifyNoInteractions(startDialogEvent);
        verifyNoInteractions(endDialogEvent);
    }
}
