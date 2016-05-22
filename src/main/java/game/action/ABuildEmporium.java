package game.action;

import java.util.List;

import game.board.MapExplorer;
import game.board.city.City;
import game.exceptions.IllegalActionException;
import game.player.Emporium;
import game.player.PermissionCard;
import game.player.Player;
import game.reward.Reward;

public class ABuildEmporium extends Action {
	private Player player;
	private Emporium emporium;
	private PermissionCard permissionCard;
	private City city;

	public ABuildEmporium(Player p, PermissionCard permissionCard, City city) throws IllegalActionException{
		super(true);
		this.player = p;
		this.permissionCard = permissionCard;
		this.city = city;
		if (!permissionCard.getCardCity().contains(city)) {
			throw new IllegalActionException("the chosen city is invalid");
		}

		if (city.hasEmporiumOfPlayer(player)) {
			throw new IllegalActionException("you already have an emporium there");
		}
		if ((city.getNumberOfEmporium() > 0)
			&& (player.getAssistants().getAmount() < city.getNumberOfEmporium())) {
				throw new IllegalActionException("you can not afford it!");		
		}
		
	}

	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(city.getNumberOfEmporium());
		player.getPermissionCard().add(permissionCard);
		assignEmporium();
		assignRewards();
	}

	private void assignEmporium(){
		this.emporium.setCity(city);
	}
	
	private void assignRewards() {
		MapExplorer explorer = new MapExplorer();
		List<Reward> rewards = explorer.getAdiacentRewards(this.city, this.player);
		for (Reward rew : rewards) {
			rew.assignBonusTo(this.player);
		}
	}

}
