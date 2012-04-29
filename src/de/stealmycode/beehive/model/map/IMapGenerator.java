/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.model.map;

import de.stealmycode.beehive.model.world.World;

/**
 *
 * @author fate
 */
public interface IMapGenerator {
    public Map generate(World world);
}
