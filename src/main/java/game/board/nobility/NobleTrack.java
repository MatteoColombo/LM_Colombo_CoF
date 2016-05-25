 package game.board.nobility;

import game.reward.Reward;

public class NobleTrack {
	private Reward[] track;
	public NobleTrack(int trackSize){
		track= new Reward[trackSize];
	}
	
	public Reward getReward(int posArray){
		return track[posArray];
	}
}
