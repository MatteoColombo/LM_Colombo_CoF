package game.board.council;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import game.exceptions.IllegalActionException;
import game.player.PoliticCard;

/**
 * The class of the council.
 * a council is composed by councilors and it works like a queue with fixed size
 * councils are used to buy permit cards or to build an emporium using the king
 * @author Matteo Colombo
 *
 */

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
	
	/**
	 * Returns the number of councilors not covered by the cards
	 * First it checks it there are more than 4 cards
	 * Then it compares the not-multiplecolored cards with the concilors 
	 * at last it counts the number of multiple colored cards
	 * @param cards the list of politic cards
	 * @return an integer, the number of uncovered councilors
	 * @throws IllegalActionException
	 */
	public int compareCardCouncil(List<PoliticCard> cards) throws IllegalActionException{
		List<Councilor> comparableCouncilors= new ArrayList<>();
		comparableCouncilors.addAll(this.councMembers);
		checkCardsNumber(cards);
		for(PoliticCard card: cards){
			boolean found=false;
			if(card.isMultipleColor())
				continue;
			for(int i=0;i <comparableCouncilors.size(); i++)
				if(comparableCouncilors.get(i).getColor().equals(card.getCardColor())){
					found=true;
					comparableCouncilors.remove(i);
					break;
				}
			if(!found)
				throw new IllegalActionException("Invalid card/cards");
		}
		for(PoliticCard card: cards){
			if(card.isMultipleColor()){
				comparableCouncilors.remove(0);
			}
		}		
			
		return comparableCouncilors.size();
	}
	
	
	private void checkCardsNumber(List<PoliticCard> cards) throws IllegalActionException{
		if(cards.size()>this.councMembers.size())
			throw new IllegalActionException("Too many cards");
	}
	
}
