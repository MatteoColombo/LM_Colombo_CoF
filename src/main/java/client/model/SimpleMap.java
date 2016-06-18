package client.model;

import java.util.ArrayList;
import java.util.List;

import model.board.Board;
import model.board.Region;
import model.reward.BoardReward;
import model.reward.Bonus;
import model.reward.Reward;

public class SimpleMap {
	private List<SimpleRegion> regions;
	private CouncilProperty kingCouncil;
	private List<Integer> colorBonuses; 
	private List<Integer> kingBonuses; 
	
	public SimpleMap(Board board) {
		
		regions = new ArrayList<>();
		for(Region r: board.getRegions()) {
			SimpleRegion sr = new SimpleRegion(r);
			regions.add(sr);
		}
		
		for(int i = regions.size()-1; i >= 0; i--) {
			int amount = board.getBoardRewardsManager()
							  .getRemainingBoardRegionRewards()
							  .get(i).getBRBonus().getAmount();
			regions.get(i).setCounquerBonus(amount); 
		}
		
		colorBonuses = new ArrayList<>();
		for(BoardReward br: board.getBoardRewardsManager().getRemainingBoardColorRewards()) {
			colorBonuses.add(br.getBRBonus().getAmount());
		}
		
		kingBonuses = new ArrayList<>();
		for(Bonus br: board.getBoardRewardsManager().getRemainingBoardKingRewards()) {
			kingBonuses.add(br.getAmount());
		}
	}
	
	public List<SimpleRegion> getRegions() {
		return this.regions;
	}
	
	public CouncilProperty getKingCouncil() {
		return this.kingCouncil;
	}
	
	public List<Integer> getColorBonuses() {
		return this.colorBonuses;
	}
	
	public List<Integer> getKingBonuses() {
		return this.kingBonuses;
	}
	
	public void setCityRewards(List<Reward> bonusList) {
		int i = 0;
		for(SimpleRegion r: regions) {
			for(SimpleCity c: r.getCities()) {
				c.setBonuses(bonusList.get(i));
				i++;
			}
		}
	}
}
