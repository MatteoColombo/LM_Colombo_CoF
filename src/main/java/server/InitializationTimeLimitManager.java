package server;

import java.util.logging.Logger;

import server.model.Game;

/**
 * This is the class which implements the 20 seconds timer that starts after the second player connected
 * @author Matteo Colombo
 *
 */
public class InitializationTimeLimitManager extends Thread{
	private Game startingGame;
	private static final long WAIT_TIME=20000;
	private Logger logger= Logger.getGlobal();
	
	/**
	 * Sets the game to which the timer is referred
	 * @param startingGame
	 */
	public InitializationTimeLimitManager(Game startingGame){
		this.startingGame=startingGame;
	}
	
	/**
	 * This is the thread which waits for 20 seconds and then it starts the game if it isn't complete
	 */
	@Override
	public void run(){
		try {
			sleep(WAIT_TIME);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.info(e.getMessage());
		}
		if(!startingGame.isComplete()){
			Server.startGame();
		}
	}
}
