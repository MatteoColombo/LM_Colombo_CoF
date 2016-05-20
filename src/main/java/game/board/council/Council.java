package game.board.council;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import game.action.IllegalActionException;
import game.player.PoliticCard;


public class Council {
	private List<Councilor> councMembers;

	/**
	 * Initializes the council with a list of the inital councilors
	 * 
	 * @param councMember a list of Councilors
	 * 
	 */
	public Council(List<Councilor> councMembers) {
		this.councMembers = councMembers;
	}

	/**
	 * 
	 * Removes the first councilor in the council and adds the received one in tail
	 * @param councilor the one Councilor which has to be added in tail
	 * 
	 */
	public void insertCouncilor(Councilor councilor) {
		councMembers.remove(0);
		councMembers.add(councilor);
	}
	
	/**
	 * Returns the list of the colors of the councilors in the council
	 * @return a list of colors
	 */
	public List<Color> getCouncilorsColor(){
		List<Color> colorList= new ArrayList<>();
		for(Councilor temp: councMembers)
			colorList.add(temp.getColor());
		return colorList;
	}
	
	/**
	 * Returns the color of the councilor in the head of the queue
	 * @return color
	 */
	public Color getHeadColor(){
		return councMembers.get(0).getColor();
	}
	
	public int calculatePrice(List<PoliticCard> politicCards) throws IllegalActionException{
		List<Color> councilorColor = this.getCouncilorsColor();
		
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
		
		return calculatePrice(councilorColor.size(), satisfiedCouncilors, multicoloredCards);			
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
		int unsatisfiedCouncilors = councSize - satisfiedCouncilors - multicoloredCards;
		if(unsatisfiedCouncilors == 0) {
			price = multicoloredCards;
		} else {
			price = multicoloredCards + unsatisfiedCouncilors * 3 + 1;
		}
		
		return price;
	}
}
