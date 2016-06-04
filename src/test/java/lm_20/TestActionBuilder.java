package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;

import org.apache.commons.cli.CommandLine;

import model.board.ColorConstants;
import model.player.Player;
import model.player.PoliticCard;

import org.junit.Before;
import org.junit.Test;

import control.ActionBuilder;
import control.CliParser;
import model.action.Action;
import model.board.Board;
/**
 * 
 * @author gianpaolobranca
 *
 */
public class TestActionBuilder {
	Board board;
	Player player;
	CliParser parser = new CliParser();
	
	@Before
	public void setUp() throws Exception {
		board = new Board("src/main/resources/map.xml", "src/main/resources/nobtrack.xml", 6, 4, ColorConstants.getCardsColors());
		player = new Player(10, 4, 0, 10, ColorConstants.getCardsColors(), 25, 3);
	}

	@Test
	public void testMakeABuildEmporium() throws Exception {
		ActionBuilder builder = new ActionBuilder(board);
		player.getPermissionCard().add(board.getRegion(0).givePermissionCard(0));
		String cityname = player.getPermissionCard().get(0).getCardCity().get(0).getName();
		String input = "emporium -city " + cityname + " -permission 1";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeABuildEmporium(player, parsed);
		a.execute();
		// an emporium has been created
		assertEquals(board.getMap().getCity(cityname).getNumberOfEmporium(), 1);
		// the card has been used
		assertEquals(true, player.getPermissionCard().get(0).getIfCardUsed());
	}
	
	@Test
	// this sometimes fail if cyan is not available in the pool
	public void testMakeASlideCouncil() throws Exception {
		ActionBuilder builder = new ActionBuilder(board);
		String input = "slide -council k -color cyan";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeASlideCouncil(player, parsed);
		a.execute();
		// the council is correctly shifted
		assertEquals(board.getKingCouncil().getCouncilorsColor().get(3), Color.CYAN);
		// the player got the reward
		assertEquals(player.getCoins().getAmount(), 14);
	}
	
	@Test
	public void testMakeABuildEmporiumWithKing() throws Exception {
		player.getPoliticCard().add(new PoliticCard(board.getKingCouncil().getCouncilorsColor().get(0)));
		player.getPoliticCard().add(new PoliticCard(board.getKingCouncil().getCouncilorsColor().get(1)));
		player.getPoliticCard().add(new PoliticCard(board.getKingCouncil().getCouncilorsColor().get(2)));
		ActionBuilder builder = new ActionBuilder(board);
		String input = "king -city Juvelar -cards 1 2 3";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeABuildEmporiumWithKing(player, parsed);
		a.execute();
		assertEquals(player.getCoins().getAmount(), 6);
		assertEquals(board.getMap().getCity("Juvelar").getNumberOfEmporium(), 1);
	}
	
	@Test
	public void testMakeAShufflePermissionCard() throws Exception {
		ActionBuilder builder = new ActionBuilder(board);
		String input = "shuffle -region 2";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeAShufflePermissionCards(player, parsed);
		// the execution is already working and tested
	}

}
