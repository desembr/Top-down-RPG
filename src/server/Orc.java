package server; 

public class Orc extends Enemy {
	private static int num = 0;
	
    public Orc(int damage, int defence, String iconFilePath){
        super("Orc" + (++num), damage, defence, iconFilePath);
    }
}