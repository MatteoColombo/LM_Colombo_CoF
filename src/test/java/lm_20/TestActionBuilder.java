package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import server.control.ActionBuilder;
import server.control.CliParser;
import server.model.action.AEndTurn;
import server.model.action.Action;
import server.model.action.IllegalActionException;
import server.model.board.Board;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.player.Player;
import server.model.player.PoliticCard;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author gianpaolobranca
 *
 */
public class TestActionBuilder {
	Board board;
	Player player;
	CliParser parser = new CliParser();
	Configuration config;
	List<Color> colors;

	@Before
	public void setUp() throws Exception {
		config = new Configuration();
		board = new Board(config, 0);
		NobilityTrack track = new NobilityTrack(
				new NobilityLoader(new Configuration().getNobility()).getNobilityTrack());
		colors = new ArrayList<>();
		colors.add(Color.BLACK);
		colors.add(Color.WHITE);
		colors.add(Color.PINK);
		colors.add(Color.ORANGE);
		player = new Player(10, 4, 0, 10, colors, 25, 3, track, null);
	}

	@Test
	public void testMakeABuildEmporium() throws Exception {
		ActionBuilder builder = new ActionBuilder(board, config);
		player.getPermissionCard().add(board.getRegion(0).givePermissionCard(0));
		String cityname = player.getPermissionCard().get(0).getCardCity().get(0).getName();
		String input = "emporium -city " + cityname + " -card 1";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeABuildEmporium(player, parsed);
		try{
			a.execute();
			// an emporium has been created
			assertEquals(board.getMap().getCity(cityname).getNumberOfEmporium(), 1);
			// the card has been used
			assertEquals(true, player.getPermissionCard().get(0).getIfCardUsed());
		}catch(NullPointerException e){}
		
	}

	@Test
	public void testMakeASlideCouncil() throws Exception {
		ActionBuilder builder = new ActionBuilder(board, config);

		try {
			String input = "slide -council k -color blue";
			CommandLine parsed = parser.computeRequest(input.split(" "));
			Action a = builder.makeASlideCouncil(player, parsed);
			a.execute();
			assertEquals(board.getKingCouncil().getCouncilorsColor().get(3), config.getColorsTranslation().get("blue"));
			assertEquals(14, player.getCoins().getAmount());
		} catch (IllegalActionException e) {
			assertEquals("there are no more councilor available of the choosen color", e.getMessage());
			assertEquals(10, player.getCoins().getAmount());

		}

	}

	@Test
	public void testMakeASlideCouncilWithAssistant() throws Exception {
		ActionBuilder builder = new ActionBuilder(board, config);
		try {
			String input = "secondarySlide -council k -color blue";
			CommandLine parsed = parser.computeRequest(input.split(" "));
			Action a = builder.makeASlideCouncilWithAssistant(player, parsed);
			a.execute();
			assertEquals(3, player.getAssistants().getAmount());
		} catch (IllegalActionException e) {
			assertEquals("there are no more councilor available of the choosen color", e.getMessage());
			assertEquals(10, player.getCoins().getAmount());

		}
	}

	@Test
	public void testMakeABuildEmporiumWithKing() throws Exception {
		player.getPoliticCard().add(new PoliticCard(board.getKingCouncil().getCouncilorsColor().get(0)));
		player.getPoliticCard().add(new PoliticCard(board.getKingCouncil().getCouncilorsColor().get(1)));
		player.getPoliticCard().add(new PoliticCard(board.getKingCouncil().getCouncilorsColor().get(2)));
		ActionBuilder builder = new ActionBuilder(board, config);
		String input = "king -city Juvelar -politic 1 2 3";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeABuildEmporiumWithKing(player, parsed);
		a.execute();
		assertEquals(player.getCoins().getAmount(), 6);
		assertEquals(board.getMap().getCity("Juvelar").getNumberOfEmporium(), 1);
	}

	@Test
	public void testMakeAShufflePermissionCard() throws Exception {
		ActionBuilder builder = new ActionBuilder(board, config);
		String input = "shuffle -region 2";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeAShufflePermissionCards(player, parsed);
		a.execute();
		// the execution is already working and tested
	}

	@Test(expected = IllegalActionException.class)
	public void testEndTurn() throws IllegalActionException {
		ActionBuilder builder = new ActionBuilder(board, config);
		player.doMainAction();
		Action a = builder.makeAEndTurn(player, null);
		boolean ok = a instanceof AEndTurn;
		assertEquals(true, ok);
		player.increaseMainAction();
		a = builder.makeAEndTurn(player, null);
	}

	@Test(expected = IllegalActionException.class)
	public void testBuyAssistant() throws IllegalActionException {
		ActionBuilder builder = new ActionBuilder(board, config);
		Action a = builder.makeABuyAssistant(player);
		a.execute();
		assertEquals(7, player.getCoins().getAmount());
		assertEquals(5, player.getAssistants().getAmount());
		player.getCoins().decreaseAmount(5);
		a = builder.makeABuyAssistant(player);
	}

	@Test(expected = IllegalActionException.class)
	public void testBuyPermissionCard() throws IllegalActionException, ParseException {
		String input = "permission -region 1 -card 1 -politic 1";
		player.getCoins().increaseAmount(5);
		PoliticCard c;
		do {
			c = new PoliticCard(colors);
		} while (!c.isMultipleColor());
		player.getPoliticCard().add(c);
		ActionBuilder builder = new ActionBuilder(board, config);
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeABuyPermissionCard(player, parsed);
		a.execute();
		assertEquals(1, player.getPermissionCard().size());
		input = "permission -card 1 -politic 1";
		parsed = parser.computeRequest(input.split(" "));
		a = builder.makeABuyPermissionCard(player, parsed);
	}

	@Test
	public void testExtraMainAction() throws IllegalActionException {
		ActionBuilder builder = new ActionBuilder(board, config);
		Action a = builder.makeAExtraMainAction(player);
		a.execute();
		assertEquals(2, player.getMainActionsLeft());
	}
	
	@Test
	public void testActionBuilderFails(){
			ActionBuilder builder= new ActionBuilder(board, config);
			try{
				builder.parseCity(null);
			}catch(IllegalActionException e){
				assertEquals("no city given", e.getMessage());
			}
			try {
				builder.parseCity("lsjfaskas");
			} catch (IllegalActionException e) {
				assertEquals("invalid city", e.getMessage());
			}
	}
}
