package game.board;

import java.util.List;

import game.reward.BVictoryPoints;
import game.reward.BoardReward;

public class BoardRewardManager {
	private List<BoardReward> bReward;
	
	public BoardRewardManager (List<BoardReward> boardRewardList){
		this.bReward= boardRewardList;
	}
	
	public List<BoardReward> getRemainingRewards(){
		return this.bReward;
	}
	
	public BVictoryPoints getColourReward(String colourReward){		
		if (bReward.contains(colourReward)) {
			
	
		}
	}
	
	public BVictoryPoints getRegionReward(String regionReward){
		if (bReward.contains(regionReward)) {
			

		}
	}
	
	public BVictoryPoints getKingReward(){
		
	}

}