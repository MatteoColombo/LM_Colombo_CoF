package client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.board.Board;
import model.board.Region;
import model.board.council.Council;
import model.reward.BoardColorReward;
import model.reward.BoardRegionReward;
import model.reward.Bonus;
import model.reward.Reward;
import util.ColorConverter;

public class SimpleMap {
	private List<SimpleRegion> regions;
	private CouncilProperty kingCouncil;

	private Map<String, IntegerProperty> colorBonuses;

	private List<Integer> kingBonuses;
	/**
	 * represent the current bonus visualized on the map
	 */
	private IntegerProperty kingBonus;

	public SimpleMap(Board board) {

		regions = new ArrayList<>();
		for (Region r : board.getRegions()) {
			SimpleRegion sr = new SimpleRegion(r);
			regions.add(sr);
		}

		List<BoardRegionReward> regionRewards = board.getBoardRewardsManager().getRemainingBoardRegionRewards();
		for (int i = regions.size() - 1; i >= 0; i--) {
			int amount = regionRewards.get(i).getBRBonus().getAmount();
			regions.get(i).setCounquerBonus(amount);
		}

		kingCouncil = new CouncilProperty();
		kingCouncil.initCouncil(board.getKingCouncil().getCouncilorsColor().size());
		
		colorBonuses = new HashMap<>();
		for (BoardColorReward br : board.getBoardRewardsManager().getRemainingBoardColorRewards()) {
			String hex = ColorConverter.awtToWeb(br.getBRKey());
			IntegerProperty value = new SimpleIntegerProperty(br.getBRBonus().getValue());
			colorBonuses.put(hex, value);
		}

		kingBonuses = new ArrayList<>();
		for(Bonus br: board.getBoardRewardsManager().getRemainingBoardKingRewards()) {
			kingBonuses.add(br.getAmount());
		}
		
		kingBonus = new SimpleIntegerProperty();
		kingBonus.set(kingBonuses.remove(0));
	}

	public List<SimpleRegion> getRegions() {
		return this.regions;
	}

	public CouncilProperty getKingCouncil() {
		return this.kingCouncil;
	}

	public Map<String, IntegerProperty> getColorBonuses() {
		return this.colorBonuses;
	}

	public IntegerProperty kingBonus() {
		return this.kingBonus;
	}

	public void setCityRewards(List<Reward> bonusList) {
		int i = 0;
		for (SimpleRegion r : regions) {
			for (SimpleCity c : r.getCities()) {
				c.setBonuses(bonusList.get(i));
				i++;
			}
		}
	}

	public void setCouncil(Council c, int number) {
		if (number == -1) {
			kingCouncil.set(c);
		} else {
			regions.get(number).getCouncil().set(c);
		}
	}
	
	public void setNextKingBonus() {
		if(kingBonuses.size() > 0) {
			kingBonus.set(kingBonuses.remove(0));
		}	
	}
}
