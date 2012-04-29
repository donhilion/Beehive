/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.model.world;

import de.stealmycode.beehive.utils.Position;

/**
 *
 * @author fate
 */
public class Hive extends AbstractDrawableObject implements IAttackable {

    public Hive(Position position) {
        super(position);
    }
    
    @Override
    public int getImageID() {
        return 27;
    }

    @Override
    public int getAttack() {
        return 0;
    }

    @Override
    public int getDefense() {
        return 0;
    }

    @Override
    public int getHealth() {
        return 0;
    }
}
