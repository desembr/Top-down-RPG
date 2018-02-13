package server; 

public class Orc extends Enemy {
	private static final long serialVersionUID = 1L;
	
	private static int num = 0;
	
	/**
	 * Constructor for Orc.
	 */
    public Orc(){
        super("Orc" + (++num), 10, 15, 40, "Orc.png");
    }
}