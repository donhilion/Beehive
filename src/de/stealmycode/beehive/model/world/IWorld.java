/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.model.world;

import de.stealmycode.beehive.model.world.animals.IMovable;
import java.util.List;

/**
 *
 * @author fate
 */
public interface IWorld {
	
    public Field[][] getFields();
    
    public void addMovableObject(IMovable movableObject);
    
    public List<IMovable> getMovables();
}
