package domain;

public class SpaceShip {

    public void startEngine() {
        System.out.println("Мотор жужжит, property isEngineStart: " + true);
    }

    public void depart(Location location) {
        System.out.println("Корабль отправляется в " + location.getName());
    }
}
