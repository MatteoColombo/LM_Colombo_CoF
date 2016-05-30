package game.action;

import java.util.List;

import game.board.BoardRewardsManager;
import game.board.city.City;
import game.board.map.MapExplorer;
import game.exceptions.IllegalActionException;
import game.player.PermissionCard;
import game.player.Player;
import game.reward.BVictoryPoints;
import game.reward.Reward;

public class ABuildEmporium extends Action {
	private Player player;
	private PermissionCard permissionCard;
	private City city;
	private BoardRewardsManager bRewardsManager;

	public ABuildEmporium(Player p, PermissionCard permissionCard, City city, BoardRewardsManager bRewardsManager)
			throws IllegalActionException {
		super(true);
		this.player = p;
		this.permissionCard = permissionCard;
		this.city = city;
		this.bRewardsManager = bRewardsManager;
		if (!permissionCard.getCardCity().contains(city)) {
			throw new IllegalActionException("the chosen city is invalid");
		}

		if (city.hasEmporiumOfPlayer(player)) {
			throw new IllegalActionException("you already have an emporium there");
		}
		if ((city.getNumberOfEmporium() > 0) && (player.getAssistants().getAmount() < city.getNumberOfEmporium())) {
			throw new IllegalActionException("you can not afford it!");
		}

	}

	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(city.getNumberOfEmporium());
		permissionCard.setCardUsed();
		assignEmporium();
		assignRewards();
		this.player.doMainAction();
		{// TODO if (check for color)
			if (!city.isCapital()) {
				BVictoryPoints playerBReward = this.bRewardsManager.getBoardColorReward(city.getColor());
				playerBReward.assignBonusTo(player);
			}
		}
		if (city.getRegion().isCompleted(this.player)) {
			BVictoryPoints playerBReward = this.bRewardsManager.getBoardRegionReward(city.getRegion());
			playerBReward.assignBonusTo(player);
		}

	}

	private void assignEmporium() {
		this.city.addEmporium(player.getEmporium().remove(0));
	}

	private void assignRewards() {
		MapExplorer explorer = new MapExplorer();
		List<Reward> rewards = explorer.getAdiacentRewards(this.city, this.player);
		for (Reward rew : rewards) {
			rew.assignBonusTo(this.player);
		}
	}

}
