package model.action;

import java.util.List;

import model.board.BoardRewardsManager;
import model.board.city.City;
import model.board.map.MapExplorer;
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
	private List<City> allMapCities;

	public ABuildEmporium(Player p, PermissionCard permissionCard, City chosenCity, List<City> allMapCities,
			BoardRewardsManager bRewardsManager) throws IllegalActionException {
		super(true, p);
		this.player = p;
		this.permissionCard = permissionCard;
		this.chosenCity = chosenCity;
		this.allMapCities = allMapCities;
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
		if (this.player.getEmporium().isEmpty()) {
			throw new IllegalActionException("you have no emporiums left!");
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
		if (mp.isColorComplete(this.player, this.chosenCity.getColor(), this.allMapCities)
				&& !this.chosenCity.isCapital()) {
			BVictoryPoints playerBReward = this.bRewardsManager.assingBoardColorReward(this.chosenCity.getColor());
			playerBReward.assignBonusTo(this.player);
		}
		if (this.chosenCity.getRegion().isCompleted(this.player)) {
			BVictoryPoints playerBReward = this.bRewardsManager.assingBoardRegionReward(this.chosenCity.getRegion());
			playerBReward.assignBonusTo(this.player);
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
