package game.action;

import java.util.List;

import game.board.council.Council;
import game.player.PermissionCard;
import game.player.Player;
import game.player.PoliticCard;

public class ABuyPermissionCard extends Action{

	Player player;
	PermissionCard permCard;
	Council council;
	List<PoliticCard> politicCards;
	
	public ABuyPermissionCard(Player p, PermissionCard permc, Council counc, List<PoliticCard> politc) {
		super(true);
		this.player = p;
		this.permCard = permc;
		this.council = counc;
		this.politicCards = politc;
	}
	
	@Override
	public void execute() throws IllegalActionException {
		
		int price = council.calculatePrice(politicCards);
		if(player.getCoins().getAmount() < price) {
			throw new IllegalActionException("you can not afford it!");
		}
		
		player.getCoins().decrease(price);
		player.getPermissionCard().add(permCard);
		permCard.getCardReward().assignBonusTo(player);
	}
}
