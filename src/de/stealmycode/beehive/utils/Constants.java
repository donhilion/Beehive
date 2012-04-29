/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.utils;

/**
 *
 * @author fate
 */
public interface Constants {
    
    // Constants for difficulty level
    public static final String DIFFICULTY_HARD      = "Hard";
    public static final String DIFFICULTY_MEDIUM    = "Medium";
    public static final String DIFFICULTY_EASY      = "Easy";

    // Constants for Configs
    public static final String CONFIG_GLOBAL                       = "Global";
    public static final String CONFIG_BASE_PROBABILITY             = "BaseProbability";
    public static final String CONFIG_HARD                           = DIFFICULTY_HARD;
    public static final String CONFIG_MEDIUM                         = DIFFICULTY_MEDIUM;
    public static final String CONFIG_EASY                           = DIFFICULTY_EASY;
    public static final String CONFIG_FIELD_PROPERTIES               = "FieldProperties";
    
    public static final String GRAPHICS_IMAGES           = "Images";
    public static final String GRAPHICS_TEXTURES         = "Textures";
    
    // Constants for Paths
    public final static String PATH_GRAPHICS_CONFIG                 = "config/graphics.yml";
    public final static String PATH_CONFIG                          = "config/config.yml";
}
