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

	/**
	 * Create a new SimpleMap
	 * @param board the board to unwrap
	 */
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

	/**
	 * @return the list of regions
	 */
	public List<SimpleRegion> getRegions() {
		return regions;
	}

	/**
	 * @return the nobility track 
	 */
	public List<SimpleNobilityCell> getNobilityTrack() {
		return nobilityTrack;
	}

	/**
	 * @return the king's council
	 */
	public CouncilProperty getKingCouncil() {
		return kingCouncil;
	}

	/**
	 * @return the map for the color bonuses
	 */
	public Map<String, IntegerProperty> getColorBonuses() {
		return colorBonuses;
	}

	/**
	 * @return the map for the councilor pool
	 */
	public Map<String, IntegerProperty> getCouncilorPool() {
		return councilorPool;
	}

	/**
	 * @return the current king bonuses
	 */
	public IntegerProperty kingBonus() {
		return kingBonus;
	}

	/**
	 * set all the city Rewards
	 * @param bonusList the list of reward to unwrap
	 */
	public void setCityRewards(List<Reward> bonusList) {
		int i = 0;
		for (SimpleRegion r : regions) {
			for (SimpleCity c : r.getCities()) {
				c.setBonuses(bonusList.get(i));
				i++;
			}
		}
	}

	/**
	 * set a council
	 * @param c the Council to unwrap
	 * @param number the index of the council to set. By convention,
	 * the king's council has index -1
	 */
	public void setCouncil(Council c, int number) {
		if (number == -1) {
			kingCouncil.set(c);
		} else {
			regions.get(number).getCouncil().set(c);
		}
	}

	/**
	 * set the color rewards
	 * @param colorReward the list of colorReard to unwrap
	 */
	public void setColorReward(List<BoardColorReward> colorReward) {
		for (BoardColorReward br : colorReward) {
			String hex = ColorConverter.awtToWeb(br.getBRKey());
			colorBonuses.get(hex).set(br.getBRBonus().getAmount());
		}
	}

	/**
	 * set the king bonus
	 * @param kingReward the list of kingreward to unwrap. 
	 * It get the first value, if it's empty, set the king bonus to 0
	 */
	public void setKingBonus(List<BVictoryPoints> kingReward) {
		if (kingReward.isEmpty())
			kingBonus.set(0);
		else
			kingBonus.set(kingReward.get(0).getAmount());

	}

	/**
	 * initialize the councilor pool.
	 * This MUST be done after the initialization of all the councils
	 */
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
