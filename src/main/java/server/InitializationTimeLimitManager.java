package server;

import java.util.logging.Logger;

import model.Game;

public class InitializationTimeLimitManager extends Thread{
	private Game startingGame;
	private final static long WAIT_TIME=20000;
	private Logger logger= Logger.getGlobal();
	
	public InitializationTimeLimitManager(Game startingGame){
		this.startingGame=startingGame;
	}
	
	@Override
	public void run(){
		try {
			sleep(WAIT_TIME);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.info(e.getMessage());
		}
		if(!startingGame.isComplete()){
			Server.startGame(startingGame);
		}
	}
}
