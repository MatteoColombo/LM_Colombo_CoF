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
		
		int unsatisfiedCouncilors = 0;
		int multicoloredCards = 0;
		
		for(PoliticCard card: politicCards) {
			if(card.isMultipleColor()) {
				politicCards.remove(card);
				multicoloredCards++;
			}
		}
		
		for(Color c: councilorColor) {
			if(politicCards.size() == 0) {
				unsatisfiedCouncilors++;
				continue;
			}
			
			boolean match = false;
			for(PoliticCard card: politicCards) {
				if(c.equals(card.getCardColor())) {
					politicCards.remove(card);
					match = true;
					break;
				}
			}
			
			if(!match) {
				unsatisfiedCouncilors++;
			}
		}
		
		if(politicCards.size() > 0) {
			throw new IllegalActionException("some cards are wrong, or you gave no one");

		}
		
		int price;
		if(unsatisfiedCouncilors == 0) {
			price = multicoloredCards;
		} else {
			price = multicoloredCards + unsatisfiedCouncilors * 3 + 1;
		}
		
		if(player.getCoins().getAmount() < price) {
			throw new IllegalActionException("you can not afford it!");
		}
		
		player.getCoins().decrease(price);
		player.getPermissionCard().add(permCard);
		permCard.getCardReward().assignBonusTo(player);
	}
}