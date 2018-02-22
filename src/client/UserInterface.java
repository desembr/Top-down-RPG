package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import server.Enemy;
import server.GameServer;
import server.Player;
import server.Room;

/**
 * This class implements a simple graphical user interface with a text entry
 * area, a text output area and an optional image.
 * 
 * @author Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class UserInterface implements ActionListener, Observer {
	private static final int WIDTH = 800, HEIGHT = 730;

	private Client client;
	private JFrame myFrame;
	private JTextField entryField;
	private JTextArea log;
	private JLabel image, playerSprite, monster1, monster2, monster3, monster4, monster5, shadow1, shadow2, shadow3, shadow4, shadow5,
			shadow6;

	private ArrayList<JLabel> monsterSprites;
	private ArrayList<JLabel> shadows;

	public static void main(String[] args) {
		new UserInterface();
	}

	public UserInterface() {
		initMenu();
	}

	/**
	 * Construct a UserInterface. As a parameter, a Client object, with will
	 * handle all the communication with the server (sending commands and
	 * receiving responses).
	 * 
	 * @param client
	 *            The Client to handle all communication with server.
	 */
	public UserInterface(Client client) {
		monsterSprites = new ArrayList<>();
		shadows = new ArrayList<>();

		this.client = client;

		createGUI();

		printWelcome();
	}

	public void initMenu() {
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout(5, 5));

		JPanel panelUno = new JPanel();
		JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
		JButton buttonN = new JButton("New Game");

		panel.add(buttonN);

		JButton button = new JButton("Load Game");
		panel.add(button);

		JButton button2 = new JButton("Highscore");
		panel.add(button2);

		JTextField textField = new JTextField();
		panel.add(textField);
		panelUno.add(panel);

		JPanel buttonPanel = new JPanel();
		JButton button3 = new JButton("Exit");
		buttonPanel.add(button3);

		myPanel.add(panelUno, BorderLayout.CENTER);
		myPanel.add(buttonPanel, BorderLayout.PAGE_END);

		myFrame = new JFrame("MyLittleRPG");
		myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		myFrame.setBounds(0, 0, WIDTH, HEIGHT);

		JPanel centerPanel = new JPanel();
		myFrame.getContentPane().setLayout(new BorderLayout(5, 5));
		centerPanel.setBackground(Color.DARK_GRAY);
		myFrame.add(centerPanel, BorderLayout.CENTER);

		myFrame.add(myPanel, BorderLayout.LINE_END);
		buttonN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myFrame.getContentPane().removeAll();
				new GameServer();
				client = new Client();
				setClient(client);
				client.addObserver(getThis());
				client.start();
				createGUI();
			}
		});
		myFrame.setVisible(true);
	}

	private void setClient(Client c) {
		this.client = c;
	}

	private UserInterface getThis() {
		return this;
	}

	/**
	 * Print out some text into the text area.
	 * 
	 * @param text
	 *            The text to print to the text area.
	 */
	public void print(String text) {
		log.append(text);
		log.setCaretPosition(log.getDocument().getLength());
	}

	/**
	 * Print out some text into the text area, followed by a line break.
	 * 
	 * @param text
	 *            The text to print to the text area.
	 */
	public void println(String text) {
		log.append(text + "\n");
		log.setCaretPosition(log.getDocument().getLength());
	}

	/**
	 * Show an image file in the interface.
	 * 
	 * @param imageName
	 *            The filePath of the image to display.
	 */
	public void showImage(String imageName) {
		try {
			URL imageURL = this.getClass().getClassLoader().getResource(imageName);

			// System.out.println(imageURL);

			if (imageURL == null) {
				// System.out.println("image not found"); // debug

				// System.out.println("Working Directory = " +
				// System.getProperty("user.dir")); // debug
			} else {
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
	 * 
	 * @param imageName
	 *            The filePath of the image to display.
	 */
	public void showPlayer(String imageName) {
		URL imageURL = this.getClass().getClassLoader().getResource(imageName);
		if (imageURL == null)
			System.out.println("image not found");
		else {
			ImageIcon icon = new ImageIcon(imageURL);
			playerSprite.setIcon(icon);
			myFrame.pack();
		}
	}

	/**
	 * Shows the enemies present in the room
	 * 
	 * @param enemies,
	 *            a List of enemies
	 */
	public void showEnemies(List<Enemy> enemies) {
		for (int i = 0; i < 5; i++) {
			if (i < enemies.size()) {
				URL imageURL = this.getClass().getClassLoader().getResource(enemies.get(i).getIconFilePath());
				if (imageURL == null)
					System.out.println("image not found");
				else {
					ImageIcon icon = new ImageIcon(imageURL);
					monsterSprites.get(i).setIcon(icon);
					myFrame.pack();
				}
			} else {
				ImageIcon icon = new ImageIcon("none"); // dummy value, so no
														// monster graphic
				monsterSprites.get(i).setIcon(icon);
				myFrame.pack();
			}
		}
	}

	/**
	 * Just a method to draw the number of shadows that should be present in a
	 * room based on how many enemies there are
	 * 
	 * @param enemies,
	 *            a List of enemies
	 */
	public void showShadows(List<Enemy> enemies) {
		for (int i = 0; i < 5; i++) {
			if (i < enemies.size()) {
				URL imageURL = this.getClass().getClassLoader().getResource("res/misc/shadow3.png");
				if (imageURL == null)
					System.out.println("image not found");
				else {
					ImageIcon icon = new ImageIcon(imageURL);
					shadows.get(i).setIcon(icon);
					myFrame.pack();
				}
			} else {
				ImageIcon icon = new ImageIcon("none"); // dummy value, so no
														// shadow
				shadows.get(i).setIcon(icon);
				myFrame.pack();
			}

		}
		// add the player shadow last
		URL imageURL = this.getClass().getClassLoader().getResource("res/misc/shadow3.png");
		if (imageURL == null)
			System.out.println("image not found");
		else {
			ImageIcon icon = new ImageIcon(imageURL);
			shadows.get(5).setIcon(icon); // shadow number 6 (if counting the
											// first as 1) is always the players
											// shadow, the max number of enemies
											// in a room is set to 5
			myFrame.pack();
		}

	}

	/**
	 * Enable or disable input in the input field.
	 * 
	 * @param on
	 *            Whether to enable or disable the entryField.
	 */
	public void enable(boolean on) {
		entryField.setEditable(on);
		if (!on)
			entryField.getCaret().setBlinkRate(0);
	}

	/**
	 * Set up graphical user interface.
	 */
	private void createGUI() {
		myFrame = new JFrame("TOPDOWNRPG");

		entryField = new JTextField(34);

		log = new JTextArea();
		log.setEditable(false);
		JScrollPane listScroller = new JScrollPane(log);
		listScroller.setPreferredSize(new Dimension(200, 200));
		listScroller.setMinimumSize(new Dimension(100, 100));

		JPanel panel = new JPanel();
		image = new JLabel();

		// Create all entity labels
		playerSprite = new JLabel();
		playerSprite.setBounds(365, 400, 64, 64); // position, size

		monster1 = new JLabel();
		monster2 = new JLabel();
		monster3 = new JLabel();
		monster4 = new JLabel();
		monster5 = new JLabel();

		monster1.setBounds(165, 100, 64, 64);
		monster2.setBounds(265, 100, 64, 64);
		monster3.setBounds(365, 100, 64, 64);
		monster4.setBounds(465, 100, 64, 64);
		monster5.setBounds(565, 100, 64, 64);

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

		shadow1.setBounds(165, 128, 64, 64);
		shadow2.setBounds(265, 128, 64, 64);
		shadow3.setBounds(365, 128, 64, 64);
		shadow4.setBounds(465, 128, 64, 64);
		shadow5.setBounds(565, 128, 64, 64);
		shadow6.setBounds(365, 428, 64, 64);

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
			public void windowClosing(WindowEvent e) {
				exitGame();
			}
		});

		entryField.addActionListener(this);

		try {
			BufferedImage icon = ImageIO.read(UserInterface.class.getClassLoader().getResourceAsStream(("res/icon.png")));
			BufferedImage cursor = ImageIO.read(UserInterface.class.getClassLoader().getResourceAsStream("res/cursor.png"));
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "custom");
			myFrame.setCursor(c);
			myFrame.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create the MenuBar.
		makeMenuBar();

		myFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // För stor för
																// laptop
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		myFrame.setLocation((int) d.getWidth() / 2 - WIDTH / 2, 0);
		myFrame.pack();
		entryField.requestFocus();
		myFrame.setVisible(true);
	}

	/**
	 * Creates the menu-bar.
	 */
	private void makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		myFrame.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem save = new JMenuItem("Save");
		fileMenu.add(save);
		save.addActionListener(e -> {
			String user = JOptionPane.showInputDialog(null, "Enter user name", "Save game", JOptionPane.PLAIN_MESSAGE);
			if (user == null)
				return;
			entryField.setText("Save " + user);
			processCommand();
		});

		JMenuItem load = new JMenuItem("Load");
		fileMenu.add(load);
		load.addActionListener(e -> {
			String user = JOptionPane.showInputDialog(null, "Enter user name", "Load game", JOptionPane.PLAIN_MESSAGE);
			if (user == null)
				return;
			entryField.setText("Load " + user);
			processCommand();
		});

		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		JMenuItem help = new JMenuItem("Help");
		helpMenu.add(help);
		help.addActionListener(e -> {
			entryField.setText("Help");
			processCommand();
		});

		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		exit.addActionListener(e -> {
			entryField.setText("Exit");
			processCommand();
		});
	}

	/**
	 * Listener method for the textField (the input field).
	 * 
	 * @param e
	 *            The Event that triggered this listener.
	 */
	public void actionPerformed(ActionEvent e) {
		log.setText("");
		processCommand();
	}

	/**
	 * A command has been entered. Read the command and do whatever is necessary
	 * to process it.
	 */
	private void processCommand() {
		String input = entryField.getText();
		entryField.setText("");

		if (input.length() > 0) {
			println(input);
			synchronized (client) {
				client.pollCommand(input);
			}
		}
	}

	/**
	 * Show the images for all objects contained in this client's player
	 * object's current room.
	 * 
	 * @param currentRoom
	 *            This client's player object's current room.
	 */
	private void showRoom(Room currentRoom) {
		showImage(currentRoom.getImage());
		println(currentRoom.getLongDescription());
	}

	/**
	 * Prints a short welcome message, with short description of the purpose of
	 * the game.
	 */
	private void printWelcome() {
		println("Hello adventurer, your task is to \nexplore, fight and gather treasure.\n"
				+ "Enter 'help' if you need some help.\nGoodluck!");
	}

	/**
	 * Exits the Client program after acknowledged exit command.
	 */
	private void exitGame() {
		println("Goodbye...");
		synchronized (client) {
			client.exit();
		}
		System.exit(0);
	}

	/**
	 * Gets called by the client object (observable) on response from server.
	 * Updates the view. Displays all changes to this client's player object's
	 * state (items, room, players in that room etc).
	 * 
	 * @param o
	 *            The Observable object triggering this method.
	 * @param player
	 *            The updated Player object received from the server to the
	 *            Client object.
	 */
	public void update(Observable o, Object player) {
		Player p = (Player) player;

		if (p.getIsDead())
			exitGame();

		showRoom(p.getRoom());

		showPlayer(p.getIconFilePath());

		showEnemies(p.getRoom().getEnemies());
		showShadows(p.getRoom().getEnemies());

		println(p.showInventory() + ", Health: " + p.getHealth() + ", Damage: " + p.getDamage() + ", Defence: " + p.getDefence()
				+ ", Weight: " + p.getWeight() + "/" + p.getMaxWeight());
		if (p.getCmdReturnMsg() != null)
			println(p.getCmdReturnMsg());
	}
}
