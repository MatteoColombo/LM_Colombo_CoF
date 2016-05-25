package game.board;

import java.util.List;

import game.reward.BVictoryPoints;
import game.reward.BoardReward;

public class BoardRewardManager {
	private List<BoardReward> bReward;

	public BoardRewardManager(List<BoardReward> boardRewardList) {
		this.bReward = boardRewardList;
	}

	public List<BoardReward> getRemainingRewards() {
		return this.bReward;
	}

	public BVictoryPoints getColourReward(String colourReward) {
		for (BoardReward br : bReward) {
			if (br != null && br.getBRName().equals(colourReward)) {

			} else {

			}
		}
	}

	public BVictoryPoints getRegionReward(String regionReward) {
		for (BoardReward br : bReward) {
			if (br != null && br.getBRName().equals(regionReward)) {

			} else {

			}
		}
	}

	public BVictoryPoints getKingReward() {

	}

}