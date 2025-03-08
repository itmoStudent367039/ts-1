package domain.characters;

import domain.Dialog;

import domain.util.Pair;

import java.util.List;

import static domain.util.Pair.of;

public class Ford implements Runnable {

    private final Dialog dialog;
    private final List<Pair<String, Runnable>> pairs = List.of(
            of("-- А что она тебе говорила?", null),
            of("-- А...", () -> System.out.println("-- Форд замурлыкал дальше"))
    );

    public Ford(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void run() {
        try {
            for (Pair<String, Runnable> pair : pairs) {
                dialog.waitForTurn(true);
                System.out.println(pair.f() + " ");
                if (pair.s() != null) {
                    pair.s().run();
                }
                dialog.switchTurn();
            }
        } catch (InterruptedException ignored) {
        }
    }
}
