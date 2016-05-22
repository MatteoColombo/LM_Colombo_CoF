package game.action;

import java.util.List;

import game.board.council.Council;
import game.exceptions.IllegalActionException;
import game.player.PermissionCard;
import game.player.Player;
import game.player.PoliticCard;

public class ABuyPermissionCard extends Action{

	Player player;
	PermissionCard permCard;
	List<PoliticCard> politicCards;
	int price;
	
	public ABuyPermissionCard(Player p, PermissionCard permc, Council counc, List<PoliticCard> politic) throws IllegalActionException{
		super(true);
		this.politicCards = politic;
		this.player = p;
		this.permCard = permc;
		int difference= counc.compareCardCouncil(politicCards);
		price = calculatePrice(difference);
		price+=calculatePriceMultipleColoredCards();
		if(player.getCoins().getAmount() < price) {
			throw new IllegalActionException("you can not afford it!");
		}
		
	}
	
	@Override
	public void execute() {
		player.getCoins().decrease(price);
		player.getPermissionCard().add(permCard);
		permCard.getCardReward().assignBonusTo(player);
	}
	
	/**
	 * Calculate the money that can be paid instead of cards
	 * @param difference the number of missing cards
	 * @return an integer, the price
	 */
	private int calculatePrice(int difference){
		if(difference == 0) {
			return 0;
		}
		return difference*3 +1;
	}
	
	/**
	 * Calculate the extra money that have to be paid because of multiple colored cards
	 * @return an integer, the extra price
	 */
	private int calculatePriceMultipleColoredCards(){
		int multipCards=0;
		for(PoliticCard card: politicCards)
			if(card.isMultipleColor())
				multipCards++;
		return multipCards;
	}
}
