package lm_20;

import static org.junit.Assert.*;

import org.apache.commons.cli.CommandLine;

import model.board.ColorConstants;
import model.player.Player;
import org.junit.Before;
import org.junit.Test;

import control.ActionBuilder;
import control.CliParser;
import model.action.Action;
import model.board.Board;

public class TestActionBuilder {
	Board board;
	Player player;
	CliParser parser = new CliParser();
	
	@Before
	public void setUp() throws Exception {
		board = new Board("src/main/resources/map.xml", "src/main/resources/nobtrack.xml", 6, 4, ColorConstants.getCardsColors());
		player = new Player(10, 4, 5, 10, ColorConstants.getCardsColors(), 25, 3);
		player.getPermissionCard().add(board.getRegion(0).givePermissionCard(0));
	}

	@Test
	public void testMakeABuildEmporium() throws Exception {
		String cityname = player.getPermissionCard().get(0).getCardCity().get(0).getName();
		String input = "emporium -city " + cityname + " -permission 1";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		ActionBuilder builder = new ActionBuilder(board);
		Action a = builder.makeABuildEmporium(player, parsed);
		a.execute();
		// an emporium has been created
		assertEquals(board.getMap().getCity(cityname).getNumberOfEmporium(), 1);
		// the card has been used
		assertEquals(true, player.getPermissionCard().get(0).getIfCardUsed());
	}

}
