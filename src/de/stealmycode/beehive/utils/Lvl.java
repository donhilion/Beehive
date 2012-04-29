package de.stealmycode.beehive.utils;

public class Lvl extends java.util.logging.Level {
	private static final long serialVersionUID = 1L;
	
	public static final Lvl DEBUG 			= new Lvl("DEBUG",699);
	public static final Lvl INFO			= new Lvl("INFORMATION",799);
	public static final Lvl WARNING 		= new Lvl("WARNING",899);
	public static final Lvl ERROR 			= new Lvl("ERROR",999);
	public static final Lvl NONE            = new Lvl("NONE",Integer.MAX_VALUE);

	
	protected Lvl(String name, int value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}
}
