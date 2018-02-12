package server; 

public class Orc extends Enemy {
	private static final long serialVersionUID = 1L;
	private static int damage = 10, defence = 15, num = 0;
	
	private static String name = "Orc", iconFilePath = "sword.png";
	
    public Orc(){
        super(name + (++num), damage, defence, iconFilePath);
    }
}