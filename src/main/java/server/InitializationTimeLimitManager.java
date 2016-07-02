package server;

import java.util.logging.Logger;

import server.model.Game;

public class InitializationTimeLimitManager extends Thread{
	private Game startingGame;
	private static final long WAIT_TIME=20000;
	private Logger logger= Logger.getGlobal();
	
	public InitializationTimeLimitManager(Game startingGame){
		this.startingGame=startingGame;
	}
	
	/**
	 * This is the thread which waits for 20 seconds and then it starts the game if it isn't complete
	 */
	@Override
	public synchronized void run(){
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
