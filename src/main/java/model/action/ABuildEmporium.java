package model.action;

import java.util.List;

import model.board.BoardRewardsManager;
import model.board.city.City;
import model.board.map.MapExplorer;
import model.board.map.MapLoader;
import model.exceptions.IllegalActionException;
import model.player.PermissionCard;
import model.player.Player;
import model.reward.BVictoryPoints;
import model.reward.Reward;

public class ABuildEmporium extends Action {
	private Player player;
	private PermissionCard permissionCard;
	private City chosenCity;
	private BoardRewardsManager bRewardsManager;
	private List<City> cities;

	public ABuildEmporium(Player p, PermissionCard permissionCard, City chosenCity, List<City> cities,
			BoardRewardsManager bRewardsManager) throws IllegalActionException {
		super(true);
		this.player = p;
		this.permissionCard = permissionCard;
		this.chosenCity = chosenCity;
		this.cities = cities;
		this.bRewardsManager = bRewardsManager;
		if (!permissionCard.getCardCity().contains(chosenCity)) {
			throw new IllegalActionException("the chosen city is invalid");
		}

		if (chosenCity.hasEmporiumOfPlayer(player)) {
			throw new IllegalActionException("you already have an emporium there");
		}
		if ((chosenCity.getNumberOfEmporium() > 0)
				&& (player.getAssistants().getAmount() < chosenCity.getNumberOfEmporium())) {
			throw new IllegalActionException("you can not afford it!");
		}

	}

	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(chosenCity.getNumberOfEmporium());
		permissionCard.setCardUsed();
		assignEmporium();
		assignRewards();
		this.player.doMainAction();
		MapExplorer mp = new MapExplorer();
		if (mp.isColorComplete(this.player, this.chosenCity.getColor(), this.cities)) {
			if (!chosenCity.isCapital()) {
				BVictoryPoints playerBReward = this.bRewardsManager.getBoardColorReward(chosenCity.getColor());
				playerBReward.assignBonusTo(player);
			}
		}
		if (chosenCity.getRegion().isCompleted(this.player)) {
			BVictoryPoints playerBReward = this.bRewardsManager.getBoardRegionReward(chosenCity.getRegion());
			playerBReward.assignBonusTo(player);
		}

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
