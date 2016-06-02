package model.action;

import java.util.List;

import model.board.BoardRewardsManager;
import model.board.King;
import model.board.city.City;
import model.board.map.MapExplorer;
import model.exceptions.IllegalActionException;
import model.player.Player;
import model.reward.BVictoryPoints;
import model.reward.Reward;

public class ABuildEmporiumWithKing extends Action {
	private Player player;
	private City chosenCity;
	private King king;
	private MapExplorer mx;
	private int priceForMovement;
	private final int PRICEPERROUTE = 2;
	private BoardRewardsManager bRewardsManager;
	private List<City> cities;

	public ABuildEmporiumWithKing(Player p, King king, City chosenCity, List<City> cities,
			BoardRewardsManager bRewardsManager) throws IllegalActionException {
		super(true);
		this.king = king;
		this.player = p;
		this.chosenCity = chosenCity;
		this.cities = cities;
		this.mx = new MapExplorer();
		this.bRewardsManager = bRewardsManager;
		if (chosenCity.hasEmporiumOfPlayer(player)) {
			throw new IllegalActionException("you already have an emporium there");
		}

		if ((chosenCity.getNumberOfEmporium() > 0)
				&& (player.getAssistants().getAmount() < (chosenCity.getNumberOfEmporium()))) {
			throw new IllegalActionException("you can not afford it!");
		}
		this.priceForMovement = PRICEPERROUTE * mx.getDistance(king.getKingLocation(), chosenCity);
		if ((priceForMovement > 0) && (player.getCoins().getAmount() < priceForMovement)) {
			throw new IllegalActionException("you can not afford it!");
		}

	}

	@Override
	public void execute() {
		king.moveKing(chosenCity);
		player.getAssistants().decreaseAmount(chosenCity.getNumberOfEmporium());
		player.getCoins().decreaseAmount(priceForMovement);
		this.player.doMainAction();
		assignEmporium();
		assignRewards();
		MapExplorer mp = new MapExplorer();
		/*if (mp.isColorComplete(this.player, this.chosenCity.getColor(), this.cities)) {
			if (!chosenCity.isCapital()) {
				BVictoryPoints playerBReward = this.bRewardsManager.getBoardColorReward(chosenCity.getColor());
				playerBReward.assignBonusTo(player);
			}
		}
		if (chosenCity.getRegion().isCompleted(this.player)) {
			BVictoryPoints playerBReward = this.bRewardsManager.getBoardRegionReward(chosenCity.getRegion());
			playerBReward.assignBonusTo(player);
		}*/

	}

	private void assignEmporium() {
		this.chosenCity.addEmporium(player.getEmporium().remove(0));
	}

	private void assignRewards() {
		MapExplorer explorer = new MapExplorer();
		List<Reward> rewards = explorer.getAdiacentRewards(this.chosenCity, this.player);
		for (Reward rew : rewards) {
			rew.assignBonusTo(this.player);
		}
	}

}
