package client; 

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.GameEngine;

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
	
    //private GameEngine engine;
	Client client;
    private JFrame myFrame;
    private JTextField entryField;
    private JTextArea log;
    private JLabel image;
    
    private JLabel playerSprite; // ny

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param gameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface(Client client/*GameEngine gameEngine*/)
    {
        //engine = gameEngine;
    	this.client = client;
        createGUI();
    }

    /**
     * Print out some text into the text area.
     */
    public void print(String text)
    {
        log.append(text);
        log.setCaretPosition(log.getDocument().getLength());
    }

    /**
     * Print out some text into the text area, followed by a line break.
     */
    public void println(String text)
    {
        log.append(text + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    /**
     * Show an image file in the interface.
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
     */
    public void showPlayer(String imageName) // ny 
    {
        URL imageURL = this.getClass().getClassLoader().getResource(imageName);
        if(imageURL == null)
            System.out.println("image not found");
        else {
            ImageIcon icon = new ImageIcon(imageURL);
            playerSprite.setIcon(icon);
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
        image = new JLabel(); // Ändrad
        
        playerSprite = new JLabel(); // ny
        playerSprite.setBounds(365,400, 64, 64); // test

        panel.setLayout(new BorderLayout());
        panel.add(image, BorderLayout.NORTH);
        
        image.add(playerSprite, BorderLayout.SOUTH); // ny
        
        panel.add(listScroller, BorderLayout.CENTER);
        panel.add(entryField, BorderLayout.SOUTH);
        
        myFrame.getContentPane().add(panel, BorderLayout.CENTER);

        // add some event listeners to some components
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        entryField.addActionListener(this);

        myFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        myFrame.setLocation((int)d.getWidth() / 2 - WIDTH / 2, (int)d.getHeight() / 2 - HEIGHT / 2);
        myFrame.pack();
        myFrame.setVisible(true);
        entryField.requestFocus();
    }

    /**
     * Actionlistener interface for entry textfield.
     */
    public void actionPerformed(ActionEvent e) 
    {
        // no need to check the type of action at the moment.
        // there is only one possible action: text entry
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

        //engine.interpretCommand(input);
        client.sendCommand(input);
    }
    
    /**
     * Gets called by client object (observable) on response from server (if state changed).
     */
    public void update(Observable o, Object ob) {
    	//TODO:Display this client's current room, items, that room's items and that room's entities
    }
}
