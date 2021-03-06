package server.control;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import server.model.TurnManager;
import server.model.action.ABuildEmporium;
import server.model.action.ABuildEmporiumWithKing;
import server.model.action.ABuyAssistant;
import server.model.action.ABuyPermissionCard;
import server.model.action.AEndTurn;
import server.model.action.AExtraMainAction;
import server.model.action.AShufflePermissionCards;
import server.model.action.ASlideCouncil;
import server.model.action.ASlideCouncilWithAssistant;
import server.model.action.Action;
import server.model.action.IllegalActionException;
import server.model.board.Board;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.board.council.Council;
import server.model.configuration.Configuration;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;

/**
 * 
 * @author gianpaolobranca
 * Parse a string descriving an action into a real action Object <br>
 * The string must follow the following format: <br>
 * [Action] [Options...]
 * e.g: slide -council 1 -color pink
 *
 */
public class ActionBuilder {

	private Board board;
	private Configuration config;

	/**
	 * saves the Board and the configuration object
	 * @param board 
	 * @param config
	 */
	public ActionBuilder(Board board, Configuration config) {
		this.board = board;
		this.config = config;
	}
	/**
	 * end the turn
	 */
	public Action makeAEndTurn(Player player, TurnManager turnManager) throws IllegalActionException {
		return new AEndTurn(player, turnManager);
	}

	/**
	 * factory for the ABuildEmporium Action
	 * 
	 * @param p
	 *            the player requesting the action
	 * @param cmd
	 *            the cli arguments
	 * @return the new created action
	 * @throws IllegalActionException
	 * @throws IllegalActionException
	 */
	public Action makeABuildEmporium(Player p, CommandLine cmd) throws IllegalActionException {

		PermissionCard permCard = parsePermissionCard(p, cmd.getOptionValue(CliParser.OPTPERMISSION));
		City city = parseCity(cmd.getOptionValue(CliParser.OPTCITY));

		return new ABuildEmporium(p, permCard, city, board.getMap().getCitiesList(), board.getBoardRewardsManager());
	}

	/**
	 * factory for the ABuildEmporiumWithKing Action
	 * 
	 * @param p
	 *            the player requesting the action
	 * @param cmd
	 *            the cli arguments
	 * @return the new created action
	 * @throws IllegalActionException
	 * @throws IllegalActionException
	 */
	public Action makeABuildEmporiumWithKing(Player p, CommandLine cmd) throws IllegalActionException {

		City city = parseCity(cmd.getOptionValue(CliParser.OPTCITY));
		List<PoliticCard> politicCards = parsePoliticCards(p, cmd.getOptionValues(CliParser.OPTCARDS));
		return new ABuildEmporiumWithKing(p, board.getKing(), city, board.getMap().getCitiesList(), politicCards,
				board.getBoardRewardsManager());
	}

	/**
	 * factory for the ASlideCouncil Action
	 * 
	 * @param p
	 *            the player requesting the action
	 * @param cmd
	 *            the cli arguments
	 * @return the new created action
	 * @throws IllegalActionException
	 * @throws IllegalActionException
	 */
	public Action makeASlideCouncil(Player p, CommandLine cmd) throws IllegalActionException {

		Council council = parseCouncil(cmd.getOptionValue(CliParser.OPTCOUNCIL), true);
		Color color = parseColor(cmd.getOptionValue(CliParser.OPTCOLOR));

		return new ASlideCouncil(p, board.getCouncilorPool(), council, color);
	}

	/**
	 * factory for the ASlideCouncilWithAssistant Action
	 * 
	 * @param p
	 *            the player requesting the action
	 * @param cmd
	 *            the cli arguments
	 * @return the new created action
	 * @throws IllegalActionException
	 * @throws IllegalActionException
	 */
	public Action makeASlideCouncilWithAssistant(Player p, CommandLine cmd) throws IllegalActionException {

		Council council = parseCouncil(cmd.getOptionValue(CliParser.OPTCOUNCIL), true);
		Color color = parseColor(cmd.getOptionValue(CliParser.OPTCOLOR));

		return new ASlideCouncilWithAssistant(p, board.getCouncilorPool(), council, color);
	}

	/**
	 * factory for the ABuyAssistant Action
	 * 
	 * @param p
	 *            the player requesting the action
	 * @return the new created action
	 * @throws IllegalActionException
	 */
	public Action makeABuyAssistant(Player p) throws IllegalActionException {
		return new ABuyAssistant(p);
	}

	/**
	 * factory for the AExtraMainAction Action
	 * 
	 * @param p
	 *            the player requesting the action
	 * @return the new created action
	 * @throws IllegalActionException
	 */
	public Action makeAExtraMainAction(Player p) throws IllegalActionException {
		return new AExtraMainAction(p);
	}

	/**
	 * factory for the ABuyPermissionCard Action
	 * 
	 * @param p
	 *            the player requesting the action
	 * @param cmd
	 *            the cli arguments
	 * @return the new created action
	 * @throws IllegalActionException
	 */
	public Action makeABuyPermissionCard(Player p, CommandLine cmd) throws IllegalActionException {

		// because there is a 1 to 1 association with region and council
		String strRegion = cmd.getOptionValue(CliParser.OPTREGION);
		Region region = parseRegion(strRegion);

		Council council = parseCouncil(strRegion, false);
		PermissionCard permCard = parsePermissionCard(region, cmd.getOptionValue(CliParser.OPTPERMISSION));
		List<PoliticCard> politicCards = parsePoliticCards(p, cmd.getOptionValues(CliParser.OPTCARDS));
		int intSlot= Integer.parseInt(cmd.getOptionValue(CliParser.OPTPERMISSION))-1;
		return new ABuyPermissionCard(p, permCard, council, politicCards, region, intSlot);
	}

	/**
	 * factory for the AShufflePermissionCards Action
	 * 
	 * @param p
	 *            the player requesting the action
	 * @param cmd
	 *            the cli arguments
	 * @return the new created action
	 * @throws IllegalActionException
	 */
	public Action makeAShufflePermissionCards(Player p, CommandLine cmd) throws IllegalActionException {
		Region region = parseRegion(cmd.getOptionValue(CliParser.OPTREGION));
		return new AShufflePermissionCards(p, region);
	}

	/**
	 * parser for the council
	 * 
	 * @param strCouncil
	 *            the string description of the council wanted (k for king, a
	 *            positive integer for regions)
	 * @param kingAllowed
	 *            indicate if the king's council is a valid option or not
	 * @return the Council found
	 * @throws IllegalActionException
	 */
	private Council parseCouncil(String strCouncil, boolean kingAllowed) throws IllegalActionException {
		if (strCouncil == null) {
			throw new IllegalActionException("no council given");
		}

		if ("k".equals(strCouncil)) {
			if (kingAllowed) {
				return board.getKingCouncil();
			}
			throw new IllegalActionException("king is not a valid option!");
		}

		Region r = parseRegion(strCouncil);
		return r.getCouncil();
	}

	/**
	 * parser for the Color
	 * 
	 * @param strColor
	 *            the string description of the color e.g "white" or "blue"
	 * @return the color found in the councilor pool
	 * @throws IllegalActionException
	 *             if the color is invalid or is no one of the actually used in
	 *             the game
	 */
	private Color parseColor(String strColor) throws IllegalActionException {
		if (strColor == null) {
			throw new IllegalActionException("no color given");
		}
		List<Color> availableColor = board.getCouncilorPool().getListColor();
		Color color;
		color = config.getColorsTranslation().get(strColor);
		if (color == null) {
			throw new IllegalActionException("illegal color");
		}

		for (Color checked : availableColor) {
			if (color.equals(checked)) {
				return checked;
			}
		}
		// no color match
		throw new IllegalActionException("illegal color");
	}

	/**
	 * parser for the Permission card on the board
	 * 
	 * @param r
	 *            the region where to search
	 * @param strPerm
	 *            a string number index of the card
	 * @return the found Permission Card
	 * @throws IllegalActionException
	 *             if index out of bound or incorrect string
	 */
	private PermissionCard parsePermissionCard(Region r, String strPerm) throws IllegalActionException {
		if (strPerm == null) {
			throw new IllegalActionException("no permission given");
		}
		try {
			int cardNumber = Integer.parseUnsignedInt(strPerm);
			if (cardNumber > r.getPermissionSlotsNumber()) {
				throw new IllegalActionException("illegal permissionCard: index out of bound");
			}
			return r.getPermissionCard(cardNumber - 1);
		} catch (NumberFormatException e) {
			throw new IllegalActionException("illegal permissionCard, number expected");
		}
	}

	/**
	 * parser for the region
	 * 
	 * @param strRegion
	 * @return the region found
	 * @throws IllegalActionException
	 *             f
	 */
	private Region parseRegion(String strRegion) throws IllegalActionException {
		if (strRegion == null) {
			throw new IllegalActionException("no region given");
		}
		try {
			int regionNumber = Integer.parseUnsignedInt(strRegion);
			if (regionNumber > board.getRegionsNumber()) {
				throw new IllegalActionException("illegal region/council: too high number");
			}
			return board.getRegion(regionNumber - 1);

		} catch (NumberFormatException e) {
			throw new IllegalActionException("illegal region/council");
		}
	}

	/**
	 * parser for the cards option
	 * 
	 * @param p
	 *            the player
	 * @param strCards
	 * @return the cards picked from the player in a list
	 * @throws IllegalActionException
	 */
	private List<PoliticCard> parsePoliticCards(Player p, String[] strCards) throws IllegalActionException {
		if (strCards == null) {
			throw new IllegalActionException("no card given");
		}

		List<PoliticCard> cards = new ArrayList<>();
		for (String strCard : strCards) {
			try {
				int cardNumber = Integer.parseUnsignedInt(strCard);
				List<PoliticCard> playerHand = p.getPoliticCard();
				if (cardNumber > playerHand.size()) {
					throw new IllegalActionException("illegal cards: too high number");
				}
				cards.add(playerHand.get(cardNumber - 1));
			} catch (NumberFormatException e) {
				throw new IllegalActionException("illegal cards");
			}
		}

		return cards;
	}

	/**
	 * parser for the permission option
	 * 
	 * @param p
	 *            the player
	 * @param strPerm
	 *            the number of the permission in string format
	 * @return the permission found
	 * @throws IllegalActionException
	 */
	private PermissionCard parsePermissionCard(Player p, String strPerm) throws IllegalActionException {
		if (strPerm == null) {
			throw new IllegalActionException("no permission given");
		}

		try {
			int cardNumber = Integer.parseUnsignedInt(strPerm);
			if (cardNumber > p.getPermissionCard().size()) {
				throw new IllegalActionException("illegal permission: too high number");
			}
			return p.getPermissionCard().get(cardNumber - 1);

		} catch (NumberFormatException e) {
			throw new IllegalActionException("illegal permission");
		}
	}

	/**
	 * parser for the city. It search in the map for a cities with the same name
	 * 
	 * @param strCity
	 *            the city name
	 * @returnt the found city
	 * @throws IllegalActionException
	 */
	public City parseCity(String strCity) throws IllegalActionException {
		if (strCity == null) {
			throw new IllegalActionException("no city given");
		}
		City city = board.getMap().getCity(strCity);
		if (city == null) {
			throw new IllegalActionException("invalid city");
		}

		return city;
	}
}
