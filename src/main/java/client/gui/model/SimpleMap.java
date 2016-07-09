package client.gui.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import server.model.board.Board;
import server.model.board.Region;
import server.model.board.council.Council;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;
import server.model.reward.Reward;
import util.ColorConverter;

public class SimpleMap {
	private List<SimpleRegion> regions;
	private CouncilProperty kingCouncil;

	private Map<String, IntegerProperty> colorBonuses;
	/**
	 * represent the current bonus visualized on the map
	 */
	private IntegerProperty kingBonus;

	private List<SimpleNobilityCell> nobilityTrack;

	private Map<String, IntegerProperty> councilorPool;

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
			IntegerProperty value = new SimpleIntegerProperty(br.getBRBonus().getAmount());
			colorBonuses.put(hex, value);
		}


		kingBonus = new SimpleIntegerProperty();
		kingBonus.set(board.getBoardRewardsManager().getRemainingBoardKingRewards().get(0).getAmount());

		nobilityTrack = new ArrayList<>(board.getNobleTrack().getMaxPoint());
		for (Reward r : board.getNobleTrack().getTrack()) {
			if (r != null) {
				nobilityTrack.add(new SimpleNobilityCell(r));
			} else {
				nobilityTrack.add(null);
			}
		}

		councilorPool = new HashMap<>();
		int maxCouncilors = board.getCouncilorPool().getCouncPerColor();
		for (Color c : board.getCouncilorPool().getListColor()) {
			councilorPool.put(ColorConverter.awtToWeb(c), new SimpleIntegerProperty(maxCouncilors));
		}
	}

	public List<SimpleRegion> getRegions() {
		return regions;
	}

	public List<SimpleNobilityCell> getNobilityTrack() {
		return nobilityTrack;
	}

	public CouncilProperty getKingCouncil() {
		return kingCouncil;
	}

	public Map<String, IntegerProperty> getColorBonuses() {
		return colorBonuses;
	}

	public Map<String, IntegerProperty> getCouncilorPool() {
		return councilorPool;
	}

	public IntegerProperty kingBonus() {
		return kingBonus;
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

	public void updateColorReward(List<BoardColorReward> colorReward) {
		for (BoardColorReward br : colorReward) {
			String hex = ColorConverter.awtToWeb(br.getBRKey());
			colorBonuses.get(hex).set(br.getBRBonus().getAmount());
		}
	}

	public void updateKingBonus(List<BVictoryPoints> kingReward) {
		if (kingReward.isEmpty())
			kingBonus.set(0);
		else
			kingBonus.set(kingReward.get(0).getAmount());

	}

	public void initCouncilorPool() {
		for (StringProperty color : kingCouncil.colors()) {
			IntegerProperty value = councilorPool.get(color.get());
			int newValue = value.get() - 1;
			value.set(newValue);
		}

		for (SimpleRegion region : regions) {
			for (StringProperty color : region.getCouncil().colors()) {
				IntegerProperty value = councilorPool.get(color.get());
				value.set(value.get() - 1);
			}
		}
	}
}