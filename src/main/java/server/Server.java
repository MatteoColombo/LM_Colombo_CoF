package server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Logger;

import model.Game;

public class Server implements ServerInt{
	private List<Game> games;
	private static final String NAME = "ServerInt";
	private static Logger logger = Logger.getGlobal();
	
	public void login(ClientInt client) {
		if(games.isEmpty() || games.get(0).isComplete()) {
			Game newGame = new Game();
			newGame.addPlayer(client);
			games.add(0, newGame);
		} else {
			games.get(0).addPlayer(client);
		}	
		return;	
	}
	
	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
		try {
            ServerInt room = new Server();
            ServerInt stub = (ServerInt) UnicastRemoteObject.exportObject(room, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(NAME, stub);
            logger.info("Main server ready");
        } catch (Exception e) {
            System.err.println("Chat Server exception:");
            e.printStackTrace();
        }
	}
}
