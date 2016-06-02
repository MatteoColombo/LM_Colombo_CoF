package control;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import model.action.*;
import model.board.Board;
import model.board.Region;
import model.board.city.City;
import model.board.council.Council;
import model.exceptions.IllegalActionException;
import model.player.PermissionCard;
import model.player.Player;
import model.player.PoliticCard;
/**
 * 
 * @author gianpaolobranca
 *
 */
public class ActionBuilder {
	
	private Board board;
	
	public ActionBuilder(Board board) {
		this.board = board;
	}
	
	/**
	 * factory for the ABuildEmporium Action
	 * @param p the player requesting the action
	 * @param cmd the cli arguments
	 * @return the new created action
	 * @throws ParseException
	 * @throws IllegalActionException
	 */
	public Action makeABuildEmporium(Player p, CommandLine cmd) throws ParseException, IllegalActionException {
		
		PermissionCard permCard = parsePermissionCard(p, cmd.getOptionValue("permission"));
		City city = parseCity(cmd.getOptionValue("city"));
		
		return new ABuildEmporium(p, permCard, city, board.getMap().getCitiesList(), board.getBoardRewardsManager());
	}
	/**
	 * factory for the ABuildEmporiumWithKing Action
	 * @param p the player requesting the action
	 * @param cmd the cli arguments
	 * @return the new created action
	 * @throws ParseException
	 * @throws IllegalActionException
	 */
	public Action makeABuildEmporiumWithKing(Player p, CommandLine cmd) throws ParseException, IllegalActionException {
		
		City city = parseCity(cmd.getOptionValue("city"));
		List<PoliticCard> politicCards = parsePoliticCards(p, cmd.getOptionValues("cards"));

		return new ABuildEmporiumWithKing(p, board.getKing(), city, board.getMap().getCitiesList(), politicCards, board.getBoardRewardsManager());
	}
	/**
	 * factory for the ASlideCouncil Action
	 * @param p the player requesting the action
	 * @param cmd the cli arguments
	 * @return the new created action
	 * @throws ParseException
	 * @throws IllegalActionException
	 */
	public Action makeASlideCouncil(Player p, CommandLine cmd) throws ParseException, IllegalActionException {
		
		Council council = parseCouncil(cmd.getOptionValue("council"), true);
		Color color = parseColor(cmd.getOptionValue("color"));
		
		return new ASlideCouncil(p, board.getCouncilorPool(), council, color);
	}
	
	/**
	 * factory for the ASlideCouncilWithAssistant Action
	 * @param p the player requesting the action
	 * @param cmd the cli arguments
	 * @return the new created action
	 * @throws ParseException
	 * @throws IllegalActionException
	 */
	public Action makeASlideCouncilWithAssistant(Player p, CommandLine cmd) throws ParseException, IllegalActionException {
		
		Council council = parseCouncil(cmd.getOptionValue("council"), true);
		Color color = parseColor(cmd.getOptionValue("color"));
		
		return new ASlideCouncilWithAssistant(p, board.getCouncilorPool(), council, color);
	}
	
	/**
	 * factory for the ABuyAssistant Action
	 * @param p the player requesting the action
	 * @return the new created action
	 * @throws IllegalActionException
	 */
	public Action makeABuyAssistant(Player p) throws IllegalActionException {
		return new ABuyAssistant(p);
	}
	
	/**
	 * factory for the AExtraMainAction Action
	 * @param p the player requesting the action
	 * @return the new created action
	 * @throws IllegalActionException
	 */
	public Action makeAExtraMainAction(Player p) throws IllegalActionException {
		return new AExtraMainAction(p);
	}
	/**
	 * factory for the ABuyPermissionCard Action
	 * @param p the player requesting the action
	 * @param cmd the cli arguments
	 * @return the new created action
	 * @throws IllegalActionException
	 */
	public Action makeABuyPermissionCard(Player p, CommandLine cmd) throws ParseException, IllegalActionException {
		
		// because there is a 1 to 1 association with region and council
		String strRegion = cmd.getOptionValue("council");
		Region region = parseRegion(strRegion);

		Council council = parseCouncil(strRegion, false);
		PermissionCard permCard = parsePermissionCard(region, cmd.getOptionValue("permission"));	
		List<PoliticCard> politicCards = parsePoliticCards(p, cmd.getOptionValues("cards"));
		
		return new ABuyPermissionCard(p, permCard, council, politicCards);
	}
	/**
	 * factory for the AShufflePermissionCards Action
	 * @param p the player requesting the action
	 * @param cmd the cli arguments
	 * @return the new created action
	 * @throws ParseException
	 * @throws IllegalActionException
	 */
	public Action makeAShufflePermissionCards(Player p, CommandLine cmd) throws ParseException, IllegalActionException {
		Region region = parseRegion(cmd.getOptionValue("region"));
		return new AShufflePermissionCards(p, region);
	}
	
	/**
	 * parser for the council
	 * @param strCouncil the string description of the council wanted 
	 * (k for king, a positive integer for regions)
	 * @param kingAllowed indicate if the king's council is a valid option or not
	 * @return the Council found
	 * @throws ParseException
	 */
	private Council parseCouncil(String strCouncil, boolean kingAllowed) throws ParseException {
		if(strCouncil == null) {
			throw new ParseException("no council given");
		}
			
		if(strCouncil.equals("k")) {
			if(kingAllowed) {
				return board.getKingCouncil();
			}
			throw new ParseException("king is not a valid option!");
		}
		
		Region r = parseRegion(strCouncil);
		return r.getCouncil();
	}
	/**
	 * parser for the Color
	 * @param strColor the string description of the color e.g "white" or "blue"
	 * @return the color found in the councilor pool
	 * @throws ParseException if the color is invalid or is no one of the actually used in the game
	 */
	private Color parseColor(String strColor) throws ParseException {
		if(strColor == null) {
			throw new ParseException("no color given");
		}
		List<Color> availableColor = board.getCouncilorPool().getListColor();
		Color color;
		try {
		    Field field = Color.class.getField(strColor);
		    color = (Color)field.get(null);
		} catch (Exception e) {
			throw new ParseException("illegal color");
		}
		for(Color checked: availableColor) {
			if(color.equals(checked)) {
				return checked;
			}
		}
		// no color match
		throw new ParseException("illegal color");
	}
	/**
	 * parser for the Permission card on the board
	 * @param r the region where to search
	 * @param strPerm a string number index of the card
	 * @return the found Permission Card
	 * @throws ParseException if index out of bound or incorrect string
	 */
	private PermissionCard parsePermissionCard(Region r, String strPerm) throws ParseException {
		if(strPerm == null) {
			throw new ParseException("no permission given");
		}
		try {
			int cardNumber = Integer.parseUnsignedInt(strPerm);
			if(cardNumber > r.getPermissionSlotsNumber()) {
				throw new ParseException("illegal permissionCard");
			}
			return r.getPermissionCard(cardNumber-1);
		} catch(NumberFormatException e) {
			throw new ParseException("illegal permissionCard");
		}
	}
	/**
	 * parser for the region
	 * @param strRegion
	 * @return the region found
	 * @throws ParseException
	 */
	private Region parseRegion(String strRegion) throws ParseException {
		if(strRegion == null) {
			throw new ParseException("no region given");
		}
		try {
			int regionNumber = Integer.parseUnsignedInt(strRegion);
			if(regionNumber > board.getRegionsNumber()) {
				throw new ParseException("illegal region/council: too high number");
			}
			return board.getRegion(regionNumber-1);
			
		} catch(NumberFormatException e) {
			throw new ParseException("illegal region/council");
		}
	}
	/**
	 * parser for the cards option
	 * @param p the player
	 * @param strCards
	 * @return the cards picked from the player in a list
	 * @throws ParseException
	 */
	private List<PoliticCard> parsePoliticCards(Player p, String[] strCards) throws ParseException {
		if(strCards == null) {
			throw new ParseException("no card given");
		}
		
		List<PoliticCard> cards= new ArrayList<PoliticCard>();
		for(String strCard: strCards) {
			try {
				int cardNumber = Integer.parseUnsignedInt(strCard);
				List<PoliticCard> playerHand = p.getPoliticCard();
				if(cardNumber > playerHand.size()) {
					throw new ParseException("illegal cards: too high number");
				}
				cards.add(playerHand.get(cardNumber-1));
			} catch(NumberFormatException e) {
				throw new ParseException("illegal cards");
			}
		}
		
		return cards;
	}
	/**
	 * parser for the permission option
	 * @param p the player
	 * @param strPerm the number of the permission in string format
	 * @return the permission found
	 * @throws ParseException
	 */
	private PermissionCard parsePermissionCard(Player p, String strPerm) throws ParseException {
		if(strPerm == null) {
			throw new ParseException("no permission given");
		}
		
		try {
			int cardNumber = Integer.parseUnsignedInt(strPerm);
			if(cardNumber > p.getPermissionCard().size()) {
				throw new ParseException("illegal permission: too high number");
			}
			return p.getPermissionCard().get(cardNumber-1);
			
		} catch(NumberFormatException e) {
			throw new ParseException("illegal permission");
		}
	}
	/**
	 * parser for the city.
	 * It search in the map for a cities with the same name
	 * @param strCity the city name
	 * @returnt the found city
	 * @throws ParseException
	 */
	private City parseCity(String strCity) throws ParseException {
		if(strCity == null) {
			throw new ParseException("no city given");
		}
		
		City city = board.getMap().getCity(strCity);
		if(city == null) {
			throw new ParseException("invalid city");
		}
		
		return city;
	}
}
