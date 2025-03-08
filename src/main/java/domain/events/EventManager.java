package domain.events;

import java.util.Map;

public class EventManager {

    private final Map<EventType, Event> eventMap;

    public EventManager(Map<EventType, Event> eventMap) {
        this.eventMap = eventMap;
    }

    public void notifyEvent(EventType event) {
        eventMap.get(event).process();
    }
}
