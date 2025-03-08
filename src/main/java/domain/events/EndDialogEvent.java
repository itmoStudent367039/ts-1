package domain.events;

import domain.Dialog;
import domain.Location;
import domain.SpaceShip;

public class EndDialogEvent implements Event {

    private final SpaceShip spaceShip;
    private final Location destination;
    private final Dialog dialog;

    public EndDialogEvent(SpaceShip spaceShip, Location destination, Dialog dialog) {
        this.spaceShip = spaceShip;
        this.destination = destination;
        this.dialog = dialog;
    }

    @Override
    public void process() {
        if (!dialog.isDialogStart()) throw new IllegalStateException("Диалог не начался");
        spaceShip.startEngine();
        spaceShip.depart(destination);
    }
}
