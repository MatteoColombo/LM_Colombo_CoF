package game.action;

import java.awt.Color;
import java.util.List;

import game.board.Council;
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
		List<Color> councilorColor = council.getCouncilorsColor();

		if(politicCards.size() > councilorColor.size()) {
			throw new IllegalActionException("you are using too many cards!");
		}
		
		int satisfiedCouncilors = 0;
		int multicoloredCards = filterColoredCard(politicCards);
		for(Color c: councilorColor) {
			if(politicCards.isEmpty()) {
				break;
			}
			
			boolean match = removeColoredCard(politicCards, c);
			if(match) {
				satisfiedCouncilors++;
			}
		}
		
		if(!politicCards.isEmpty()) {
			throw new IllegalActionException("some cards are wrong, or you gave no one");

		}
		
		int price = calculatePrice(councilorColor.size(), satisfiedCouncilors, multicoloredCards);	
		if(player.getCoins().getAmount() < price) {
			throw new IllegalActionException("you can not afford it!");
		}
		
		player.getCoins().decrease(price);
		player.getPermissionCard().add(permCard);
		permCard.getCardReward().assignBonusTo(player);
	}
	
	private int filterColoredCard(List<PoliticCard> lp) {
		int multicoloredCards = 0;
		
		for(PoliticCard card: lp) {
			if(card.isMultipleColor()) {
				lp.remove(card);
				multicoloredCards++;
			}
		} 
		return multicoloredCards;
	}
	
	private boolean removeColoredCard(List<PoliticCard> lp, Color c) {
		boolean match = false;
		for(PoliticCard card: lp) {
			if(c.equals(card.getCardColor())) {
				lp.remove(card);
				match = true;
				break;
			}
		}
		return match;
	}
	
	private int calculatePrice(int councSize, int satisfiedCouncilors, int multicoloredCards) {
		int price;
		int unsatisfiedCouncilors = councSize - satisfiedCouncilors;
		if(unsatisfiedCouncilors == 0) {
			price = multicoloredCards;
		} else {
			price = multicoloredCards + unsatisfiedCouncilors * 3 + 1;
		}
		
		return price;
	}
}
