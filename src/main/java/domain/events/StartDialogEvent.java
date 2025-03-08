package domain.events;

import domain.Dialog;

public class StartDialogEvent implements Event {

    private final Dialog dialog;

    public StartDialogEvent(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void process() {
        dialog.setDialogStart();
        dialog.start();
    }
}
