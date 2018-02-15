package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 *  Main class on the server-side which handles the game-logic and communicates with players.
 * 
 * @author  Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class GameServer extends Thread
{
	//private UserInterface gui;
	private GameEngine engine;
	//private DatagramSocket serverSocket;
	private ServerSocket server;
	private Socket clientSocket;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private static final int PORT = 8989;
	private String command = "";

    /**
     * Create the game and initialize its internal map.
     */
    public GameServer() throws IOException {
		engine = new GameEngine();
	
		try{
			server = new ServerSocket(PORT);
			
			while(true){
				try{
					//Wait for Connection
					connection();
					//Streams for connection
					streams();
					//Communication
					receiveCommand();
					
				}catch(EOFException e){
					e.printStackTrace();
				}finally{
					//Close connection
					if(command.equals("END"))
					close();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
    }
    
    /**
     * Listen for commands from a client.
     * 
     */
    private String receiveCommand() {
    		try{
    			command = (String) in.readObject();
    		}catch(IOException e){
    			System.out.println("Unknown Message");
    		}catch(ClassNotFoundException e){
    			System.out.println("Unknown Format");
    		}
    	return command;
    }
    
    /**
     * Send responses to communicating clients, consisting of all connected players and possibly 
     * also a responseMessage from the executed command.
     * @param responseMessage The message returned by the input, processed command. It's null
     * if the command didn't return one, and should then not be sent back to the client/user.
     */
    private void sendResponse(String responseMessage) {
    	//TODO: implement communication with clients.
    	List<Player> players = engine.getPlayers();
    	
    	
    }
    
    /**
     * The continuous listening/responding main loop of the server program.
     */
    public void run() {
    	while (!interrupted()) {
    		String commandLine = receiveCommand();
    		String returnMessage = engine.interpretCommand(commandLine);
    		sendResponse(returnMessage);
    	}
    }
    
    private void streams() throws IOException {
    	out = new ObjectOutputStream(clientSocket.getOutputStream());
    	out.flush();
    	
    	in = new ObjectInputStream(clientSocket.getInputStream());
    	System.out.println("Server ready to exchange data\n");
    }
    
    private void connection() throws IOException {
    	System.out.println("Waiting for player to connect!\n");
    	clientSocket = server.accept();
    	System.out.println(" Player " + clientSocket.getInetAddress().getHostName() + " Connected\n");
    }
    
    private void close() {
    	System.out.println("Closing Connection\n");
    	try{
    		out.close();
    		in.close();
    		clientSocket.close();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * Entry point to server program.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	new GameServer();
    }
}
