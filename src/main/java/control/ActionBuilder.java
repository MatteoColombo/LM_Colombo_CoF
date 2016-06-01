package control;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import model.action.*;
import model.board.Board;
import model.board.Region;
import model.board.council.Council;
import model.exceptions.IllegalActionException;
import model.player.PermissionCard;
import model.player.Player;

public class ActionBuilder {
	
	private Board board;
	
	/**
	 * factory for the ASlideCouncil Action
	 * @param p the player requesting the action
	 * @param cmd the cli arguments
	 * @return the new created action
	 * @throws ParseException
	 * @throws IllegalActionException
	 */
	public Action makeASlideCouncil(Player p, CommandLine cmd) throws ParseException, IllegalActionException {
		
		Council council = parseCouncil(cmd.getOptionValue("council"));
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
		
		Council council = parseCouncil(cmd.getOptionValue("council"));
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
	
	public Action makeABuyPermissionCard(Player p, CommandLine cmd) {
		return null;
	}
	
	private Council parseCouncil(String strCouncil) throws ParseException {
		if(strCouncil == null) {
			throw new ParseException("no council given");
		}
			
		if(strCouncil.equals("k")) {
			return board.getKingCouncil();
		}
		try {
			int regionNumber = Integer.parseInt(strCouncil);
			if(regionNumber > board.getRegionsNumber()) {
				throw new ParseException("illegal council");
			} else {
				return board.getRegionCouncil(regionNumber -1);
			}
		} catch(NumberFormatException e) {
			throw new ParseException("illegal council");
		}
	}
	
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
	
	private PermissionCard parsePermissionCard(Region r, String strPerm) throws ParseException {
		try {
			int cardNumber = Integer.parseInt(strPerm);
			if(cardNumber > r.getPermissionSlotsNumber()) {
				throw new ParseException("illegal permissionCard");
			}
			return r.getPermissionCard(cardNumber-1);
		} catch(NumberFormatException e) {
			throw new ParseException("illegal permissionCard");
		}
	}
}
