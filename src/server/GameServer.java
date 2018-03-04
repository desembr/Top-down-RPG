package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.entities.Player;

/**
 * Main class on the server-side program which manages the game-logic and
 * listens/accepts client connections. Inserts a new, managed Player object for
 * every new Client that connects to this server.
 * 
 * @author Tom Bjurenlind
 * @version 2018-02-24
 */
public class GameServer extends Thread {
	private static final int serverPort = 44120;

	private GameEngine engine;
	private ServerSocket serverSocket;

	private static List<ClientHandler> clientHandlers;

	/**
	 * Create the game and initialize its internal map.
	 */
	public GameServer(boolean isLowRes) {
		engine = new GameEngine(isLowRes);

		clientHandlers = new ArrayList<>();

		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Server started...listening on port " + serverPort);

		start();
	}

	/**
	 * The continuous listening main loop of the server program. This loop
	 * listens and accepts new Client connections.
	 */
	public synchronized void run() {
		// Continuously listen and accept new Client connections on
		// serverSocket.
		while (!interrupted()) {
			try {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client connected");
				Player newPlayer = new Player(engine.getStartRoom());
				engine.addPlayer(newPlayer);
				clientHandlers.add(new ClientHandler(clientSocket, engine, newPlayer));
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println("Server exiting...bye");
		try {
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Returns the ClientHandlers for those handlers whose player objects is
	 * currently the same room as Player p's current room or previous room.
	 * 
	 * @param p
	 *            The player whose current room's and previous room's players'
	 *            clients should get updated from room state change caused by
	 *            Player p's client's issued command.
	 * @return The ClientHandlers meeting the conditions described above.
	 */
	public static synchronized List<ClientHandler> getClientHandlers(Player p) {
		if (p == null)
			return null;

		List<ClientHandler> res = new ArrayList<>();
		for (ClientHandler ch : clientHandlers) {
			synchronized (ch) {
				Room r = ch.getPlayer().getRoom();
				if (r == p.getRoom() || r == p.getPreviousRoom())
					res.add(ch);
			}
		}
		return res;
	}

	/**
	 * Removes a ClientHandler on Client-disconnect.
	 * 
	 * @param ch
	 *            The ClientHandler to remove.
	 */
	public static synchronized void removeClientHandler(ClientHandler ch) {
		if (ch != null)
			clientHandlers.remove(ch);
	}

	/**
	 * Entry point to server program.
	 */
	public static void main(boolean isLowRes) {
		new GameServer(isLowRes);
	}
}
