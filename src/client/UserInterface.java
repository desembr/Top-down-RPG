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
	private static final int WIDTH = 640, HEIGHT = 480;
	
	private Client client;
    private JFrame myFrame;
    private JTextField entryField;
    private JTextArea log;
    private JLabel image, playerSprite;
    
    /**
     * Construct a UserInterface. As a parameter, a Client object, with will handle
     * all the communication with the server (sending commands and receiving responses). 
     * @param client  The Client to handle all communication with server.
     */
    public UserInterface(Client client)
    {
    	this.client = client;
        createGUI();
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
	        if(imageURL == null)
	            System.out.println("image not found");
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
        playerSprite.setBounds(365,400, 64, 64);

        panel.setLayout(new BorderLayout());
        panel.add(image, BorderLayout.NORTH);
        
        image.add(playerSprite, BorderLayout.SOUTH);
        
        panel.add(listScroller, BorderLayout.CENTER);
        panel.add(entryField, BorderLayout.SOUTH);
        
        myFrame.getContentPane().add(panel, BorderLayout.CENTER);

        // add some event listeners to some components
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        entryField.addActionListener(this);
        
        // change the icon of the window
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
        	System.out.println("Command entered: " + input);
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
    	for (Item i : inventory) {
    		print(i.getName() + " ");
    	}
    	println(" ");
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
     * Exits the Client program.
     */
    private void exitGame() {
    	println("Goodbye...");
    	System.exit(0);
    }
    
    /**
     * Gets called by the client object (observable) on response from server. Updates the view.
     * Displays all changes to this client's player object's state (items, room, players in that room etc).
     * @param o The Observable object triggering this method.
     * @param player The updated Player object received from the server to the Client object.
     */
    public void update(Observable o, Object player) {
    	System.out.println("UserInterface is getting updated with new game state");
    	Player p = (Player)player;
    	if (p.getIsDead())
    		exitGame();
		showRoom(p.getRoom());
		showInventory(p);
    }
}
