package domain.characters;

import domain.Dialog;
import domain.events.EventManager;
import domain.events.EventType;
import domain.util.Pair;

import java.util.List;

import static domain.util.Pair.of;

public class Arthur implements Runnable {

    private final Dialog dialog;
    private EventManager eventManager;
    private final List<Pair<String, Runnable>> pairs = List.of(
            of("-- Не знаю, я ведь не слушал.", null),
            of("-- Колонны Нельсона больше нет, Макдональдса нет; все, что осталось, это я и слова \"В основном, безвредная\". Через несколько секунд останется только \"В основном, безвредная\". А ведь только вчера на моей планете все было так хорошо!", () -> System.out.print("-- Артур думает, что это ужасно. "))
    );

    public Arthur(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void run() {
        try {
            for (Pair<String, Runnable> pair : pairs) {
                dialog.waitForTurn(false);
                if (pair.s() != null) {
                    pair.s().run();
                }
                System.out.println(pair.f());
                dialog.switchTurn();
            }
            eventManager.notifyEvent(EventType.DIALOG_END);
        } catch (InterruptedException ignored) {
        }
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
}
