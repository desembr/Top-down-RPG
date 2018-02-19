package client; 

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.Enemy;
import server.Item;
import server.Player;
import server.Room;

/**
 * This class implements a simple graphical user interface with a text entry
 * area, a text output area and an optional image.
 * 
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class UserInterface implements ActionListener, Observer
{
	private static final int WIDTH = 800, HEIGHT = 660;
	
	private Client client;
    private JFrame myFrame;
    private JTextField entryField;
    private JTextArea log;
    private JLabel image, playerSprite, monster1, monster2, monster3, monster4, monster5, shadow1, shadow2, shadow3, shadow4, shadow5, shadow6; 
    
    private ArrayList<JLabel> monsterSprites; 
    private ArrayList<JLabel> shadows; 
    
    /**
     * Construct a UserInterface. As a parameter, a Client object, with will handle
     * all the communication with the server (sending commands and receiving responses). 
     * @param client  The Client to handle all communication with server.
     */
    public UserInterface(Client client)
    {
    	monsterSprites = new ArrayList<>(); 
    	shadows = new ArrayList<>(); 
    	
    	this.client = client;
    	
        createGUI();
        
        printWelcome();
    }

    /**
     * Print out some text into the text area.
     * @param text The text to print to the text area.
     */
    public void print(String text)
    {
        log.append(text);
        log.setCaretPosition(log.getDocument().getLength());
    }

    /**
     * Print out some text into the text area, followed by a line break.
     * @param text The text to print to the text area.
     */
    public void println(String text)
    {
        log.append(text + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    /**
     * Show an image file in the interface.
     * @param imageName The filePath of the image to display.
     */
    public void showImage(String imageName)
    {
    	try {
	        URL imageURL = this.getClass().getClassLoader().getResource(imageName);
	        
	        //System.out.println(imageURL); 
	        
	        if(imageURL == null)
	        {
	            System.out.println("image not found"); // debug
	        
	            System.out.println("Working Directory = " + System.getProperty("user.dir")); // debug
	        }
	        else {
	            ImageIcon icon = new ImageIcon(imageURL);
	            image.setIcon(icon);
	            myFrame.pack();
	        }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Show an image file on the other imageFile.
     * @param imageName The filePath of the image to display.
     */
    public void showPlayer(String imageName)
    {
        URL imageURL = this.getClass().getClassLoader().getResource(imageName);
        if(imageURL == null)
            System.out.println("image not found");
        else {
            ImageIcon icon = new ImageIcon(imageURL);
            playerSprite.setIcon(icon);
            myFrame.pack();
        }
    }
    
    /**
     * Shows the enemies present in the room
     * @param enemies, a List of enemies
     */
    
    public void showEnemies(List<Enemy> enemies)
    {
    	for (int i = 0; i < enemies.size(); i++)
    	{
    	
	    	URL imageURL = this.getClass().getClassLoader().getResource(enemies.get(i).getIconFilePath() );
	        if(imageURL == null)
	            System.out.println("image not found");
	        else {
	            ImageIcon icon = new ImageIcon(imageURL);
	            monsterSprites.get(i).setIcon(icon);
	            myFrame.pack();
	        }
        
    	}
    }
    
    /**
     * Just a method to draw the numbe4r of shadows that should be present in a room
     * based on how many enemies there are
     * @param enemies, a List of enemies
     */
    
    public void showShadows(List<Enemy> enemies)
    {
    	for (int i = 0; i < enemies.size() + 1 ; i++) //TILLFÄLLIG, MÅSTE SNYGGAS UPP
    	{
    	
	    	URL imageURL = this.getClass().getClassLoader().getResource("res/misc/shadow3.png");
	        if(imageURL == null)
	            System.out.println("image not found");
	        else {
	            ImageIcon icon = new ImageIcon(imageURL);
	            shadows.get(i).setIcon(icon);
	            myFrame.pack();
	        }
        
    	}
    	
    }
    
    /**
     * Enable or disable input in the input field.
     */
    public void enable(boolean on)
    {
        entryField.setEditable(on);
        if(!on)
            entryField.getCaret().setBlinkRate(0);
    }

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        myFrame = new JFrame("TOPDOWNRPG");
        
        entryField = new JTextField(34);

        log = new JTextArea();
        log.setEditable(false);
        JScrollPane listScroller = new JScrollPane(log);
        listScroller.setPreferredSize(new Dimension(200, 200));
        listScroller.setMinimumSize(new Dimension(100,100));

        JPanel panel = new JPanel();
        image = new JLabel();
        
        playerSprite = new JLabel();
        playerSprite.setBounds(365,400, 64, 64); // position, size
        
        monster1 = new JLabel(); 
        monster2 = new JLabel();
        monster3 = new JLabel();
        monster4 = new JLabel();
        monster5 = new JLabel();
        
        monster1.setBounds(165,  100,  64,  64);
        monster2.setBounds(265,  100,  64,  64);
        monster3.setBounds(365,  100,  64,  64);
        monster4.setBounds(465,  100,  64,  64);
        monster5.setBounds(565,  100,  64,  64);
        
        monsterSprites.add(monster1); 
    	monsterSprites.add(monster2); 
    	monsterSprites.add(monster3); 
    	monsterSprites.add(monster4); 
    	monsterSprites.add(monster5); 
    	
    	shadow1 = new JLabel(); 
    	shadow2 = new JLabel(); 
    	shadow3 = new JLabel(); 
    	shadow4 = new JLabel(); 
    	shadow5 = new JLabel(); 
    	shadow6 = new JLabel(); 
    	
    	shadows.add(shadow1); 
    	shadows.add(shadow2); 
    	shadows.add(shadow3); 
    	shadows.add(shadow4); 
    	shadows.add(shadow5); 
    	shadows.add(shadow6); 
    	
    	shadow1.setBounds(165,  128,  64,  64);
    	shadow2.setBounds(265,  128,  64,  64);
    	shadow3.setBounds(365,  128,  64,  64);
    	shadow4.setBounds(465,  128,  64,  64);
    	shadow5.setBounds(565,  128,  64,  64);
    	shadow6.setBounds(365,  428,  64,  64);
    	
        panel.setLayout(new BorderLayout());
        panel.add(image, BorderLayout.NORTH);
        
        image.add(playerSprite, BorderLayout.SOUTH);
        
        image.add(monster1, BorderLayout.SOUTH); 
        image.add(monster2, BorderLayout.SOUTH); 
        image.add(monster3, BorderLayout.SOUTH); 
        image.add(monster4, BorderLayout.SOUTH); 
        image.add(monster5, BorderLayout.SOUTH); 
        
        image.add(shadow1, BorderLayout.SOUTH); 
        image.add(shadow2, BorderLayout.SOUTH); 
        image.add(shadow3, BorderLayout.SOUTH); 
        image.add(shadow4, BorderLayout.SOUTH); 
        image.add(shadow5, BorderLayout.SOUTH); 
        image.add(shadow6, BorderLayout.SOUTH); 
        
        panel.add(listScroller, BorderLayout.CENTER);
        panel.add(entryField, BorderLayout.SOUTH);
        
        myFrame.getContentPane().add(panel, BorderLayout.CENTER);

        // add some event listeners to some components
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        entryField.addActionListener(this);
        
        
        try {
			BufferedImage icon = ImageIO.read(UserInterface.class.getClassLoader().
					getResourceAsStream(("res/icon.png")));
			myFrame.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        myFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        myFrame.setLocation((int)d.getWidth() / 2 - WIDTH / 2, (int)d.getHeight() / 2 - HEIGHT / 2);
        myFrame.pack();
        entryField.requestFocus();
        myFrame.setVisible(true);
    }

    /**
     * Listener method for the textField (the input field).
     * @param e The Event that triggered this listener.
     */
    public void actionPerformed(ActionEvent e) 
    {
    	log.setText("");
        processCommand();
    }

    /**
     * A command has been entered. Read the command and do whatever is 
     * necessary to process it.
     */
    private void processCommand()
    {
        String input = entryField.getText();
        entryField.setText("");

        if (input.length() > 0) {
        	println(input);
        	client.pollCommand(input);
        }
    }
    
    /**
     * Show the images for all items in this
     * client's player object's inventory
     * @param p The player whose inventory to display.
     */
    private void showInventory(Player p)
    {
    	List<Item> inventory = p.getItems();
    	println("Items in inventory: ");
    	if (inventory.size() > 0) {
    		for (Item i : inventory) {
        		print(i.getName() + " ");
        	}
    	}
    	else
    		print("None");
    	println(" ");
    	
    	println("Score: " + p.getScore());
    	if (p.getCmdReturnMsg() != null)
    		println(p.getCmdReturnMsg());
    }
    
    /**
     * Show the images for all objects contained
     * in this client's player object's current room.
     * @param currentRoom This client's player object's current room.
     */
    private void showRoom(Room currentRoom)
    {
    	showImage(currentRoom.getImage());
    	println(currentRoom.getLongDescription());
    }
    
    /**
     * Prints a short welcome message, with short description of the purpose of the game.
     */
    private void printWelcome()
    {
    	println("Hello adventurer, your task is to \nkill all monsters in all rooms.\n"
    			+ "Enter 'help' if you need some help.\nGoodluck!");
    }
    

    /**
     * Exits the Client program after acknowledged exit command.
     */
    private void exitGame() {
    	println("Goodbye...");
    	client.exit();
    	System.exit(0);
    }
    
    /**
     * Gets called by the client object (observable) on response from server. Updates the view.
     * Displays all changes to this client's player object's state (items, room, players in that room etc).
     * @param o The Observable object triggering this method.
     * @param player The updated Player object received from the server to the Client object.
     */
    public void update(Observable o, Object player) {
    	Player p = (Player)player;
    	
    	if (p.getIsDead())
    		exitGame();
    	
		showRoom(p.getRoom());
		
		showPlayer(p.getIconFilePath() ); 
		
		if(p.getRoom().getEnemies().size() > 0 )
		{
			showEnemies(p.getRoom().getEnemies() );
			showShadows(p.getRoom().getEnemies() );
		}
		
		showInventory(p);
		
		//System.out.println( p.getRoom().getEnemies().size() ); 
		//System.out.println( p.getRoom().getEnemies().get(0).getIsDead() ); 
    }
}
