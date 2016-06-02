package model.action;

import java.util.List;

import model.board.council.Council;
import model.exceptions.IllegalActionException;
import model.player.PermissionCard;
import model.player.Player;
import model.player.PoliticCard;

public class ABuyPermissionCard extends Action{

	private Player player;
	private PermissionCard permCard;
	private List<PoliticCard> politicCards;
	private int price;
	
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
		player.getCoins().decreaseAmount(price);
		player.getPermissionCard().add(permCard);
		permCard.getCardReward().assignBonusTo(player);
		
		// when the action perform, the card given by the used are destroyed
		List<PoliticCard> playerHand = player.getPoliticCard();
		for(PoliticCard used: politicCards) {
			playerHand.remove(used);
		}
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
