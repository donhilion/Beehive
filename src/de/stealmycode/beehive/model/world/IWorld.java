/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.model.world;

import java.util.List;

import de.stealmycode.beehive.model.world.animals.Critter;
import de.stealmycode.beehive.model.world.animals.IMovable;

/**
 *
 * @author fate
 */
public interface IWorld {
	
    public Field[][] getFields();
    
    public void addMovableObject(IMovable movableObject);
    
    public List<IMovable> getMovableList();
}
