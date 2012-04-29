package de.stealmycode.beehive.model.world.animals;

import de.stealmycode.beehive.utils.Position;

public class Warrior extends AbstractMovableObject {

    public Warrior(Position position) {
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
