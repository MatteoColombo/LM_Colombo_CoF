package client.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.board.Board;
import model.board.Region;
import model.board.council.Council;
import model.reward.BoardReward;
import model.reward.Bonus;
import model.reward.Reward;

public class SimpleMap {
	private List<SimpleRegion> regions;
	private CouncilProperty kingCouncil;
	private List<Integer> colorBonuses; 
	private IntegerProperty kingBonus; 
	
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
		
		kingCouncil = new CouncilProperty();
		
		colorBonuses = new ArrayList<>();
		for(BoardReward br: board.getBoardRewardsManager().getRemainingBoardColorRewards()) {
			colorBonuses.add(br.getBRBonus().getAmount());
		}
		
		kingBonus = new SimpleIntegerProperty();
		kingBonus.set(board.getBoardRewardsManager().getRemainingBoardKingRewards().get(0).getAmount());
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
	
	public IntegerProperty kingBonus() {
		return this.kingBonus;
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
	
	public void setCouncil(Council c, int number) {
		if(number == -1) {
			kingCouncil.set(c);
		} else {
			regions.get(number).getCouncil().set(c);
		}
	}
}
