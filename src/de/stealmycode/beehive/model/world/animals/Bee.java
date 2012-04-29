package de.stealmycode.beehive.model.world.animals;

import de.stealmycode.beehive.utils.Position;

public class Bee extends AbstractMovableObject {

    public Bee(Position position) {
        super(position);
    }

    @Override
    public int getAttack() {
        return 1;
    }

    @Override
    public int getDefense() {
        return 1;
    }

    @Override
    public int getHealth() {
        return 1;
    }

    @Override
    public int getImageID() {
        return 37;
    }

    @Override
    public String getAccessibleProperty() {
        return "flyable";
    }
}
