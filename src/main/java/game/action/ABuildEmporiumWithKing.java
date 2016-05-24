package game.action;

import java.util.List;

import game.board.King;
import game.board.city.City;
import game.board.map.MapExplorer;
import game.exceptions.IllegalActionException;
import game.player.Player;
import game.reward.Reward;

public class ABuildEmporiumWithKing extends Action {
	private Player player;
	private City city;
	private King king;
	private MapExplorer mx;
	private int priceForMovement;
	private final int PRICEPERROUTE = 2;

	public ABuildEmporiumWithKing(Player p, King king, City city)
			throws IllegalActionException {
		super(true);
		this.king = king;
		this.player = p;
		this.city = city;
		this.mx = new MapExplorer();
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
		player.getAssistants().decreaseAmount(city.getNumberOfEmporium());
		player.getCoins().decreaseAmount(priceForMovement);
		king.moveKing(city);
		assignEmporium();
		assignRewards();
		this.player.doMainAction();
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
