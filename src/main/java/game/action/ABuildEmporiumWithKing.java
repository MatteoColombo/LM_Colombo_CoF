package game.action;

import java.util.List;

import game.board.BoardRewardsManager;
import game.board.King;
import game.board.city.City;
import game.board.map.MapExplorer;
import game.exceptions.IllegalActionException;
import game.player.Player;
import game.reward.BVictoryPoints;
import game.reward.Reward;

public class ABuildEmporiumWithKing extends Action {
	private Player player;
	private City city;
	private King king;
	private MapExplorer mx;
	private int priceForMovement;
	private final int PRICEPERROUTE = 2;
	private BoardRewardsManager bRewardsManager;

	public ABuildEmporiumWithKing(Player p, King king, City city, BoardRewardsManager bRewardsManager)
			throws IllegalActionException {
		super(true);
		this.king = king;
		this.player = p;
		this.city = city;
		this.mx = new MapExplorer();
		this.bRewardsManager = bRewardsManager;
		if (city.hasEmporiumOfPlayer(player)) {
			throw new IllegalActionException("you already have an emporium there");
		}

		if ((city.getNumberOfEmporium() > 0) && (player.getAssistants().getAmount() < (city.getNumberOfEmporium()))) {
			throw new IllegalActionException("you can not afford it!");
		}
		this.priceForMovement = PRICEPERROUTE * mx.getDistance(king.getKingLocation(), city);
		if ((priceForMovement > 0) && (player.getCoins().getAmount() < priceForMovement)) {
			throw new IllegalActionException("you can not afford it!");
		}

	}

	@Override
	public void execute() {
		king.moveKing(city);
		player.getAssistants().decreaseAmount(city.getNumberOfEmporium());
		player.getCoins().decreaseAmount(priceForMovement);
		this.player.doMainAction();
		assignEmporium();
		assignRewards();
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
