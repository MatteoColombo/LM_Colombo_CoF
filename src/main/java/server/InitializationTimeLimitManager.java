package server;

import model.Game;

public class InitializationTimeLimitManager extends Thread{
	private Game startingGame;
	private final static long WAIT_TIME=20000;
	public InitializationTimeLimitManager(Game startingGame){
		this.startingGame=startingGame;
		this.start();
	}
	
	public void run(){
		try {
			sleep(WAIT_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!startingGame.isComplete()){
			Server.startGame(startingGame);
		}
	}
}
