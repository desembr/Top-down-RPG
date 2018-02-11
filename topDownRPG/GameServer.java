 

//import com.zuul.client.UserInterface;

/**
 *  This class creates the necessary implementation objects and starts the game off.
 * 
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class GameServer extends Thread
{
	private UserInterface gui;
	private GameEngine engine;

    /**
     * Create the game and initialize its internal map.
     */
    public GameServer() 
    {
		engine = new GameEngine();
		gui = new UserInterface(engine);
		engine.setGUI(gui);
    }
    
    public void run() {
    	while (!interrupted()) {
    		
    	}
    }
}
