package domain;

import domain.characters.Arthur;
import domain.characters.Ford;
import domain.events.EndDialogEvent;
import domain.events.Event;
import domain.events.EventManager;
import domain.events.EventType;
import domain.events.StartDialogEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static domain.events.EventType.DIALOG_END;
import static domain.events.EventType.DIALOG_START;

public class Main {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        var dialog = new Dialog(lock, lock.newCondition(), lock.newCondition());
        Arthur a = new Arthur(dialog);
        var arthur = new Thread(a);
        var ford = new Thread(new Ford(dialog));
        dialog.setFirst(ford);
        dialog.setSecond(arthur);
        var startEvent = new StartDialogEvent(dialog);
        var destination = new Location("Открытый космос");
        var spaceShip = new SpaceShip();
        Map<EventType, Event> eventMap = new ConcurrentHashMap<>() {{
            put(DIALOG_START, startEvent);
            put(DIALOG_END, new EndDialogEvent(spaceShip, destination, dialog));
        }};
        var eventManager = new EventManager(eventMap);
        a.setEventManager(eventManager);
        eventManager.notifyEvent(DIALOG_START);
    }
}
